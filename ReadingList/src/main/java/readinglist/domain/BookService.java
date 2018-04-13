package readinglist.domain;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import readinglist.database.Database;
import readinglist.database.ReadingListDao;

public class BookService {

    private Database database;
    private ReadingListDao rdd;

    public BookService() {
        database = new Database(new File("db", "readinglist.db"));
        rdd = new ReadingListDao(database);
    }

    public String saveBook(String name, String startpage, String endpage, String deadline) {

        String error = "";

        if (name.trim().equals("")) {
            error = "Nimi ei saa olla tyhjä.\n";
        }

        if (!startpage.trim().matches("\\d{1,4}")) {
            error = error.concat("Alkusivun pitää olla 1-4 numeroinen luku.\n");
        }

        if (!endpage.trim().matches("\\d{1,4}")) {
            error = error.concat("Loppusivun pitää olla 1-4 numeroinen luku.\n");
        }

        if (!deadline.trim().matches("\\d{1,2}.\\d{1,2}") && !deadline.trim().matches("\\d{1,2}.\\d{1,2}.\\d{2,4}")) {
            error = error.concat("Deadlinen pitää olla muodossa x.y \ntai x.y.xxxx.\n");

            if (deadline.trim().matches("\\d{1,2}.\\d{1,2}")) {
                deadline = deadline.concat("." + Calendar.getInstance().get(Calendar.YEAR));
            }
        }

        if (error.equals("")) {
            Book b = new Book(null, name, startpage + " - " + endpage, deadline);

            try {
                rdd.save(b);
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return error;
    }

    public void deleteBook(Book book) {
        try {
            rdd.delete(book.getId());
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBook(Book book) {
        try {
            rdd.update(book);
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateBookName(Book book, String name) {

        String error = "";

        if (name.matches("")) {
            error = "Nimi ei saa olla tyhjä.";
        } else {
            try {
                book.setName(name);
                rdd.update(book);
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
    }

    public String updateBookPages(Book book, String pages) {

        String error = "";

        if (pages.matches("\\d{1,4} - \\d{1,4}")) {
            try {
                book.setPages(pages);
                rdd.update(book);
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            error = "Sivujen täytyy olla muodossa x - y";
        }
        return error;
    }

    public String updateBookDeadline(Book book, String deadline) {

        String error = "";

        String date = deadline.trim();

        if (date.matches("\\d{1,2}.\\d{1,2}") | date.matches("\\d{1,2}.\\d{1,2}.\\d{2,4}")) {

            if (!(date.matches("\\d{1,2}.\\d{1,2}.\\d{2,4}"))) {
                date = date.concat("." + Calendar.getInstance().get(Calendar.YEAR));
            }

            try {
                book.setDeadline(date);
                rdd.update(book);
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BookService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return l;
    }

    public List<String> getPages() {
        List<String> l = new ArrayList<>();

        try {
            l = rdd.findAllPages();

        } catch (SQLException ex) {
            Logger.getLogger(BookService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return l;
    }

    public List<String> getDeadlines() {
        List<String> l = new ArrayList<>();

        try {
            l = rdd.findAllDeadline();

        } catch (SQLException ex) {
            Logger.getLogger(BookService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return l;
    }

    public List<Book> getBooks() {
        List<Book> l = new ArrayList<>();

        try {
            l = rdd.findAll();

        } catch (SQLException ex) {
            Logger.getLogger(BookService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return l;
    }

}