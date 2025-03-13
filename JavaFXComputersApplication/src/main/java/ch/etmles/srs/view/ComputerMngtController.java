package ch.etmles.srs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

import static ch.etmles.srs.buisness.NetworkCard.ping;

public class ComputerMngtController {

    @FXML
    private Button btnHello;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        String ip = "10.228.158.138";
        welcomeText.setText(ping(ip));
    }

}