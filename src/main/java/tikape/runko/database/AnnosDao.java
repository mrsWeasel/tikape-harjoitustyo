package tikape.runko.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tikape.runko.domain.Annos;

public class AnnosDao extends AbstractNamedObjectDao<Annos> {

    public AnnosDao(Database database, String tableName) {
        super(database, tableName);
    }
    
    public Annos createFromRow(ResultSet rs) throws SQLException {
        return new Annos(rs.getInt("id"), rs.getString("nimi"));
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
