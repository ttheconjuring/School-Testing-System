package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateTestController implements Initializable {

    // ================================================== \\

    /*Visual elements*/

    @FXML
    private TextField textField_testName;

    @FXML
    private TextField textField_description;

    @FXML
    private TextField textField_responseTime;

    @FXML
    private TextField textField_code;

    @FXML
    private TextField textField_questions;

    @FXML
    private TextField textField_passingScore;

    @FXML
    private TextField textField_status;

    @FXML
    private Button button_generate;

    @FXML
    private Button button_next;

    @FXML
    private Button button_cancel;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_next().setOnAction(this::createTest);

        /* transition */
        getButton_cancel().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", Utilities.getCurrenUserID()), actionEvent));

        /* side functionality*/
        getButton_generate().setOnAction(actionEven -> generateCode());
    }

    private void createTest(ActionEvent actionEvent) {
        if (thereAreEmptyGaps()) {
            Utilities.showAlert("Empty gaps", "Please, fill the must gaps!", Alert.AlertType.WARNING);
        } else {
            if (getTextField_description().getText().isEmpty()) {
                getTextField_description().setText("null");
            }
            if (getTextField_passingScore().getText().isEmpty()) {
                getTextField_passingScore().setText("0");
            }
            if (Utilities.isValid(getTextField_responseTime().getText(), "^[1-5]$")) {
                if (Utilities.isValid(getTextField_code().getText(), ValidationRegexes.CODE.getRegex())) {
                    if (Utilities.isValid(getTextField_questions().getText(), "^\\b(?:[1-9]|10)\\b$")) {
                        if (Utilities.isValid(getTextField_status().getText(), "^(free|locked)$")) {
                            response(DBUtilities.createTest(
                                    Utilities.getCurrenUserID(),
                                    getTextField_testName().getText(),
                                    getTextField_description().getText(),
                                    getTextField_code().getText(),
                                    getTextField_responseTime().getText(),
                                    getTextField_passingScore().getText(),
                                    getTextField_questions().getText(),
                                    Utilities.getCurrentDateAndTime(),
                                    Utilities.getCurrentDateAndTime(),
                                    getTextField_status().getText()), actionEvent);
                        } else {
                            Utilities.showAlert("Invalid Test Status!", "The test status can be either \"free\" or \"locked\"!", Alert.AlertType.ERROR);
                            getTextField_status().clear();
                        }
                    } else {
                        Utilities.showAlert("Invalid Questions Number!", "The questions must be between 1 and 10 including!", Alert.AlertType.ERROR);
                        getTextField_questions().clear();
                    }
                } else {
                    Utilities.showAlert("Invalid Code!", InvalidInputMessages.CODE.getMessage(), Alert.AlertType.ERROR);
                    getTextField_code().clear();
                }
            } else {
                Utilities.showAlert("Invalid Response Time!", "The response time must be a number between 1 and 5 (minutes)!", Alert.AlertType.ERROR);
                getTextField_responseTime().clear();
            }

        }
    }

    private void response(boolean flag, ActionEvent actionEvent) {
        if (flag) {
            Utilities.showAlert("Done!", "You successfully created test! Please, continue with Question Part.", Alert.AlertType.CONFIRMATION);
            Utilities.switchToPreparedScene(Utilities.prepareScene("Create-Question.fxml", "1"), actionEvent);
        } else {
            Utilities.showAlert("Error!", "Sorry, but you probably have invalid data", Alert.AlertType.ERROR);
            clearFields();
        }
    }

    private void generateCode() {
        SecureRandom random = new SecureRandom();
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialCharacters = "!@#$%&";
        String digits = "0123456789";
        String generatedString = String.valueOf(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()))) +
                specialCharacters.charAt(random.nextInt(specialCharacters.length())) +
                digits.charAt(random.nextInt(digits.length())) +
                upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())) +
                specialCharacters.charAt(random.nextInt(specialCharacters.length())) +
                lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        getTextField_code().setText(generatedString);
    }

    private boolean thereAreEmptyGaps() {
        return getTextField_testName().getText().isEmpty() ||
                getTextField_responseTime().getText().isEmpty() || getTextField_code().getText().isEmpty() ||
                getTextField_questions().getText().isEmpty() || getTextField_status().getText().isEmpty();
    }

    private void clearFields() {
        getTextField_testName().clear();
        getTextField_description().clear();
        getTextField_responseTime().clear();
        getTextField_code().clear();
        getTextField_questions().clear();
    }

    private TextField getTextField_testName() {
        return this.textField_testName;
    }

    private TextField getTextField_description() {
        return this.textField_description;
    }

    private TextField getTextField_responseTime() {
        return this.textField_responseTime;
    }

    private TextField getTextField_code() {
        return this.textField_code;
    }

    private TextField getTextField_questions() {
        return this.textField_questions;
    }

    private TextField getTextField_passingScore() {
        return this.textField_passingScore;
    }

    private TextField getTextField_status() {
        return this.textField_status;
    }

    private Button getButton_generate() {
        return this.button_generate;
    }

    private Button getButton_next() {
        return this.button_next;
    }

    private Button getButton_cancel() {
        return this.button_cancel;
    }

}
