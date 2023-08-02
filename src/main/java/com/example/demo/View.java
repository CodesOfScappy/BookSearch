package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Die Hauptklasse der JavaFX-Anwendung.
 * Sie erbt von der `Application`-Klasse und dient als Startpunkt für das JavaFX-Fenster.
 */
public class View extends Application {

    /**
     * Die `start`-Methode ist der Einstiegspunkt in die JavaFX-Anwendung.
     * Sie wird von JavaFX aufgerufen, um das Fenster zu erstellen und anzuzeigen.
     *
     * @param primaryStage Die Hauptbühne (Stage), auf der das Fenster angezeigt wird.
     * @throws Exception Eine mögliche Ausnahme, die beim Laden der Benutzeroberfläche auftreten kann.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Laden Sie die primary.fxml-Datei mit dem FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();

        // Erstellen Sie eine Szene und fügen Sie die Wurzel (root) hinzu
        Scene scene = new Scene(root, 800, 600);

        // Setzen Sie die Szene auf die Hauptbühne (primaryStage)
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Bücher-Datenbank");

        // Zeigen Sie die Hauptbühne an
        primaryStage.show();
    }

    /**
     * Der Einstiegspunkt für die JavaFX-Anwendung.
     * Diese Methode wird aufgerufen, um die JavaFX-Anwendung zu starten.
     *
     * @param args Die Befehlszeilenargumente, die an die Anwendung übergeben werden können.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
