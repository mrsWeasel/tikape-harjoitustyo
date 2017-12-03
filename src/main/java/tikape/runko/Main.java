package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.domain.Annos;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAineWrapper;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.AnnosRaakaAineDao;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Spark.staticFileLocation("/public");
        Database database = new Database("jdbc:sqlite:smoothiet.db");
        //database.init();

        AnnosDao annosDao = new AnnosDao(database, "Annos");
        RaakaAineDao raakaAineDao = new RaakaAineDao(database, "RaakaAine");
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database, "AnnosRaakaAine");
        //System.out.println(raakaAineDao.findOne(1).getNimi());
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAllInAlphabeticalOrder());
            map.put("raakaaineet", raakaAineDao.findAllInAlphabeticalOrder());
            
            
            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        post("/annokset", (req, res) -> {
            // ei sallita kahden saman nimisen annoksen tallentamista
            if (annosDao.findByName(req.queryParams("nimi")) != null) {
                res.redirect("/annokset?virhe=nimikaytossa");
                return "";
            } else {
                // tallennetaan annos
                Annos a = new Annos(null, req.queryParams("nimi"));
                Annos b = annosDao.save(a);
                
                // reseptiin kuuluvat raaka-aineet
                // haetaan koko lomakedata, koska kenttien määrä ei ole vakio
                Set<String> params = req.queryParams();
                
                for (String param : params) {
                    //System.out.println(param);
                    if (param.contains("ra_nimi_")) {
                        // parsitaan raaka-aineen id name-attribuutista
                        Integer id = Integer.parseInt(param.substring(8, param.indexOf("[")));
                        // raaka-aineiden järjestys
                        Integer jarjestys = Integer.parseInt(param.substring((param.indexOf("[") + 1), (param.indexOf("]"))));
                        // raaka-aineiden määrä + yksikkö
                        Double maara = Double.parseDouble(req.queryParams("ra_maara_" + id));
                        String yksikko = req.queryParams("ra_yksikko_" + id);
                        
                        annosRaakaAineDao.save(new AnnosRaakaAine(b.getId(), id, jarjestys, maara, yksikko));
                        
                    }
                }
                
                res.redirect("/annokset/" + b.getId() + "?lisatty=" + req.queryParams("nimi"));
                return "";
            }
            
        });
        
        post("/raaka-aineet", (req, res) -> {
            // ei sallita kahden saman nimisen raaka-aineen tallentamista
            if (raakaAineDao.findByName(req.queryParams("nimi")) != null) {
                res.redirect("/raaka-aineet?virhe=nimikaytossa");
                return "";
            } else {
                RaakaAine a = new RaakaAine(null, req.queryParams("nimi"));
                RaakaAine b = raakaAineDao.save(a);
                res.redirect("/raaka-aineet?lisatty=" + req.queryParams("nimi"));
                return "";
            }
            
        });
        
        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));
            
            List<RaakaAine> ra = raakaAineDao.findAllByKey(Integer.parseInt(req.params("id")));
            List<RaakaAineWrapper> raakaAineet = new ArrayList<>();
            
            for (RaakaAine r : ra) {
                raakaAineet.add(new RaakaAineWrapper(r, annosRaakaAineDao.findOneByTwoKeys(Integer.parseInt(req.params("id")), r.getId())));
            }
            
            map.put("raakaaineet", raakaAineet);
            
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        post("/poista/:id", (req, res) -> {
            if (annosDao.findOne(Integer.parseInt(req.params("id"))) != null) {
                annosRaakaAineDao.delete(Integer.parseInt(req.params("id")));
                annosDao.delete(Integer.parseInt(req.params("id")));
            }
            
            res.redirect("/annokset");
            return "";
        });

        get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAllInAlphabeticalOrder());

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());

    }
}
