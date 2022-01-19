package de.unihannover.sestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    }

    @FXML
    private Button plotterBtn;
    @FXML
    private LineChart<String ,Number> lineChart;

    public void handlePlotterBtnClick(){
        String t = "";
        AlphavantageApi.TimeSeriesWrapper data = null;
        try {
            data = AlphavantageApi.getInstance().time_series_daily(stockName.getEditor().getText());
            t = data.metadata.symbol.toUpperCase();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Parent chartParent;
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time Series days");
        yAxis.setLabel("Price");
        LineChart<String ,Number> lineChart = new LineChart<String ,Number>(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        for(AlphavantageApi.TimeSeriesEntry a: data.timeSeriesSorted()){
            LocalDate localDate = a.date.plusMonths(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String formattedString = localDate.format(formatter);
            series.getData().add(new XYChart.Data(formattedString, a.timeSeries.close));
            System.out.println(a.timeSeries.close);
            System.out.println(a);
            System.out.println(formattedString);
        }

        lineChart.getData().add(series);
        try {
            Stage chartsWindow = new Stage();
            chartParent = FXMLLoader.load(getClass().getResource("chart-view.fxml"));

            chartsWindow.setTitle(t);
            chartsWindow.setScene(new Scene(chartParent, 800, 650));
            chartsWindow.show();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}