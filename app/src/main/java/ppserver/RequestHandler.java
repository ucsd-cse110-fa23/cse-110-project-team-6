package ppserver;

import com.sun.net.httpserver.*;
import java.io.*;
import java.util.*;
import pantrypal.Recipe;

public class RequestHandler implements HttpHandler {

    private ArrayList<Recipe> savedRecipes = new ArrayList<>();

    public RequestHandler() {
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
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

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        if (savedRecipes.size() > 0) {
            response = savedToString();
            System.out.println("Returned Saved Recipes");
        } else {
            response = "No Saved Recipes";
        }
        System.out.println(response);

        return response;
    }

    private String savedToString() {
        String response = "";

        for (int i = 0; i < savedRecipes.size(); i++) {
            response += savedRecipes.get(i).getName() + "\n";
            response += "---\n";
            for (int j = 0; j < savedRecipes.get(i).getIngredients().size(); j++) {
                response += savedRecipes.get(i).getIngredients().get(j) + "\n";
            }
            response += "---\n";
            for (int j = 0; j < savedRecipes.get(i).getSteps().size(); j++) {
                response += savedRecipes.get(i).getSteps().get(j) + "\n";
            }
            response += "---\n";
        }

        return response;
    }

    public String handlePut(HttpExchange httpExchange) throws IOException {
        if (savedRecipes.size() > 0) {
            savedRecipes.clear();
        }

        // Retrieve input stream from httpExchange object to get request body
        InputStream inStream = httpExchange.getRequestBody();
        // BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        // Read first line of input stream
        Scanner scanner = new Scanner(inStream);
        System.out.println("instream: " + inStream);
        
        // Stores data in variables
        // receive list of recipes in arraylist 
        String input = "";
        
        int i = 0;
        // 0 = name
        // 1 = ingredients
        // 2 = steps

        String name = "";
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            System.out.println("saved: " + input);
            if (input.equals("---")) {
                if (i == 2) {
                    savedRecipes.add(new Recipe(name, ingredients, steps));
                    name = "";
                    ingredients.clear();
                    steps.clear();
                }
                i++;
                i = i % 3;
            }
            if (i == 0) {
                name = input;
            } else if (i == 1) {
                ingredients.add(input);
            } else if (i == 2) {
                steps.add(input);
            }
        }  

        String response = "Saved Recipes";
        scanner.close();

        return response;
    }
}