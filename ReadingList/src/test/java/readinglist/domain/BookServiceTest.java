package readinglist.domain;


import java.sql.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookServiceTest {

    public BookServiceTest() {
    }

    BookService bs;
    Book b;

    @Before
    public void setUp() throws SQLException {
        bs = new BookService("test.db");
        bs.deleteAllBooks();
        bs.saveBook("Potter", "123", "321", "12.12.12");
        b = bs.getBooks().get(0);
    }

    @Test
    public void saveBookSavesBook() {
        assertEquals(bs.getBooks().size(), 1);
    }
    
    @Test
    public void deleteBookDeletesBook() {
        bs.deleteBook(b);
        assertEquals(bs.getBooks().size(), 0);
    }
    
    @Test
    public void updateBookUpdatesBook() {
        Book b = new Book(1, "Harry Potter", "123 - 321", "12.12.12");
        bs.updateBook(b);
        assertEquals("Harry Potter", b.getName());   
    }
    
    @Test
    public void updateBookNameUpdatesBookName() {
        bs.updateBookName(b, "Nalle Puh");
        assertEquals("Nalle Puh", b.getName());
    }
    
    @Test
    public void updateBookPagesUpdatesBookPages() {
        bs.updateBookPages(b, "15 - 25");
        assertEquals("15 - 25", b.getPages());
    }
    
    @Test
    public void updateBookDeadlineUpdatesBookDeadline() {
        bs.updateBookDeadline(b, "1.2.18");
        assertEquals("1.2.18", b.getDeadline());
    }
    
    @Test
    public void saveBookReturnsCorrectErrors() {
        //Nimi-virhe
        assertEquals("Nimi ei saa olla tyhjä.\n", bs.saveBook("", "123", "321", "12.12.12"));
        
        //Alkusivu-virhe
        assertEquals("Alkusivun täytyy olla 1-4 numeroinen luku.\n", bs.saveBook("Potter", "", "321", "12.12.12"));
        
        //Loppusivu-virhe
        assertEquals("Loppusivun täytyy olla 1-4 numeroinen luku.\n", bs.saveBook("Potter", "123", "11111", "12.12.12"));
        
        //Deadline-virhe
        assertEquals("Deadlinen täytyy olla muodossa x.y \ntai x.y.xxxx.\n", bs.saveBook("Potter", "123", "321", "123"));
    }
    
}
