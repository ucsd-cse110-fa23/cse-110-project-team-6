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

    // ChatGPT response
    private JSONObject responseJson;
    
    // Return ChatGPT response
    public JSONObject getResponse() {
        return this.responseJson;
    }

    // Set ChatGPT response
    public void setResponse(JSONObject newResponse) {
        this.responseJson = newResponse;
    }

    // Read ChatGPT prompt from txt file
    private static String parsePromptText(String fileName) throws IOException{
        Path filePath = Path.of(fileName);
        String chatPrompt = Files.readString(filePath);
        return chatPrompt;
    }   

    // Constructor for ChatGPT object
    // ChatGPT reads input and outputs recipe formatted as JSON object based on inputted ingredients
    public ChatGPT(String input) throws Exception {
        String promptUSER = input; //User input
        String promptGPT = parsePromptText("src/test.txt"); //Fixed input - context for GPT
        int maxTokens = 500; // Max # of tokens to output - can increase if needed

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

        JSONArray choices = responseJson.getJSONArray("choices");
        //JSONObject generatedText = choices.getJSONObject(0).getJSONArray("text");;
        //JSONObject generatedText = choices.getJSONArray(0).getJSONObject(0);
        
        // Format and return JSON object
        String generatedText = choices.getJSONObject(0).getString("text");
        JSONObject generatedTextJson = new JSONObject(generatedText);
        setResponse(generatedTextJson);
    }
}