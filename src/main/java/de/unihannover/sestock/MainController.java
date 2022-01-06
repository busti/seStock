package de.unihannover.sestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    LineChart<Number, Number> chart1;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dynamic-plot.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Dynamic Stock Plot");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        //series1.setName("Series 1");
        series1.getData().add(new XYChart.Data<>(1, 20));
        series1.getData().add(new XYChart.Data<>(2, 100));
        series1.getData().add(new XYChart.Data<>(3, 80));
        //series1.getData().add(new XYChart.Data<>(4, 180));
        //series1.getData().add(new XYChart.Data<>(5, 20));
        //series1.getData().add(new XYChart.Data<>(6, 10));
        chart1.getData().add(series1);

//        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(chart1.getXAxis(), chart1.getYAxis());
//        lineChart.getData().add(series1);

        stage.setScene(scene);
        stage.show();
    }
}