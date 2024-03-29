package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private ScrollPane scrollPane_results;

    // ================================================== \\

    /* useful */
    private int resultsCount;

    private ArrayList<String> testLeaderboard;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadAllResultTemplates(int numberOfResults) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #435585");
        for (int i = 0; i < numberOfResults; i++) {
            vbox.getChildren().add(Utilities.prepareScene("Result-Template.fxml", getTestLeaderboard().get(i)));
        }
        getScrollPane_results().setContent(vbox);
        getScrollPane_results().setFitToWidth(true);
        getScrollPane_results().setFitToHeight(true);
    }

    protected void setData(int resultsCount, ArrayList<String> testLeaderboard) {
        setResultsCount(resultsCount);
        setTestLeaderboard(testLeaderboard);
        loadAllResultTemplates(getResultsCount());
    }

    private ScrollPane getScrollPane_results() {
        return this.scrollPane_results;
    }

    private int getResultsCount() {
        return this.resultsCount;
    }

    private void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    private ArrayList<String> getTestLeaderboard() {
        return this.testLeaderboard;
    }

    private void setTestLeaderboard(ArrayList<String> testLeaderboard) {
        this.testLeaderboard = testLeaderboard;
    }
}
