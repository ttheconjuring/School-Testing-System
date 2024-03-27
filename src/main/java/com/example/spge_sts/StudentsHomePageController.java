package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class StudentsHomePageController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private Button button_profile;

    @FXML
    private Button button_log_out;

    @FXML
    private TextField textField_test_code;

    @FXML
    private Button button_go;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* transitions */
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_profile().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Students-Account-Information.fxml", DBUtilities.getUserData(Utilities.getCurrenUserID()))));

        getButton_go().setOnAction(actionEvent -> {
            /* core functionality*/
            /* checks */
            if (!getTextField_test_code().getText().isEmpty()) {
                if (DBUtilities.testIsFree(getTextField_test_code().getText())) {
                    if (!DBUtilities.studentHasAlreadyEnteredTheTest(Utilities.getCurrenUserID(), getTextField_test_code().getText())) {
                        /* preparations */
                        Utilities.setResponseTimeInMinutes(DBUtilities.getResponseTimeOfTestBy(getTextField_test_code().getText()));
                        extractQuestionsData(getTextField_test_code().getText());
                        Utilities.setQuestionIndex(0);

                        /* transition */
                        Utilities.switchToPreparedScene(Utilities.prepareScene("Answer-Question.fxml", Utilities.getQuestionDataByIndex(Utilities.getQuestionIndex())), actionEvent);
                    } else {
                        Utilities.showAlert("Test Is Locked!", "Sorry, but it seems like you have already attended this test.", Alert.AlertType.ERROR);
                    }
                } else {
                    Utilities.showAlert("Test Is Locked!", "Sorry, but this test is locked!\nAsk your teacher for further information.", Alert.AlertType.ERROR);
                }
            } else {
                Utilities.showAlert("No Code Entered", "Please, enter code!", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void extractQuestionsData(String code) {
        ArrayList<String> ids = loadQuestionIDs(code);
        ArrayList<Map<String, String>> questionsData = new ArrayList<>();
        ids.forEach(id -> questionsData.add(loadQuestionDataByID(id)));
        Utilities.setQuestionsData(questionsData);
    }

    private ArrayList<String> loadQuestionIDs(String code) {
        return DBUtilities.getQuestionIDs(code);
    }

    private Map<String, String> loadQuestionDataByID(String ID) {
        return DBUtilities.getQuestionData(ID);
    }

    protected void setButton_profile(String username) {
        getButton_profile().setText("Hello @" + username + "!");
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

}
