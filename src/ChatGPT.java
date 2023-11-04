import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo-instruct";

    private JSONObject responseJson;
    
    public JSONObject getResponse() {
        return this.responseJson;
    }

    public void setResponse(JSONObject newResponse) {
        this.responseJson = newResponse;
    }

    private static String parsePromptText(String fileName) throws IOException{
        Path filePath = Path.of(fileName);
        String chatPrompt = Files.readString(filePath);
        return chatPrompt;
    }   

    public ChatGPT(String input) throws Exception {
        String promptUSER = input;
        //String mealType = args[1];
        String promptGPT = parsePromptText("test.txt");
        int maxTokens = 500;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", promptGPT + promptUSER);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 0);

        // Create the HTTP Client

        HttpClient client = HttpClient.newHttpClient();

        
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();
    

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
    
        // Process the response
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        System.out.println(responseJson.toString());

        JSONArray choices = responseJson.getJSONArray("choices");
        //JSONObject generatedText = choices.getJSONObject(0).getJSONArray("text");;
        //JSONObject generatedText = choices.getJSONArray(0).getJSONObject(0);
        
        String generatedText = choices.getJSONObject(0).getString("text");
        JSONObject generatedTextJson = new JSONObject(generatedText);
        setResponse(generatedTextJson);
    }

    // public static void main(String[] args) throws Exception {
    //     String promptUSER = args[0];
    //     //String mealType = args[1];
    //     String promptGPT = parsePromptText("test.txt");
    //     int maxTokens = 500;

    //     // Create a request body which you will pass into request object
    //     JSONObject requestBody = new JSONObject();
    //     requestBody.put("model", MODEL);
    //     requestBody.put("prompt", promptGPT + promptUSER);
    //     requestBody.put("max_tokens", maxTokens);
    //     requestBody.put("temperature", 0);

    //     // Create the HTTP Client

    //     HttpClient client = HttpClient.newHttpClient();

        
    //     // Create the request object
    //     HttpRequest request = HttpRequest
    //     .newBuilder()
    //     .uri(URI.create(API_ENDPOINT))
    //     .header("Content-Type", "application/json")
    //     .header("Authorization", String.format("Bearer %s", API_KEY))
    //     .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
    //     .build();
    

    //     // Send the request and receive the response
    //     HttpResponse<String> response = client.send(
    //     request,
    //     HttpResponse.BodyHandlers.ofString()
    //     );
    
    //     // Process the response
    //     String responseBody = response.body();
    //     JSONObject responseJson = new JSONObject(responseBody);
    //     // System.out.println(responseJson.toString());

    //     JSONArray choices = responseJson.getJSONArray("choices");
    //     //JSONObject generatedText = choices.getJSONObject(0).getJSONArray("text");;
    //     //JSONObject generatedText = choices.getJSONArray(0).getJSONObject(0);
        
    //     String generatedText = choices.getJSONObject(0).getString("text");
    //     JSONObject generatedTextJson = new JSONObject(generatedText);
    //     System.out.println(generatedTextJson);
    //     //return generatedTextJson;
    // }
}