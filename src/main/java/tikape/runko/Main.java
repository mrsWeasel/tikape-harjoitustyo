package tikape.runko;

import java.util.HashMap;
import java.util.Set;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.domain.Annos;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.AnnosRaakaAine;
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
                Annos a = new Annos(null, req.queryParams("nimi"));
                Annos b = annosDao.save(a);
                // todo: tallenna annosraakaaine-tauluun ainekset
                
                Set<String> params = req.queryParams();
                System.out.println(req.queryParams());
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
            System.out.println(System.getProperty("user.dir"));
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());

        get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAllInAlphabeticalOrder());

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());

        get("/raaka-aineet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raaka-aine", raakaAineDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "raaka-aine");
        }, new ThymeleafTemplateEngine());
    }
}
