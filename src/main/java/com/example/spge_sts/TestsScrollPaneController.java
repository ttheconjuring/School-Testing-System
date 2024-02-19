package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TestsScrollPaneController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private ScrollPane scrollPane_tests;

    @FXML
    private TextField textField_search;

    @FXML
    private Button button_search;

    @FXML
    private Button button_back;

    // ================================================== \\

    /* helpful */

    private int testsCount;

    private ArrayList<String> testIDs;

    private ArrayList<String> testNames;


    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_search().setOnAction(actionEvent -> search(getTextField_search().getText(), getTestsCount()));
        getButton_back().setOnAction(actionEvent -> Utilities.switchTo("Teachers-Home-Page.fxml", actionEvent));
    }

    private void loadAllTestTemplates(int numberOfTests) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #435585;");
        for (int i = 1; i <= numberOfTests; i++) {
            hbox.getChildren().add(Utilities.prepareScene("Test-Template.fxml", getInfo(i - 1)));
        }
        getScrollPane_tests().setContent(hbox);
        getScrollPane_tests().setFitToWidth(true);
        getScrollPane_tests().setFitToHeight(true);
    }

    private void search(String string, int numberOfTests) {
        if (string.isEmpty()) {
            loadAllTestTemplates(getTestsCount());
        } else {
            if (string.equals("free")) {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setStyle("-fx-background-color: #435585;");
                for (int i = 1; i <= numberOfTests; i++) {
                    Map<String, String> info = getInfo(i - 1);
                    if (info.get("Status").equals("free")) {
                        hbox.getChildren().add(Utilities.prepareScene("Test-Template.fxml", info));
                    }
                }
                getScrollPane_tests().setContent(hbox);
                getScrollPane_tests().setFitToWidth(true);
                getScrollPane_tests().setFitToHeight(true);
            } else if (string.equals("locked")) {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setStyle("-fx-background-color: #435585;");
                for (int i = 1; i <= numberOfTests; i++) {
                    Map<String, String> info = getInfo(i - 1);
                    if (info.get("Status").equals("locked")) {
                        hbox.getChildren().add(Utilities.prepareScene("Test-Template.fxml", info));
                    }
                }
                getScrollPane_tests().setContent(hbox);
                getScrollPane_tests().setFitToWidth(true);
                getScrollPane_tests().setFitToHeight(true);
            } else {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setStyle("-fx-background-color: #435585;");
                for (int i = 1; i <= numberOfTests; i++) {
                    Map<String, String> info = getInfo(i - 1);
                    if (info.get("TestName").equals(string)) {
                        hbox.getChildren().add(Utilities.prepareScene("Test-Template.fxml", info));
                    }
                }
                getScrollPane_tests().setContent(hbox);
                getScrollPane_tests().setFitToWidth(true);
                getScrollPane_tests().setFitToHeight(true);
            }
        }
    }

    private Map<String, String> getInfo(int index) {
        Map<String, String> testInfo = new HashMap<>();
        testInfo.put("TestName", getTestNames().get(index));
        testInfo.put("Status", DBUtilities.getTestStatus(getTestIDs().get(index)));
        testInfo.put("Code", DBUtilities.getCodeOfTest(getTestIDs().get(index)));
        testInfo.put("TestID", getTestIDs().get(index));
        testInfo.put("Results", String.valueOf(DBUtilities.getCountOfResultsFromTest(getTestIDs().get(index))));
        testInfo.put("Pass", calculate("pass", index));
        testInfo.put("Fail", calculate("fail", index));
        testInfo.put("Average", String.format("%.1f", (double) DBUtilities.getSumOfScores(getTestIDs().get(index)) / DBUtilities.getCountOfResultsFromTest(getTestIDs().get(index))));
        testInfo.put("DateCreated", DBUtilities.getTestDateCreation(getTestIDs().get(index)));
        return testInfo;
    }

    private String calculate(String status, int index) {
        double allResults = DBUtilities.getCountOfResultsFromTest(getTestIDs().get(index));
        if (status.equals("pass") || status.equals("fail"))
            if (allResults > 0) {
                double statusResults = DBUtilities.getCountOfStatusResultsFromTest(status, getTestIDs().get(index));
                if (statusResults > 0) {
                    double percentage = statusResults / allResults * 100;
                    return String.valueOf(Math.round(percentage));
                } else {
                    return "0";
                }
            }
        return "??";
    }

    protected void setData(int testsCount, ArrayList<String> testIDs, ArrayList<String> testNames) {
        setTestsCount(testsCount);
        setTestIDs(testIDs);
        setTestNames(testNames);
        loadAllTestTemplates(getTestsCount());
    }

    private void setTestsCount(int testsCount) {
        this.testsCount = testsCount;
    }

    private int getTestsCount() {
        return this.testsCount;
    }

    private ArrayList<String> getTestIDs() {
        return this.testIDs;
    }

    private void setTestIDs(ArrayList<String> testIDs) {
        this.testIDs = testIDs;
    }

    private ArrayList<String> getTestNames() {
        return this.testNames;
    }

    private void setTestNames(ArrayList<String> testNames) {
        this.testNames = testNames;
    }

    private ScrollPane getScrollPane_tests() {
        return this.scrollPane_tests;
    }

    private TextField getTextField_search() {
        return this.textField_search;
    }

    private Button getButton_search() {
        return this.button_search;
    }

    private Button getButton_back() {
        return this.button_back;
    }
}
