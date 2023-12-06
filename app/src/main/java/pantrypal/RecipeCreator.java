package pantrypal;

import java.util.ArrayList;

import org.json.JSONObject;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class RecipeCreator {
    private String prompt;

    public RecipeCreator () {
    }

    // retrieving the prompt
    public String getPrompt() {
        return this.prompt;
    }

    // setting the prompt
    public void setPrompt(String input) {
        this.prompt = input;
    }

    // Call ChatGPT class generate method
    private JSONObject generateGPTRecipe(String input, String mealType) throws Exception {
        ChatGPT bot = new ChatGPT(input, mealType, false);
        return bot.getResponse();
    }
    private JSONObject regenerateGPTRecipe(String input, String mealType) throws Exception{
        ChatGPT bot = new ChatGPT(input, mealType, true);
        return bot.getResponse();
    }

    // Create image based on input string
    public String createImage(String name, String ingredients, String instructions) throws Exception{
        DallE image = new DallE();
        image.generateImage(name, ingredients, instructions);
        return image.getURL();
    }
    
    // Create recipe based on input string
    public Recipe createRecipe(String input, String mealType, Boolean regenerate) {
        Recipe newRecipe;
        try {
            JSONObject recipe;
            if(regenerate){
                recipe = regenerateGPTRecipe(input, mealType);
            }else{
                recipe = generateGPTRecipe(input, mealType);
            }
            // Get individual fields of JSON
            String title = recipe.getString("recipe title");
            //String ingredients = recipe.getJSONArray("ingredients").toString();
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < recipe.getJSONArray("ingredients").length(); ++i) {
                ingredients.add(recipe.getJSONArray("ingredients").get(i).toString());
            }
            //String instructions = recipe.getJSONArray("instructions").toString();
            ArrayList<String> instructions = new ArrayList<>();
            for (int i = 0; i < recipe.getJSONArray("instructions").length(); ++i) {
                instructions.add(recipe.getJSONArray("instructions").get(i).toString());
            }
            newRecipe = new Recipe(title, ingredients, instructions, mealType);

            // GENERATE IMAGE FOR RECIPE
            newRecipe.setURL(createImage(title, newRecipe.getIngredientString(), newRecipe.getStepString()));
            return newRecipe;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Alert alert = new Alert(Alert.AlertType.NONE, "Oopsie! Try again");
            Window window = alert.getDialogPane().getScene().getWindow();
            alert.getDialogPane().setPrefSize(256, 256);
            window.setOnCloseRequest(e1 -> alert.hide());
      
            alert.show();
            return null;
        }
    }
}
