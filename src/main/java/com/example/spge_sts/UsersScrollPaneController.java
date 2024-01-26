package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersScrollPaneController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private ScrollPane scrollPane_users;

    @FXML
    private TextField textField_search;

    @FXML
    private Button button_search;

    // ================================================== \\

    private int usersCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality */
        getButton_search().setOnAction(actionEvent -> search(getTextField_search().getText()));
    }

    private void loadAllAccountTemplates(int numberOfAccounts) {
        VBox vbox = new VBox();
        for (int i = 1; i <= numberOfAccounts; i++) {
            // String.valueOf(i), because prepareScene(String, String)
            // The ID here is index, it is not supposed to be used like that, corner case
            vbox.getChildren().add(Utilities.prepareScene("Account-Template.fxml", String.valueOf(i)));
        }
        getScrollPane_users().setContent(vbox);
    }

    private void search(String string) {
        if (string.isEmpty()) {
            loadAllAccountTemplates(getRecordsCount());
        } else {
            ArrayList<String> usersIDs;
            if (string.matches("\\d+")) {
                usersIDs = DBUtilities.getUsersIdsBy("UserID", string);
            } else if (string.equals("teacher") || string.equals("student")) {
                usersIDs = DBUtilities.getUsersIdsBy("UserRole", string);
            } else {
                usersIDs = DBUtilities.getUsersIdsBy("Username", string);
            }
            VBox vbox = new VBox();
            usersIDs.forEach(id -> vbox.getChildren().add(Utilities.prepareScene("Account-Template.fxml", id)));
            getScrollPane_users().setContent(vbox);
        }
    }

    protected void setData(int usersCount) {
        setUsersCount(usersCount);
        loadAllAccountTemplates(getRecordsCount());
    }

    private void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    private int getRecordsCount() {
        return this.usersCount;
    }

    private ScrollPane getScrollPane_users() {
        return this.scrollPane_users;
    }

    private TextField getTextField_search() {
        return this.textField_search;
    }

    private Button getButton_search() {
        return this.button_search;
    }
}
