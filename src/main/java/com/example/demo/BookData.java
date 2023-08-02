package com.example.demo;

public class BookData {
    private String title;
    private String author;
    private String publishDate;
    private String publisher;
    private String coverURL;
    private String description;
    private String isbn13; // Feld für die 13-stellige ISBN-Nummer

    /**
     * Konstruktor für die BookData-Klasse.
     * @param title Der Titel des Buches.
     * @param author Der Autor des Buches.
     * @param publishDate Das Veröffentlichungsdatum des Buches.
     * @param publisher Der Verlag des Buches.
     * @param coverURL Die URL des Buchcovers.
     * @param description Die Beschreibung des Buches.
     * @param isbn13 Die 13-stellige ISBN-Nummer des Buches.
     */
    public BookData(String title, String author, String publishDate, String publisher, String coverURL,
                    String description, String isbn13) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.coverURL = coverURL;
        this.description = description;
        this.isbn13 = isbn13;
    }

    /**
     * Gibt den Titel des Buches zurück.
     * @return Der Titel des Buches.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gibt die 13-stellige ISBN-Nummer des Buches zurück.
     * @return Die 13-stellige ISBN-Nummer des Buches.
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * Setzt die 13-stellige ISBN-Nummer des Buches.
     * @param isbn13 Die zu setzende 13-stellige ISBN-Nummer.
     */
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     * Gibt den Autor des Buches zurück.
     * @return Der Autor des Buches.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gibt das Veröffentlichungsdatum des Buches zurück.
     * @return Das Veröffentlichungsdatum des Buches.
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     * Gibt den Verlag des Buches zurück.
     * @return Der Verlag des Buches.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Gibt die URL des Buchcovers zurück.
     * @return Die URL des Buchcovers.
     */
    public String getCoverURL() {
        return coverURL;
    }

    /**
     * Gibt die Beschreibung des Buches zurück.
     * @return Die Beschreibung des Buches.
     */
    public String getDescription() {
        // TODO Auto-generated method stub
        return description;
    }


}


