package com.example.demo;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Der `SearchBookController` ist der Controller für das Fenster, das das Suchergebnis für ein Buch anhand seiner ISBN-Nummer anzeigt.
 * Es verwendet ein `WebView`, um die Suchergebnisse von "www.eurobuch.com" zu laden und anzuzeigen.
 */
public class SearchBookController {

    @FXML
    WebView webView;

    // Neue Kontrollvariable hinzufügen
    private boolean searchButtonClicked = false;

    /**
     * Sucht nach einem Buch anhand seiner ISBN-Nummer und zeigt die Suchergebnisse in einem `WebView` an.
     *
     * @param isbn Die ISBN-Nummer des zu suchenden Buches.
     */
    public void searchBookByISBN(String isbn) {
        String baseUrl = "https://www.eurobuch.com/?mtm_campaign=GENALLG&gclid=Cj0KCQjw2qKmBhCfARIsAFy8buKFVgrqGtlcPM0ijQFJH9OWlUlH-s0qHD-kyC2SelbN3R7osgqkGBMaAicQEALw_wcB";
        String searchUrl = baseUrl + "&isbn=" + isbn;
        webView.getEngine().load(searchUrl);

        // Füge einen EventListener hinzu, um den Suchbutton nach dem Laden der Webseite zu klicken
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED && !searchButtonClicked) { // Füge die Bedingung hinzu
                searchButtonClicked = true; // Setze die Kontrollvariable auf true, um weitere Klicks zu verhindern
                // Verzögere den Klick auf den Suchbutton um 2 Sekunden
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            webView.getEngine().executeScript("document.getElementsByName('isbn')[0].value='" + isbn + "';"
                                    + "document.getElementsByName('search_submit')[0].click();");
                        });
                    }
                }, 3000); // 3000 Millisekunden = 3 Sekunden Verzögerung
            } else if (newValue == Worker.State.FAILED) {
                System.out.println("Load failed: " + webView.getEngine().getLoadWorker().getMessage());
            }
        });
    }
}






