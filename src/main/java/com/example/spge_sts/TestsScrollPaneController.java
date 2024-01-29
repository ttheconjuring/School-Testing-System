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

    // ================================================== \\

    /* helpful */

    private int testsCount;

    private ArrayList<String> testIDs;

    private ArrayList<String> testNames;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadAllTestTemplates(int numberOfTests) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #8A41AC;");
        for (int i = 1; i <= numberOfTests; i++) {
            hbox.getChildren().add(Utilities.prepareScene("Test-Template.fxml", getInfo(i - 1)));
        }
        getScrollPane_tests().setContent(hbox);
    }

    private Map<String, String> getInfo(int index) {
        Map<String, String> testInfo = new HashMap<>();
        testInfo.put("TestName", getTestNames().get(index));
        testInfo.put("Status", DBUtilities.getTestStatus(getTestIDs().get(index)));
        testInfo.put("TestID", getTestIDs().get(index));
        testInfo.put("Results", String.valueOf(DBUtilities.getCountOfResultsFromTest(getTestIDs().get(index))));
        testInfo.put("Pass", calculate("pass", index));
        testInfo.put("Fail", calculate("fail", index));
        return testInfo;
    }

    private String calculate(String status, int index) {
        double allResults = DBUtilities.getCountOfResultsFromTest(getTestIDs().get(index));
        if (allResults > 0) {
            double statusResults = DBUtilities.getCountOfStatusResultsFromTest(status, getTestIDs().get(index));
            if (statusResults > 0) {
                double percentage = statusResults / allResults * 100;
                return String.format("%.0f", Math.ceil(percentage)) + "%";
            } else {
                return "0%";
            }
        }
        return "??%";
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

}
