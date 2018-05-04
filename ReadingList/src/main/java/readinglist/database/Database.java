package readinglist.database;

import java.io.File;
import java.sql.*;

/**
 * Luokka, jonka avulla otetaan yhteys tietokantatiedostoon.
 *
 */
public class Database {

    private static Boolean test = false;
    private static Boolean hasData = false;
    private static Connection conn;

    /**
     * Konstruktorin ollessa tyhjä käytetään oletustietokantatiedostoa.
     *
     */
    public Database() {
    }

    /**
     * Parametrillinen konstruktori testaamista varten.
     *
     * @param test Arvon ollessa true metodi getConnection() ottaa yhteyden
     * testitietokantaan normaalin sijaan.
     */
    public Database(Boolean test) {
        this.test = test;
    }

    /**
     * Metodi, jonka avulla voidaan luoda yhteys tietokantatiedostoon.
     * Automaattisesti käyttää konstruktorissa määriteltyä tiedostoa tai
     * oletustiedostoa konstruktorin ollessa parametriton.
     *
     * @return Palauttaa Connection-olion, jolla on yhteys tietokantatiedostoon.
     *
     */
    public static Connection getConnection() throws SQLException {

        if (test) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            init();

        } else {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
            conn = DriverManager.getConnection("jdbc:sqlite:readinglist.db");

            init();
        }

        return conn;
    }

    /**
     * Metodi, joka lisää muutaman esimerkkitekstin lukulistalle.
     *
     * Metodi on toteutettu siten, että esimerkkitekstit lisätään vain, jos
     * tietokantataulua "Book" ei ole olemassa. Tällöin metodi luo taulun ja
     * lisää esimerkkitekstit. Tulevilla käynnistyskerroilla tekstejä ei siis
     * lisätä, ellei tietokantatiedostoa poisteta.
     *
     */
    private static void init() throws SQLException {

        if (!hasData) {

            hasData = true;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");

            if (!rs.next()) {

                PreparedStatement stmt2 = conn.prepareStatement("CREATE TABLE Book"
                        + "(id integer PRIMARY KEY, name varchar(255),"
                        + " pages varchar(20),"
                        + " deadline varchar(10))");
                stmt2.execute();

                //Esimerkkidataa
                PreparedStatement prep = conn.prepareStatement("INSERT INTO Book"
                        + " (name, pages, deadline)"
                        + " VALUES (?, ?, ?)");

                prep.setString(1, "John M. Taurek 1977. Should the Numbers Count?");
                prep.setString(2, "293 - 316");
                prep.setString(3, "5.5.2018");
                prep.executeUpdate();

                prep.setString(1, "Jeff McMahan 2006. The Ethics of Killing in War");
                prep.setString(2, "23 - 41");
                prep.setString(3, "15.5.2018");
                prep.executeUpdate();

                prep.setString(1, "Harry Potter and the Prisoner of Azkaban");
                prep.setString(2, "1 - 317");
                prep.setString(3, "25.10.2018");
                prep.executeUpdate();

                prep.setString(1, "The Blah Story, Volume 1");
                prep.setString(2, "550 - 728");
                prep.setString(3, "15.3.2019");
                prep.executeUpdate();

                stmt.close();
                prep.close();
                stmt2.close();
            }

            stmt.close();

        }

    }

}
