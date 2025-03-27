package ch.etmles.srs.view;

import ch.etmles.srs.buisness.NetworkCard;
import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ComputerMngtController {

    @FXML
    public Circle clePingResult;

    @FXML
    public TextField txtConfigurationMask;

    @FXML
    public TextField txtGetIpAddress;

    @FXML
    public TextField txtGetMacAddress;

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtModel;

    @FXML
    public Slider spNbCores;

    @FXML
    public TextField txtStorage;

    @FXML
    public ComboBox cbOSChoice;

    @FXML
    public TextField txtAddIpAddress;

    @FXML
    public TextField txtAddMask;

    @FXML
    public TextField txtAddMacAddress;

    @FXML
    public Button btnResetValue;

    @FXML
    public VBox vboxPanConfiguration;

    @FXML
    private TextField txtConfigurationMacAdress;

    @FXML
    private TextField txtIPAddressConfiguration;


    NetworkCard myNetworkCard = new NetworkCard();

    public void onResetButtonClick(ActionEvent event) {
        txtName.clear();
        txtModel.clear();
        txtStorage.clear();
        txtAddIpAddress.clear();
        txtAddMask.clear();
    }

    public void onAddButtonClick(ActionEvent event) {
    }

    public void onCbOsChoice(ActionEvent event) {
    }

    public void onImportButtonClick(ActionEvent event) {
    }

    public void onEditButtonClick(ActionEvent event) {
    }

    public void onExportButtonClick(ActionEvent event) {
    }

    @FXML
    public void onPingButtonClick(ActionEvent event) throws IOException {
        //Met le cercle de confirmation en bleu
        clePingResult.setFill(Color.BLUE);

        //Récupère l'adresse IP depuis le textfield
        String ipAddress = txtIPAddressConfiguration.getText();

        //Effectue le ping avec la valeur
        String result = myNetworkCard.ping(ipAddress);

        // Si le ping est réussit, rendre le cercle vert,
        if (result.contains("Joiniable")) {
            // Ping réussi, cercle vert
            clePingResult.setFill(Color.GREEN);
        }
        //Aussi non, si le ping n'est pas réussit, rend le cercle rouge
        else if (result.contains("Inaccessible")) {
            // Ping échoué, cercle rouge
            clePingResult.setFill(Color.RED);
        }
        //Reset le cercle en bleu après 5 seconde
        resetCircleColorAfterDelay();
    }

    public void onCalculateSubnetButtonClick(ActionEvent event) {

        String ipAddress = txtIPAddressConfiguration.getText();
        String mask = txtConfigurationMask.getText();

        // Définir les adresses IP et masque
        myNetworkCard.setIpAddress(ipAddress);
        myNetworkCard.setMask(mask);

        // Calculer l'adresse du sous-réseau
        String subNetAddress;
        subNetAddress = myNetworkCard.getSubNetAddress();

        // Créer une alerte pour afficher les résultats dans un pop-up
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultats du sous-réseau");
        alert.setHeaderText("Détails du sous-réseau");

        // Construire le message à afficher
        String message = "Adresse du sous-réseau : " + subNetAddress;

        alert.setContentText(message);

        // Afficher l'alerte
        alert.showAndWait();
    }

    public void onGetButtonClickMac(ActionEvent event) {

        //Récupère l'adresse IP depuis le textfield
        String ipAddress = txtGetIpAddress.getText();

        //Effectue le ping avec la valeur
        String result = myNetworkCard.getMACAddress(ipAddress);

        txtGetMacAddress.setText(result);
    }

    private void resetCircleColorAfterDelay() {
        // Créer une pause de 5 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        //Change la couleur du cercle en bleu
        pause.setOnFinished(event -> clePingResult.setFill(Color.BLUE));

        // Démarrer la pause
        pause.play();
    }
}