package ppserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Scanner;

import org.junit.runner.manipulation.Filter;

import com.sun.net.httpserver.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.set;

import org.bson.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.*;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import pantrypal.DallE;
import pantrypal.Recipe;
import pantrypal.RecipeCreator;

import java.util.ArrayList;

public class URLHandler implements HttpHandler {
    String uri = "mongodb+srv://edlu:yZUULciZVkLPVGy4@pantrypal.3naacei.mongodb.net/?retryWrites=true&w=majority";

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
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

    public String handleGet(HttpExchange httpExchange) { 
        StringBuilder response = new StringBuilder("Could not find recipe");

        
        URI queryString = httpExchange.getRequestURI(); // query should look like <URL>?username=<username>&title=<recipe title>
        System.out.println("query string: " + queryString);
        String query = queryString.getQuery();
        System.out.println("query: " + query);


        String params[] = query.split("&", 0);
        String username = params[0].substring(params[0].indexOf("=") + 1);
        String title = params[1].substring(params[1].indexOf("=") + 1);        

        System.out.println(username);
        System.out.print(title);
        
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("recipes");            

            // find a list of documents and use a List object instead of an iterator
            Bson filter = and(eq("username", username), eq("recipe title", title));
            
            Document recipe = collection.find(filter).first();
            if (recipe.equals(null)) {
                return response.toString();
            }

            String rTitle = recipe.getString("recipe title");
            ArrayList<String> instructions = (ArrayList<String>)recipe.get("instructions");
            ArrayList<String> ingredients = (ArrayList<String>) recipe.get("ingredients");
            // format HTML response
            response = new StringBuilder();
            response.append(rTitle + "\n\n");
            response.append("ingredients:\n");
            for (int i = 0; i < ingredients.size(); ++i) {
                response.append(ingredients.get(i) + "\n");
            }
            response.append("\ninstructions:\n");
            for (int i = 0; i < instructions.size(); ++i) {
               response.append(instructions.get(i) + "\n");
            }
        }
        return response.toString();
    }
}
