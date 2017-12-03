package tikape.runko.domain;

public class AnnosRaakaAine {
    private Integer annos_id;
    private Integer raaka_aine_id;
    private Integer jarjestys;
    private double maara;
    private String yksikko;
 
    public AnnosRaakaAine(Integer annos_id, Integer raaka_aine_id, Integer jarjestys, double maara, String yksikko) {
        this.annos_id = annos_id;
        this.raaka_aine_id = raaka_aine_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.yksikko = yksikko;
    }
    
    public Integer getAnnosId() {
        return this.annos_id;
    }
    
    public Integer getRaakaAineId() {
        return this.raaka_aine_id;
    }
    
    public Integer getJarjestys() {
        return this.jarjestys;
    }
    
    public double getMaara() {
        return this.maara;
    }
    
    public String getYksikko() {
        return this.yksikko;
    }
    
}
