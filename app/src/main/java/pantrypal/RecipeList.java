package pantrypal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RecipeList {
    private ArrayList<Recipe> recipes;

    // Constructor for the RecipeList class
    public RecipeList() {
        recipes = new ArrayList<Recipe>(); 
    }

    // Adds a recipe to the list
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
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

    @Override
    public String toString() {
        String response = "";

        for (int i = 0; i < recipes.size(); i++) {
            response += recipes.get(i).getName() + "\n";
            response += "---\n";
            response += recipes.get(i).getIngredientString();
            response += "---\n";
            response += recipes.get(i).getStepString();
            response += "---\n";
        }

        return response;
    }

    public void saveRecipes() {
        // System.out.println(this.toString());
        // System.out.println(recipes.size());
        performRequest("PUT");
    }

    public void loadRecipes() {
        Scanner sc = performRequest("GET");
        System.out.println("got saved recipes");

        // Scanner scanner = new Scanner(savedRecipeString);
        
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
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            if (input.equals("No Saved Recipes")) {
                break;
            }

            if (input.equals("---")) {
                if (i == 2) {
                    recipes.add(new Recipe(name, ingredients, steps));
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
        sc.close();

        System.out.println("Loaded " + recipes.size() + " recipes");
    }

    private Scanner performRequest(String method) {
        System.out.println(method);
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/";
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(this.toString());
                out.flush();
                out.close();
            }

            Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
            // String response = in.readLine();
            // in.close();
            return sc;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
}
