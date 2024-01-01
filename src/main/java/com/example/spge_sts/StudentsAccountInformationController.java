package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StudentsAccountInformationController implements Initializable {

    @FXML
    private Label label_username;

    @FXML
    private Label label_firstName;

    @FXML
    private Label label_lastName;

    @FXML
    private Label label_email;

    @FXML
    private Label label_phone;

    @FXML
    private Button button_back;

    private String ID;

    protected void setData(Map<String, String> data) {
        this.setLabel_username(data.get("Username"));
        this.setLabel_firstName(data.get("FirstName"));
        this.setLabel_lastName(data.get("LastName"));
        this.setLabel_email(data.get("Email"));
        this.setLabel_phone(data.get("Phone"));
        this.setID(data.get("UserID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_back().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Students-Home-Page.fxml", this.getID()), actionEvent));
    }

    private Label getLabel_username() {
        return this.label_username;
    }

    private Label getLabel_firstName() {
        return this.label_firstName;
    }

    private Label getLabel_lastName() {
        return this.label_lastName;
    }

    private Label getLabel_email() {
        return this.label_email;
    }

    private Label getLabel_phone() {
        return this.label_phone;
    }

    private Button getButton_back() {
        return this.button_back;
    }

    private void setLabel_firstName(String firstName) {
        getLabel_firstName().setText("First Name: " + firstName);
    }

    private void setLabel_lastName(String lastName) {
        getLabel_lastName().setText("Last Name: " + lastName);
    }

    private void setLabel_email(String email) {
        getLabel_email().setText("Email: " + email);
    }

    private void setLabel_phone(String phone) {
        getLabel_phone().setText("Phone: " + phone);
    }

    private void setLabel_username(String username) {
        getLabel_username().setText("Username: " + username);
    }

    private void setID(String ID) {
        this.ID = ID;
    }

    private String getID() {
        return this.ID;
    }

    // ======================= CSS =======================

    public void onHover() {
        getButton_back().setOnMouseEntered(e -> getButton_back().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void noHover() {
        getButton_back().setOnMouseExited(e -> getButton_back().setStyle("-fx-background-color:  #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    // ===================================================
}
