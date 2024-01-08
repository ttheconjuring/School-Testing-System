package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private TextField textField_openedAnswer;

    @FXML
    private Button button_next;

    @FXML
    private ToggleButton toggleButton_opened;

    @FXML
    private ToggleButton toggleButton_closed;

    private String testID;
    private int questionsCount;
    private String questionType = "closed";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getToggleButton_closed().setOnAction(actionEvent -> setClicked(getToggleButton_closed(), getToggleButton_opened()));
        getToggleButton_opened().setOnAction(actionEvent -> setClicked(getToggleButton_opened(), getToggleButton_closed()));
        getButton_next().setOnAction(this::saveQuestion);
    }

    private void saveQuestion(ActionEvent actionEvent) {
        if (thereAreEmptyGaps()) {
            Utilities.showAlert("Empty gaps", "Please, fill the must gaps!", Alert.AlertType.WARNING);
        } else {
            if (getCurrentQuestionNumber() <= getQuestionsCount()) {
                String points = getTextField_points().getText();
                if (!points.isEmpty() && (Integer.parseInt(points) >= 1 && Integer.parseInt(points) <= 10)) {
                    String question = getTextField_question().getText();
                    if (!question.isEmpty()) {
                        String trueAnswer = getTextField_trueAnswer().getText();
                        if (!trueAnswer.isEmpty()) {
                            if (getToggleButton_closed().isSelected()) {
                                String answer1 = getTextField_answer_1().getText();
                                if (!answer1.isEmpty()) {
                                    String answer2 = getTextField_answer_2().getText();
                                    if (!answer2.isEmpty()) {
                                        String answer3 = getTextField_answer_3().getText();
                                        if (!answer3.isEmpty()) {
                                            String answer4 = getTextField_answer_4().getText();
                                            if (!answer4.isEmpty()) {
                                                response(DBUtilities.createQuestion(
                                                        getTestID(),
                                                        getTextField_question().getText(),
                                                        getQuestionType(),
                                                        getTextField_answer_1().getText() + ", " +
                                                                getTextField_answer_2().getText() + ", " +
                                                                getTextField_answer_3().getText() + ", " +
                                                                getTextField_answer_4().getText(),
                                                        getTextField_trueAnswer().getText(),
                                                        getTextField_points().getText()
                                                ), actionEvent);
                                            } else {
                                                Utilities.showAlert("Invalid Answer #4", "The answer #4 can NOT be empty!", Alert.AlertType.ERROR);
                                            }
                                        } else {
                                            Utilities.showAlert("Invalid Answer #3", "The answer #3 can NOT be empty!", Alert.AlertType.ERROR);
                                        }
                                    } else {
                                        Utilities.showAlert("Invalid Answer #2", "The answer #2 can NOT be empty!", Alert.AlertType.ERROR);
                                    }
                                } else {
                                    Utilities.showAlert("Invalid Answer #1!", "The answer #1 can NOT be empty!", Alert.AlertType.ERROR);
                                }
                            } else {
                                response(DBUtilities.createQuestion(
                                        getTestID(),
                                        getTextField_question().getText(),
                                        getQuestionType(),
                                        getTextField_openedAnswer().getText(),
                                        getTextField_trueAnswer().getText(),
                                        getTextField_points().getText()
                                ), actionEvent);
                            }
                        } else {
                            Utilities.showAlert("Invalid True Answer!", "The true answer can NOT be empty!", Alert.AlertType.ERROR);
                        }
                    } else {
                        Utilities.showAlert("Invalid Question!", "The question field can NOT be empty!", Alert.AlertType.ERROR);
                    }
                } else {
                    Utilities.showAlert("Invalid Points!", "The points must be between 1 and 10!", Alert.AlertType.ERROR);
                    getTextField_points().clear();
                }

            }
        }
    }

    private void response(boolean flag, ActionEvent actionEvent) {
        if (flag) {
            if (getCurrentQuestionNumber() == getQuestionsCount()) {
                Utilities.showAlert("Complete Test and Questions Creation!", "Congratulations, test is ready! You can check it at \"My Tests\".", Alert.AlertType.INFORMATION);
                Utilities.switchToPreparedScene(Utilities.prepareScene("Teachers-Home-Page.fxml", Utilities.getCurrenUserID()), actionEvent);
            } else {
                Utilities.switchToPreparedScene(Utilities.prepareScene("Create-Question.fxml", String.valueOf(getCurrentQuestionNumber() + 1)), actionEvent);
            }
        } else {
            Utilities.showAlert("Error!", "Sorry, but you probably have invalid data", Alert.AlertType.ERROR);
            clearFields();
        }
    }

    private int getCurrentQuestionNumber() {
        return Integer.parseInt(getLabel_questionNumber().getText().substring(1));
    }

    private void clearFields() {
        getTextField_points().clear();
        getTextField_question().clear();
        getTextField_answer_1().clear();
        getTextField_answer_2().clear();
        getTextField_answer_3().clear();
        getTextField_answer_4().clear();
        getTextField_openedAnswer().clear();
        getTextField_trueAnswer().clear();
    }

    private void setClicked(ToggleButton clickedButton, ToggleButton otherButton) {
        clickedButton.setSelected(true);
        clickedButton.setOpacity(1);
        otherButton.setOpacity(0.5);
        otherButton.setSelected(false);
        revealTextFields();
    }

    private boolean thereAreEmptyGaps() {
        boolean emptyGaps;
        if (getToggleButton_closed().isSelected()) {
            emptyGaps = getTextField_points().getText().isEmpty() ||
                    getTextField_question().getText().isEmpty() ||
                    getTextField_answer_1().getText().isEmpty() ||
                    getTextField_answer_2().getText().isEmpty() ||
                    getTextField_answer_3().getText().isEmpty() ||
                    getTextField_answer_4().getText().isEmpty() ||
                    getTextField_trueAnswer().getText().isEmpty();
        } else {
            emptyGaps = getTextField_points().getText().isEmpty() ||
                    getTextField_question().getText().isEmpty() ||
                    getTextField_trueAnswer().getText().isEmpty();
        }
        return emptyGaps;
    }


    private void revealTextFields() {
        if (getToggleButton_closed().isSelected()) {
            getTextField_answer_1().setDisable(false);
            getTextField_answer_1().setOpacity(1);
            getTextField_answer_2().setDisable(false);
            getTextField_answer_2().setOpacity(1);
            getTextField_answer_3().setDisable(false);
            getTextField_answer_3().setOpacity(1);
            getTextField_answer_4().setDisable(false);
            getTextField_answer_4().setOpacity(1);
            getTextField_openedAnswer().setDisable(true);
            getTextField_openedAnswer().setOpacity(0);
            setQuestionType("closed");
        } else {
            getTextField_answer_1().setDisable(true);
            getTextField_answer_1().setOpacity(0);
            getTextField_answer_2().setDisable(true);
            getTextField_answer_2().setOpacity(0);
            getTextField_answer_3().setDisable(true);
            getTextField_answer_3().setOpacity(0);
            getTextField_answer_4().setDisable(true);
            getTextField_answer_4().setOpacity(0);
            getTextField_openedAnswer().setDisable(false);
            getTextField_openedAnswer().setOpacity(1);
            setQuestionType("opened");
        }
    }

    protected void setQuestionNumber(String number) {
        getLabel_questionNumber().setText("#" + number);
    }

    private Label getLabel_questionNumber() {
        return this.label_questionNumber;
    }

    private TextField getTextField_points() {
        return this.textField_points;
    }

    private TextField getTextField_question() {
        return this.textField_question;
    }

    private TextField getTextField_answer_1() {
        return this.textField_answer_1;
    }

    private TextField getTextField_answer_2() {
        return this.textField_answer_2;
    }

    private TextField getTextField_answer_3() {
        return this.textField_answer_3;
    }

    private TextField getTextField_answer_4() {
        return this.textField_answer_4;
    }

    private TextField getTextField_trueAnswer() {
        return this.textField_trueAnswer;
    }

    private Button getButton_next() {
        return this.button_next;
    }

    private TextField getTextField_openedAnswer() {
        return this.textField_openedAnswer;
    }

    private ToggleButton getToggleButton_opened() {
        return this.toggleButton_opened;
    }

    private ToggleButton getToggleButton_closed() {
        return this.toggleButton_closed;
    }

    private String getTestID() {
        return this.testID;
    }

    private void setTestID(String testID) {
        this.testID = testID;
    }

    private int getQuestionsCount() {
        return questionsCount;
    }

    private void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    protected void setData() {
        String data = DBUtilities.getLastInsertedTestID();
        String[] dataArray = data.split(" ");
        setTestID(dataArray[0]);
        setQuestionsCount(Integer.parseInt(dataArray[1]));
    }

    private String getQuestionType() {
        return this.questionType;
    }

    private void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}

