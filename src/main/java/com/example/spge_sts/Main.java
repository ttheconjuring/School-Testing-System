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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Sign-In.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("school.png"))));
        stage.setTitle("SPGE-STS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /*
    * TODO:
    *  1.Redesign some fxml files, because there are cases where the labels are not long enough to visualize the entire information
    *  2.Implement logic for the search bar in "My Tests"
    *  3.Make better deletion process of account, a.k.a. delete the user responses and results if he is student, or tests if he is teacher.
    *  4.Make possible the deletion of a tests*/

    public static void main(String[] args) {
        launch();
    }

    /*
    BUGS:
        1.The duration_minutes column in table results shows incorrect time, because it does not count
    the time before the first click on button submit.
        2.When a student is doing a test and does not respond to the last question manually, a.k.a. click
    the submit button, the program saves the response and the result, but does not redirect to the home
    page.
    */
}