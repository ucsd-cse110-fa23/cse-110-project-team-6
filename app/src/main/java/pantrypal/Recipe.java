package pantrypal;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;

    public Recipe (String name, ArrayList<String> ingredients, ArrayList<String> steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    Recipe () {
    }

    public String getName() {
        return name;
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
}   