package readinglist.domain;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import readinglist.database.Database;
import readinglist.database.ReadingListDao;
import readinglist.ui.ReadingListUi;

public class BookService {

    private Database database;
    private ObservableList<Book> bookList;
    private ObservableList<String> nameList;
    private ObservableList<String> pagesList;
    private ObservableList<String> deadlineList;
    private ReadingListDao rdd;
    private ListView<String> nameListView;
    private ListView<String> pagesListView;
    private ListView<String> deadlineListView;
    private VBox deleteButtons;

    public BookService() throws SQLException {
        
        database = new Database();
        rdd = new ReadingListDao(database);
        deleteButtons = new VBox();
        
        //Testaamista varten
        
        deleteButtons.getChildren().clear();
        for (int s = 0; s < 5; s++) {
            rdd.save(new Book(null, "" + s, "" + s + s, "" + s + s + s));
        }
        
        //^

        bookList = FXCollections.observableList(rdd.findAll());
        nameList = FXCollections.observableList(rdd.findAllNames());
        pagesList = FXCollections.observableList(rdd.findAllPages());
        deadlineList = FXCollections.observableList(rdd.findAllDeadline());
        nameListView = new ListView(nameList);
        pagesListView = new ListView(pagesList);
        deadlineListView = new ListView(deadlineList);
        
        

        //NAMES ListView
        nameListView.setMinWidth(200);
        nameListView.setPrefWidth(520);

        nameListView.setEditable(true);

        nameListView.setCellFactory(TextFieldListCell.forListView());
        nameListView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                nameListView.getItems().set(t.getIndex(), t.getNewValue());

                int i = t.getIndex();

                Book b = (Book) bookList.get(i);

                try {
                    b.setName(t.getNewValue());
                    rdd.update(b);
                } catch (SQLException ex) {
                    Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        });

        //PAGES ListView
        pagesListView.setMinWidth(100);
        pagesListView.setMaxWidth(100);

        pagesListView.setEditable(true);

        pagesListView.setCellFactory(TextFieldListCell.forListView());

        pagesListView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                pagesListView.getItems().set(t.getIndex(), t.getNewValue());

                int i = t.getIndex();

                Book b = (Book) bookList.get(i);

                try {
                    b.setPages(t.getNewValue());
                    rdd.update(b);
                } catch (SQLException ex) {
                    Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //DEADLINE ListView
        deadlineListView.setPrefWidth(150);
        deadlineListView.setMinWidth(100);

        deadlineListView.setEditable(true);

        deadlineListView.setCellFactory(TextFieldListCell.forListView());

        deadlineListView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                deadlineListView.getItems().set(t.getIndex(), t.getNewValue());

                int i = t.getIndex();

                Book b = (Book) bookList.get(i);

                try {
                    b.setDeadline(t.getNewValue());
                    rdd.update(b);
                } catch (SQLException ex) {
                    Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        });

        //Poistonapit
        rdd.findAll().forEach(b -> {
            deleteButtons.getChildren().add(createDeleteButton(b));
        });

    }

    public Button createDeleteButton(Book book) {

        Button dbt = new Button("X");
        dbt.setMinHeight(25);
        dbt.setMaxHeight(25);
        
        dbt.setOnAction((e) -> {

            try {
                rdd.delete(book.getId());
                redrawListView();
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return dbt;
    }
    
    public void redrawListView() throws SQLException {
        nameListView.getItems().clear();
        pagesListView.getItems().clear();
        deadlineListView.getItems().clear();
        deleteButtons.getChildren().clear();
        
        nameList = FXCollections.observableList(rdd.findAllNames());
        pagesList = FXCollections.observableList(rdd.findAllPages());
        deadlineList = FXCollections.observableList(rdd.findAllDeadline());
        
        nameList.forEach(n -> {
            nameListView.getItems().add(n);
        });
        
        pagesList.forEach(p -> {
            pagesListView.getItems().add(p);
        });
        
        deadlineList.forEach(d -> {
            deadlineListView.getItems().add(d);
        });
        
        rdd.findAll().forEach(b -> {
            deleteButtons.getChildren().add(createDeleteButton(b));
        });
        
    }

    public ListView<String> getNameListView() {
        return nameListView;
    }

    public ListView<String> getPagesListView() {
        return pagesListView;
    }

    public ListView<String> getDeadlineListView() {
        return deadlineListView;
    }

    public VBox getDeleteButtons() {
        return deleteButtons;
    }

}
