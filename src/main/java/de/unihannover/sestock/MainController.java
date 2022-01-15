package de.unihannover.sestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private LineChart<?, ?> LineChartStock;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dynamic-plot.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Dynamic Stock Plot");
        stage.setScene(scene);

        stage.show();



    }
}