package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AccountTemplateController implements Initializable {

    // ================================================== \\

    /*Visual elements*/

    @FXML
    private Label label_id;

    @FXML
    private Label label_username_userRole;

    @FXML
    private Button button_view;

    // ================================================== \\

    /* Useful variable that helps me get data about users,
    * ! NOT ALWAYS EQUALS CurrentUserID !  */

    private String ID;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality*/
        getButton_view().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(getID()))));
    }

    protected void setData(Map<String, String> data) {
        getLabel_id().setText("#" + data.get("UserID"));
        getLabel_username_userRole().setText(data.get("Username") + " (" + data.get("UserRole") + ")");
        setID(data.get("UserID"));
    }

    private Label getLabel_id() {
        return this.label_id;
    }

    private Label getLabel_username_userRole() {
        return this.label_username_userRole;
    }

    private Button getButton_view() {
        return this.button_view;
    }

    private String getID() {
        return this.ID;
    }

    private void setID(String ID) {
        this.ID = ID;
    }

}
