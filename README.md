# Bücher-Datenbank JavaFX-Anwendung

Dies ist eine JavaFX-Anwendung, die es ermöglicht, Bücher anhand verschiedener Kriterien zu suchen und die Suchergebnisse anzuzeigen. Die Anwendung verwendet die Google Books API, um Buchdaten abzurufen, und zeigt die Ergebnisse in einer benutzerfreundlichen Oberfläche an.

## Funktionen

- Suche nach Büchern anhand von ISBN, Autor, Titel, Verlag, Veröffentlichungsdatum und Genre.
- Anzeige der Suchergebnisse in einer übersichtlichen TableView.
- Detailansicht für jedes gefundene Buch, um es direkt zu bestellen.

## Verwendung

1. Klone dieses Repository oder lade den Quellcode herunter.
2. Öffne das Projekt in deiner bevorzugten Java-Entwicklungsumgebung (z. B. Eclipse oder IntelliJ).
3. Stelle sicher, dass die erforderlichen JavaFX-Bibliotheken und Abhängigkeiten vorhanden sind.
4. Erstelle eine API-Schlüssel für die Google Books API und setze sie in der `GoogleBooksApi`-Klasse (siehe Kommentare im Code).
5. Führe das Projekt aus, um die JavaFX-Anwendung zu starten.

## Struktur

- `MainController`: Controller für das Hauptfenster der Anwendung.
- `Launcher`: Startet die JavaFX-Anwendung.
- `View`: Hauptklasse der JavaFX-Anwendung, erstellt das Hauptfenster und zeigt die Suchergebnisse an.
- `GoogleBooksApi`: Stellt eine Methode bereit, um Buchdaten über die Google Books API abzurufen.
- `SearchBookController`: Controller für das Suchergebnisfenster, verwendet ein `WebView` zur Anzeige der Suchergebnisse von "www.eurobuch.com".

## Anforderungen

- Java Development Kit (JDK) 8 oder höher.
- JavaFX-Bibliotheken und Abhängigkeiten.

## Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert. Siehe die [Lizenzdatei](LICENSE) für weitere Informationen.

## Hinweis

Stelle sicher, dass du eine gültige API-Schlüssel für die Google Books API hast, bevor du das Projekt ausführst. Andernfalls können die API-Anfragen fehlschlagen.

Bei Fragen oder Problemen wende dich bitte an [Info.Scappy@gmail.com].
