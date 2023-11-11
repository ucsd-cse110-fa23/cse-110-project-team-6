package pantrypal;

import org.json.JSONObject;

public class RecipeCreator {
    private String prompt;

    private RecipeList recipeList;

    public RecipeCreator () {
        this.recipeList = new RecipeList();
    }

    // retrieving the prompt 
    public String getPrompt() {
        return this.prompt;
    }

    // setting the prompt
    public void setPrompt(String input) {
        this.prompt = input;
    }

    // retrieving the recipe list
    public RecipeList getRecipeList() {
        return this.recipeList;
    }

    // Call ChatGPT class generate method
    public JSONObject generateGPTRecipe(String input) throws Exception {
        ChatGPT bot = new ChatGPT(input);
        return bot.getResponse();
    }

    // Create recipe based on input string
    public void createRecipe(String input) {
        Recipe newRecipe;
        try {
            JSONObject recipe = generateGPTRecipe(input);
            // Get individual fields of JSON
            String title = recipe.getString("recipe title");
            String ingredients = recipe.getJSONArray("ingredients").toString();
            String instructions = recipe.getJSONArray("instructions").toString();
            newRecipe = new Recipe(title, ingredients, instructions);
            recipeList.addRecipe(newRecipe);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}