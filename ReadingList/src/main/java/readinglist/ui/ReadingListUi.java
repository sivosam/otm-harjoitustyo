package readinglist.ui;

import java.util.Calendar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import readinglist.domain.Book;
import readinglist.domain.BookService;

public class ReadingListUi extends Application {

    BookService bs = new BookService();
    ObservableList<Book> bookList = FXCollections.observableList(bs.getBooks());
    ObservableList<String> nameList = FXCollections.observableList(bs.getNames());
    ObservableList<String> pagesList = FXCollections.observableList(bs.getPages());
    ObservableList<String> deadlineList = FXCollections.observableList(bs.getDeadlines());

    ListView<String> nameListView = new ListView<>(nameList);
    ListView<String> pagesListView = new ListView<>(pagesList);
    ListView<String> deadlineListView = new ListView<>(deadlineList);

    VBox deleteButtons = new VBox();

    public static void main(String[] args) {
        launch(ReadingListUi.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        GridPane setting = new GridPane();

        VBox newBookBox = new VBox();

        BorderPane center = new BorderPane();
        HBox titlebar = new HBox();
        HBox readinglistBox = new HBox();
        ScrollPane scroll = new ScrollPane(readinglistBox);

        Label errorLabel = new Label("");
        errorLabel.getStyleClass().add("error");
        errorLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("Gray"), CornerRadii.EMPTY, Insets.EMPTY)));

        center.setCenter(scroll);
        center.setTop(titlebar);
        center.setPadding(new Insets(10));

        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        readinglistBox.setPrefHeight(500);

        newBookBox.setPadding(new Insets(10));
        newBookBox.setSpacing(30);

        deleteButtons.setPadding(new Insets(0, 0, 0, 3));
        deleteButtons.setMinWidth(33);

        setting.addColumn(0, newBookBox);
        setting.addColumn(1, center);

        //Sarake "Lukulista"
        Label readinglistTitle = createTitle("Lukulista");
        readinglistTitle.setPrefWidth(620);

        nameListView.setMinWidth(200);
        nameListView.setPrefWidth(620);

        nameListView.setEditable(true);

        nameListView.setCellFactory(TextFieldListCell.forListView());
        nameListView.setOnEditCommit((ListView.EditEvent<String> t) -> {

            int i = t.getIndex();

            Book b = (Book) bookList.get(i);

            String error = bs.updateBookName(b, t.getNewValue().trim());

            if (error.equals("")) {
                nameListView.getItems().set(i, t.getNewValue().trim());
                errorLabel.setText("");
            } else {
                errorLabel.setText(error);
            }

        });

        //Sarake "Sivut"
        Label pagesTitle = createTitle("Sivut");
        pagesTitle.setMinWidth(100);

        pagesListView.setMinWidth(100);
        pagesListView.setMaxWidth(100);

        pagesListView.setEditable(true);

        pagesListView.setCellFactory(TextFieldListCell.forListView());

        pagesListView.setOnEditCommit((ListView.EditEvent<String> t) -> {

            int i = t.getIndex();
            Book b = (Book) bookList.get(i);

            String error = bs.updateBookPages(b, t.getNewValue().trim());

            if (error.equals("")) {
                pagesListView.getItems().set(i, t.getNewValue().trim());
                errorLabel.setText("");
            } else {
                errorLabel.setText(error);
            }
        });

        //Sarake "Deadline"
        Label deadlineTitle = createTitle("Deadline");
        deadlineTitle.setMinWidth(140);

        deadlineListView.setPrefWidth(100);
        deadlineListView.setMinWidth(100);

        deadlineListView.setEditable(true);

        deadlineListView.setCellFactory(TextFieldListCell.forListView());

        deadlineListView.setOnEditCommit((ListView.EditEvent<String> t) -> {

            int i = t.getIndex();
            Book b = (Book) bookList.get(i);
            String date = t.getNewValue().trim();

            String error = bs.updateBookDeadline(b, date);

            if (error.equals("")) {
                if (date.matches("\\d{2,4}\\.\\d{2,4}")) {
                    date = date.concat("." + Calendar.getInstance().get(Calendar.YEAR));
                }
                deadlineListView.getItems().set(i, date);
                errorLabel.setText("");
            } else {
                errorLabel.setText(error);
            }

        });

        //Poistonapit
        bs.getBooks().forEach(b -> {
            deleteButtons.getChildren().add(createDeleteButton(b));
        });

        // Sarake "Uusi"
        Label addBookTitle = createTitle("Uusi");

        VBox uusiFields = new VBox();
        HBox pagesFields = new HBox();
        pagesFields.setSpacing(2);
        TextField namefield = new TextField();
        namefield.setPromptText("Nimi");

        TextField spField = new TextField();
        TextField epField = new TextField();
        Label pagesDash = new Label("-");
        pagesDash.setFont(new Font("Arial", 20));
        spField.setPromptText("Alkusivu");
        epField.setPromptText("Loppusivu");

        TextField dlField = new TextField();
        dlField.setPromptText("Deadline");

        Button addBookButton = new Button();
        addBookButton.setText("Lisää");

        addBookButton.setOnAction((e) -> {

            String error = bs.saveBook(namefield.getText(),
                    spField.getText(), epField.getText(),
                    dlField.getText());

            if (error.equals("")) {
                redrawListView();

                namefield.clear();
                spField.clear();
                epField.clear();
                dlField.clear();

                errorLabel.setText("");
            } else {
                errorLabel.setText(error);
            }
        });

        pagesFields.getChildren().addAll(spField, pagesDash, epField);
        uusiFields.setSpacing(10);
        uusiFields.getChildren().addAll(namefield, pagesFields, dlField, addBookButton);

        titlebar.getChildren().addAll(readinglistTitle, pagesTitle, deadlineTitle);
        newBookBox.getChildren().addAll(addBookTitle, uusiFields, errorLabel);
        readinglistBox.getChildren().addAll(nameListView, pagesListView, deadlineListView, deleteButtons);

        Scene scene = new Scene(setting);
        scene.getStylesheets().add("kuosi.css");

        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(1100);
        stage.setResizable(false);
        stage.setTitle("Lukulista");
        stage.show();

    }

    // Simppeli metodi toiston vähentämiseksi, joka luo ja palauttaa uuden otsikon. 
    public Label createTitle(String text) {
        Label l = new Label(text);
        l.setFont(new Font("Arial", 30));
        l.getStyleClass().add("title");
        l.setPadding(new Insets(5, 0, 0, 10));

        return l;
    }

    /**
     * Metodi lukulistan alkioiden poistonappien luomista varten.
     *
     * @param book teksti, jota varten nappi luodaan
     *
     * @return Palauttaa oikein sommitellun napin, jossa valmiina
     * toiminnallisuus poistoa varten.
     *
     */
    public Button createDeleteButton(Book book) {

        Button dbt = new Button("X");
        dbt.setMinHeight(25);
        dbt.setMaxHeight(25);

        dbt.setOnAction((e) -> {

            bs.deleteBook(book);
            redrawListView();

        });
        return dbt;
    }

    /**
     * Metodi lukulistan alkioiden päivittämistä varten.
     *
     */
    public void redrawListView() {
        nameListView.getItems().clear();
        pagesListView.getItems().clear();
        deadlineListView.getItems().clear();
        deleteButtons.getChildren().clear();
        bookList.clear();

        nameList = FXCollections.observableList(bs.getNames());
        pagesList = FXCollections.observableList(bs.getPages());
        deadlineList = FXCollections.observableList(bs.getDeadlines());
        bookList = FXCollections.observableList(bs.getBooks());

        nameList.forEach(n -> {
            nameListView.getItems().add(n);
        });

        pagesList.forEach(p -> {
            pagesListView.getItems().add(p);
        });

        deadlineList.forEach(d -> {
            deadlineListView.getItems().add(d);
        });

        bs.getBooks().forEach(b -> {
            deleteButtons.getChildren().add(createDeleteButton(b));
        });

    }

}
