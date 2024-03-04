package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private Button button_profile;

    @FXML
    private Button button_log_out;

    @FXML
    private Button button_users;

    @FXML
    private Button button_create_account;

    @FXML
    private Button button_statistics;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_profile().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(Utilities.getCurrenUserID()))));
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_users().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Users-ScrollPane.fxml", "-1"), actionEvent));
        getButton_create_account().setOnAction(actionEvent -> Utilities.switchTo("Create-Account.fxml", actionEvent));
        getButton_statistics().setOnAction(actionEvent -> Utilities.switchTo("Admin-Statistics.fxml", actionEvent));
    }

    private Button getButton_profile() {
        return this.button_profile;
    }

    private Button getButton_log_out() {
        return this.button_log_out;
    }

    private Button getButton_users() {
        return this.button_users;
    }

    private Button getButton_create_account() {
        return this.button_create_account;
    }

    private Button getButton_statistics() {
        return this.button_statistics;
    }
}
