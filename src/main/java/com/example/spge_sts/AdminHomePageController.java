package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private Button button_profile;

    @FXML
    private Button button_log_out;

    @FXML
    private Button button_users;

    @FXML
    private Button button_create_account;

    @FXML
    private Button button_statistics;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_profile().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(Utilities.getCurrenUserID()))));
        getButton_log_out().setOnAction(actionEvent -> Utilities.switchTo("Sign-In.fxml", actionEvent));
        getButton_users().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Users-ScrollPane.fxml", "-1"), actionEvent));
        getButton_create_account().setOnAction(actionEvent -> Utilities.switchTo("Create-Account.fxml", actionEvent));
        getButton_statistics().setOnAction(actionEvent -> Utilities.switchToPreparedScene(Utilities.prepareScene("Admin-Statistics.fxml", getData()), actionEvent));
    }

    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("usersCount", DBUtilities.getRecordsCount("users"));
        data.put("teachers", DBUtilities.getUsersCountByRole("teacher"));
        data.put("students", DBUtilities.getUsersCountByRole("student"));
        data.put("resultsCount", DBUtilities.getRecordsCount("results"));
        data.put("pass", DBUtilities.getResultsCountByStatus("pass"));
        data.put("fail", DBUtilities.getResultsCountByStatus("fail"));
        data.put("responsesCount", DBUtilities.getRecordsCount("responses"));
        data.put("correct", DBUtilities.getResponsesCountByStatus("true"));
        data.put("incorrect", DBUtilities.getResponsesCountByStatus("false"));
        data.put("testsCount", DBUtilities.getRecordsCount("tests"));
        data.put("free", DBUtilities.getTestsCountByStatus("free"));
        data.put("locked", DBUtilities.getTestsCountByStatus("locked"));
        if (DBUtilities.sumPassingScoreOfAllTests() != null && DBUtilities.getRecordsCount("tests") != null) {
            data.put("averagePassingScore", String.format("%.2f", Double.parseDouble(Objects.requireNonNull(DBUtilities.sumPassingScoreOfAllTests())) / Double.parseDouble(Objects.requireNonNull(DBUtilities.getRecordsCount("tests")))));
        } else {
            data.put("averagePassingScore", "?");
        }
        if (DBUtilities.sumQuestionsOfAllTests() != null && DBUtilities.getRecordsCount("tests") != null) {
            data.put("averageQuestionsCount", String.format("%.2f", Double.parseDouble(Objects.requireNonNull(DBUtilities.sumQuestionsOfAllTests())) / Double.parseDouble(Objects.requireNonNull(DBUtilities.getRecordsCount("tests")))));
        } else {
            data.put("averageQuestionsCount", "?");
        }
        data.put("questionsCount", DBUtilities.getRecordsCount("questions"));
        data.put("opened", DBUtilities.getQuestionsCountByType("opened"));
        data.put("closed", DBUtilities.getQuestionsCountByType("closed"));
        if (DBUtilities.sumPointsOfAllQuestions() != null && DBUtilities.getRecordsCount("questions") != null) {
            data.put("averagePoints", String.format("%.2f", Double.parseDouble(Objects.requireNonNull(DBUtilities.sumPointsOfAllQuestions())) / Double.parseDouble(Objects.requireNonNull(DBUtilities.getRecordsCount("questions")))));
        } else {
            data.put("averagePoints", "?");
        }
        return data;
    }

    private Button getButton_profile() {
        return this.button_profile;
    }

    private Button getButton_log_out() {
        return this.button_log_out;
    }

    private Button getButton_users() {
        return this.button_users;
    }

    private Button getButton_create_account() {
        return this.button_create_account;
    }

    private Button getButton_statistics() {
        return this.button_statistics;
    }
}
