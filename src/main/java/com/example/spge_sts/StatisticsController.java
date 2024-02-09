package com.example.spge_sts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    // ================================================== \\

    /* visual elements */

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Integer> barChart;

    // ================================================== \\

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private PieChart getPieChart_pass_fail() {
        return this.pieChart;
    }

    protected void setDataToPieChart(ObservableList<PieChart.Data> pieChart) {
        getPieChart_pass_fail().getData().addAll(pieChart);
    }

    private BarChart<String, Integer> getBarChart() { return this.barChart; }

    protected void setDataToBarChart(XYChart.Series<String, Integer> series) {
        getBarChart().getData().addAll(series);
    }

}
