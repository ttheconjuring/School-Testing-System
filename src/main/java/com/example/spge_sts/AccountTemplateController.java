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

    /* ? */

    private String ID;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* core functionality*/
        getButton_view().setOnAction(actionEvent -> Utilities.popUpNewWindow(Utilities.prepareScene("Teachers-Account-Information.fxml", DBUtilities.getUserData(getID()))));
    }

    protected void setData(Map<String, String> data) {
        this.setLabel_idText("#" + data.get("UserID"));
        this.setID(data.get("UserID"));
        this.setLabel_username_userRoleText(data.get("Username") + " (" + data.get("UserRole") + ")");
    }

    private void setLabel_idText(String text) {
        getLabel_id().setText(text);
    }

    private void setLabel_username_userRoleText(String text) {
        getLabel_username_userRole().setText(text);
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
