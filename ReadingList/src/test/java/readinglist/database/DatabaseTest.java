package readinglist.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    Database db;
    Connection conn;

    @Before
    public void setUp() throws SQLException {
        //Poistetaan mahdollinen testitietokanta, jotta metodia init() voidaan testata
        File file = new File("test.db");
        file.delete();
        //
        
        db = new Database(true);
        conn = db.getConnection();
        
    }

    @Test
    public void databaseIsInitialized() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book");
        ResultSet rs = stmt.executeQuery();

        ArrayList<String> l = new ArrayList<>();

        while (rs.next()) {
            String name = rs.getString("name");
            l.add(name);
        }
        System.out.println(l);

        assertEquals(4, l.size());
    }

}
