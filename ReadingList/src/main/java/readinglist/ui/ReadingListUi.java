package readinglist.ui;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import readinglist.database.Database;
import readinglist.database.ReadingListDao;
import readinglist.domain.Book;
import readinglist.domain.BookService;

public class ReadingListUi extends Application {

    /* ONGELMAT:
    
    1.  Sivujen muokkaaminen: pitää jotenkin napata kirjan id, jotta voidaan db päivittää. DONE
    2.  Kirjojen lisääminen ja poistaminen: lista päivittyy vasta kun ohjelma käynnistetään uudelleen. Joku refresh tarvitaan tms. poistaminen DONE
    3.  Muokkaamisen sommittelu, ehkä kokonaan pois ja poistonapit vaan listan viereen – nimet ja sivut jne. voi muokata suoraan listasta DONE
    
     */
    public static void main(String[] args) {
        launch(ReadingListUi.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Database database = new Database();
        ReadingListDao rdd = new ReadingListDao(database);
        
        BookService bs = new BookService();

        GridPane setting = new GridPane();

        VBox uusiBox = new VBox();

        BorderPane center = new BorderPane();
        HBox titlebar = new HBox();
        HBox readinglistBox = new HBox();
        VBox deleteButtons = new VBox();

        readinglistBox.setPrefHeight(400);

        uusiBox.setPadding(new Insets(10));
        center.setPadding(new Insets(10));

        deleteButtons.setMinWidth(27);
        deleteButtons.setMaxWidth(27);

        uusiBox.setSpacing(30);

        setting.addColumn(0, uusiBox);
        setting.addColumn(1, center);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(75);

        setting.getColumnConstraints().addAll(c1, c2);

        //Sarake "Lukulista"
        Label lukulistaTitle = new Label("Lukulista");
        lukulistaTitle.setFont(new Font("Arial", 30));
        lukulistaTitle.getStyleClass().add("title");
        lukulistaTitle.setPadding(new Insets(5, 0, 0, 10));
        lukulistaTitle.setPrefWidth(500);

        //Sarake "Sivut"
        Label sivutTitle = new Label("Sivut");
        sivutTitle.setFont(new Font("Arial", 30));
        sivutTitle.getStyleClass().add("title");
        sivutTitle.setPadding(new Insets(5, 0, 0, 10));
        sivutTitle.setPrefWidth(200);
        sivutTitle.setMinWidth(100);

        //Sarake "Deadline"
        Label deadlineTitle = new Label("Deadline");
        deadlineTitle.setFont(new Font("Arial", 30));
        deadlineTitle.getStyleClass().add("title");
        deadlineTitle.setPadding(new Insets(5, 0, 0, 10));
        deadlineTitle.setPrefWidth(200);
        deadlineTitle.setMinWidth(100);

        // Sarake "Uusi"
        Label uusiTitle = new Label("Uusi");
        uusiTitle.setFont(new Font("Arial", 30));
        uusiTitle.getStyleClass().add("title");
        uusiTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox uusiFields = new VBox();
        HBox pagesFields = new HBox();
        pagesFields.setSpacing(2);

        Label name = new Label("Nimi:");
        TextField namefield = new TextField();
        namefield.setPromptText("Nimi");

        Label pages = new Label("Sivut:");
        TextField spField = new TextField();
        TextField epField = new TextField();
        Label pgd = new Label("-");
        pgd.setFont(new Font("Arial", 20));
        spField.setPromptText("Alkusivu");
        epField.setPromptText("Loppusivu");
        epField.focusedProperty();

        Label dl = new Label("Deadline:");
        TextField dlField = new TextField();
        dlField.setPromptText("Deadline");

        Button uusiButton = new Button();
        uusiButton.setText("Lisää");

        uusiButton.setOnAction((e) -> {

            Book b = new Book(null, namefield.getText(),
                    spField.getText() + " - " + epField.getText(),
                    dlField.getText());

            try {
                rdd.save(b);
                
//                nameList.add(b.getName());
//                nameListView.refresh();
//                pagesList.add(b.getPages());
//                pagesListView.refresh();
//                deadlineList.add(b.getDeadline());
//                deadlineListView.refresh();
                
            } catch (SQLException ex) {
                Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        pagesFields.getChildren().addAll(spField, pgd, epField);
        uusiFields.setSpacing(10);
        uusiFields.getChildren().addAll(name, namefield, pages, pagesFields, dl, dlField, uusiButton);



        //
        titlebar.getChildren().addAll(lukulistaTitle, sivutTitle, deadlineTitle);

        uusiBox.getChildren().addAll(uusiTitle, uusiFields);
        center.setTop(titlebar);
        
        readinglistBox.getChildren().addAll(bs.getNameListView(), bs.getPagesListView(), bs.getDeadlineListView(), bs.getDeleteButtons());

        ScrollPane scroll = new ScrollPane(readinglistBox);
        scroll.setFitToWidth(true);

        center.setCenter(scroll);

        Scene scene = new Scene(setting);
        scene.getStylesheets().add("kuosi.css");

        stage.setScene(scene);
        stage.setWidth(1100);
        stage.setTitle("Lukulista");
        stage.show();

    }

}
