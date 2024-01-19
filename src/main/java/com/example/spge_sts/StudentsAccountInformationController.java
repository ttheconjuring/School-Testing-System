package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StudentsAccountInformationController implements Initializable {

    // ================================================== \\

    /*Visual elements*/

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

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_back().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Students-Home-Page.fxml", Utilities.getCurrenUserID()), actionEvent));
    }

    protected void setData(Map<String, String> data) {
        getLabel_username().setText("Username: " + data.get("Username"));
        getLabel_firstName().setText("First Name: " + data.get("FirstName"));
        getLabel_lastName().setText("Last Name: " + data.get("LastName"));
        getLabel_email().setText("Email: " + data.get("Email"));
        getLabel_phone().setText("Phone: " + data.get("Phone"));
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

}
