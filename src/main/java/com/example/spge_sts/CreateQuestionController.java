package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateQuestionController implements Initializable {

    @FXML
    private Label label_questionNumber;

    @FXML
    private TextField textField_points;

    @FXML
    private TextField textField_question;

    @FXML
    private TextField textField_answer_1;

    @FXML
    private TextField textField_answer_2;

    @FXML
    private TextField textField_answer_3;

    @FXML
    private TextField textField_answer_4;

    @FXML
    private TextField textField_trueAnswer;

    @FXML
    private Button button_next;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
