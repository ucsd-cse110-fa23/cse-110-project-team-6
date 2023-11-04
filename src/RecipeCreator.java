import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

class RecipeCreator {
    private String prompt;
    //private ArrayList<Recipe> recipeList;
    private static final String RECIPE_FILE = "recipes.txt";

    private RecipeList recipeList;

    public RecipeCreator () {
        this.recipeList = new RecipeList();
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String input) {
        this.prompt = input;
    }

    public RecipeList getRecipeList() {
        return this.recipeList;
    }

    public JSONObject generateGPTRecipe(String input) throws Exception {
        ChatGPT bot = new ChatGPT(input);
        return bot.getResponse();
    }

    public void createRecipe(String input) {
        Recipe newRecipe;
        try {
            JSONObject recipe = generateGPTRecipe(input);
            String title = recipe.getString("recipe title");
            String ingredients = recipe.getString("ingredients");
            String instructions = recipe.getString("instructions");
            System.out.println("TITLE: " + title + "\nINGREDIENTS: " + ingredients + "\nINSTRUCTIONS: " + instructions);
            String recipeString = "TITLE: " + title + "\nINGREDIENTS: " + ingredients + "\nINSTRUCTIONS: " + instructions;
            newRecipe = new Recipe(title, ingredients, instructions);
            //recipeList.add(newRecipe);
        } catch (Exception e) {
            //newRecipe = new Recipe("ERROR", "ERROR", "ERROR");
            System.out.println("Error: " + e);
        }
        //return newRecipe;
    }
}