import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    static Properties prop = new Properties();

    public static void main(String[] args) throws IOException {
        loadProperties();
        OkHttpClient client = new OkHttpClient();
//        GET https://api.weather.yandex.ru/v2/informers?lat=55.75396&lon=37.620393
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(prop.getProperty("BASE_HOST"))
                .addPathSegment(prop.getProperty("API_VERSION"))
                .addPathSegment(prop.getProperty("FORECAST_TYPE"))
                .addQueryParameter("lat", prop.getProperty("lat"))
                .addQueryParameter("lon", prop.getProperty("lon"))
                .addQueryParameter("lang", prop.getProperty("lang"))
                .addQueryParameter("limit", "5")
                .build();

        System.out.println(url.toString());

        Request requesthttp = new Request.Builder()
                .addHeader("accept", "application/json")
                .addHeader(prop.getProperty("HEADER"), prop.getProperty("API_KEY"))
                .url(url)
                .build();
        System.out.println(requesthttp.headers());
        String jsonResponse = client.newCall(requesthttp).execute().body().string();
        System.out.println(jsonResponse);
    }
//GET https://api.weather.yandex.ru/v2/informers?lat=55.75396&lon=37.620393
    private static void loadProperties() throws IOException {
        try (FileInputStream configFile = new FileInputStream("src/main/resources/properties.properties")) {
            prop.load(configFile);
        }
    }
}
