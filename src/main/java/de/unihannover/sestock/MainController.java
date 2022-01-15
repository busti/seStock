package de.unihannover.sestock;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class MainController {
    @FXML
    public ComboBox<String> stockName;

    public String stockFooBar = "AAPL";

    @FXML
    protected void onStockNameChange() throws IOException, InterruptedException {
        var text = stockName.getEditor().getText();
        List<String> data = AlphavantageApi.getInstance().symbol_search(text);
        updateComboBoxRecommendations(data);
        stockName.show();
    }

    private void updateComboBoxRecommendations(List<String> recomendations) {
        stockName.getItems().clear();
        stockName.getItems().addAll(recomendations);
    }

    @FXML
    private Label welcomeText;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        //Opening dynamic plot for stock view
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