package com.example.spge_sts;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AnswerQuestionController implements Initializable {

    // ================================================== \\

    /*Visual elements*/

    @FXML
    private Label label_questionNumber;

    @FXML
    private Label label_points;

    @FXML
    private Label label_question;

    @FXML
    private Label label_timer;

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

    // ================================================== \\

    /*Variables that store information I need to handle some
     * specific operations/cases and send queries as well*/

    private String questionID;

    private String questionType;

    private String correctAnswer;

    private String testID;

    // ================================================== \\

    /* Variables that helps me to change the questions automatically,
    after the response time expires and keep track of how much time
    left before this event
     */
    private boolean submitButtonTriggered = false;

    // ================================================== \\

    /* Variables I need to refresh the timer label text and
    * keep track of how much time a student has until the
    * question changes automatically */

    private int timeLeft = Utilities.getResponseTimeInMinutes() * 60;
    private final SimpleStringProperty timerText = new SimpleStringProperty();

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* preparation */
        setTimeForResponse(Utilities.getResponseTimeInMinutes());
        updateTimerLabel();

        /* core functionality  */
        getButton_submit().setOnAction(actionEvent -> {
            setSubmitButtonTriggered();
            saveResponse(getResponseData());
            proceedToNextQuestion(actionEvent);
        });
    }

    private void setTimeForResponse(int minutes) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(minutes), event -> {
            if (!isSubmitButtonTriggered()) {
                getButton_submit().fire();
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void updateTimerLabel() {
        timerText.set(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));
        getLabel_timer().textProperty().bind(timerText);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            timerText.set(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setSubmitButtonTriggered() {
        this.submitButtonTriggered = true;
    }

    private void saveResponse(String[] responseData) {
        boolean flag = DBUtilities.createResponse(
                responseData[0],
                responseData[1],
                responseData[2],
                responseData[3],
                responseData[4],
                responseData[5]);
        if (!flag) {
            Utilities.showAlert("Response Error!", "Sorry, something went wrong!", Alert.AlertType.ERROR);
        }
    }

    private String[] getResponseData() {
        String[] responseData = new String[6];

        String responseText;
        String selectedOptions;
        if (getQuestionType().equals("closed")) {
            if (radioButton_answer_1.isSelected()) {
                responseText = radioButton_answer_1.getText();
                selectedOptions = "a)";
            } else if (radioButton_answer_2.isSelected()) {
                responseText = radioButton_answer_2.getText();
                selectedOptions = "b)";
            } else if (radioButton_answer_3.isSelected()) {
                responseText = radioButton_answer_3.getText();
                selectedOptions = "c)";
            } else if (radioButton_answer_4.isSelected()) {
                responseText = radioButton_answer_4.getText();
                selectedOptions = "d)";
            } else {
                responseText = null;
                selectedOptions = null;
            }
        } else {
            responseText = getTextField_answer().getText();
            selectedOptions = null;
        }

        String isCorrect;
        if (responseText == null) {
            isCorrect = "false";
        } else {
            if (responseText.equals(getCorrectAnswer())) {
                isCorrect = "true";
            } else {
                isCorrect = "false";
            }
        }

        responseData[0] = getQuestionID();
        responseData[1] = Utilities.getCurrenUserID();
        responseData[2] = responseText;
        responseData[3] = selectedOptions;
        responseData[4] = isCorrect;
        responseData[5] = Utilities.getCurrentDateAndTime();

        return responseData;
    }

    private void proceedToNextQuestion(ActionEvent actionEvent) {
        Utilities.setQuestionIndex(Utilities.getQuestionIndex() + 1);
        if (Utilities.getQuestionIndex() != Utilities.getQuestionsCount()) {
            Utilities.switchToPreparedScene(Utilities.prepareScene("Answer-Question.fxml", Utilities.getQuestionDataByIndex(Utilities.getQuestionIndex())), actionEvent);
        } else {
            saveResult();
            Utilities.showAlert("Test Complete!", endMessage(), Alert.AlertType.INFORMATION); // WORKS ONLY IF THE SUBMIT BUTTON IS  MANUALLY CLICKED ON THE LAST QUESTION !!
            Utilities.switchTo("Students-Home-Page.fxml", actionEvent);
        }
    }

    private void saveResult() {
        if (!DBUtilities.createResult(
                Utilities.getCurrenUserID(),
                getTestID(),
                String.valueOf(DBUtilities.calculatePointsReceivedByStudent(Utilities.getCurrenUserID(), getTestID())),
                Utilities.getCurrentDateAndTime(),
                getDuration(),
                getTestStatus()
        )) {
            Utilities.showAlert("Result Problem!","Sorry, problem occurred when we tried to save your result!", Alert.AlertType.ERROR);
        }
    }

    private String getTestStatus() {
        int pointsReceived = DBUtilities.calculatePointsReceivedByStudent(Utilities.getCurrenUserID(), getTestID());
        int totalPoints = DBUtilities.calculateAllPointsOfATest(getTestID());
        int passingScore = DBUtilities.getPassingScoreOfTestBy(getTestID());
        if (pointsReceived >= passingScore) {
            return "pass";
        } else {
            return "fail";
        }
    }

    private String endMessage() {
        StringBuilder message = new StringBuilder();
        int pointsReceived = DBUtilities.calculatePointsReceivedByStudent(Utilities.getCurrenUserID(), getTestID());
        int totalPoints = DBUtilities.calculateAllPointsOfATest(getTestID());
        int passingScore = DBUtilities.getPassingScoreOfTestBy(getTestID());
        if (pointsReceived >= passingScore) {
            message.append("Congratulations! You have SUCCESSFULLY competed the test!").append("\n");
        } else {
            message.append("Sorry, you have FAILED your test.").append("\n");
        }
        message.append("Your result: ").append(pointsReceived).append("/").append(totalPoints).append("\n");
        message.append("Passing Score: ").append(passingScore);
        return message.toString();
    }

    private String getDuration() {
        /* calculates the time between the first click of the button submit to the last one.
        * Bug: the time before the first click of button submit is not calculated/included */
        int totalDurationInSeconds = DBUtilities.calculateDurationInSeconds(getTestID(), Utilities.getCurrenUserID());
        int durationInMinutes = totalDurationInSeconds / 60;
        int durationInSeconds = totalDurationInSeconds % 60;
        String duration;
        if (durationInMinutes <= 9) {
            if (durationInSeconds <= 9) {
                duration = "0" + durationInMinutes + ":0" + durationInSeconds;
            } else {
                duration = "0" + durationInMinutes + ":" + durationInSeconds;
            }
        } else {
            if (durationInSeconds <= 9) {
                duration = durationInMinutes + ":0" + durationInSeconds;
            } else {
                duration = durationInMinutes + ":" + durationInSeconds;
            }
        }
        return duration;
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

        setQuestionID(questionData.get("QuestionID"));
        setQuestionType(questionData.get("QuestionType"));
        setCorrectAnswer(questionData.get("CorrectAnswer"));
        setTestID(questionData.get("TestID"));
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

    private Label getLabel_timer() {
        return this.label_timer;
    }

    private String getQuestionID() {
        return this.questionID;
    }

    private void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    private String getQuestionType() {
        return this.questionType;
    }

    private void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    private String getCorrectAnswer() {
        return this.correctAnswer;
    }

    private void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String getTestID() {
        return this.testID;
    }

    private void setTestID(String testID) {
        this.testID = testID;
    }

    private boolean isSubmitButtonTriggered() {
        return this.submitButtonTriggered;
    }

}
