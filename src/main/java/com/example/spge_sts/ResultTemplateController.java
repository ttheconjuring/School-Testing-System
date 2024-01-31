package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultTemplateController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private Label label_result;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    protected void setData(String result) {
        getLabel_result().setText(result);
    }

    private Label getLabel_result() {
        return this.label_result;
    }
}
