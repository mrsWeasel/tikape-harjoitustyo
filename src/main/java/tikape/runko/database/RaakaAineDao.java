package tikape.runko.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;

public class RaakaAineDao extends AbstractNamedObjectDao<RaakaAine> {

    public RaakaAineDao(Database database, String tableName) {
        super(database, tableName);
    }
    
    public RaakaAine createFromRow(ResultSet rs) throws SQLException {
        return new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    public List<RaakaAine> findAllByKey(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine "
                    + "INNER JOIN AnnosRaakaAine ON RaakaAine.id = AnnosRaakaAine.raaka_aine_id "
                    + "WHERE annos_id = ? "
                    + "ORDER BY jarjestys");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            List<RaakaAine> r = new ArrayList<>();
            
            while(rs.next()) {
                r.add(new RaakaAine(rs.getInt("id"), rs.getString("nimi")));
            }
            
            return r;
        }
    }
}
