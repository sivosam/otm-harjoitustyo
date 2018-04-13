package readinglist.database;

import java.io.File;
import java.sql.*;

public class Database {

    static File file1;
    
    public Database(File file) {
        file1 = file;
    }
    
    public static Connection getConnection() throws SQLException {
//        File file1 = new File("db", "readinglist.db");
//        if (db == 2) {
//            file1 = new File("db", "test.db");
//        }
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file1.getAbsolutePath());
        
        return conn;
    }

    
}
