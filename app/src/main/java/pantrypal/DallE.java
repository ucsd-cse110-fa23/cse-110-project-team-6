package pantrypal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;


public class DallE {

    // TODO: Set the URL of the API Endpoint
    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";
    private static final String MODEL = "dall-e-2";
    public boolean foundImage = false;

    public DallE(String input, String test) {
        String prompt = input;
        int n = 1;

        foundImage = Paths.get("image.jpg").toFile().exists();
    }

    public DallE(String input)
        throws IOException, InterruptedException, URISyntaxException {
            
    // Set request parameters
        String prompt = input;
        int n = 1;


    // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");


    // Create the HTTP client
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

    // TODO: Process the response
    //System.out.println(responseJson);
    //org.json.JSONArray data = responseJson.getJSONArray("data");
    //JSONObject object = data.getJSONObject(0);
        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");

        System.out.println("DALL-E Response:");
        System.out.println(generatedImageURL);

        // delete if the file exists
        if (Paths.get("image.jpg").toFile().exists()){
            Paths.get("image.jpg").toFile().delete();
        }

    // Download the Generated Image to Current Directory
        try(
        InputStream in = new URI(generatedImageURL).toURL().openStream()
        )
        {
            Files.copy(in, Paths.get("image.jpg"));
        }
    }
}