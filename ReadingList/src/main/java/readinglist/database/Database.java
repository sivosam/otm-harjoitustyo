package readinglist.database;

import java.io.File;
import java.sql.*;

public class Database {

    public static Connection getConnection() throws SQLException {
        File file1 = new File("db", "readinglist.db");
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file1.getAbsolutePath());
        
        return conn;
    }

    
}
