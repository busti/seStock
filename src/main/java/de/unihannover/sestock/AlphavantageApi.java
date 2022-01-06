package de.unihannover.sestock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class AlphavantageApi {
    private static AlphavantageApi instance = new AlphavantageApi();

    public static AlphavantageApi getInstance() {
        return instance;
    }

    private static String API_KEY = "ZD3ECEFKA5VV0RWG";
    private static String API_URI = "https://www.alphavantage.co/query?apikey=" + API_KEY;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    private Map<String, Object> getData(String query) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(URI.create(API_URI + query)).build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        var data = gson.fromJson(response, Map.class);
        return data;
    }

    public List<String> symbol_search(String keyword) throws IOException, InterruptedException {
        var data = getData("&function=SYMBOL_SEARCH&keywords=" + keyword);
        var symbols = (List<Map<String, String>>) data.get("bestMatches");
        return symbols.stream().map(m -> m.get("1. symbol")).collect(java.util.stream.Collectors.toList());
    }

    public List<Double> getStockData(String stockName) {
        return List.of();
    }
}
