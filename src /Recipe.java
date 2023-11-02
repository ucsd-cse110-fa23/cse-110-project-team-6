public class Recipe {
    private String name;
    private int prepTime;
    private int cookTime;
    private String ingredients;
    private String steps;

    Recipe (String name, int prepTime, int cookTime, String ingredients, String steps) {
        this.name = name;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public int getPrepTime() {
        return prepTime;
    }   

    public int getCookTime() {
        return cookTime;
    }

    public String getIngredients() {
        return ingredients;
    } 

    public String getSteps() {
        return steps;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}   