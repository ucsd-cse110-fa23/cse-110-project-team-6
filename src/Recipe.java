class Recipe {
    private String name;
    private String ingredients;
    private String steps;

    Recipe (String name, String prepTime, String cookTime, String ingredients, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
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

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}   