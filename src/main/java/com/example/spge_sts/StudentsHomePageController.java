package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsHomePageController implements Initializable {

    @FXML
    private Button button_profile;

    @FXML
    private Button button_log_out;

    @FXML
    private TextField textField_test_code;

    @FXML
    private Button button_go;

    private String ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_profile().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Students-Account-Information.fxml", DBUtilities.getUserData(this.getID())), actionEvent));
    }

    private Button getButton_profile() {
        return this.button_profile;
    }

    private Button getButton_log_out() {
        return this.button_log_out;
    }

    private TextField getTextField_test_code() {
        return this.textField_test_code;
    }

    private Button getButton_go() {
        return this.button_go;
    }

    protected void setID(String ID) {
        this.ID = ID;
    }

    private String getID() {
        return this.ID;
    }

    protected void setButton_profile(String username) {
        getButton_profile().setText("Hello @" + username + "!");
    }

    // ======================= CSS =======================
    public void buttonLogOutOnHover() {
        getButton_log_out().setOnMouseEntered(e -> getButton_log_out().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #39004d"));
    }

    public void buttonLogOutNoHover() {
        getButton_log_out().setOnMouseExited(e -> getButton_log_out().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #ffffff"));
    }

    public void buttonGoOnHover() {
        getButton_go().setOnMouseEntered(e -> getButton_go().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonGoNoHover() {
        getButton_go().setOnMouseExited(e -> getButton_go().setStyle("-fx-background-color: #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonProfileOnHover() {
        getButton_profile().setOnMouseEntered(e -> getButton_profile().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #39004d"));
    }

    public void buttonProfileNoHover() {
        getButton_profile().setOnMouseExited(e -> getButton_profile().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #ffffff"));
    }
    // ===================================================
}
