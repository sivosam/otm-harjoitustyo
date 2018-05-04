package readinglist.database;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import readinglist.domain.Book;

public class ReadingListDaoTest {

    Database db;
    ReadingListDao rdd;
    Book potter;

    @Before
    public void setUp() throws SQLException {
        db = new Database(true);
        rdd = new ReadingListDao(db);
        rdd.deleteAll();

        potter = new Book(null, "Harry Potter", "100 - 150", "12.12.2018");
        rdd.save(potter);

    }

    @Test
    public void findAllFindsAll() throws SQLException {
        assertEquals(1, rdd.findAll().size());
    }

    @Test
    public void deleteDeletes() throws SQLException {
        rdd.delete(1);
        assertEquals(0, rdd.findAll().size());
    }

    @Test
    public void findAllNamesFindsAllNames() throws SQLException {
        assertEquals("Harry Potter", rdd.findAllNames().get(0));

        rdd.save(new Book(null, "1984", "100 - 150", "12.11.2018"));
        assertEquals(2, rdd.findAllNames().size());
    }

    @Test
    public void FindAllPagesFindsAllPages() throws SQLException {
        assertEquals("100 - 150", rdd.findAllPages().get(0));

        rdd.save(new Book(null, "1984", "100 - 150", "12.11.2018"));
        assertEquals(2, rdd.findAllPages().size());
    }

    @Test
    public void findAllDeadlineFindsAllDeadlines() throws SQLException {
        assertEquals("12.12.2018", rdd.findAllDeadline().get(0));

        rdd.save(new Book(null, "1984", "100 - 150", "12.11.2018"));
        assertEquals(2, rdd.findAllDeadline().size());
    }

    @Test
    public void updateUpdatesBook() throws SQLException {
        Book b = rdd.findOne(1);
        b.setName("Valtio");
        rdd.update(b);

        assertEquals("Valtio", rdd.findOne(1).getName());
    }

    @Test
    public void findOneFindsOne() throws SQLException {
        assertEquals(potter.getName(), rdd.findOne(1).getName());
    }

    @Test
    public void findOneFindsNoneIfEmpty() throws SQLException {
        rdd.deleteAll();
        assertEquals(null, rdd.findOne(1));
    }

}
