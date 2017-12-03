package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.domain.AbstractNamedObject;

public abstract class AbstractNamedObjectDao<T extends AbstractNamedObject>
        implements Dao<T, Integer, String> {

    protected Database database;
    protected String tableName;

    public AbstractNamedObjectDao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    
    public T findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return createFromRow(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " + tableName + " with id " + key);
            e.printStackTrace();
            return null;
        }
    }
    
    
    public T findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE nimi = ?");
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    System.out.println("huuu");
                    return createFromRow(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " + tableName + " with name " + name);
            e.printStackTrace();
            return null;
        }
    }

   
    public List<T> findAll() throws SQLException {
        List<T> objectList = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM " + tableName).executeQuery()) {

            while (result.next()) {
                objectList.add(createFromRow(result));
            }
        }

        return objectList;
    }
    
    
    public List<T> findAllInAlphabeticalOrder() throws SQLException {
        List<T> objectList = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM " + tableName + " ORDER BY nimi").executeQuery()) {

            while (result.next()) {
                objectList.add(createFromRow(result));
            }
        }

        return objectList;
    }
    
    
    public T save(T object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
            
        }
        
        return findByName(object.getNimi());
    }

    public abstract T createFromRow(ResultSet resultSet) throws SQLException;

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    }
    

}