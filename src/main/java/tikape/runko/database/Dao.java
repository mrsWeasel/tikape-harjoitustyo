package tikape.runko.database;

import java.sql.*;
import java.util.*;

public interface Dao<T, K, S> {

    T findOne(K key) throws SQLException;
    
    T findByName(S name) throws SQLException;

    List<T> findAll() throws SQLException;
    
    List<T> findAllInAlphabeticalOrder() throws SQLException;
    
    T save(T object) throws SQLException;

    void delete(K key) throws SQLException;
}
