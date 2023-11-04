// import csv libraries for me


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

class RecipeCreator {
    private String prompt;
    private static final String RECIPE_FILE = "recipes.txt";


    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String input) {
        this.prompt = input;
    }

    private void saveRecipe (Recipe recipe) throws Exception {
        File recipeFile = new File(RECIPE_FILE);
        BufferedWriter bw;
        if (!recipeFile.exists()) {
            recipeFile.createNewFile();
            FileWriter fw = new FileWriter(recipeFile);
            bw = new BufferedWriter(fw);
            bw.write("recipe title,ingredients,instructions");
        }
        else {
            FileWriter fw = new FileWriter(recipeFile);
            bw = new BufferedWriter(fw);
        }
        bw.write("\n" + recipe.getName() + "," + recipe.getIngredients() + "," + recipe.getSteps());
    }
    
    // public RecipeCreator() {
    //     setPrompt("I have ribeye steak, parsley, russet potatoes, garlic, butter, rosemary, and sauerkraut.");
    // }

    public JSONObject generateGPTRecipe(String input) throws Exception {
        ChatGPT bot = new ChatGPT(input);
        return bot.getResponse();
    }

    public void createRecipe(String input) {
        try {
            JSONObject recipe = generateGRecipe(input);
            String title = recipe.getString("recipe title");
            String ingredients = recipe.getString("ingredients");
            String instructions = recipe.getString("instructions");
            System.out.println("TITLE: " + title + "\nINGREDIENTS: " + ingredients + "\nINSTRUCTIONS: " + instructions);
            String recipeString = "TITLE: " + title + "\nINGREDIENTS: " + ingredients + "\nINSTRUCTIONS: " + instructions;
            Recipe newRecipe = new Recipe(title, ingredients, instructions);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }   
    }
}