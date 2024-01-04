package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersScrollPaneController implements Initializable {

    @FXML
    private ScrollPane scrollPane_users;

    @FXML
    private TextField textField_search;

    private int recordsCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadAccountTemplates(int numberOfAccounts) {
        VBox vbox = new VBox();
        for (int i = 1; i <= numberOfAccounts; i++) {
            // String.valueOf(i), because prepareScene(String, String)
            // The ID here is index, it is not supposed to be used like that, corner case
            vbox.getChildren().add(Utilities.prepareScene("Account-Template.fxml", String.valueOf(i)));
        }
        getScrollPane_users().setContent(vbox);
    }

    private int getRecordsCount() {
        return this.recordsCount;
    }

    private ScrollPane getScrollPane_users() {
        return this.scrollPane_users;
    }

    protected void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
        loadAccountTemplates(getRecordsCount());
    }
}
