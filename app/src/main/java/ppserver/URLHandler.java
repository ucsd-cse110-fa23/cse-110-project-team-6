package ppserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Scanner;

import org.json.JSONObject;

import com.sun.net.httpserver.*;

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

import pantrypal.Recipe;
import pantrypal.RecipeCreator;

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
        String response = "Could not find recipe";
        URI queryString = httpExchange.getRequestURI(); // query should look like <URL>/username?=<username>&title?=<recipe title>
        String query = queryString.getRawQuery();
        String username = query.substring(query.indexOf("=") + 1);
        String title = query.substring(query.indexOf("title?=") + "title?=".length());

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            response = "";

            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("recipes");
 
            // find a list of documents and use a List object instead of an iterator
            Bson userFilter = eq("username", username);
            FindIterable <Document> iterable = collection.find(userFilter);
            MongoCursor<Document> cursor = iterable.iterator();

            // iterate through collection of recipes and add to response
            while (cursor.hasNext()) {
                JSONObject recipeData = new JSONObject(cursor.next());
                response = new Recipe(recipeData).toJson().toString();
            }
        }
        return response;
    }
}
