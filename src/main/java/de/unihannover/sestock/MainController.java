package de.unihannover.sestock;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class MainController {
    @FXML
    public ComboBox<String> stockName;

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
}