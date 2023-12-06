package pantrypal;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.JSONObject;

public class RecipeList {
    private ArrayList<Recipe> recipes;
    private ArrayList<Integer> recipeOrder;
    private HashMap<String, Integer> recipeMap;

    // Comparator for sorting recipes in chronological and reverse chronological order
    Comparator<Recipe> chronoSorter = (Recipe a, Recipe b) -> {
        int aIndex = recipeOrder.get(recipes.indexOf(a));
        int bIndex = recipeOrder.get(recipes.indexOf(b));
        
        if (aIndex < bIndex) {
            return -1;
        } else {
            return 1;
        }
    };

    // Comparator for sorting recipes in alphabetical and reverse alphabetical order
    Comparator<Recipe> alphaSorter = (Recipe a, Recipe b) -> {
        return a.getName().compareTo(b.getName());
    };

    private String username;

    // Constructor for the RecipeList class
    public RecipeList(String username) {
        recipes = new ArrayList<Recipe>();
        recipeOrder = new ArrayList<Integer>();
        recipeMap = new HashMap<String, Integer>();
        this.username = username;
    }

    public RecipeList() {
        recipes = new ArrayList<Recipe>();
        recipeOrder = new ArrayList<Integer>();
        recipeMap = new HashMap<String, Integer>();
    }

    public RecipeList(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        recipeOrder = new ArrayList<Integer>();
        recipeMap = new HashMap<String, Integer>();
    }

    // Adds a recipe to the list
    public void addRecipe(Recipe recipe) {
        recipes.add(0,recipe);
        recipeOrder.add(recipes.size()-1);
        recipeMap.put(recipe.getName(), recipes.size()-1);
    }

    // Removes a recipe from the list
    public void removeRecipe(Recipe recipe) {
        int removedRecipeIdx = recipes.indexOf(recipe);
        recipeOrder.remove(removedRecipeIdx);
        
        // Update the original recipe indices in the recipeOrder list
        for (int i = 0; i < recipeOrder.size(); i++) {
            if (recipeOrder.get(i) > removedRecipeIdx) {
                recipeOrder.set(i, recipeOrder.get(i) - 1);
            }
        }

        recipes.remove(recipe);
        recipeMap.remove(recipe.getName());
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

    public String getUsername() {
        return this.username;
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

    public void clear() {
        this.recipes = new ArrayList<Recipe>();
        this.username = null;
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

    /*
     * Sorts the recipes in chronological order (oldest first)
     */
    public ArrayList<Recipe> chronoSort() {
        ArrayList<Recipe> tempList = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            tempList.add(recipes.get(i));
        }
        Collections.sort(tempList, chronoSorter);
        return tempList;
        
    }

    /*
     * Sorts the recipes in reverse chronological order (newest first)
     */
    public ArrayList<Recipe> reverseChronoSort() {
        ArrayList<Recipe> tempList = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            tempList.add(recipes.get(i));
        }
        Collections.sort(tempList, chronoSorter);
        Collections.reverse(tempList);
        return tempList;
    }

    /*
     * Sorts the recipes in alphabetical order
     */
    public ArrayList<Recipe> alphaSort() {
        ArrayList<Recipe> tempList = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            tempList.add(recipes.get(i));
        }
        Collections.sort(tempList, alphaSorter);

        return tempList;
    }

    /*
     * Sorts the recipes in reverse alphabetical order
     */
    public ArrayList<Recipe> reverseAlphaSort() {
        ArrayList<Recipe> tempList = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            tempList.add(recipes.get(i));
        }
        Collections.sort(tempList, alphaSorter);
        Collections.reverse(tempList);
    
        return tempList;
    }


    private Scanner performRequest(String method) {
        System.out.println("method:" + method);
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/Main";
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