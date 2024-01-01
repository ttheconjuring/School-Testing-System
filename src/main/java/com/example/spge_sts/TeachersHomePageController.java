package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TeachersHomePageController implements Initializable {

    @FXML
    private Button button_log_out;

    @FXML
    private Button button_profile;

    @FXML
    private Button button_my_tests;

    @FXML
    private Button button_users;

    @FXML
    private Button button_create_test;

    @FXML
    private Button button_create_account;

    private String ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_create_account().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Create-Account.fxml", this.getID()), actionEvent));
        // getButton_create_account().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Create-Account.fxml", this.getID())));
        getButton_profile().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(getID())), actionEvent));
        // getButton_profile().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(getID()))));
        getButton_users().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Users-ScrollPane.fxml", "-1")));
    }

    private Button getButton_log_out() {
        return this.button_log_out;
    }

    private Button getButton_profile() {
        return this.button_profile;
    }

    private Button getButton_my_tests() {
        return this.button_my_tests;
    }

    private Button getButton_users() {
        return this.button_users;
    }

    private Button getButton_create_test() {
        return this.button_create_test;
    }

    private Button getButton_create_account() {
        return this.button_create_account;
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
    public void buttonMyTestsOnHover() {
        getButton_my_tests().setOnMouseEntered(e -> getButton_my_tests().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonMyTestsNoHover() {
        getButton_my_tests().setOnMouseExited(e -> getButton_my_tests().setStyle("-fx-background-color: #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonUsersOnHover() {
        getButton_users().setOnMouseEntered(e -> getButton_users().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonUserNoHover() {
        getButton_users().setOnMouseExited(e -> getButton_users().setStyle("-fx-background-color: #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCreateTestOnHover() {
        getButton_create_test().setOnMouseEntered(e -> getButton_create_test().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCreateTestNoHover() {
        getButton_create_test().setOnMouseExited(e -> getButton_create_test().setStyle("-fx-background-color: #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCreateAccountOnHover() {
        getButton_create_account().setOnMouseEntered(e -> getButton_create_account().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCreateAccountNoHover() {
        getButton_create_account().setOnMouseExited(e -> getButton_create_account().setStyle("-fx-background-color: #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonLogOutOnHover() {
        getButton_log_out().setOnMouseEntered(e -> getButton_log_out().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #39004d"));
    }

    public void buttonLogOutNoHover() {
        getButton_log_out().setOnMouseExited(e -> getButton_log_out().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #ffffff"));
    }

    public void buttonProfileOnHover() {
        getButton_profile().setOnMouseEntered(e -> getButton_profile().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #39004d"));
    }

    public void buttonProfileNoHover() {
        getButton_profile().setOnMouseExited(e -> getButton_profile().setStyle("-fx-background-color: #8A41AC; -fx-text-fill: #ffffff"));
    }
    // ===================================================
}
