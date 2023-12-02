package pantrypal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RecipeList {
    private ArrayList<Recipe> recipes;
    private String username;

    // Constructor for the RecipeList class
    public RecipeList(String username) {
        recipes = new ArrayList<Recipe>();
        this.username = username;
    }

    public RecipeList() {
        recipes = new ArrayList<Recipe>();
    }

    // Adds a recipe to the list
    public void addRecipe(Recipe recipe) {
        recipes.add(0,recipe);
    }

    // Removes a recipe from the list
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    // Returns the list of recipes
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    // Returns a recipe at a specific index
    public Recipe getRecipe(int index) {
        return recipes.get(index);
    }

    // Returns the size of the list
    public int getSize() {
        return recipes.size();
    }

    public void setUsername(String username) {
        this.username = username;
    }
  
    @Override
    public String toString() {
        String response = username + "\n";

        for (int i = 0; i < recipes.size(); i++) {
            response = response + recipes.get(i).toJson().toString();
            response = response + "---";
        }

        return response;
    }

    public void saveRecipes() {
        System.out.println(this.toString());
        // System.out.println(recipes.size());
        performRequest("PUT");
    }

    public void loadRecipes() {
        Scanner sc = performRequest("GET");
        System.out.println("got saved recipes");
        String[] recipes = {};
        while (sc.hasNextLine()) {
            String response = sc.nextLine();
            System.out.println("response to client:" + response);
            recipes = response.split("---", 0);
        }
        
        try {
            for (int i = 0; i < recipes.length; ++i) {
                JSONObject jsonRecipe = new JSONObject(recipes[i]);
                System.out.println("added: " + jsonRecipe);
                this.addRecipe(new Recipe(jsonRecipe));
            }
        }
        catch (Exception e) {
        }

        sc.close();
        System.out.println("Loaded " + this.getSize() + " recipes");
    }

    private Scanner performRequest(String method) {
        System.out.println("method:" + method);
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/";
            if (method.equals("GET")) {
                urlString = urlString + "?username=" + username;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("PUT")) {            
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                System.out.println("putting");
                out.write(this.toString());
                out.flush();
                out.close();
            }                

            Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
            return sc;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    
}
