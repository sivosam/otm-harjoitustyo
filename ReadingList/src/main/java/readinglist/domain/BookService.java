package readinglist.domain;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import readinglist.database.Database;
import readinglist.database.ReadingListDao;

/**
 * Luokka lukulistan sovelluslogiikalle. Tarjoaa toiminnallisuuden mm. tekstien
 * lisäämiseen ja muokkaamiseen. Luokka tallentaa muutokset konstruktorissa
 * määriteltyyn tietokantaan luokkaa ReadingListDao käyttäen.
 *
 */
public class BookService {

    private Database database;
    private ReadingListDao rdd;

    /**
     * Konstruktori voidaan suorittaa parametrittomana, jolloin se asettaa
     * tietokannaksi readinglist.db.
     *
     */
    public BookService() {
        database = new Database();
        rdd = new ReadingListDao(database);
    }

    /**
     * Tämän parametrillisen konstruktorin avulla voidaan asettaa haluttu
     * tietokanta.
     *
     * @param file polku haluttuun tietokantatiedostoon
     *
     */
    public BookService(String file) {
        database = new Database(new File("db", file));
        rdd = new ReadingListDao(database);
    }

    /**
     * Metodi uuden tekstin tallentamista varten.
     *
     * @param name Tekstin nimi
     * @param startpage Teksin aloitussivu
     * @param endpage Tekstin viimeinen sivu
     * @param deadline Päivämäärä, jolloin tekstin tulee olla luettuna
     *
     * @return Tyhjän String:in, jos teksti tallennettiin onnistuneesti. Muutoin
     * palauttaa virheviestin String:inä.
     *
     */
    public String saveBook(String name, String startpage, String endpage, String deadline) {

        String error = "";

        if (name.trim().equals("")) {
            error = "Nimi ei saa olla tyhjä.\n";
        }

        if (!startpage.trim().matches("\\d{1,4}")) {
            error = error.concat("Alkusivun täytyy olla 1-4 numeroinen luku.\n");
        }

        if (!endpage.trim().matches("\\d{1,4}")) {
            error = error.concat("Loppusivun täytyy olla 1-4 numeroinen luku.\n");
        }

        if (!deadline.trim().matches("\\d{1,2}\\.\\d{1,2}") && !deadline.trim().matches("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}")) {
            error = error.concat("Deadlinen täytyy olla muodossa x.y \ntai x.y.xxxx.\n");
        }

        if (deadline.trim().matches("\\d{1,2}\\.\\d{1,2}")) {
            deadline = deadline.concat("." + Calendar.getInstance().get(Calendar.YEAR));
        }

        if (error.equals("")) {
            Book b = new Book(null, name, startpage + " - " + endpage, deadline);

            try {
                rdd.save(b);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        return error;
    }

    /**
     * Metodi tekstin poistamista varten.
     *
     * @param book Poistettava kirja
     */
    public void deleteBook(Book book) {
        try {
            rdd.delete(book.getId());
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

//    public void updateBook(Book book) {
//        try {
//            rdd.update(book);
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//    }
    /**
     * Metodi tekstin nimen muokkaamista varten.
     *
     * @param book Muokattava teksti
     * @param name Uusi nimi
     *
     * @return Tyhjän String:in, jos nimi muokattiin onnistuneesti. Muutoin
     * palauttaa virheviestin String:inä.
     *
     */
    public String updateBookName(Book book, String name) {

        String error = "";

        if (name.matches("")) {
            error = "Nimi ei saa olla tyhjä.";
        } else {
            try {
                book.setName(name);
                rdd.update(book);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return error;
    }

    /**
     * Metodi tekstin sivumäärän muokkaamista varten.
     *
     * @param book Muokattava teksti
     * @param pages Uusi sivumäärä muodossa xx - yy
     *
     * @return Tyhjän String:in, jos sivumäärä muokattiin onnistuneesti. Muutoin
     * palauttaa virheviestin String:inä.
     *
     */
    public String updateBookPages(Book book, String pages) {

        String error = "";

        if (pages.matches("\\d{1,4} - \\d{1,4}")) {
            try {
                book.setPages(pages);
                rdd.update(book);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } else {
            error = "Sivujen täytyy olla muodossa x - y";
        }
        return error;
    }

    /**
     * Metodi tekstin deadlinen muokkaamista varten.
     *
     * @param book Muokattava teksti
     * @param deadline Uusi deadline
     *
     * @return Tyhjän String:in, jos deadline muokattiin onnistuneesti. Muutoin
     * palauttaa virheviestin String:inä.
     *
     */
    public String updateBookDeadline(Book book, String deadline) {

        String error = "";

        if (deadline.matches("\\d{1,2}\\.\\d{1,2}") || deadline.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}")) {

            if (!(deadline.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}"))) {
                deadline = deadline.concat("." + Calendar.getInstance().get(Calendar.YEAR));
            }

            try {
                book.setDeadline(deadline);
                rdd.update(book);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } else {
            error = "Deadlinen täytyy olla muodossa x.y \ntai x.y.xx tai x.y.xxxx";
        }

        return error;
    }

    public List<String> getNames() {
        List<String> l = new ArrayList<>();

        try {
            l = rdd.findAllNames();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return l;
    }

    public List<String> getPages() {
        List<String> l = new ArrayList<>();

        try {
            l = rdd.findAllPages();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return l;
    }

    public List<String> getDeadlines() {
        List<String> l = new ArrayList<>();

        try {
            l = rdd.findAllDeadline();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return l;
    }

    public List<Book> getBooks() {
        List<Book> l = new ArrayList<>();

        try {
            l = rdd.findAll();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return l;
    }

    //Testaamista varten
    public void deleteAllBooks() {
        try {
            rdd.deleteAll();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
