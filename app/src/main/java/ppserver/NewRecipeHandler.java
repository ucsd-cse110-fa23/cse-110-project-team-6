package ppserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.json.JSONObject;

import com.sun.net.httpserver.*;

import pantrypal.Recipe;
import pantrypal.RecipeCreator;

public class NewRecipeHandler implements HttpHandler {
    
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("POST")) {
                response = handlePost(httpExchange);
            }    
            else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        System.out.println("response: " + response);

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }
    
    private String handlePost(HttpExchange httpExchange) throws IOException {
        // get POST data
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JSONObject postJson = new JSONObject(postData);
        System.out.println(postJson);

        // convert POST data into prompt and meal type
        String mealType = postJson.getString("prompt");
        String ingredients = postJson.getString("mealType");
        Boolean regenerate = postJson.getBoolean("regenerate");
    
        // generate a new recipe
        RecipeCreator rc = new RecipeCreator();
        Recipe recipe = rc.createRecipe(ingredients, mealType, regenerate);

        // convert recipe to response string
        String response = recipe.toJson().toString();
        System.out.println("response:" + response);
        scanner.close();

        // send response back to client
        byte[] bs = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, bs.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(bs);
        outStream.close();
        return response;
    }
}
