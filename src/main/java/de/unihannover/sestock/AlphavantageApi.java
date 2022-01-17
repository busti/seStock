package de.unihannover.sestock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

public class AlphavantageApi {
    private static AlphavantageApi instance = new AlphavantageApi();

    public static AlphavantageApi getInstance() {
        return instance;
    }

    private static String API_KEY = "ZD3ECEFKA5VV0RWG";
    private static String API_URI = "https://www.alphavantage.co/query?apikey=" + API_KEY;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    private String makeRequest(String query) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(URI.create(API_URI + query)).build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public List<String> symbol_search(String keyword) throws IOException, InterruptedException {
        var response = makeRequest("&function=SYMBOL_SEARCH&keywords=" + keyword);
        var data = gson.fromJson(response, Map.class);
        var symbols = (List<Map<String, String>>) data.get("bestMatches");
        return symbols.stream().map(m -> m.get("1. symbol")).collect(java.util.stream.Collectors.toList());
    }

    public static class Metadata {
        @SerializedName("1. Information")
        public String information;

        @SerializedName("2. Symbol")
        public String symbol;

        @Override
        public String toString() {
            return "Metadata{" +
                    "information='" + information + '\'' +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }

    public static class TimeSeries {
        // public String date;
        @SerializedName("1. open")
        public double open;

        @SerializedName("2. high")
        public double high;

        @SerializedName("3. low")
        public double low;

        @SerializedName("4. close")
        public double close;

        @SerializedName("5. volume")
        public double volume;

        @Override
        public String toString() {
            return "TimeSeries{" +
                    "open=" + open +
                    ", high=" + high +
                    ", low=" + low +
                    ", close=" + close +
                    ", volume=" + volume +
                    '}';
        }
    }

    public static class TimeSeriesEntry {
        public LocalDate date;
        public TimeSeries timeSeries;

        public TimeSeriesEntry(LocalDate date, TimeSeries timeSeries) {
            this.date = date;
            this.timeSeries = timeSeries;
        }

        @Override
        public String toString() {
            return "TimeSeriesEntry{" +
                    "date=" + date +
                    ", timeSeries=" + timeSeries +
                    '}';
        }
    }

    public static class TimeSeriesWrapper {
        @SerializedName("Meta Data")
        public Metadata metadata;

        @SerializedName("Time Series (Daily)")
        public Map<String, TimeSeries> timeSeriesMap;

        public List<TimeSeriesEntry> timeSeriesSorted() {
            return timeSeriesMap
                    .entrySet()
                    .stream()
                    .map(e -> new TimeSeriesEntry(
                            LocalDate.parse(e.getKey()),
                            e.getValue()
                    ))
                    .sorted(Comparator.comparing(a -> a.date))
                    .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public String toString() {
            return "TimeSeriesWrapper{" +
                    "metadata=" + metadata +
                    ", timeSeries=" + timeSeriesMap +
                    '}';
        }
    }

    public TimeSeriesWrapper time_series_daily(String symbol) throws IOException, InterruptedException {
        var response = makeRequest("&function=TIME_SERIES_DAILY&symbol=" + symbol);
        var data = gson.fromJson(response, TimeSeriesWrapper.class);
        return data;
    }

    public TimeSeriesWrapper time_series_weekly(String symbol) throws IOException, InterruptedException {
        var response = makeRequest("&function=TIME_SERIES_WEEKLY&symbol=" + symbol);
        var data = gson.fromJson(response, TimeSeriesWrapper.class);
        return data;
    }

    public TimeSeriesWrapper time_series_monthly(String symbol) throws IOException, InterruptedException {
        var response = makeRequest("&function=TIME_SERIES_MONTHLY&symbol=" + symbol);
        var data = gson.fromJson(response, TimeSeriesWrapper.class);
        return data;
    }
}
