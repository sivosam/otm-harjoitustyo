package readinglist.domain;

public class Book {

    private Integer id;
    private String name;
    private String pages;
    private String deadline;

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
    
}
