package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_profile().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Students-Account-Information.fxml", DBUtilities.getUserData(Utilities.getCurrenUserID())), actionEvent));
        getButton_go().setOnAction(actionEvent -> startTest(getTextField_test_code().getText()));
    }

    private void startTest(String code) {
        // TODO: add Utilities method to handle the situation
        loadQuestionDataByID(loadQuestionIDs(code).getFirst());
    }

    private ArrayList<String> loadQuestionIDs (String code) {
        return DBUtilities.getQuestionIDs(code);
    }

    private Map<String, String> loadQuestionDataByID(String ID) {
        return DBUtilities.getQuestionDataByID(ID);
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

    protected void setButton_profile(String username) {
        getButton_profile().setText("Hello @" + username + "!");
    }

}
