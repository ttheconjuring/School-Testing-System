package com.example.spge_sts;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    private Utilities() {
    }

    private static final List<Stage> openedStages = new ArrayList<>();
    private static String currenUserID;

    protected static String getCurrenUserID() {
        return currenUserID;
    }

    protected static void setCurrenUserID(String currenID) {
        Utilities.currenUserID = currenID;
    }

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
            case "Users-ScrollPane.fxml" -> {
                // in this case we don't use ID, but the method returns Parent root and that is why
                UsersScrollPaneController usersScrollPaneController = loader.getController();
                usersScrollPaneController.setRecordsCount(DBUtilities.getCountOfUsers());
            }
            case "Account-Template.fxml" -> {
                AccountTemplateController accountTemplateController = loader.getController();
                if (ID.length() >= 2) { // if there is more than 10 users, this will be a problem
                    accountTemplateController.setData(DBUtilities.getUserData(ID));
                } else {
                    // The ID here is index, it is not supposed to be used like that, corner case
                    accountTemplateController.setData(DBUtilities.getIdUsernameRoleByIndex(Integer.parseInt(ID)));
                }
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
        }
        return root;
    }

    protected static boolean isValid(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
