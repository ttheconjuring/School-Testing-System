package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AnswerQuestionController implements Initializable {

    @FXML
    private Label label_questionNumber;

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
        getButton_submit().setOnAction(this::proceedToNextQuestion);
    }

    private void proceedToNextQuestion(ActionEvent actionEvent) {
        Utilities.setQuestionIndex(Utilities.getQuestionIndex() + 1);
        if (Utilities.getQuestionIndex() != Utilities.getQuestionsCount()) {
            Utilities.switchToPreparedScene(Utilities.prepareScene("Answer-Question.fxml", Utilities.getQuestionDataByIndex(Utilities.getQuestionIndex())), actionEvent);
        } else {
            Utilities.showAlert("Test Complete!", "You have reached the end!", Alert.AlertType.INFORMATION);
            Utilities.switchToPreparedScene(Utilities.prepareScene("Students-Home-Page.fxml", Utilities.getCurrenUserID()), actionEvent);
        }
    }

    private Label getLabel_questionNumber() {
        return this.label_questionNumber;
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

    private void hideRadioButtons() {
        getRadioButton_answer_1().setDisable(true);
        getRadioButton_answer_1().setOpacity(0);
        getRadioButton_answer_2().setDisable(true);
        getRadioButton_answer_2().setOpacity(0);
        getRadioButton_answer_3().setDisable(true);
        getRadioButton_answer_3().setOpacity(0);
        getRadioButton_answer_4().setDisable(true);
        getRadioButton_answer_4().setOpacity(0);

        getTextField_answer().setDisable(false);
        getTextField_answer().setOpacity(1);
    }

    private void hideOpenAnswer() {
        getRadioButton_answer_1().setDisable(false);
        getRadioButton_answer_1().setOpacity(1);
        getRadioButton_answer_2().setDisable(false);
        getRadioButton_answer_2().setOpacity(1);
        getRadioButton_answer_3().setDisable(false);
        getRadioButton_answer_3().setOpacity(1);
        getRadioButton_answer_4().setDisable(false);
        getRadioButton_answer_4().setOpacity(1);

        getTextField_answer().setDisable(true);
        getTextField_answer().setOpacity(0);
    }

    protected void setQuestionData(Map<String, String> questionData) {
        getLabel_questionNumber().setText("#" + questionData.get("Number"));
        getLabel_points().setText("p." + questionData.get("Points"));
        getLabel_question().setText(questionData.get("QuestionText"));
        if (questionData.get("QuestionType").equals("closed")) {
            hideOpenAnswer();
            String[] answers = questionData.get("Answers").split(", ");
            getRadioButton_answer_1().setText(answers[0]);
            getRadioButton_answer_2().setText(answers[1]);
            getRadioButton_answer_3().setText(answers[2]);
            getRadioButton_answer_4().setText(answers[3]);
        } else {
            hideRadioButtons();
            getTextField_answer().clear();
        }
    }
}
