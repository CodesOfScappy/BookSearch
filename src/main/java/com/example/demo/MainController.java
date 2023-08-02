package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Der `MainController` ist der Controller für das Hauptfenster der Anwendung.
 * Er ermöglicht die Suche nach Büchern anhand verschiedener Kriterien und zeigt die Suchergebnisse in einer TableView an.
 * Zudem ermöglicht er das Öffnen eines Detailfensters zum Bestellen eines ausgewählten Buches.
 */
public class MainController {
    // Die FXML-Elemente für die Benutzeroberfläche
    // ...
    @FXML
    private TextField isbnSearch;
    @FXML
    private TextField authorSearch;
    @FXML
    private TextField titleSearch;
    @FXML
    private TextField publisherSearch;
    @FXML
    private TextField publishDateSearch;
    @FXML
    private ComboBox<String> bookGenreSearch;
    @FXML
    private CheckBox isbnAktiv;
    @FXML
    private CheckBox authorAktiv;
    @FXML
    private CheckBox titleAktiv;
    @FXML
    private CheckBox publisherAktiv;
    @FXML
    private CheckBox publishDateAktiv;
    @FXML
    private CheckBox genreSearchAktiv;
    @FXML
    private Button searchButton;

    @FXML
    private TableView<BookData> resultTableView;
    @FXML
    private TableColumn<BookData, String> titleColumn;
    @FXML
    private TableColumn<BookData, String> authorColumn;
    @FXML
    private TableColumn<BookData, String> publisherColumn;

    private GoogleBooksApi googleBookAPI;


    /**
     * Initialisiert den Controller und setzt die TableView-Spalten und die ComboBox für die Buchgenres.
     * Fügt zudem einen EventListener für Doppelklicks auf TableView-Einträge hinzu.
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
        // Instanz der GoogleBookAPI-Klasse erstellen
        googleBookAPI = new GoogleBooksApi();

        // Spalten programmatisch hinzufügen
        titleColumn = new TableColumn<>("Titel");
        titleColumn.setPrefWidth(150);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        authorColumn = new TableColumn<>("Autor");
        authorColumn.setPrefWidth(150);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        publisherColumn = new TableColumn<>("Verlag");
        publisherColumn.setPrefWidth(150);
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        // Weitere Spalten für andere Buchdaten hinzufügen

        // Spalten zur TableView hinzufügen
        resultTableView.getColumns().addAll(titleColumn, authorColumn, publisherColumn);
        setupGenreComboBox();

        // EventListener für Doppelklick auf TableView-Eintrag
        resultTableView.setOnMouseClicked((MouseEvent event )->{
            if(event.getButton().equals(MouseButton.PRIMARY)&& event.getClickCount()== 2) {
                BookData selectetBook = resultTableView.getSelectionModel().getSelectedItem();
                if (selectetBook != null) {
                    openSearchBookWindow(selectetBook.getIsbn13());
                    // prüfung on isb vorhanden

                }
            }
        });
    }


    /**
     * Öffnet ein neues Fenster zum Suchen und Bestellen eines Buches anhand seiner ISBN-Nummer.
     *
     * @param isbn13 Die ISBN-Nummer des zu suchenden Buches.
     */
    private void openSearchBookWindow(String isbn13) {
        System.out.println("ISBN-13: " + isbn13);

        if (isbn13 != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookbuy.fxml"));
                Parent root = loader.load();
                SearchBookController bookController = loader.getController();
                bookController.searchBookByISBN(isbn13);

                Stage stage = new Stage();

                stage.setScene(new Scene(root,900,900));
                stage.setTitle("Buch Bestellen");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Keine 13-stellige ISBN-Nummer gefunden.");
        }
    }


    /**
     * Wird aufgerufen, wenn der Benutzer auf die Suchen-Schaltfläche klickt.
     * Führt eine API-Abfrage mit den ausgewählten Suchkriterien durch und zeigt die Ergebnisse in der TableView an.
     */
    @FXML
    private void Searching() {
        // Erstelle eine Liste zur Speicherung der Suchkriterien
        List<String> searchParams = new ArrayList<>();

        // Prüfe, welche Checkboxen aktiviert sind, und füge die Suchkriterien zur Liste hinzu
        if (isbnAktiv.isSelected() && !isbnSearch.getText().isEmpty()) {
            searchParams.add("isbn:" + isbnSearch.getText().trim());
        }
        if (authorAktiv.isSelected() && !authorSearch.getText().isEmpty()) {
            searchParams.add("inauthor:" + authorSearch.getText().trim());
        }
        if (titleAktiv.isSelected() && !titleSearch.getText().isEmpty()) {
            searchParams.add("intitle:" + titleSearch.getText().trim());
        }
        if (publisherAktiv.isSelected() && !publisherSearch.getText().isEmpty()) {
            searchParams.add("inpublisher:" + publisherSearch.getText().trim());
        }
        if (publishDateAktiv.isSelected() && !publishDateSearch.getText().isEmpty()) {
            searchParams.add("publishedDate:" + publishDateSearch.getText().trim());
        }
        if (genreSearchAktiv.isSelected() && bookGenreSearch.getValue() != null) {
            searchParams.add("genre:" + bookGenreSearch.getValue());
        }

        // Erstelle den API-Query-String durch Verkettung der Suchkriterien
        String queryParams = String.join("+", searchParams);

        // Hier kannst du die API-Abfrage durchführen und die Daten in einer Liste von BookData-Objekten speichern
        List<BookData> bookList = performAPISearch(queryParams);

        // Die Daten in das TableView einfügen
        ObservableList<BookData> data = FXCollections.observableArrayList(bookList);
        //TEST
        System.out.println("Number of books in bookList: " + bookList.size());

        resultTableView.setItems(data);

        // TEST
        System.out.println("Searching method is called.");
    }


    /**
     * Führt eine API-Abfrage mit den angegebenen Suchkriterien durch und gibt eine Liste von BookData-Objekten zurück.
     *
     * @param queryParams Die Suchkriterien im API-Query-Format.
     * @return Eine Liste von BookData-Objekten, die die gefundenen Buchdaten enthalten.
     */
    private List<BookData> performAPISearch(String queryParams) {
        try {
            List<BookData> bookList = googleBookAPI.fetchBookDataListFromAPI(queryParams);
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Leere Liste, falls der API-Aufruf fehlschlägt oder Daten nicht gefunden wurden
        }
    }

    /**
     * Initialisiert die ComboBox für die Buchgenres und fügt eine Liste von Genres hinzu.
     * Optional kann ein Standard-Genre festgelegt werden.
     */
    private void setupGenreComboBox() {
        // Erstelle eine Liste von Genres
        ObservableList<String> genres = FXCollections.observableArrayList("Fiction", "Fantasy", "Science Fiction",
                "Mystery", "Romance", "Thriller", "Biography", "History", "Self-help", "Cooking", "Poetry", "Religion",
                "Science", "Travel", "Children's", "Young Adult");

        // Füge die Genres zur ComboBox hinzu
        bookGenreSearch.setItems(genres);

        // Optional: Wenn du ein Standard-Genre festlegen möchtest, kannst du das hier tun
        // bookGenreSearch.setValue("Fiction");
    }

}

