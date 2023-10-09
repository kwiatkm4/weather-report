package pl.edu.pw.mwo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AppController {

    @FXML
    private TextField citySearchbar;
    @FXML
    private Button searchButton;

    @FXML
    public void onCitySearch() {
        var cityText = citySearchbar.getText();

        if (cityText == null || cityText.isEmpty()) {
            System.out.println("Empty");
            return;
        }

        System.out.println(cityText);

        Thread t = new Thread(() -> {
            ;
        });
    }
}