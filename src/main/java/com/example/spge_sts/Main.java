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

    /* TODO:
        3.Handle results queries *
         */

    public static void main(String[] args) {
        launch();
    }
}