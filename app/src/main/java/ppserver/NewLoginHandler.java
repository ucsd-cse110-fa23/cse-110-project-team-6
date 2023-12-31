package ppserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.json.JSONObject;

import com.sun.net.httpserver.*;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NewLoginHandler implements HttpHandler {
    String uri = "mongodb+srv://edlu:yZUULciZVkLPVGy4@pantrypal.3naacei.mongodb.net/?retryWrites=true&w=majority";

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
        String username = postJson.getString("username");
        String password = postJson.getString("password");
        String response = "Username has been added";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // connect to MongoDB
            MongoDatabase database = mongoClient.getDatabase("accounts_db");
            MongoCollection<Document> collection = database.getCollection("accounts");

            
            Bson filter = eq("username", username);
            if (collection.countDocuments(filter) == 0) {
                Document account = new Document("id", new ObjectId());
                account.append("Username", username)
                .append("Password", password);
            }else{
                response = "This username is already taken";
                System.out.println("This username is already taken");
            }
         }
        // convert recipe to response string
        System.out.println(response);
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
