package com.example.spge_sts;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
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
    private Label label_date_created;

    @FXML
    private Button button_leaderboard;

    @FXML
    private Button button_changeStatus;

    @FXML
    private Button button_statistics;

    @FXML
    private Button button_delete_test;

    @FXML
    private ImageView imageView_status;

    // ================================================== \\

    /* helpful */

    private String TestID;

    private String testStatus;

    private double passValue;

    private double failValue;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_changeStatus().setOnAction(actionEvent -> {
            if (getTestStatus().equals("free")) {
                lockTest();
            } else {
                unlockTest();
            }
            updateTestDate();
        });
        getButton_leaderboard().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Leaderboard.fxml", getTestID())));
        getButton_delete_test().setOnAction(actionEvent -> confirmTestDeletion());

        getButton_statistics().setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(Utilities.class.getResource("Statistics.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            StatisticsController statisticsController = loader.getController();
            ObservableList<PieChart.Data> dataForPieChartPassFail = FXCollections.observableArrayList(
                    new PieChart.Data("pass", getPassValue()),
                    new PieChart.Data("fail", getFailValue())
            );
            dataForPieChartPassFail.forEach(data ->
                    data.nameProperty().bind(Bindings.concat(
                            data.getName(), " ", data.pieValueProperty(), "%"
                    )));
            statisticsController.setDataToPieChartPassFail(dataForPieChartPassFail);
            Utilities.switchToPreparedScene(root, actionEvent);
        });
    }

    private void confirmTestDeletion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Test Deletion");
        alert.setHeaderText(null);
        alert.setContentText("You will permanently delete this test!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteTest();
        }
    }

    private void deleteTest() {
        if (DBUtilities.deleteTest(getTestID())) {
            Utilities.showAlert("Test Deletion Complete!", "You successfully deleted this test!", Alert.AlertType.CONFIRMATION);
            Utilities.closeLastStage();
        } else {
            Utilities.showAlert("Test Deletion Failed!", "Sorry, the test couldn't be deleted!", Alert.AlertType.ERROR);
        }
    }

    protected void setData(Map<String, String> testInfo) {
        getLabel_testName().setText(testInfo.get("TestName"));
        getLabel_testCode().setText("(" + testInfo.get("Code") + ")");
        getLabel_results().setText("Results: " + testInfo.get("Results"));
        getLabel_pass().setText("Pass: " + testInfo.get("Pass") + "%");
        setPassValue(Double.parseDouble(testInfo.get("Pass")));
        getLabel_fail().setText("Fail: " + testInfo.get("Fail") + "%");
        setFailValue(Double.parseDouble(testInfo.get("Fail")));
        getLabel_date_created().setText("Created at:\n" + testInfo.get("DateCreated"));
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

    private Label getLabel_testCode() {
        return this.label_testCode;
    }

    private Label getLabel_results() {
        return this.label_results;
    }

    private Label getLabel_pass() {
        return this.label_pass;
    }

    private Label getLabel_fail() {
        return this.label_fail;
    }

    private Label getLabel_date_created() {
        return this.label_date_created;
    }

    private Button getButton_leaderboard() {
        return this.button_leaderboard;
    }

    private Button getButton_changeStatus() {
        return this.button_changeStatus;
    }

    private Button getButton_statistics() {
        return this.button_statistics;
    }

    private Button getButton_delete_test() {
        return this.button_delete_test;
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

    private double getFailValue() {
        return this.failValue;
    }

    private void setFailValue(double failValue) {
        this.failValue = failValue;
    }

    private double getPassValue() {
        return this.passValue;
    }

    private void setPassValue(double passValue) {
        this.passValue = passValue;
    }
}
