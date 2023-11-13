package pantrypal;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class RequestHandler implements HttpHandler {

    private ArrayList<Recipe> recipes;

    public RequestHandler(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }


    public String performRequest(String method, String language, String year, String query) {
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(language + "," + year);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        

        // Retrieves uri from httpExchange object representing the request
        URI uri = httpExchange.getRequestURI();

        // Retrieves query from uri
        String query = uri.getRawQuery();

        // Retrieves value from query

        if (query != null) {
            String recipeName = query.split("=")[1];
            String reci
            if (year != null) {
                response = year;
                System.out.println("Queried for " + value + " and found " + year);
            } else {
                response = "No data found for " + value;
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        // Retrieve input stream from httpExchange object to get request body
        InputStream inStream = httpExchange.getRequestBody();
        
        // Read first line of input stream
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        
        // Stores data in variables
        String language = postData.substring(
                0,
                postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap with language as key and year as value
        data.put(language, year);

        String response = "Posted entry {" + language + ", " + year + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

    public String handlePut(HttpExchange httpExchange) throws IOException {
        // Retrieve input stream from httpExchange object to get request body
        InputStream inStream = httpExchange.getRequestBody();
        
        // Read first line of input stream
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        
        // Stores data in variables
        String language = postData.substring(
                0,
                postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);

        String response;
        // Replace year if language already exists in hashmap
        if (data.containsKey(language)) {
            String previousYear = data.get(language);
            data.replace(language, year);
            response = "Updated entry {" + language + ", " + year + "} {previous year: " + previousYear + "}";
        } 
        else {
            // Store data in hashmap with language as key and year as value if language does not exist
            data.put(language, year);
            response = "Added entry {" + language + ", " + year + "}";
        }

        System.out.println(response);
        scanner.close();

        return response;
    }

    public String handleDelete(HttpExchange httpExchange) throws IOException{
        // Default response
        String response = "Invalid DELETE request";

        // Retrieves uri from httpExchange object representing the request
        URI uri = httpExchange.getRequestURI();

        // Retrieves query from uri
        String query = uri.getRawQuery();

        // Retrieves value from query
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String year = data.get(value); // Retrieve data from hashmap
            
            // Check if entry exists
            if (query != null) {
                // If the year does not exist, return error message
                if (year == null) {
                    response = "No data found for " + value;
                }
                // Else, remove entry from hashmap
                else {
                    data.remove(value);
                    response = "Deleted entry {" + value + ", " + year + "}";
                }
            }
        }
        return response;
    }
}