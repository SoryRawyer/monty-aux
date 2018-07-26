package biz.sory.monty;

import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Blob;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {

    public String getGreeting() {
        return "Hello world";
    }

    public static void main(String[] args) throws IOException {
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//        Bucket bucket = storage.get("monty-media");
//
//        for (Blob blob : bucket.list().iterateAll()) {
//            System.out.println(blob.getName());
//        }

        // make that request
        URL url = new URL("https://musicbrainz.org/ws/2/artist/?query=panda%20bear&fmt=json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Application name/0.01 ( sory.biz )");
        int status = con.getResponseCode();
        System.out.println(status);

        // read the response from the request
        BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = read.readLine()) != null) {
            content.append(inputLine);
        }
        read.close();

        // parse! that! json!
        Gson gson = new Gson();
        MBJsonResponse resp = gson.fromJson(content.toString(), MBJsonResponse.class);
        System.out.println(resp.artists.get(0).get("score"));

    }

}
