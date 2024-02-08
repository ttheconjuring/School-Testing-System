package com.example.spge_sts;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private PieChart pieChart_pass_fail;

    @FXML
    private PieChart pieChart_points;

    // ================================================== \\

    /* PieChart data containers */

    private ObservableList<PieChart.Data> pieChart_pass_fail_data;
    private ObservableList<PieChart.Data> pieChart_points_data;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private PieChart getPieChart_pass_fail() {
        return this.pieChart_pass_fail;
    }

    private PieChart getPieChart_points() {
        return this.pieChart_points;
    }

    protected void setDataToPieChartPassFail(ObservableList<PieChart.Data> pieChart_pass_fail_data) {
        getPieChart_pass_fail().getData().addAll(pieChart_pass_fail_data);
    }

    protected void setDataToPieChartPoints(ObservableList<PieChart.Data> pieChart_points_data) {
        getPieChart_points().getData().addAll(pieChart_points_data);
    }
}
