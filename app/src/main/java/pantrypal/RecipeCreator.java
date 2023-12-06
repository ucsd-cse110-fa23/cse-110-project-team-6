package pantrypal;

import java.util.ArrayList;

import org.json.JSONObject;

import javafx.scene.control.Alert;
import javafx.stage.Window;

class MockRecipeCreator {
    boolean regenerate;
    String recipeJSON1 = "{\"recipe title\":\"Ribeye Steak with Garlic Potatoes\",\"instructions\":[\"1. Preheat the oven to 425 degrees Fahrenheit.\",\"2. Cut the russet potatoes into 1-inch cubes.\",\"3. In a bowl, combine the potatoes with 2 tablespoons of olive oil, 2 cloves of minced garlic, and salt and pepper to taste.\",\"4. Line a baking sheet with foil and spread the potatoes evenly on it.\",\"5. Bake for 25-30 minutes, or until the potatoes are soft and crispy.\",\"6. While the potatoes are cooking, heat a cast iron skillet over high heat.\",\"7. Season the ribeye steak with salt and pepper.\",\"8. Once the skillet is hot, add 1 tablespoon of olive oil and sear the steak for 3-4 minutes on each side for medium-rare, or adjust cooking time to desired level of doneness.\",\"9. Let the steak rest for 5 minutes before slicing it.\",\"10. Serve the steak with the roasted garlic potatoes and garnish with chopped parsley.\",\"11. Enjoy your delicious Ribeye Steak with Garlic Potatoes!\"],\"ingredients\":[\"Ribeye steak\",\"Parsley\",\"Russet potatoes\",\"Olive oil\",\"Garlic\",\"Salt\",\"Pepper\"]}";
    JSONObject mockRecipe = new JSONObject(recipeJSON1);

    String recipeJSON2 = "{\"recipe title\":\"Peanut Butter and Jelly Sandwich\",\"instructions\":[\"1. Filler 1\",\"2. Filler 2\",\"3. Filler 3\",\"4. Filler 4\"],\"ingredients\":[\"Bread\",\"Peanut butter\",\"Jelly\"]}";
    JSONObject mockRecipe2 = new JSONObject(recipeJSON2);

    MockRecipeCreator () {
        this.regenerate = false;
    }

    void regenerate(){
        this.regenerate = true;
    }

    JSONObject createRecipe(){
        if(!regenerate){
            return this.mockRecipe;
        }else{
            return this.mockRecipe2;
        }
    }
}

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
