package readinglist.database;

import java.sql.*;
import java.util.List;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    void delete(K key) throws SQLException;
    
    T saveOrUpdate(T object) throws SQLException;
}

