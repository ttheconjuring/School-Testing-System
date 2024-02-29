package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private TextField textField_username;

    @FXML
    private PasswordField passwordField_password;

    @FXML
    private Button button_sign_in;

    @FXML
    private Button button_back;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_sign_in().setOnAction(this::singIn);
        getButton_back().setOnAction(actionEvent -> Utilities.switchTo("Welcome.fxml", actionEvent));
    }

    private void singIn(ActionEvent actionEvent) {
        String ID = DBUtilities.suchUserExists(this.getTextField_UsernameText(), this.getPasswordField_PasswordText());
        if (ID != null) {
            Utilities.setCurrenUserID(ID);
            redirect(ID, actionEvent);
        } else {
            Utilities.showAlert("Error", "Wrong username or password!", Alert.AlertType.ERROR);
        }
        clearInput();
    }

    private void redirect(String ID, ActionEvent actionEvent) {
        String role = DBUtilities.getUserData(ID).get("UserRole");
        assert role != null;
        if(role.equals("admin")) {
            System.out.println("hello admin!");
        } else if (role.equals("teacher")) {
            Utilities.switchTo("Teachers-Home-Page.fxml", actionEvent);
        } else {
            Utilities.switchTo("Students-Home-Page.fxml", actionEvent);
        }
    }

    private void clearInput() {
        getTextField_username().clear();
        getPasswordField_password().clear();
    }

    private TextField getTextField_username() {
        return this.textField_username;
    }

    private PasswordField getPasswordField_password() {
        return this.passwordField_password;
    }

    private Button getButton_sign_in() {
        return this.button_sign_in;
    }

    private Button getButton_back() {
        return this.button_back;
    }

    private String getTextField_UsernameText() {
        return getTextField_username().getText();
    }

    private String getPasswordField_PasswordText() {
        return getPasswordField_password().getText();
    }

}




