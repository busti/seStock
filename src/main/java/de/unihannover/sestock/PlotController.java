package de.unihannover.sestock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlotController implements Initializable {

    @FXML
    private LineChart<?, ?> LineChartStock;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Button CloseStockChartButton;

    public void initialize(URL url, ResourceBundle rb){
        //Open series
        XYChart.Series open = new XYChart.Series();
        open.getData().add(new XYChart.Data("2021-08-25", 139.9200));
        open.getData().add(new XYChart.Data("2021-08-26", 139.9700));
        open.getData().add(new XYChart.Data("2021-08-27", 138.7100));
        open.getData().add(new XYChart.Data("2021-08-28", 135.2200));
        open.getData().add(new XYChart.Data("2021-08-29", 136.4200));
        open.getData().add(new XYChart.Data("2021-08-30", 137.1200));
        open.getData().add(new XYChart.Data("2021-08-31", 133.9200));

        //Close series
        XYChart.Series close = new XYChart.Series();
        close.getData().add(new XYChart.Data("2021-08-25", 110.9200));
        close.getData().add(new XYChart.Data("2021-08-26", 108.9200));
        close.getData().add(new XYChart.Data("2021-08-27", 107.9200));
        close.getData().add(new XYChart.Data("2021-08-28", 113.9200));
        close.getData().add(new XYChart.Data("2021-08-29", 80.9200));
        close.getData().add(new XYChart.Data("2021-08-30", 79.9200));
        close.getData().add(new XYChart.Data("2021-08-31", 101.9200));

        //High series
        XYChart.Series high = new XYChart.Series();
        high.getData().add(new XYChart.Data("2021-08-25", 150.9200));
        high.getData().add(new XYChart.Data("2021-08-26", 155.9200));
        high.getData().add(new XYChart.Data("2021-08-27", 129.9200));
        high.getData().add(new XYChart.Data("2021-08-28", 159.9200));
        high.getData().add(new XYChart.Data("2021-08-29", 160.9200));
        high.getData().add(new XYChart.Data("2021-08-30", 165.9200));
        high.getData().add(new XYChart.Data("2021-08-31", 168.9200));

        //Close series
        XYChart.Series low = new XYChart.Series();
        low.getData().add(new XYChart.Data("2021-08-25", 170.9200));
        low.getData().add(new XYChart.Data("2021-08-26", 171.9200));
        low.getData().add(new XYChart.Data("2021-08-27", 175.9200));
        low.getData().add(new XYChart.Data("2021-08-28", 173.9200));
        low.getData().add(new XYChart.Data("2021-08-29", 180.9200));
        low.getData().add(new XYChart.Data("2021-08-30", 165.9200));
        low.getData().add(new XYChart.Data("2021-08-31", 190.9200));


        LineChartStock.getData().addAll(open);
        LineChartStock.getData().addAll(close);
        LineChartStock.getData().addAll(high);
        LineChartStock.getData().addAll(low);

    }

    Stage plotStage;
    public void getStage(Stage stage){
        this.plotStage = stage;
    }

    public void onCloseButtonClick(ActionEvent event){
        plotStage.close();
    }

}
