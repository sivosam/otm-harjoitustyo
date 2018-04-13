package readinglist.ui;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import readinglist.database.Database;
import readinglist.database.ReadingListDao;
import readinglist.domain.Book;
import readinglist.domain.BookService;

public class ReadingListUi extends Application {

    /* TODO:
    
    1.  Lisää -sarakkeen refaktorointi
    2.  Otsikoiden ankkuroiminen
    3.  Otsikoiden fontit uusiks

    
     */
    public static void main(String[] args) {
        launch(ReadingListUi.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BookService bs = new BookService();

        GridPane setting = new GridPane();

        VBox newBookBox = new VBox();

        BorderPane center = new BorderPane();
        HBox titlebar = new HBox();
        HBox readinglistBox = new HBox();
        ScrollPane scroll = new ScrollPane(readinglistBox);

        center.setCenter(scroll);
        center.setTop(titlebar);
        center.setPadding(new Insets(10));

        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        readinglistBox.setPrefHeight(500);

        newBookBox.setPadding(new Insets(10));
        newBookBox.setSpacing(30);

        setting.addColumn(0, newBookBox);
        setting.addColumn(1, center);

        //Sarake "Lukulista"
        Label readinglistTitle = createTitle("Lukulista");
        readinglistTitle.setPrefWidth(620);

        //Sarake "Sivut"
        Label pagesTitle = createTitle("Sivut");
        pagesTitle.setMinWidth(100);

        //Sarake "Deadline"
        Label deadlineTitle = createTitle("Deadline");
        deadlineTitle.setMinWidth(140);

        // Sarake "Uusi"
        Label addBookTitle = createTitle("Uusi");

        VBox uusiFields = new VBox();
        HBox pagesFields = new HBox();
        pagesFields.setSpacing(2);
        TextField namefield = new TextField();
        namefield.setPromptText("Nimi");

        TextField spField = new TextField();
        TextField epField = new TextField();
        Label pgd = new Label("-");
        pgd.setFont(new Font("Arial", 20));
        spField.setPromptText("Alkusivu");
        epField.setPromptText("Loppusivu");

        TextField dlField = new TextField();
        dlField.setPromptText("Deadline");

        Button addBookButton = new Button();
        addBookButton.setText("Lisää");
        Label errorLabel = new Label("");

        addBookButton.setOnAction((e) -> {

//            Book b = new Book(null, namefield.getText(),
//                    spField.getText() + " - " + epField.getText(),
//                    dlField.getText());
            try {
                String error = bs.saveBook(namefield.getText(),
                        spField.getText(), epField.getText(),
                        dlField.getText());

                if (error.equals("")) {
                    bs.redrawListView();

                    namefield.clear();
                    spField.clear();
                    epField.clear();
                    dlField.clear();

                    errorLabel.setText("");
                } else {
                    errorLabel.setText(error);
                    errorLabel.getStyleClass().add("error");
                    errorLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("Gray"), CornerRadii.EMPTY, Insets.EMPTY)));
                }

            } catch (SQLException ex) {
                Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        pagesFields.getChildren().addAll(spField, pgd, epField);
        uusiFields.setSpacing(10);
        uusiFields.getChildren().addAll(namefield, pagesFields, dlField, addBookButton);

        //
        titlebar.getChildren().addAll(readinglistTitle, pagesTitle, deadlineTitle);
        newBookBox.getChildren().addAll(addBookTitle, uusiFields, errorLabel);
        readinglistBox.getChildren().addAll(bs.getNameListView(), bs.getPagesListView(), bs.getDeadlineListView(), bs.getDeleteButtons());

        Scene scene = new Scene(setting);
        scene.getStylesheets().add("kuosi.css");

        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(1100);
        stage.setResizable(false);
        stage.setTitle("Lukulista");
        stage.show();

    }

    public Label createTitle(String text) {
        Label l = new Label(text);
        l.setFont(new Font("Arial", 30));
        l.getStyleClass().add("title");
        l.setPadding(new Insets(5, 0, 0, 10));

        return l;
    }

}
