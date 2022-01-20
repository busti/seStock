package de.unihannover.sestock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
            var data = AlphavantageApi.getInstance().time_series_daily("TSLA");
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(Stage stage, String stock) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SeStockApplication.class.getResource("print-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Dies ist ein Drucker :-)");
        Label lb = (Label) scene.lookup("#stock");
        lb.setText(stock);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}