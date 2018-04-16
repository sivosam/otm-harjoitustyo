package readinglist.domain;


import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void equalWhenSameId() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        Book b2 = new Book(1, "Potter", "15 - 22", "12.12.12");

        assertTrue(b1.equals(b2));
    }

    @Test
    public void notEqualWhenSameId() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        Book b2 = new Book(2, "Potter", "15 - 22", "12.12.12");

        assertFalse(b1.equals(b2));
    }

    @Test
    public void notEqualWhenDifferentObjects() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        Object o = new Object();

        assertFalse(b1.equals(o));
    }
    
    @Test
    public void toStringPrintsCorrectly() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        assertEquals("Potter", b1.toString());
    }
    
    @Test
    public void notEqualWhenNull() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        assertFalse(b1.equals(null));
    }
    
    @Test
    public void equalWhenSame() {
        Book b1 = new Book(1, "Potter", "15 - 22", "12.12.12");
        assertTrue(b1.equals(b1));
    }
    
}
