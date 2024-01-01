package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeachersAccountInformationController implements Initializable {

    @FXML
    private TextField textField_username;

    @FXML
    private TextField textField_firstName;

    @FXML
    private TextField textField_lastName;

    @FXML
    private TextField textField_email;

    @FXML
    private TextField textField_phone;

    @FXML
    private Button button_edit_username;

    @FXML
    private Button button_edit_firstName;

    @FXML
    private Button button_edit_lastName;

    @FXML
    private Button button_edit_email;

    @FXML
    private Button button_edit_phone;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_save;

    @FXML
    private Button button_back;

    private String ID;
    private String userRole;

    protected void setData(Map<String, String> data) {
        setTextField_username(data.get("Username"));
        setTextField_firstName(data.get("FirstName"));
        setTextField_lastName(data.get("LastName"));
        setTextField_email(data.get("Email"));
        setTextField_phone(data.get("Phone"));
        setID(data.get("UserID"));
        setUserRole(data.get("UserRole"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_back().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", getID()), actionEvent));

        getButton_delete().setOnAction(this::confirmAccountDeletion);
        getButton_edit().setOnAction(actionEvent -> enterEditMode());

        getButton_cancel().setOnAction(this::leaveEditMode);
        getButton_save().setOnAction(actionEvent -> {
            saveChanges(getEditableTextField(), actionEvent);
        });

        getButton_edit_username().setOnAction(actionEvent -> edit(getTextField_username()));
        getButton_edit_firstName().setOnAction(actionEvent -> edit(getTextField_firstName()));
        getButton_edit_lastName().setOnAction(actionEvent -> edit(getTextField_lastName()));
        getButton_edit_email().setOnAction(actionEvent -> edit(getTextField_email()));
        getButton_edit_phone().setOnAction(actionEvent -> edit(getTextField_phone()));
    }

    private void saveChanges(TextField textField, ActionEvent actionEvent) {
        String id = textField.getId();
        switch (id) {
            case "textField_username":
                if (getUserRole().equals("teacher")) {
                    if (Utilities.isValid(getTextOf(textField), ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                        update(getTextOf(textField));
                        leaveEditMode(actionEvent);
                    } else {
                        Utilities.showAlert("Invalid username!", InvalidInputMessages.TEACHER_USERNAME.getMessage(), Alert.AlertType.ERROR);
                        textField.clear();
                    }
                } else {
                    if (Utilities.isValid(getTextOf(textField), ValidationRegexes.STUDENT_USERNAME.getRegex())) {
                        update(getTextOf(textField));
                        leaveEditMode(actionEvent);
                    } else {
                        Utilities.showAlert("Invalid username!", InvalidInputMessages.STUDENT_USERNAME.getMessage(), Alert.AlertType.ERROR);
                        textField.clear();
                    }
                }
                break;
            case "textField_firstName":
                if (Utilities.isValid(getTextOf(textField), ValidationRegexes.FIRST_NAME.getRegex())) {
                    update(getTextOf(textField));
                    leaveEditMode(actionEvent);
                } else {
                    Utilities.showAlert("Invalid first name!", InvalidInputMessages.FIRST_NAME.getMessage(), Alert.AlertType.ERROR);
                    textField.clear();
                }
                break;
            case "textField_lastName":
                if (Utilities.isValid(getTextOf(textField), ValidationRegexes.LAST_NAME.getRegex())) {
                    update(getTextOf(textField));
                    leaveEditMode(actionEvent);
                } else {
                    Utilities.showAlert("Invalid first name!", InvalidInputMessages.LAST_NAME.getMessage(), Alert.AlertType.ERROR);
                    textField.clear();
                }
                break;
            case "textField_email":
                if (Utilities.isValid(getTextOf(textField), ValidationRegexes.EMAIL.getRegex())) {
                    update(getTextOf(textField));
                    leaveEditMode(actionEvent);
                } else {
                    Utilities.showAlert("Invalid email!", InvalidInputMessages.EMAIL.getMessage(), Alert.AlertType.ERROR);
                    textField.clear();
                }
                break;
            case "textField_phone":
                if (Utilities.isValid(getTextOf(textField), ValidationRegexes.PHONE.getRegex())) {
                    update(getTextOf(textField));
                    leaveEditMode(actionEvent);
                } else {
                    Utilities.showAlert("Invalid phone!", InvalidInputMessages.PHONE.getMessage(), Alert.AlertType.ERROR);
                    textField.clear();
                }
                break;
        }
    }

    private void update(String string) {
        if (DBUtilities.updateRecord(string, getID())) {
            Utilities.showAlert("Successful Update", "You have successfully updated your account data!", Alert.AlertType.INFORMATION);
        } else {
            Utilities.showAlert("Update Failed", "Something went wrong! We couldn't update your information!", Alert.AlertType.ERROR);
        }
    }

    private TextField getEditableTextField() {
        if (getTextField_username().isEditable()) {
            return getTextField_username();
        } else if (getTextField_firstName().isEditable()) {
            return getTextField_firstName();
        } else if (getTextField_lastName().isEditable()) {
            return getTextField_lastName();
        } else if (getTextField_email().isEditable()) {
            return getTextField_email();
        } else {
            return getTextField_phone();
        }
    }

    private void edit(TextField textField) {
        textField.setEditable(true);
        String text = getTextOf(textField);
        textField.setText(text.substring(text.indexOf(':') + 2));
        textField.setStyle("-fx-background-color: #39004d;-fx-background-radius: 20px;-fx-pref-width: 300px;-fx-text-fill: white");
        semiActivateAllEditButtons();
        activate(getButton_save());
    }

    private void semiActivateAllEditButtons() {
        semiActivate(getButton_edit_username());
        semiActivate(getButton_edit_firstName());
        semiActivate(getButton_edit_lastName());
        semiActivate(getButton_edit_email());
        semiActivate(getButton_edit_phone());
    }

    private void confirmAccountDeletion(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account Deletion");
        alert.setHeaderText(null);
        alert.setContentText("You will permanently delete your account!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteAccount(actionEvent);
        }
    }

    private void deleteAccount(ActionEvent actionEvent) {
        if (DBUtilities.deleteRecord(getID())) {
            Utilities.showAlert("Account Deletion", "You successfully deleted your account!", Alert.AlertType.INFORMATION);
            if (getUserRole().equals("teacher")) {
                if (!Utilities.getCurrenUserID().equals(getID())) {
                    Utilities.closeLastStage();
                    Utilities.closeLastStage();
                } else {
                    // better avoid this scenario
                    Utilities.switchTo("Sign-In.fxml", actionEvent);
                }
            } else {
                Utilities.closeLastStage();
                Utilities.closeLastStage();
            }
        } else {
            Utilities.showAlert("Account Deletion Error", "Sorry, something went wrong!", Alert.AlertType.ERROR);
        }
    }

    private void enterEditMode() {
        deactivate(getButton_edit());
        deactivate(getButton_delete());

        getTextField_username().setAlignment(Pos.CENTER_LEFT);
        getTextField_firstName().setAlignment(Pos.CENTER_LEFT);
        getTextField_lastName().setAlignment(Pos.CENTER_LEFT);
        getTextField_email().setAlignment(Pos.CENTER_LEFT);
        getTextField_phone().setAlignment(Pos.CENTER_LEFT);

        activate(getButton_cancel());
        semiActivate(getButton_save());

        activate(getButton_edit_username());
        activate(getButton_edit_firstName());
        activate(getButton_edit_lastName());
        activate(getButton_edit_email());
        activate(getButton_edit_phone());
    }

    private void activate(Button button) {
        button.setDisable(false);
        button.setOpacity(1);
    }

    private void semiActivate(Button button) {
        button.setDisable(true);
        button.setOpacity(0.5);
    }

    private void deactivate(Button button) {
        button.setDisable(true);
        button.setOpacity(0);
    }

    private void leaveEditMode(ActionEvent actionEvent) {
        Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(getID())), actionEvent);
    }

    private String getTextOf(TextField textField) {
        return textField.getText();
    }

    private void setTextField_username(String username) {
        getTextField_username().setText("Username: " + username);
    }

    private void setTextField_firstName(String firstName) {
        getTextField_firstName().setText("First Name: " + firstName);
    }

    private void setTextField_lastName(String lastName) {
        getTextField_lastName().setText("Last Name: " + lastName);
    }

    private void setTextField_email(String email) {
        getTextField_email().setText("Email: " + email);
    }

    private void setTextField_phone(String phone) {
        getTextField_phone().setText("Phone: " + phone);
    }

    private TextField getTextField_username() {
        return this.textField_username;
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

    private Button getButton_edit_username() {
        return this.button_edit_username;
    }

    private Button getButton_edit_firstName() {
        return this.button_edit_firstName;
    }

    private Button getButton_edit_lastName() {
        return this.button_edit_lastName;
    }

    private Button getButton_edit_email() {
        return this.button_edit_email;
    }

    private Button getButton_edit_phone() {
        return this.button_edit_phone;
    }

    private Button getButton_delete() {
        return this.button_delete;
    }

    private Button getButton_edit() {
        return this.button_edit;
    }

    private Button getButton_cancel() {
        return this.button_cancel;
    }

    private Button getButton_save() {
        return this.button_save;
    }

    private Button getButton_back() {
        return this.button_back;
    }

    private String getID() {
        return this.ID;
    }

    private void setID(String ID) {
        this.ID = ID;
    }

    private String getUserRole() {
        return this.userRole;
    }

    private void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // ======================= CSS =======================

    public void buttonDeleteOnHover() {
        getButton_delete().setOnMouseEntered(e -> getButton_delete().setStyle("-fx-background-color: #991f00; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonDeleteNoHover() {
        getButton_delete().setOnMouseExited(e -> getButton_delete().setStyle("-fx-background-color:  #EF2929; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonEditOnHover() {
        getButton_edit().setOnMouseEntered(e -> getButton_edit().setStyle("-fx-background-color: #808000; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonEditNoHover() {
        getButton_edit().setOnMouseExited(e -> getButton_edit().setStyle("-fx-background-color:  #E4CF0A; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCancelOnHover() {
        getButton_cancel().setOnMouseEntered(e -> getButton_cancel().setStyle("-fx-background-color: #3d3d5c; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonCancelNoHover() {
        getButton_cancel().setOnMouseExited(e -> getButton_cancel().setStyle("-fx-background-color: #a3a3c2; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonSaveOnHover() {
        getButton_save().setOnMouseEntered(e -> getButton_save().setStyle("-fx-background-color: #004d1a; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonSaveNoHover() {
        getButton_save().setOnMouseExited(e -> getButton_save().setStyle("-fx-background-color: #89CC34; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonBackOnHover() {
        getButton_back().setOnMouseEntered(e -> getButton_back().setStyle("-fx-background-color: #39004d; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    public void buttonBackNoHover() {
        getButton_back().setOnMouseExited(e -> getButton_back().setStyle("-fx-background-color:  #8A41AC; -fx-background-radius: 20px; -fx-border-color: black; -fx-border-radius: 20px"));
    }

    // ===================================================

}
