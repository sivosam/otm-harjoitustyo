package readinglist.domain;

import java.io.File;
import java.sql.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookServiceTest {

    BookService bs;
    Book b1;
    Book b2;

    @Before
    public void setUp() throws SQLException {
        bs = new BookService(true);
        bs.deleteAllBooks();
        bs.saveBook("Potter", "123", "321", "12.12.12");
        bs.saveBook("1984", "1", "55", "11.11.11");
        b1 = bs.getBooks().get(0);
        b2 = bs.getBooks().get(1);
    }

    @Test
    public void saveBookSavesBook() {
        assertEquals(bs.getBooks().size(), 2);
        bs.saveBook("Test", "1", "2", "1.1.18");
        assertEquals(bs.getBooks().size(), 3);
    }

    @Test
    public void deleteBookDeletesCorrectBook() {
        bs.deleteBook(b1);
        assertEquals(bs.getBooks().size(), 1);
        assertEquals(b2.getName(), "1984");
    }

    @Test
    public void updateBookNameUpdatesCorrectBook() {
        bs.updateBookName(b1, "Nalle Puh");
        assertEquals("Nalle Puh", b1.getName());
        assertFalse("Nalle Puh".equals(b2.getName()));
    }

    @Test
    public void updateBookPagesUpdatesCorrectBook() {
        bs.updateBookPages(b1, "15 - 25");
        assertEquals("15 - 25", b1.getPages());
        assertFalse("15 - 25".equals(b2.getPages()));
    }

    @Test
    public void updateBookDeadlineUpdatesCorrectBook() {
        bs.updateBookDeadline(b1, "1.2.18");
        assertEquals("1.2.18", b1.getDeadline());
        assertFalse("1.2.18".equals(b2.getDeadline()));
    }

    @Test
    public void saveBookReturnsCorrectErrors() {
        //Nimi-virhe
        assertEquals(" Nimi ei saa olla tyhjä.\n ", bs.saveBook("", "123", "321", "12.12.12"));

        //Alkusivu-virhe
        assertEquals(" Alkusivun täytyy olla 1-4 numeroinen luku.\n ", bs.saveBook("Potter", "", "321", "12.12.12"));

        //Loppusivu-virhe
        assertEquals(" Loppusivun täytyy olla 1-4 numeroinen luku.\n ", bs.saveBook("Potter", "123", "11111", "12.12.12"));

        //Deadline-virhe
        assertEquals(" Deadlinen täytyy olla muodossa x.y \n tai x.y.xxxx.\n ", bs.saveBook("Potter", "123", "321", "123"));
    }

    @Test
    public void deadlineAlwaysHasYear() {
        bs.saveBook("Potter", "123", "321", "12.12");
        assertEquals("12.12.2018", bs.getBooks().get(2).getDeadline());

        bs.updateBookDeadline(bs.getBooks().get(2), "15.12");
        assertEquals("15.12.2018", bs.getBooks().get(2).getDeadline());
    }

    @Test
    public void updateBookReturnsCorrectErrors() {
        //Nimi-virhe
        assertEquals(" Nimi ei saa olla tyhjä. ", bs.updateBookName(b1, ""));

        //Sivu-virhe
        assertEquals(" Sivujen täytyy olla muodossa x - y ", bs.updateBookPages(b1, "123"));

        //Deadline-virhe
        assertEquals(" Deadlinen täytyy olla muodossa x.y \n tai x.y.xx tai x.y.xxxx ", bs.updateBookDeadline(b1, "12:15:18"));
    }

}
