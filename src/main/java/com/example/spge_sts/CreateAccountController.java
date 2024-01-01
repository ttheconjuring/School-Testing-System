package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountController implements Initializable {

    @FXML
    private ToggleButton toggleButton_teacher_role;

    @FXML
    private ToggleButton toggleButton_student_role;

    @FXML
    private TextField textField_username;

    @FXML
    private PasswordField passwordField_password;

    @FXML
    private TextField textField_firstName;

    @FXML
    private TextField textField_lastName;

    @FXML
    private TextField textField_email;

    @FXML
    private TextField textField_phone;

    @FXML
    private Button button_create;

    @FXML
    private Button button_cancel;

    private String ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getToggleButton_teacher_role().setOnAction(actionEvent -> setClicked(getToggleButton_teacher_role(), getToggleButton_student_role()));
        getToggleButton_student_role().setOnAction(actionEvent -> setClicked(getToggleButton_student_role(), getToggleButton_teacher_role()));
        getButton_create().setOnAction(this::createAccount);
        getButton_cancel().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", this.getID()), actionEvent));
    }

    private void createAccount(ActionEvent actionEvent) {
        if (thereAreEmptyGaps()) {
            Utilities.showAlert("Empty gaps", "Please, fill all the gaps!", Alert.AlertType.WARNING);
        } else {
            if ((getTheChosenRole().equals("teacher") && Utilities.isValid(getTextOf(getTextField_username()), ValidationRegexes.TEACHER_USERNAME.getRegex()))
                    || (getTheChosenRole().equals("student") && Utilities.isValid(getTextOf(getTextField_username()), ValidationRegexes.STUDENT_USERNAME.getRegex()))) {
                if (Utilities.isValid(getTextOf(getPasswordField_password()), ValidationRegexes.PASSWORD.getRegex())) {
                    if (Utilities.isValid(getTextOf(getTextField_firstName()), ValidationRegexes.FIRST_NAME.getRegex())) {
                        if (Utilities.isValid(getTextOf(getTextField_lastName()), ValidationRegexes.LAST_NAME.getRegex())) {
                            if (Utilities.isValid(getTextOf(getTextField_email()), ValidationRegexes.EMAIL.getRegex())) {
                                if (Utilities.isValid(getTextOf(getTextField_phone()), ValidationRegexes.PHONE.getRegex())) {
                                    response(DBUtilities.createRecord(
                                            getTextField_username().getText(),
                                            getPasswordField_password().getText(),
                                            getTextField_email().getText(),
                                            getTextField_phone().getText(),
                                            getTextField_firstName().getText(),
                                            getTextField_lastName().getText(),
                                            getTheChosenRole()), actionEvent);
                                } else {
                                    Utilities.showAlert("Invalid phone!", InvalidInputMessages.PHONE.getMessage(), Alert.AlertType.ERROR);
                                    getTextField_phone().clear();
                                }
                            } else {
                                Utilities.showAlert("Invalid email!", InvalidInputMessages.EMAIL.getMessage(), Alert.AlertType.ERROR);
                                getTextField_email().clear();
                            }
                        } else {
                            Utilities.showAlert("Invalid first name!", InvalidInputMessages.LAST_NAME.getMessage(), Alert.AlertType.ERROR);
                            getTextField_lastName().clear();
                        }
                    } else {
                        Utilities.showAlert("Invalid first name!", InvalidInputMessages.FIRST_NAME.getMessage(), Alert.AlertType.ERROR);
                        getTextField_firstName().clear();
                    }
                } else {
                    Utilities.showAlert("Invalid password!", InvalidInputMessages.PASSWORD.getMessage(), Alert.AlertType.ERROR);
                    getPasswordField_password().clear();
                }
            } else {
                if (getTheChosenRole().equals("teacher")) {
                    Utilities.showAlert("Invalid username!", InvalidInputMessages.TEACHER_USERNAME.getMessage(), Alert.AlertType.ERROR);
                } else {
                    Utilities.showAlert("Invalid username!", InvalidInputMessages.STUDENT_USERNAME.getMessage(), Alert.AlertType.ERROR);
                }
                getTextField_username().clear();
            }
        }
    }

    private String getTextOf(TextField textField) {
        return textField.getText();
    }

    private void response(boolean flag, ActionEvent actionEvent) {
        if (flag) {
            Utilities.showAlert("Done!", "You successfully created an account!", Alert.AlertType.CONFIRMATION);
            Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", this.getID()), actionEvent);
        } else {
            Utilities.showAlert("Error!", "Sorry, but you probably have invalid data", Alert.AlertType.ERROR);
            clearFields();
        }
    }

    private void setClicked(ToggleButton clickedButton, ToggleButton otherButton) {
        clickedButton.setSelected(true);
        clickedButton.setOpacity(1);
        otherButton.setOpacity(0.5);
        otherButton.setSelected(false);
    }

    private String getTheChosenRole() {
        return getToggleButton_teacher_role().isSelected() ? "teacher" : "student";
    }

    private void clearFields() {
        getTextField_username().clear();
        getPasswordField_password().clear();
        getTextField_firstName().clear();
        getTextField_lastName().clear();
        getTextField_email().clear();
        getTextField_phone().clear();
    }

    private boolean thereAreEmptyGaps() {
        return getTextOf(getTextField_username()).isEmpty() || getTextOf(getPasswordField_password()).isEmpty() ||
                getTextOf(getTextField_firstName()).isEmpty() || getTextOf(getTextField_lastName()).isEmpty() ||
                getTextOf(getTextField_email()).isEmpty() || getTextOf(getTextField_phone()).isEmpty();
    }

    private Button getButton_create() {
        return this.button_create;
    }

    private Button getButton_cancel() {
        return this.button_cancel;
    }

    private ToggleButton getToggleButton_teacher_role() {
        return this.toggleButton_teacher_role;
    }

    private ToggleButton getToggleButton_student_role() {
        return this.toggleButton_student_role;
    }

    private TextField getTextField_username() {
        return this.textField_username;
    }

    private PasswordField getPasswordField_password() {
        return this.passwordField_password;
    }

    private TextField getTextField_firstName() {
        return this.textField_firstName;
    }

    private TextField getTextField_lastName() {
        return this.textField_lastName;
    }

    private TextField getTextField_email() {
        return this.textField_email;
    }

    private TextField getTextField_phone() {
        return this.textField_phone;
    }

    private String getID() {
        return this.ID;
    }

    protected void setID(String ID) {
        this.ID = ID;
    }

}
