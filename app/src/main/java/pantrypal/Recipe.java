package pantrypal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private String mealType;

    public Recipe (String name, ArrayList<String> ingredients, ArrayList<String> steps, String mealType) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealType = mealType;
    }

    public Recipe (JSONObject recipe) {
        //this.mealType = PantryPal.getRoot().getMeal().getMealType(); TODO: replace with parsing the json object to get mealType
        this.name = recipe.getString("recipe title"); 
        //String ingredients = recipe.getJSONArray("ingredients").toString();
        this.ingredients = new ArrayList<>();
        for (int i = 0; i < recipe.getJSONArray("ingredients").length(); ++i) {
            ingredients.add(recipe.getJSONArray("ingredients").get(i).toString());
        }
        //String instructions = recipe.getJSONArray("instructions").toString();
        this.steps = new ArrayList<>();
        for (int i = 0; i < recipe.getJSONArray("instructions").length(); ++i) {
            this.steps.add(recipe.getJSONArray("instructions").get(i).toString());
        }
    }

    Recipe () {
    }

    public String getName() {
        return name;
    }

    public String getMealType() {
        return mealType;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    } 

    public String getIngredientString() {
        String s = "";
        for (int i = 0; i < ingredients.size(); i++) {
            s += ingredients.get(i) + "\n";
        }
        return s;
    }

    public String getStepString() {
        String s = "";
        for (int i = 0; i < steps.size(); i++) {
            s += steps.get(i) + "\n";
        }
        return s;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public JSONObject toJson() {
        JSONArray ingredients = new JSONArray(this.ingredients);
        JSONArray steps = new JSONArray(this.steps);
        String jsonString = "{\"recipe title\":\"" + this.name + "\", \"ingredients\":" + ingredients + ", \"instructions\":" + steps + "}";
        
        return new JSONObject(jsonString);
    }
}   