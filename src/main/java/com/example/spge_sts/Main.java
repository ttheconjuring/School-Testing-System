package com.example.spge_sts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("school.png"))));
        stage.setTitle("SPGE-STS");
        stage.setScene(scene);
        stage.setResizable(false);
        Utilities.openNewStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    

    /*
    BUGS:
        1.The duration_minutes column in table results shows incorrect time, because it does not count
    the time before the first click on button submit;
        2.When a student is doing a test and does not respond to the last question manually, a.k.a. click
    the submit button, the program saves the response and the result, but does not redirect to the home
    page;
        3.When you load Teachers-Account-Information.fxml with custom data, the first name TextField is
    always selected;
        4.When you try to edit the username, first name or last name of admin, it doesn't work
        5.When you enter non-existing test code, it shows inappropriate message
    */
}