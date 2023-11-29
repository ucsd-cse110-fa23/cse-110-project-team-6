package ppserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.json.JSONObject;

import com.sun.net.httpserver.*;

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

import pantrypal.Recipe;
import pantrypal.RecipeCreator;

public class AccountHandler implements HttpHandler {
    String uri = "mongodb+srv://edlu:yZUULciZVkLPVGy4@pantrypal.3naacei.mongodb.net/?retryWrites=true&w=majority";

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("POST")) {
                response = handlePost(httpExchange);
            }
            else if (method.equals("GET")) {
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
    
    private String handlePost(HttpExchange httpExchange) throws IOException {
        // get POST data
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        JSONObject postJson = new JSONObject(postData);
        String username = postJson.getString("username");
        String password = postJson.getString("password");
        String response = "";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("accounts");
            
            
            Bson filter = eq("username", username);
            // Bson update 
            if (collection.countDocuments(filter) == 0) {
                System.out.println("Account is available");
            }
            else {
                System.out.println("Account taken");
            }
        }
    
        // convert recipe to response string
        //System.out.println(response);
        scanner.close();

        // send response back to client
        byte[] bs = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, bs.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(bs);
        outStream.close();
        return response;
    }

    private String handleGet (HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String getData = scanner.nextLine();
        JSONObject getJson = new JSONObject(getData);
        String username = getJson.getString("username");
        String password = getJson.getString("password");
        String response = "";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("accounts");
            
            Bson userFilter = eq("username", username);
            Bson passFilter = eq("password", password);

            // Bson update 
            if (collection.countDocuments(userFilter) == 0) {
                // TODO: generate incorrect username textfield on UI

                System.out.println("Username does not exist. Please try again.");
            }
            else {
                if (collection.countDocuments(passFilter) == 0) {
                    // TODO: generate incorrect password textfield on UI

                    System.out.println("Incorrect password. Please try again.");
                }
                else {
                    System.out.println("Login successful.");
                    response = "Login successful.";
                }
            }
         }

        // convert recipe to response string
        //System.out.println(response);
        scanner.close();

        // send response back to client
        byte[] bs = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, bs.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(bs);
        outStream.close();
        // convert recipe to response string
        return response;
    }

    public static void main (String args[]) {
        String uri = "mongodb+srv://edlu:yZUULciZVkLPVGy4@pantrypal.3naacei.mongodb.net/?retryWrites=true&w=majority";
        String username = "eddy";
        String password = "123";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("accounts");
            
            Bson filter = eq("username", username);
            // Bson update 
            if (collection.countDocuments(filter) == 0) {
                Document account = new Document("_id", new ObjectId());
                account.append("username", username);
                account.append("password", password);
                collection.insertOne(account);
                System.out.println("Account is available");
            }
            else {
                System.out.println("Account taken. ");
            }
         }
    }
}