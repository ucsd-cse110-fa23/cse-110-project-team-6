import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    private String title;
    private String ingredients;
    private String steps;

    public String getTitle() {
        return this.title;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public String getSteps() {
        return this.steps;
    }

    private static String parsePromptText(String fileName) {
        Scanner sc = new Scanner(fileName);
        String chatPrompt = "";
        while (sc.hasNextLine()) {
            chatPrompt = chatPrompt + sc.nextLine();
        }
        sc.close();
        return chatPrompt;
    }


    public static void main(String[] args) throws Exception {
        // Set request parameters
        String promptUSER = args[0];
        //String mealType = args[1];
        // TODO: read from file
        String promptGPT = parsePromptText("prompt.txt");
        int maxTokens = 300; 

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", promptUSER + promptGPT);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

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
        String generatedText = choices.getJSONObject(0).getString("text");


        System.out.println(generatedText);

        

    }

}