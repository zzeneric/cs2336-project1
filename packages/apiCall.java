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

        System.out.println("Current temperature: " + kelvinToFahrenheit(rawTemp) + "°F");
        System.out.println("Feels like: " + kelvinToFahrenheit(rawFeelsLike) + "°F");
        System.out.println("Minimum: " + kelvinToFahrenheit(rawMin) + "°F");
        System.out.println("Maximum: " + kelvinToFahrenheit(rawMax) + "°F");
    }

    public static JsonObject returnTemperatureJson(StringBuffer content){
        String newString = content + "";

        Gson gson= new Gson();
        JsonObject jsonObject = gson.fromJson( newString, JsonObject.class);
        JsonObject temperatureTable = gson.fromJson( jsonObject.get("main"), JsonObject.class);
        return temperatureTable;
    }
    
    public static void getConnection(int zip) throws IOException {
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
}
