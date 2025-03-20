package ch.etmles.srs.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.InetAddress;

import static ch.etmles.srs.buisness.NetworkCard.getMACAddress;
import static ch.etmles.srs.buisness.NetworkCard.ping;

public class ComputerMngtController {


    public void onResetButtonClick(ActionEvent event) {

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
    public void onPingButtonClick(ActionEvent event) {
        String subnet = "10.228.158.138";
        welcomeText.setText(ping(subnet) + getMACAddress(subnet));
    }

    public void onCalculateSubnetButtonClick(ActionEvent event) {
    }

    public void onGetButtonClick(ActionEvent event) {
    }
}