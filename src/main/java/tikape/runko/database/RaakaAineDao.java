package tikape.runko.database;
import java.sql.Connection;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
