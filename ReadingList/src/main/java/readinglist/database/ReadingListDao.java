package readinglist.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import readinglist.domain.Book;

public class ReadingListDao implements Dao<Book, Integer> {

    private Database db;

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
        Integer startPage = rs.getInt("startPage");
        Integer endPage = rs.getInt("endPage");
        String deadline = rs.getString("deadline");
        Integer currentPage = rs.getInt("currentPage");

        Book b = new Book(id, name, startPage, endPage, deadline, currentPage);

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
            Integer startPage = rs.getInt("startPage");
            Integer endPage = rs.getInt("endPage");
            String deadline = rs.getString("deadline");
            Integer currentPage = rs.getInt("currentPage");
            list.add(new Book(id, name, startPage, endPage, deadline, currentPage));
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
    
    public List<Integer> findAllStartPage() throws SQLException {
        Connection conn = db.getConnection();
        List<Integer> list = new ArrayList<>();
        
        ResultSet rs = conn.prepareStatement("SELECT startpage FROM Book").executeQuery();
        
         while (rs.next()) {
            int sd = rs.getInt("startpage");
            list.add(sd);
        }
        
        rs.close();
        conn.close();
        
        return list;
        
    }
    
    public List<Integer> findAllEndPage() throws SQLException {
        Connection conn = db.getConnection();
        List<Integer> list = new ArrayList<>();
        
        ResultSet rs = conn.prepareStatement("SELECT endpage FROM Book").executeQuery();
        
         while (rs.next()) {
            int sd = rs.getInt("endpage");
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

    @Override
    public Book saveOrUpdate(Book object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void save(Book book) throws SQLException {
        Connection conn = db.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Book"
                + " (name, startPage, endPage, deadline, currentPage)"
                + " VALUES (?, ?, ?, ?, ?)");
        
        stmt.setString(1, book.getName());
        stmt.setInt(2, book.getStartPage());
        stmt.setInt(3, book.getEndPage());
        stmt.setString(4, book.getDeadline());
        stmt.setInt(5, book.getCurrentPage());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();   
    }
    
    public void update(Book book) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateCurrentPage(Book book, Integer page) throws SQLException {
        Connection conn = db.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET"
                + " startpage = ? WHERE id = ?");
        
        stmt.setInt(1, page);
        stmt.setInt(2, book.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

}
