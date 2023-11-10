package main.java.ppal;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;

    Recipe (String name, ArrayList<String> ingredients, ArrayList<String> steps) {
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

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    
    }
    */
}   