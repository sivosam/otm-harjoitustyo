package readinglist.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import readinglist.domain.Book;

/**
 * Luokka tietokannan kanssa keskusteluun. Konstruktori vaatii parametrina
 * sopivan Database-olion.
 *
 */
public class ReadingListDao implements Dao<Book, Integer> {

    private Database db;

    /**
     * Luokan konstruktori
     *
     * @param database Database-olio, jonka kautta yhdistetään itse
     * tietokantatiedostoon
     *
     */
    public ReadingListDao(Database database) {
        this.db = database;
    }

    @Override
    public Book findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String pages = rs.getString("pages");
        String deadline = rs.getString("deadline");

        Book b = new Book(id, name, pages, deadline);

        rs.close();
        stmt.close();
        conn.close();

        return b;
    }

    @Override
    public List<Book> findAll() throws SQLException {
        Connection conn = db.getConnection();
        List<Book> list = new ArrayList<>();

        ResultSet rs = conn.prepareStatement("SELECT * FROM Book").executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String pages = rs.getString("pages");
            String deadline = rs.getString("deadline");

            list.add(new Book(id, name, pages, deadline));
        }

        rs.close();
        conn.close();

        return list;
    }

    public List<String> findAllDeadline() throws SQLException {
        Connection conn = db.getConnection();
        List<String> list = new ArrayList<>();

        ResultSet rs = conn.prepareStatement("SELECT deadline FROM Book").executeQuery();

        while (rs.next()) {
            String dl = rs.getString("deadline");
            list.add(dl);
        }

        rs.close();
        conn.close();

        return list;
    }

    public List<String> findAllPages() throws SQLException {
        Connection conn = db.getConnection();
        List<String> list = new ArrayList<>();

        ResultSet rs = conn.prepareStatement("SELECT pages FROM Book").executeQuery();

        while (rs.next()) {
            String sd = rs.getString("pages");
            list.add(sd);
        }

        rs.close();
        conn.close();

        return list;

    }

    public List<String> findAllNames() throws SQLException {
        Connection conn = db.getConnection();
        List<String> list = new ArrayList<>();

        ResultSet rs = conn.prepareStatement("SELECT name FROM Book").executeQuery();

        while (rs.next()) {
            String sd = rs.getString("name");
            list.add(sd);
        }

        rs.close();
        conn.close();

        return list;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book WHERE id = ?");
        stmt.setObject(1, key);

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void save(Book book) throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Book"
                + " (name, pages, deadline)"
                + " VALUES (?, ?, ?)");

        stmt.setString(1, book.getName());
        stmt.setString(2, book.getPages());
        stmt.setString(3, book.getDeadline());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void update(Book book) throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET"
                + " name = ?, pages = ?, deadline = ? WHERE id = ?");

        stmt.setString(1, book.getName());
        stmt.setString(2, book.getPages());
        stmt.setString(3, book.getDeadline());
        stmt.setInt(4, book.getId());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    //Testaamista varten
    public void deleteAll() throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book");
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
