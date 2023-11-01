package pl.edu.pw.mwo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PamiwApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(PamiwApp.class.getResource("main-view.fxml"));
        var scene = new Scene(loader.load(),800,650);

        stage.setTitle("PamiwApp");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}