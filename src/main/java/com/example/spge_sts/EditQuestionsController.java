package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditQuestionsController implements Initializable {

    @FXML
    private TextField textField_question;

    @FXML
    private TextField textField_question_1;

    @FXML
    private TextField textField_question_2;

    @FXML
    private TextField textField_question_3;

    @FXML
    private TextField textField_question_4;

    @FXML
    private Button button_save;

    @FXML
    private Button button_save_1;

    @FXML
    private Button button_save_2;

    @FXML
    private Button button_save_3;

    @FXML
    private Button button_save_4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    protected void setData(List<Map<String, String>> questionsData) {
        List<String> questions = new ArrayList<>();
        for (Map<String, String> questionsDatum : questionsData) {
            questions.add(String.format("%s | %s | %s | %s",
                    questionsDatum.get("question_text"),
                    questionsDatum.get("answers"),
                    questionsDatum.get("correct_answer"),
                    questionsDatum.get("points")));
        }
        if (questions.size() >= 5) {
            getTextField_question().setText(questions.getFirst());
            getTextField_question_1().setText(questions.get(1));
            getTextField_question_2().setText(questions.get(2));
            getTextField_question_3().setText(questions.get(3));
            getTextField_question_4().setText(questions.get(4));
        } else if (questions.size() == 4) {
            getTextField_question().setText(questions.getFirst());
            getTextField_question_1().setText(questions.get(1));
            getTextField_question_2().setText(questions.get(2));
            getTextField_question_3().setText(questions.get(3));
            getTextField_question_4().setDisable(true);
        } else if (questions.size() == 3) {
            getTextField_question().setText(questions.getFirst());
            getTextField_question_1().setText(questions.get(1));
            getTextField_question_2().setText(questions.get(2));
            getTextField_question_3().setDisable(true);
            getTextField_question_4().setDisable(true);
        } else if (questions.size() == 2) {
            getTextField_question().setText(questions.getFirst());
            getTextField_question_1().setText(questions.get(1));
            getTextField_question_2().setDisable(true);
            getTextField_question_3().setDisable(true);
            getTextField_question_4().setDisable(true);
        } else {
            getTextField_question().setText(questions.getFirst());
            getTextField_question_1().setDisable(true);
            getTextField_question_2().setDisable(true);
            getTextField_question_3().setDisable(true);
            getTextField_question_4().setDisable(true);
        }
    }


    public TextField getTextField_question() {
        return textField_question;
    }

    public TextField getTextField_question_1() {
        return textField_question_1;
    }

    public TextField getTextField_question_2() {
        return textField_question_2;
    }

    public TextField getTextField_question_3() {
        return textField_question_3;
    }

    public TextField getTextField_question_4() {
        return textField_question_4;
    }

    public Button getButton_save() {
        return button_save;
    }

    public Button getButton_save_1() {
        return button_save_1;
    }

    public Button getButton_save_2() {
        return button_save_2;
    }

    public Button getButton_save_3() {
        return button_save_3;
    }

    public Button getButton_save_4() {
        return button_save_4;
    }

}
