package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    private Utilities() {
    }

    // ==========================================================================================

    /* 1.TeachersAccountInformationController

        helps me keep track of the additionally opened stages, so I can close them when it is necessary */

    private static final List<Stage> openedStages = new ArrayList<>();

    private static List<Stage> getOpenedStages() {
        return openedStages;
    }

    protected static void openNewStage(Stage stage) {
        getOpenedStages().add(stage);
    }

    protected static void closeLastStage() {
        if (!getOpenedStages().isEmpty()) {
            Stage lastStage = getOpenedStages().removeLast();
            lastStage.close();
        }
    }

    // ==========================================================================================

    /* 1.AnswerQuestionController
       2.CreateAccountController
       3.CreateQuestionController
       4.CreateTestController
       5.StudentsHomePageController
       6.TeachersAccountInformationController
       7.TeachersHomePageController

        helps me keep tracked of the logged-in user */

    private static String currenUserID;

    protected static String getCurrenUserID() {
        return currenUserID;
    }

    protected static void setCurrenUserID(String currenID) {
        Utilities.currenUserID = currenID;
    }

    // ==========================================================================================

    /* 1.AnswerQuestionController
       2.StudentsHomePageController

        Initially, all that was planned to be in AnswerQuestionController, but it didn't work that way
        and I had to move it here, idk man */

    private static ArrayList<Map<String, String>> questionsData;

    private static int questionIndex = 0;

    protected static int getQuestionIndex() {
        return questionIndex;
    }

    protected static void setQuestionIndex(int index) {
        Utilities.questionIndex = index;
    }

    protected static int getQuestionsCount() {
        return questionsData.size();
    }

    protected static Map<String, String> getQuestionDataByIndex(int index) {return questionsData.get(index);}

    protected static void setQuestionsData(ArrayList<Map<String, String>> questionsData) {
        Utilities.questionsData = questionsData;
    }

    // ==========================================================================================

   /*   This variable helps to automatically proceed to the next question, if the student has not
   *    submitted yet. It is here, because I had trouble to put it somewhere else.*/

    private static int responseTimeInMinutes;

    protected static int getResponseTimeInMinutes() {
        return responseTimeInMinutes;
    }

    protected static void setResponseTimeInMinutes(int responseTimeInMinutes) {Utilities.responseTimeInMinutes = responseTimeInMinutes;}

    // ==========================================================================================

    protected static void switchTo(String fxmlFile, ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Utilities.class.getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void switchToPreparedScene(Parent root, ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    protected static void popUpNewWindow(Parent root) {
        try {
            Stage newStage = new Stage();
            Utilities.openNewStage(newStage);
            newStage.getIcons().add(new Image(Objects.requireNonNull(Utilities.class.getResourceAsStream("school.png"))));
            newStage.setTitle("SPGE-STS");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void showAlert(String title, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    protected static Parent prepareScene(String fxmlFile, String ID) {
        FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlFile));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (fxmlFile) {
            case "Teachers-Home-Page.fxml" -> {
                TeachersHomePageController teachersHomePageController = loader.getController();
                teachersHomePageController.setButton_profile(DBUtilities.getUserData(ID).get("Username"));
            }
            case "Students-Home-Page.fxml" -> {
                StudentsHomePageController studentsHomePageController = loader.getController();
                studentsHomePageController.setButton_profile(DBUtilities.getUserData(ID).get("Username"));
            }
            case "Tests-ScrollPane.fxml" -> {
                TestsScrollPaneController testsScrollPaneController = loader.getController();
                testsScrollPaneController.setData(
                        DBUtilities.getCountOfTestsCreateByUser(ID),
                        DBUtilities.getTestIDsCreateByUser(ID),
                        DBUtilities.getTestNamesCreateByUser(ID)
                );
            }
            case "Users-ScrollPane.fxml" -> {
                // in this case we don't use ID, but the method returns Parent root and that is why
                UsersScrollPaneController usersScrollPaneController = loader.getController();
                usersScrollPaneController.setData(DBUtilities.getCountOfUsers());
            }
            case "Account-Template.fxml" -> {
                AccountTemplateController accountTemplateController = loader.getController();
                if (ID.length() == 4) {
                    accountTemplateController.setData(DBUtilities.getUserData(ID));
                } else {
                    // The ID here is index, it is not supposed to be used like that, corner case
                    accountTemplateController.setData(DBUtilities.getIdUsernameRoleByIndex(Integer.parseInt(ID)));
                }
            }
            case "Create-Question.fxml" -> {
                CreateQuestionController createQuestionController = loader.getController();
                // The ID here is number, it is not supposed to be used like that, corner case
                createQuestionController.setQuestionNumber(ID);
                createQuestionController.setData();
            }
        }
        return root;
    }

    protected static Parent prepareScene(String fxmlFile, Map<String, String> data) {
        FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlFile));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (fxmlFile) {
            case "Teachers-Account-Information.fxml" -> {
                TeachersAccountInformationController teachersAccountInformationController = loader.getController();
                teachersAccountInformationController.setData(data);
            }
            case "Students-Account-Information.fxml" -> {
                StudentsAccountInformationController studentsAccountInformationController = loader.getController();
                studentsAccountInformationController.setData(data);
            }
            case "Answer-Question.fxml" -> {
                AnswerQuestionController answerQuestionController = loader.getController();
                answerQuestionController.setQuestionData(data);
            }
            case "Test-Template.fxml" -> {
                TestTemplateController testTemplateController = loader.getController();
                testTemplateController.setData(data);
            }
        }
        return root;
    }

    protected static boolean isValid(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    protected static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

}
