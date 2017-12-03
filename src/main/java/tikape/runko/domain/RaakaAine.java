package tikape.runko.domain;

public class RaakaAine extends AbstractNamedObject {
    
    public RaakaAine (Integer id, String nimi) {
        super(id, nimi);
    }
    
    public String toString() {
        return "Raaka-aineen nimi: " + this.nimi + ", id: " + this.id;
    }
}