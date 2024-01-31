package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class TestTemplateController implements Initializable {

    // ================================================== \\

    /* Visual elements */

    @FXML
    private Label label_testName;

    @FXML
    private Label label_testCode;

    @FXML
    private Label label_results;

    @FXML
    private Label label_pass;

    @FXML
    private Label label_fail;

    @FXML
    private Button button_leaderboard;

    @FXML
    private Button button_changeStatus;

    @FXML
    private ImageView imageView_status;

    // ================================================== \\

    /* helpful */

    private String TestID;

    private String testStatus;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_changeStatus().setOnAction(actionEvent -> {
            if (getTestStatus().equals("free")) {
                lockTest();
            } else {
                unlockTest();
            }
            updateTestDate();
        });
        getButton_leaderboard().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Leaderboard.fxml", getTestID())));
    }

    protected void setData(Map<String, String> testInfo) {
        getLabel_testName().setText(testInfo.get("TestName"));
        getLabel_testCode().setText("(" + testInfo.get("Code") + ")");
        getLabel_results().setText("Results: " + testInfo.get("Results"));
        getLabel_pass().setText("Pass: " + testInfo.get("Pass"));
        getLabel_fail().setText("Fail: " + testInfo.get("Fail"));
        setTestID(testInfo.get("TestID"));
        setTestStatus(testInfo.get("Status"));
        if (getTestStatus().equals("free")) {
            unlockTest();
        } else {
            lockTest();
        }
    }

    private void lockTest() {
        if (DBUtilities.updateTestStatus("locked", getTestID())) {
            setTestStatus("locked");
            getButton_changeStatus().setStyle("-fx-background-color: #A7A7A7");
            getImageView_status().setImage(new Image(new File("src/main/resources/com/example/spge_sts/lock.png").toURI().toString()));
        } else {
            Utilities.showAlert("Test Status Not Changed", "Sorry, but something went wrong! Your test status is not changed!", Alert.AlertType.ERROR);
        }
    }

    private void unlockTest() {
        if (DBUtilities.updateTestStatus("free", getTestID())) {
            getButton_changeStatus().setStyle("-fx-background-color: #5A9C07");
            getImageView_status().setImage(new Image(new File("src/main/resources/com/example/spge_sts/unlock.png").toURI().toString()));
            setTestStatus("free");
        } else {
            Utilities.showAlert("Test Status Not Changed", "Sorry, but something went wrong! Your test status is not changed!", Alert.AlertType.ERROR);
        }
    }

    private void updateTestDate() {
        DBUtilities.updateTestDate(Utilities.getCurrentDateAndTime(), getTestID());
    }

    private Label getLabel_testName() {
        return this.label_testName;
    }

    private Label getLabel_testCode() {return this.label_testCode; }

    private Label getLabel_results() {
        return this.label_results;
    }

    private Label getLabel_pass() {
        return this.label_pass;
    }

    private Label getLabel_fail() {
        return this.label_fail;
    }

    private Button getButton_leaderboard() {
        return this.button_leaderboard;
    }

    private Button getButton_changeStatus() {
        return this.button_changeStatus;
    }

    private ImageView getImageView_status() {
        return this.imageView_status;
    }

    private String getTestID() {
        return this.TestID;
    }

    private void setTestID(String testID) {
        TestID = testID;
    }

    private String getTestStatus() {
        return this.testStatus;
    }

    private void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }
}
