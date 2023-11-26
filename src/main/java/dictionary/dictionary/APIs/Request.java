package dictionary.dictionary.APIs;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



public class Request {
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/";

    public static void main(String[] args) throws Exception {
        String languageCode = "en";
        String word = "hello";
        String response = getDefinition(languageCode, word);
        parseAndPrintDefinition(response);
    }

    private static String getDefinition(String languageCode, String word) throws Exception {
        URL url = new URL(API_URL + languageCode + "/" + word);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return content.toString();
    }

    private static void parseAndPrintDefinition(String json) {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("Word: " + jsonObject.getString("word") + "\n");
            if (jsonObject.has("phonetics")) {
                JSONArray phoneticsArray = jsonObject.getJSONArray("phonetics");
                if (phoneticsArray.length() > 0 && phoneticsArray.getJSONObject(0).has("text")) {
                    System.out.println("Phonetics: " + phoneticsArray.getJSONObject(0).getString("text") + "\n");
                }
            }
            if (jsonObject.has("origin")) {
                System.out.println("Origin: " + jsonObject.getString("origin") + "\n");
            }
            if (jsonObject.has("meanings")) {
                JSONArray meaningsArray = jsonObject.getJSONArray("meanings");
                for (int j = 0; j < meaningsArray.length(); j++) {
                    JSONObject meaningObject = meaningsArray.getJSONObject(j);
                    System.out.println("Part of Speech: " + meaningObject.getString("partOfSpeech") + "\n");
                    JSONArray definitionsArray = meaningObject.getJSONArray("definitions");
                    for (int k = 0; k < definitionsArray.length(); k++) {
                        JSONObject definitionObject = definitionsArray.getJSONObject(k);
                        System.out.println("Definition: " + definitionObject.getString("definition") + "\n");
                        if (definitionObject.has("example")) {
                            System.out.println("Example: " + definitionObject.getString("example") + "\n");
                        }
                    }
                }
            }
        }
    }


}

