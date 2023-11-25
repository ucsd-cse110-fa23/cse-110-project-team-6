package ppserver;

import com.sun.net.httpserver.*;
import java.io.*;
import java.util.*;
import pantrypal.Recipe;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.set;

import org.bson.Document;
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

public class RequestHandler implements HttpHandler {

    // may have to replace this with your own credentials
    String uri = "mongodb+srv://edlu:yZUULciZVkLPVGy4@pantrypal.3naacei.mongodb.net/?retryWrites=true&w=majority";
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
        String response = "Invalid GET request!";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> collection = database.getCollection("recipes");
 
            // find a list of documents and use a List object instead of an iterator
            FindIterable <Document> iterable = collection.find();
            MongoCursor<Document> cursor = iterable.iterator();

            response = "";
            // iterate through collection of recipes and add to response
            while (cursor.hasNext()) {
                JSONObject recipeData = new JSONObject(cursor.next());
                response = response + (new Recipe(recipeData)).toJson().toString();
                response = response + "---";
                System.out.println(response);
            }
        }
        return response;
    }

    public String handlePut(HttpExchange httpExchange) throws IOException {
        String response = "Invalid PUT request!";
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String[] recipes = null;
        // parse the recipe data
        while (scanner.hasNextLine()) {
            recipes = scanner.nextLine().split("---", 0);
        }

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> collection = database.getCollection("recipes");

            // delete old recipe collection on mongodb
            collection.deleteMany(new Document());

            // add new recipe collection to mongodb
            ArrayList<Document> documentList = new ArrayList<>();
            for (int i = 0; i < recipes.length; ++i) {
                documentList.add(Document.parse(recipes[i]));
            }
            collection.insertMany(documentList);
            response = "Saved Recipes!";
        }
        scanner.close();
        return response;
    }
}