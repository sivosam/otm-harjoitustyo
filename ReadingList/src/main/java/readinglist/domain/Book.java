package readinglist.domain;

import java.util.Objects;

/**
 * Yksittäistä kirjaa tai muuta tekstiä kuvaava luokka
 *
 *
 */
public class Book {

    private Integer id;
    private String name;
    private String pages;
    private String deadline;

    /**
     * Luokan konstruktori
     *
     * @param id Tietokannan käyttämä pääavain. Jos arvo on null, asettaa
     * tietokanta sen automaattisesti.
     * @param name Kirjan tai tekstin nimi
     * @param pages Vaadittu sivumäärä, muodossa xx - yy
     * @param deadline Päivämäärä, jolloin teksti tulee olla luettuna. Muodossa
     * pp.kk.vv/vvvv
     *
     */
    public Book(Integer id, String name, String pages, String deadline) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
