package ch.etmles.srs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ComputerMngtController {

    @FXML
    private Button btnHello;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
            welcomeText.setText("Hello");
    }

}