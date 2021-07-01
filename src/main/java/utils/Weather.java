package utils;

import com.google.gson.*;
import org.apache.http.client.utils.URIBuilder;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public final class Weather {
    private Weather(){ }

    private static final String API_KEY = "bd77d9040c6d43b141e89016c0f580ce";

    private static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private static JsonObject callAPI(String cityName){
        var jsonObject = new JsonObject();

        try {
            var uri = new URIBuilder("https://api.openweathermap.org/data/2.5/weather");
            uri.addParameter("q", cityName);
            uri.addParameter("units", "metric");
            uri.addParameter("appid", API_KEY);

            final HttpRequest requestPost = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uri.build().toString()))
                    .build();

            final HttpResponse<String> response = httpClient.send(requestPost, HttpResponse.BodyHandlers.ofString());

            jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

        } catch (URISyntaxException | IOException | InterruptedException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return jsonObject;
    }

    public static String getLocalWeather(String cityName){
        var json = callAPI(cityName);

        if (json.entrySet().isEmpty()) return "Unable to retrieve the weather data!";

        if (json.get("cod").getAsString().equals("404")){
            return json.get("message").getAsString();
        } else {
            var weatherArray = json.getAsJsonArray("weather");
            var weatherDesc = ((JsonObject) weatherArray.get(0)).get("description").getAsString();
            var temperature = json.getAsJsonObject("main").get("temp").getAsFloat();
            var windSpeed = json.getAsJsonObject("wind").get("speed").getAsFloat();
            var windDeg = json.getAsJsonObject("wind").get("deg").getAsInt();

            return String.format("%s %.2f°C, wind %.2f m/s %d°", weatherDesc, temperature, windSpeed, windDeg);
        }
    }
}
