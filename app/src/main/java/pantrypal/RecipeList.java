package pantrypal;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeList {
    private ArrayList<Recipe> recipes;
    private PPServer server;

    // Constructor for the RecipeList class
    public RecipeList() {
        recipes = new ArrayList<Recipe>(); 
        try {
            server = new PPServer(recipes);
        } catch (IOException e) {
            System.out.println("server could not be created");
        }
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

    public void saveRecipestoCSV(RecipeList recipes, String filePath) {
        
    }
}
