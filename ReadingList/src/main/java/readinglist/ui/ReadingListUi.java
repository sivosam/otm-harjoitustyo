package readinglist.ui;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import readinglist.database.Database;
import readinglist.database.ReadingListDao;
import readinglist.domain.Book;

public class ReadingListUi extends Application {

    public static void main(String[] args) {
        launch(ReadingListUi.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Database database = new Database();
        ReadingListDao rdd = new ReadingListDao(database);

        GridPane setting = new GridPane();

        VBox left = new VBox();
        VBox center = new VBox();
        VBox right = new VBox();
        VBox deadline = new VBox();

        left.setPadding(new Insets(10));
        center.setPadding(new Insets(10));
        right.setPadding(new Insets(10));
        deadline.setPadding(new Insets(10));

        left.setSpacing(30);
        center.setSpacing(30);
        right.setSpacing(30);
        deadline.setSpacing(30);

        setting.addColumn(0, left);
        setting.addColumn(1, center);
        setting.addColumn(2, right);
        setting.addColumn(3, deadline);
        
        
        setting.setGridLinesVisible(true);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(12.5);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(12.5);

        setting.getColumnConstraints().addAll(c1, c2, c3, c4);

        //Sarake "Lukulista"
        Label lukulistaTitle = new Label("Lukulista");
        lukulistaTitle.setFont(new Font("Arial", 30));
        lukulistaTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox readingList = new VBox();
        readingList.setSpacing(10);

        rdd.findAll().stream().forEach(b -> {
            Label l = new Label(b.getName());
            l.setFont(new Font("Arial", 20));

//            Button bt = new Button(b.getName());
//            bt.setFont(new Font("Arial", 20));
//
//            bt.setOnAction((e) -> {
//
//            });
//
            readingList.getChildren().add(l);
        });

        //Sarake "Sivut"
        Label luettuTitle = new Label("Sivut");
        luettuTitle.setFont(new Font("Arial", 30));
        luettuTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox sivutList = new VBox();
        sivutList.setSpacing(10);

        rdd.findAll().stream().forEach(b -> {
            Label l = new Label(Integer.toString(b.getStartPage()) + " - " + Integer.toString(b.getEndPage()));
            l.setFont(new Font("Arial", 20));
            sivutList.getChildren().add(l);
        });

        //Sarake "Deadline"
        Label deadlineTitle = new Label("Deadline");
        deadlineTitle.setFont(new Font("Arial", 30));
        deadlineTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox dlList = new VBox();
        dlList.setSpacing(10);

        rdd.findAll().stream().forEach(b -> {
            Label l = new Label(b.getDeadline());
            l.setFont(new Font("Arial", 20));
            dlList.getChildren().add(l);
        });

        // Sarake "Uusi"
        Label uusiTitle = new Label("Uusi");
        uusiTitle.setFont(new Font("Arial", 30));
        uusiTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox uusiFields = new VBox();
        HBox pagesFields = new HBox();

        Label name = new Label("Nimi:");
        TextField namefield = new TextField();

        Label pages = new Label("Sivut:");
        TextField spField = new TextField();
        TextField epField = new TextField();
        Label pgd = new Label("-");
        pgd.setFont(new Font("Arial", 20));

        Label dl = new Label("Deadline:");
        TextField dlField = new TextField();

        Button uusiButton = new Button();
        uusiButton.setText("Lisää");

        uusiButton.setOnAction((e) -> {

            Book b = new Book(null, namefield.getText(),
                    Integer.parseInt(spField.getText()),
                    Integer.parseInt(epField.getText()),
                    dlField.getText(),
                    Integer.parseInt(spField.getText()));

            try {
                rdd.save(b);
            } catch (SQLException ex) {
                Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);
            }

            Label l = new Label(b.getName());
            l.setFont(new Font("Arial", 20));
            readingList.getChildren().add(l);

            Label p = new Label(spField.getText() + " - " + epField.getText());
            p.setFont(new Font("Arial", 20));
            sivutList.getChildren().add(p);

            Label d = new Label(dlField.getText());
            d.setFont(new Font("Arial", 20));
            dlList.getChildren().add(d);

        });

        pagesFields.getChildren().addAll(spField, pgd, epField);
        uusiFields.setSpacing(10);
        uusiFields.getChildren().addAll(name, namefield, pages, pagesFields, dl, dlField, uusiButton);

        //
        left.getChildren().addAll(uusiTitle, uusiFields);
        center.getChildren().addAll(lukulistaTitle, readingList);
        right.getChildren().addAll(luettuTitle, sivutList);
        deadline.getChildren().addAll(deadlineTitle, dlList);

        Scene scene = new Scene(setting);

//        stage.setMinHeight(600);
//        stage.setMinWidth(1200);
        stage.setScene(scene);
        stage.setTitle("Lukulista");
        stage.show();

    }

}
