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

    // ================================================== \\

    /* visual elements */

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

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_create_account().setOnAction(actionEvent -> Utilities.switchTo("Create-Account.fxml", actionEvent));
        getButton_profile().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(Utilities.getCurrenUserID()))));
        getButton_users().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Users-ScrollPane.fxml", "-1")));
        getButton_my_tests().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Tests-ScrollPane.fxml", Utilities.getCurrenUserID())));
        getButton_create_test().setOnAction(actionEvent -> Utilities.switchTo("Create-Test.fxml", actionEvent));
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

}
