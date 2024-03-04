package com.example.spge_sts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private Button button_ae;

    @FXML
    private Button button_bg;

    @FXML
    private Button button_start;

    @FXML
    private Button button_quit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_start().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_quit().setOnAction(actionEvent -> Platform.exit());
    }

    private Button getButton_ae() {
        return this.button_ae;
    }

    private Button getButton_bg() {
        return this.button_bg;
    }

    private Button getButton_start() {
        return this.button_start;
    }

    private Button getButton_quit() {
        return this.button_quit;
    }
}
