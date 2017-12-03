package tikape.runko.domain;

public class RaakaAineWrapper {
    RaakaAine raakaAine;
    AnnosRaakaAine annosRaakaAine;
    
    public RaakaAineWrapper(RaakaAine raakaAine, AnnosRaakaAine annosRaakaAine) {
        this.raakaAine = raakaAine;
        this.annosRaakaAine = annosRaakaAine;
    }
    
    public RaakaAine getRaakaAine() {
        return this.raakaAine;
    }
    
    public AnnosRaakaAine getAnnosRaakaAine() {
        return this.annosRaakaAine;
    }
}
