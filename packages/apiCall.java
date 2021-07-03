package packages;
import java.io.*;
import java.net.*;

import com.google.gson.*;

public class apiCall{
    
    public static double kelvinToFahrenheit(double temp){
        double newTemp = ((temp - 273.15) * 1.8) + 32;
        return Math.round(newTemp * 100.0) / 100.0;
    }

    public static void getTemperatures(JsonObject array){
        double rawTemp = array.get("temp").getAsDouble();
        double rawFeelsLike = array.get("feels_like").getAsDouble();
        double rawMin = array.get("temp_min").getAsDouble();
        double rawMax = array.get("temp_max").getAsDouble();

        System.out.println("Current temperature:\t" + kelvinToFahrenheit(rawTemp) + "°F");
        System.out.println("Feels like:\t\t" + kelvinToFahrenheit(rawFeelsLike) + "°F");
        System.out.println("Minimum:\t\t" + kelvinToFahrenheit(rawMin) + "°F");
        System.out.println("Maximum:\t\t" + kelvinToFahrenheit(rawMax) + "°F");
    }

    public static JsonObject returnTemperatureJson(StringBuffer content){
        String newString = content + "";

        Gson gson= new Gson();
        JsonObject jsonObject = gson.fromJson( newString, JsonObject.class);
        JsonObject temperatureTable = gson.fromJson( jsonObject.get("main"), JsonObject.class);
        return temperatureTable;
    }
    
    public static void getTime() throws IOException {
        String newUrl = "http://worldtimeapi.org/api/timezone/America/Chicago/";

        URL url = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String newString = content + "";
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson( newString, JsonObject.class);
        String rawDate = jsonObject.get("datetime").getAsString();
        rawDate = rawDate.substring(0,16);
        rawDate = rawDate.replace("T", ", ");
        System.out.println("Current time:\t\t" + rawDate);
    }

    public static void getWeather(int zip) throws IOException {
        String newUrl = "https://api.openweathermap.org/data/2.5/weather?zip=" + Integer.toString(zip) + ",US&appid=26aa1d90a24c98fad4beaac70ddbf274";

        URL url = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JsonObject temps = returnTemperatureJson(content);
        getTemperatures(temps);
    }

    public static void getConnections(int zip) throws IOException {
        getTime();
        getWeather(zip);
    }
}
