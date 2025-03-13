package ch.etmles.srs.view;

import ch.etmles.srs.buisness.Computer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ComputerMngtController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


}