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

    @FXML
    private TextField textField_testName;

    @FXML
    private TextField textField_description;

    @FXML
    private TextField textField_duration;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_next().setOnAction(this::createTest);
        getButton_cancel().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", Utilities.getCurrenUserID()), actionEvent));
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
            if (Utilities.isValid(getTextField_duration().getText(), "^(?:[5-9]|[1-5]\\d|60)$")) {
                if (Utilities.isValid(getTextField_code().getText(), ValidationRegexes.CODE.getRegex())) {
                    if (Utilities.isValid(getTextField_questions().getText(), "^\\b(?:[1-9]|10)\\b$")) {
                        if (Utilities.isValid(getTextField_status().getText(), "^(free|locked)$")) {
                            response(DBUtilities.createTest(
                                    getTextField_testName().getText(),
                                    getTextField_description().getText(),
                                    getTextField_code().getText(),
                                    getTextField_duration().getText(),
                                    getTextField_passingScore().getText(),
                                    getTextField_questions().getText(),
                                    getTheCreationDateAndTime(),
                                    getTheUpdatedDateAndTime(Integer.parseInt(getTextField_duration().getText())),
                                    Utilities.getCurrenUserID(),
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
                Utilities.showAlert("Invalid Duration!", "The duration must be a number between 5 and 60 (minutes)!", Alert.AlertType.ERROR);
                getTextField_duration().clear();
            }

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

    private void response(boolean flag, ActionEvent actionEvent) {
        if (flag) {
            Utilities.showAlert("Done!", "You successfully created test! Please, continue with Question Part.", Alert.AlertType.CONFIRMATION);
            Utilities.switchToPreparedScene(Utilities.prepareScene("Create-Question.fxml", "1"), actionEvent);
        } else {
            Utilities.showAlert("Error!", "Sorry, but you probably have invalid data", Alert.AlertType.ERROR);
            clearFields();
        }
    }

    private String getTheCreationDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    private String getTheUpdatedDateAndTime(int minutesToAdd) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusMinutes(minutesToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return futureDateTime.format(formatter);
    }

    private boolean thereAreEmptyGaps() {
        return getTextField_testName().getText().isEmpty() ||
                getTextField_duration().getText().isEmpty() || getTextField_code().getText().isEmpty() ||
                getTextField_questions().getText().isEmpty() || getTextField_status().getText().isEmpty();
    }

    private void clearFields() {
        getTextField_testName().clear();
        getTextField_description().clear();
        getTextField_duration().clear();
        getTextField_code().clear();
        getTextField_questions().clear();
    }

    private TextField getTextField_testName() {
        return this.textField_testName;
    }

    private TextField getTextField_description() {
        return this.textField_description;
    }

    private TextField getTextField_duration() {
        return this.textField_duration;
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
