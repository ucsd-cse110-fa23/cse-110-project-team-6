package pantrypal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    // Constructor for ChatGPT object
    // ChatGPT reads input and outputs recipe formatted as JSON object based on inputted ingredients

    public ChatGPT(String input, String mealType, boolean regenerate) throws Exception {
        String promptUSER;
        if (input != null) {
            promptUSER = input; //User input
        }
        else {
            throw new Exception("null input");
        }
        String promptGPT = "I want to cook " + mealType + " using only specific ingredients. Provide your answer in JSON form with fields \"recipe title\" as a JSON string, \"ingredients\" as a JSON array, and \"instructions\" as a JSON array. Reply with only the answer in JSON form and include no other commentary."; //Fixed input - context for GPT

        // user wants a new recipe using the same ingredients
        String regeneratePrompt = "I want to cook a different " + mealType + " using only the same ingredients specified above, which are provided again below. Provide your answer in JSON form with fields \"recipe title\" as a JSON string, \"ingredients\" as a JSON array, and \"instructions\" as a JSON array. Reply with only the answer in JSON form and include no other commentary.";
        
        int maxTokens = 500; // Max # of tokens to output - can increase if needed

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        if (regenerate) {
            requestBody.put("prompt", regeneratePrompt + promptUSER);
        }
        else{
            requestBody.put("prompt", promptGPT + promptUSER);
        }
        //requestBody.put("prompt", promptGPT + promptUSER);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1);
        

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
        
        // Format and return JSON object
        String generatedText = choices.getJSONObject(0).getString("text");
        while (!generatedText.startsWith("{")) {
            generatedText = generatedText.substring(1);
        }
        JSONObject generatedTextJson = new JSONObject(generatedText);
        setResponse(generatedTextJson);
    }
}