package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AnswerQuestionController implements Initializable {

    @FXML
    private Label label_questionNumber;

    @FXML
    private Label label_timer;

    @FXML
    private Label label_points;

    @FXML
    private Label label_question;

    @FXML
    private ToggleGroup answers;

    @FXML
    private RadioButton radioButton_answer_1;

    @FXML
    private RadioButton radioButton_answer_2;

    @FXML
    private RadioButton radioButton_answer_3;

    @FXML
    private RadioButton radioButton_answer_4;

    @FXML
    private TextField textField_answer;

    @FXML
    private Button button_submit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /* TODO: 1.add method to set the data received from Utilities
    *        2.add functionality to button submit - it should repeat the process with the next question ID*/


    private Label getLabel_questionNumber() {
        return this.label_questionNumber;
    }

    private Label getLabel_timer() {
        return this.label_timer;
    }

    private Label getLabel_points() {
        return this.label_points;
    }

    private Label getLabel_question() {
        return this.label_question;
    }

    private ToggleGroup getAnswers() {
        return this.answers;
    }

    private RadioButton getRadioButton_answer_1() {
        return this.radioButton_answer_1;
    }

    private RadioButton getRadioButton_answer_2() {
        return this.radioButton_answer_2;
    }

    private RadioButton getRadioButton_answer_3() {
        return this.radioButton_answer_3;
    }

    private RadioButton getRadioButton_answer_4() {
        return this.radioButton_answer_4;
    }

    private TextField getTextField_answer() {
        return this.textField_answer;
    }

    private Button getButton_submit() {
        return this.button_submit;
    }
}
