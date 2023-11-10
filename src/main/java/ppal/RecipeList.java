package main.java.ppal;

import java.util.ArrayList;

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
}
