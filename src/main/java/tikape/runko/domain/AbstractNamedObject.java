package tikape.runko.domain;

public abstract class AbstractNamedObject {

    protected Integer id;
    protected String nimi;
    
    public AbstractNamedObject(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }

}