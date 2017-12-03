package tikape.runko.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer, String> {
    private Database database;
    private String tableName;
    
    public AnnosRaakaAineDao (Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }
   
    public void delete() {
        //ei toteutettu vielä
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public AnnosRaakaAine findOneByTwoKeys(Integer key1, Integer key2) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE annos_id = ? AND raaka_aine_id = ?");
            stmt.setInt(1, key1);
            stmt.setInt(2, key2);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return createFromRow(rs);
            } else {
                return null;
            }
        }
    }
    
   
    public List<AnnosRaakaAine> findAllByKey(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine "
                    + "WHERE annos_id = ? "
                    + "ORDER BY jarjestys");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            List<AnnosRaakaAine> ra = new ArrayList<>();
            
            while(rs.next()) {
                ra.add(new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raaka_aine_id"), rs.getInt("jarjestys"), rs.getDouble("maara"), rs.getString("yksikko")));
            }
            
            return ra;
        }
    }
    
    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AnnosRaakaAine> findAllInAlphabeticalOrder() throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnosRaakaAine findByName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public AnnosRaakaAine createFromRow(ResultSet rs) throws SQLException {
        return new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raaka_aine_id"), rs.getInt("jarjestys"), rs.getDouble("maara"), rs.getString("yksikko"));
    }

    @Override
    public AnnosRaakaAine save(AnnosRaakaAine object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            //todo: ensin pitäisi tarkistaa, löytyyko tämä rivi JO kannasta...
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (annos_id, raaka_aine_id, jarjestys, maara, yksikko) "
                    + "VALUES (?,?,?,?,?)");
            
            stmt.setInt(1, object.getAnnosId());
            stmt.setInt(2, object.getRaakaAineId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setDouble(4, object.getMaara());
            stmt.setString(5, object.getYksikko());
         
            
            stmt.executeUpdate();
            stmt.close();
            
            AnnosRaakaAine ar = findOneByTwoKeys(object.getAnnosId(), object.getRaakaAineId());
            return ar;
        }
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
}
