package pantrypal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Recipe {
    private String tag;
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private String imageURL = "";


    public Recipe (String name, ArrayList<String> ingredients, ArrayList<String> steps, String tag) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.tag = tag;
    }

    public Recipe (JSONObject recipe, String tag) {
        System.out.println(tag);
        this.tag = tag;
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

    public Recipe (JSONObject recipe) {
        this.tag = recipe.getString("tag");
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

    // returns recipe meal type
    public String getTag() {
        return tag;
    }

    //returns recipe title
    public String getName() {
        return name;
    }

    // returns ingredient list
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // returns ingredients as a string
    public String getIngredientString() {
        String s = "";
        for (int i = 0; i < ingredients.size(); i++) {
            s += ingredients.get(i) + "\n";
        }
        return s;
    }

    // return steps as a string
    public String getStepString() {
        String s = "";
        for (int i = 0; i < steps.size(); i++) {
            s += steps.get(i) + "\n";
        }
        return s;
    }

    // returns recipe steps
    public ArrayList<String> getSteps() {
        return steps;
    }

    // sets recipe names
    public void setName(String name) {
        this.name = name;
    }

    // sets ingredients
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    //sets recipe steps
    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    // sets url associated with recipe
    public void setURL(String url) {
        this.imageURL = url;
    }

    // returns url associated with recipe
    public String getURL() {
        return this.imageURL;
    }

    // returns recipe as json
    public JSONObject toJson() {
        JSONArray ingredients = new JSONArray(this.ingredients);
        JSONArray steps = new JSONArray(this.steps);
        String jsonString = "{\"recipe title\":\"" + this.name + "\", \"tag\":\"" + this.tag + "\", \"ingredients\":" + ingredients + ", \"instructions\":" + steps + "}";
        System.out.println(jsonString);
        return new JSONObject(jsonString);
    }
}