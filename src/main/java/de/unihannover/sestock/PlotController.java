package de.unihannover.sestock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PlotController {

    @FXML
    private LineChart<?, ?> LineChartStock;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Button CloseStockChartButton;

    @FXML
    private Label stockTitle;

    private String stockName;

    public void updateStockChart(String stName){

        //Open series
        XYChart.Series open = new XYChart.Series();
        open.setName("open");

        //Close series
        XYChart.Series close_series = new XYChart.Series();
        close_series.setName("close");

        //High series
        XYChart.Series high = new XYChart.Series();
        high.setName("high");

        //Close series
        XYChart.Series low = new XYChart.Series();
        low.setName("low");

        System.out.print("\nEndlich angekommen " + stName + "\n");

        stockTitle.setText("Stock Chart "+stName);

        //Filling charts with selected data
        try {
            var data = AlphavantageApi.getInstance().time_series_daily("IBM");
            for (var entry : data.timeSeriesSorted()) {
                low.getData().add(new XYChart.Data(entry.date.format(DateTimeFormatter.ISO_DATE), entry.timeSeries.low));
                high.getData().add(new XYChart.Data(entry.date.format(DateTimeFormatter.ISO_DATE), entry.timeSeries.high));
                open.getData().add(new XYChart.Data(entry.date.format(DateTimeFormatter.ISO_DATE), entry.timeSeries.open));
                close_series.getData().add(new XYChart.Data(entry.date.format(DateTimeFormatter.ISO_DATE), entry.timeSeries.close));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LineChartStock.getData().addAll(open);
        LineChartStock.getData().addAll(close_series);
        LineChartStock.getData().addAll(high);
        LineChartStock.getData().addAll(low);
    }

    Stage plotStage;
    public void getStage(Stage stage){
        this.plotStage = stage;
    }

    public String getStockName(){
        return stockName;
    }

    public void onCloseButtonClick(ActionEvent event){
        LineChartStock.getData().removeAll();
        plotStage.close();
        System.out.println("Closing window " + getStockName());
    }

}
