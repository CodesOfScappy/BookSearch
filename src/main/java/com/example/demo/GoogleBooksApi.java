package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Die `GoogleBooksApi`-Klasse ermöglicht den Zugriff auf die Google Books API, um Buchdaten abzurufen.
 * Sie stellt eine Methode bereit, um Buchdaten basierend auf den angegebenen Suchkriterien abzurufen.
 */
public class GoogleBooksApi {

    // Die URL der Google Books API und der API-Key
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyBF2gBKf9DzhkAY-mGgKU7rAqT8DuZ_i_s";

    /**
     * Ruft Buchdaten über die Google Books API basierend auf den angegebenen Suchkriterien ab.
     *
     * @param queryParams Die Suchkriterien für die Buchsuche in der Google Books API.
     * @return Eine Liste von {@link BookData} Objekten, die die gefundenen Buchdaten enthalten.
     * @throws IOException Wenn ein Fehler beim Verbinden mit der API oder beim Lesen der API-Antwort auftritt.
     */
    public List<BookData> fetchBookDataListFromAPI(String queryParams) throws IOException {
        String queryParamsEncoded = String.join("+", queryParams);
        @SuppressWarnings("deprecation")
        URL url = new URL(API_URL + queryParamsEncoded + "&key=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            // Kontrollausgabe auf Kosole
            System.out.println("API-Antwort:");
            System.out.println(response.toString());

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray itemsArray = jsonObject.optJSONArray("items");
            List<BookData> bookList = new ArrayList<>();

            if (itemsArray != null) {
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject bookInfo = itemsArray.getJSONObject(i).optJSONObject("volumeInfo");
                    if (bookInfo != null) {
                        String title = bookInfo.optString("title", "Buchtitel nicht verfügbar");
                        String author = "Autor nicht verfügbar";
                        JSONArray authorsArray = bookInfo.optJSONArray("authors");
                        if (authorsArray != null && authorsArray.length() > 0) {
                            author = authorsArray.join(", ").replaceAll("\"", "");
                        }
                        String publishDate = bookInfo.optString("publishedDate", "Veröffentlichungsdatum nicht verfügbar");
                        String publisher = bookInfo.optString("publisher", "Verlag nicht verfügbar");

                        // Hinzufügen der ISBN-Nummern mit 13 Stellen (Filter)
                        String isbn13 = findISBN13(bookInfo.optJSONArray("industryIdentifiers"));

                        JSONObject imageLinks = bookInfo.optJSONObject("imageLinks");
                        String coverURL = "";
                        if (imageLinks != null) {
                            coverURL = imageLinks.optString("thumbnail", "");
                        }

                        String description = bookInfo.optString("description", "Beschreibung nicht verfügbar");

                        // Kontrollausgabe auf der Konsole!
                        System.out.println("Buch " + (i + 1) + ":");
                        System.out.println("Titel: " + title);
                        System.out.println("Autor: " + author);
                        System.out.println("Veröffentlichungsdatum: " + publishDate);
                        System.out.println("Verlag: " + publisher);
                        System.out.println("ISBN-Nummern (13 Stellen): " + isbn13); // Ausgabe der ISBN-Nummern mit 13 Stellen
                        System.out.println("Cover-URL: " + coverURL);
                        System.out.println("Beschreibung: " + description);

                        bookList.add(new BookData(title, author, publishDate, publisher, coverURL, description, isbn13));
                    }
                }
            }

            return bookList;
        } else {
            System.out.println("API-Aufruf fehlgeschlagen. Statuscode: " + connection.getResponseCode());
            return new ArrayList<>(); // Rückgabewert, falls der API-Aufruf fehlschlägt
        }
    }




    /**
     * Hilfsmethode zum Finden der ISBN-Nummer mit 13 Stellen aus einem JSON-Array von Identifiern.
     *
     * @param identifiersArray Das JSON-Array der Identifiern aus der API-Antwort.
     * @return Die ISBN-Nummer mit 13 Stellen als String, wenn vorhanden; sonst "ISBN nicht verfügbar".
     */
    private String findISBN13(JSONArray identifiersArray) {
        if (identifiersArray != null) {
            for (int j = 0; j < identifiersArray.length(); j++) {
                JSONObject identifierObject = identifiersArray.getJSONObject(j);
                String type = identifierObject.optString("type", "");
                String identifier = identifierObject.optString("identifier", "");
                if (type.equals("ISBN_13") && identifier.length() == 13) {
                    return identifier;
                }
            }
        }
        return "ISBN nicht verfügbar";
    }
}


