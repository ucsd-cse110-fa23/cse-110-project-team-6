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
        int rCode;

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("accounts");
            
            Bson filter = eq("username", username);
            // Bson update 
            if (collection.countDocuments(filter) == 0) {
                Document newUser = new Document();
                newUser.append("username", username);
                newUser.append("password", password);
                collection.insertOne(newUser);
                System.out.println("Account is available");
                rCode = 200;
            }
            else {
                System.out.println("Account taken");
                rCode = 400;
            }
        }
        scanner.close();

        // send response back to client
        byte[] bs = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(rCode, bs.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(bs);
        outStream.close();
        return response;
    }

    private String handleGet (HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request!";
        URI queryString = httpExchange.getRequestURI();
        String query = queryString.getRawQuery();
        String username = query.substring(query.indexOf("=") + 1, query.indexOf("&"));
        String password = query.substring(query.indexOf("?password=") + 10);
        int rCode;

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("username: " + username);
            System.out.println("password: " + password);
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("PantryPal_db");
            MongoCollection<Document> collection = database.getCollection("accounts");
            
            Bson filter = eq("username", username);
            if (collection.countDocuments(filter) == 0) {
                // TODO: generate incorrect username textfield on UI
                rCode = 400;
                System.out.println("Username does not exist. Please try again.");
            }
            else {
                Document user = collection.find(filter).first();
                if (user.getString("password").equals(password)) {
                    rCode = 200;
                    System.out.println("Login successful.");
                    response = "Login successful.";
                }
                else {
                    rCode = 404;
                    System.out.println("Incorrect password. Please try again.");
                }
            }
         }

        // send response back to client
        byte[] bs = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(rCode, bs.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(bs);
        outStream.close();
        // convert recipe to response string
        return response;
    }
}
