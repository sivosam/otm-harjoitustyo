package readinglist.domain;

public class Book {

    private Integer id;
    private String name;
    private Integer startPage;
    private Integer endPage;
    private String deadline;
    private Integer currentPage;

    public Book(Integer id, String name, Integer startPage, Integer endPage, String deadline, Integer currentPage) {
        this.id = id;
        this.name = name;
        this.startPage = startPage;
        this.endPage = endPage;
        this.deadline = deadline;
        this.currentPage = currentPage;
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

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    
    

    
}
