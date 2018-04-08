package readinglist.ui;


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

public class ReadingListUi extends Application {

    public static void main(String[] args) {
        launch(ReadingListUi.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Database database = new Database();
        ReadingListDao rdd = new ReadingListDao(database);

        GridPane setting = new GridPane();

        VBox uusiBox = new VBox();
        VBox muokkaaBox = new VBox();

        BorderPane center = new BorderPane();
        HBox titlebar = new HBox();
        HBox readinglistBox = new HBox();

        readinglistBox.setPrefHeight(300);
        muokkaaBox.setPrefHeight(100);

        uusiBox.setPadding(new Insets(10));
        center.setPadding(new Insets(10));

        uusiBox.setSpacing(30);

        setting.addColumn(0, uusiBox);
        setting.addColumn(1, center);
        setting.addRow(1, muokkaaBox);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(75);

        setting.getColumnConstraints().addAll(c1, c2);

        //Sarake "Lukulista"
        Label lukulistaTitle = new Label("Lukulista");
        lukulistaTitle.setFont(new Font("Arial", 30));
        lukulistaTitle.setPadding(new Insets(5, 0, 0, 10));
        lukulistaTitle.setPrefWidth(500);

        VBox nimiList = new VBox();
        nimiList.setSpacing(10);
        nimiList.setPrefWidth(520);

//        rdd.findAll().stream().forEach(b -> {
//            Label n = new Label(b.getName());
//            n.setFont(new Font("Arial", 20));
//
////            Button bt = new Button(b.getName());
////            bt.setFont(new Font("Arial", 15));
////
////            bt.setOnAction((e) -> {
////
////            });
//            nimiList.getChildren().add(n);
//        });
        ObservableList<Book> nameList = FXCollections.observableList(rdd.findAll());

        ListView namesListView = new ListView(nameList);
        namesListView.setPrefWidth(520);

        // Muokkaus
        Label muokkaaTitle = new Label("Muokkaa");
        muokkaaTitle.setFont(new Font("Arial", 30));
        muokkaaTitle.setPadding(new Insets(5, 0, 0, 10));

        VBox muokkaaFields = new VBox();
        muokkaaFields.setPadding(new Insets(5, 0, 0, 10));
        muokkaaFields.setSpacing(10);

        ComboBox<Book> menu = new ComboBox<>(FXCollections.observableList(rdd.findAll()));

        Button bt = new Button("Poista");
        bt.setOnAction((e) -> {

            Book b = menu.getValue();

            try {
                rdd.delete(b.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ReadingListUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        muokkaaFields.getChildren().addAll(menu, bt);

        muokkaaBox.getChildren().addAll(muokkaaTitle, muokkaaFields);

        //Sarake "Sivut"
        Label sivutTitle = new Label("Sivut");
        sivutTitle.setFont(new Font("Arial", 30));
        sivutTitle.setPadding(new Insets(5, 0, 0, 10));
        sivutTitle.setPrefWidth(200);

        VBox sivutList = new VBox();
        sivutList.setSpacing(10);
        sivutList.setPrefWidth(200);

        List<Integer> sp = rdd.findAllStartPage();
        List<Integer> ep = rdd.findAllEndPage();
        List<String> pagesList = new ArrayList<>();

        for (int i = 0; i < rdd.findAll().size(); i++) {
            pagesList.add(sp.get(i) + " - " + ep.get(i));
        }

        ObservableList sl = FXCollections.observableList(pagesList);
        ListView pagesListView = new ListView(sl);

        pagesListView.setEditable(true);
        
        pagesListView.setCellFactory(TextFieldListCell.forListView());

        pagesListView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                pagesListView.getItems().set(t.getIndex(), t.getNewValue());
            }
        });

//        rdd.findAll().stream().forEach(b -> {
//            Label l = new Label(Integer.toString(b.getStartPage()) + " - " + Integer.toString(b.getEndPage()));
//            l.setFont(new Font("Arial", 20));
//            sivutList.getChildren().add(l);
//        });
        //Sarake "Deadline"
        Label deadlineTitle = new Label("Deadline");
        deadlineTitle.setFont(new Font("Arial", 30));
        deadlineTitle.setPadding(new Insets(5, 0, 0, 10));
        deadlineTitle.setPrefWidth(200);

        VBox dlList = new VBox();
        dlList.setSpacing(10);
        dlList.setPrefWidth(200);

//        rdd.findAll().stream().forEach(b -> {
//            Label l = new Label(b.getDeadline());
//            l.setFont(new Font("Arial", 20));
//            dlList.getChildren().add(l);
//        });
        ObservableList dll = FXCollections.observableList(rdd.findAllDeadline());
        ListView deadlineListView = new ListView(dll);

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
            nimiList.getChildren().add(l);

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
        titlebar.getChildren().addAll(lukulistaTitle, sivutTitle, deadlineTitle);

        uusiBox.getChildren().addAll(uusiTitle, uusiFields);
        center.setTop(titlebar);

        readinglistBox.getChildren().addAll(namesListView, pagesListView, deadlineListView);

        ScrollPane scroll = new ScrollPane(readinglistBox);
        scroll.setFitToWidth(true);

        center.setCenter(scroll);

        Scene scene = new Scene(setting);

        stage.setScene(scene);
        stage.setWidth(1100);
        stage.setTitle("Lukulista");
        stage.show();

    }

}
