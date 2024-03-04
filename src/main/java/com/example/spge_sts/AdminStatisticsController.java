package com.example.spge_sts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminStatisticsController implements Initializable {

    @FXML
    private Label label_total_users;

    @FXML
    private Label label_teachers;

    @FXML
    private Label label_students;

    @FXML
    private Label label_total_results;

    @FXML
    private Label label_pass;

    @FXML
    private Label label_fail;

    @FXML
    private Label label_total_responses;

    @FXML
    private Label label_correct;

    @FXML
    private Label label_incorrect;

    @FXML
    private Label label_total_tests;

    @FXML
    private Label label_free;

    @FXML
    private Label label_locked;

    @FXML
    private Label label_average_passing_score;

    @FXML
    private Label label_average_questions_count;

    @FXML
    private Label label_total_questions;

    @FXML
    private Label label_opened;

    @FXML
    private Label label_closed;

    @FXML
    private Label label_average_points;

    @FXML
    private Button button_visualize_statistics;

    @FXML
    private Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getButton_back().setOnAction(actionEvent -> Utilities.switchTo("Admin-Home-Page.fxml", actionEvent));
    }

    private Label getLabel_total_users() {
        return this.label_total_users;
    }

    private Label getLabel_teachers() {
        return this.label_teachers;
    }

    private Label getLabel_students() {
        return this.label_students;
    }

    private Label getLabel_total_results() {
        return this.label_total_results;
    }

    private Label getLabel_pass() {
        return this.label_pass;
    }

    private Label getLabel_fail() {
        return this.label_fail;
    }

    private Label getLabel_total_responses() {
        return this.label_total_responses;
    }

    private Label getLabel_correct() {
        return this.label_correct;
    }

    private Label getLabel_incorrect() {
        return this.label_incorrect;
    }

    private Label getLabel_total_tests() {
        return this.label_total_tests;
    }

    private Label getLabel_free() {
        return this.label_free;
    }

    private Label getLabel_locked() {
        return this.label_locked;
    }

    private Label getLabel_average_passing_score() {
        return this.label_average_passing_score;
    }

    private Label getLabel_average_questions_count() {
        return this.label_average_questions_count;
    }

    private Label getLabel_total_questions() {
        return this.label_total_questions;
    }

    private Label getLabel_opened() {
        return this.label_opened;
    }

    private Label getLabel_closed() {
        return this.label_closed;
    }

    private Label getLabel_average_points() {
        return this.label_average_points;
    }

    private Button getButton_visualize_statistics() {
        return this.button_visualize_statistics;
    }

    private Button getButton_back() {
        return this.button_back;
    }
}
