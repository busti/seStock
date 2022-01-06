package de.unihannover.sestock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpClient;

public class SeStockApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeStockApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("SeStock");
        stage.setScene(scene);
        stage.show();


        try {
            AlphavantageApi.TimeSeriesWrapper data = AlphavantageApi.getInstance().time_series_daily("AAPL");
            System.out.println(data.timeSeries.get("2021-12-31"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}