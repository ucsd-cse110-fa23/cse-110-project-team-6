package pantrypal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
//import

import pantrypal.DallE.MockDalle;

public class Tests {

    // changing string to json object
    String recipeJSON1 = "{\"recipe title\":\"Ribeye Steak with Garlic Potatoes\",\"instructions\":[\"1. Preheat the oven to 425 degrees Fahrenheit.\",\"2. Cut the russet potatoes into 1-inch cubes.\",\"3. In a bowl, combine the potatoes with 2 tablespoons of olive oil, 2 cloves of minced garlic, and salt and pepper to taste.\",\"4. Line a baking sheet with foil and spread the potatoes evenly on it.\",\"5. Bake for 25-30 minutes, or until the potatoes are soft and crispy.\",\"6. While the potatoes are cooking, heat a cast iron skillet over high heat.\",\"7. Season the ribeye steak with salt and pepper.\",\"8. Once the skillet is hot, add 1 tablespoon of olive oil and sear the steak for 3-4 minutes on each side for medium-rare, or adjust cooking time to desired level of doneness.\",\"9. Let the steak rest for 5 minutes before slicing it.\",\"10. Serve the steak with the roasted garlic potatoes and garnish with chopped parsley.\",\"11. Enjoy your delicious Ribeye Steak with Garlic Potatoes!\"],\"ingredients\":[\"Ribeye steak\",\"Parsley\",\"Russet potatoes\",\"Olive oil\",\"Garlic\",\"Salt\",\"Pepper\"]}";
    JSONObject recipe = new JSONObject(recipeJSON1);

    String recipeJSON2 = "{\"recipe title\":\"Peanut Butter and Jelly Sandwich\",\"instructions\":[\"1. Filler 1\",\"2. Filler 2\",\"3. Filler 3\",\"4. Filler 4\"],\"ingredients\":[\"Bread\",\"Peanut butter\",\"Jelly\"]}";
    JSONObject recipe2 = new JSONObject(recipeJSON2);

    String recipeJSON3 = "{\"recipe title\":\"Ice Cream Cake\",\"instructions\":[\"1. Filler 1\",\"2. Filler 2\",\"3. Filler 3\",\"4. Filler 4\"],\"ingredients\":[\"Candles\",\"Cake\",\"Ice Cream\"]}";
    JSONObject recipe3 = new JSONObject(recipeJSON3);

    @Test
    void createRecipe() throws Exception {
        Recipe r = new Recipe(recipe, "Dinner");
        assertEquals("Ribeye Steak with Garlic Potatoes", r.getName());
        assertEquals(7, r.getIngredients().size());
        assertEquals(11, r.getSteps().size());
    }

    @Test
    void addRecipeToList() throws Exception {
        Recipe r = new Recipe(recipe, "Dinner");
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        assertEquals(1, rl.getSize());
    }
    
    @Test
    void deleteRecipeFromList() throws Exception {
        Recipe r = new Recipe(recipe, "Dinner");
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        rl.removeRecipe(r);
        assertEquals(0, rl.getSize());
    }

    @Test
    void editRecipe() throws Exception{
        Recipe r = new Recipe(recipe, "Dinner");
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        r.setName("Steak and Potatoes");
        assertEquals("Steak and Potatoes", r.getName());
        ArrayList<String> newIngredients = new ArrayList<String>();
        newIngredients.add("Ribeye steak");
        newIngredients.add("parsley");
        newIngredients.add("russet potatoes");
        newIngredients.add("olive oil");
        newIngredients.add("garlic");
        newIngredients.add("salt");
        newIngredients.add("pepper");
        newIngredients.add("butter");
        r.setIngredients(newIngredients);
        assertEquals(8, r.getIngredients().size());
    }

    // testing the end to end functionality of the program
    // using the add, delete, and edit functions
    @Test
    void EndToEnd() throws Exception{
        Recipe r = new Recipe(recipe, "Dinner");
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        assertEquals(1, rl.getSize());
        Recipe r2 = new Recipe(recipe2, "Lunch");
        rl.addRecipe(r2);
        assertEquals(2, rl.getSize());
        rl.removeRecipe(r);
        assertEquals(1, rl.getSize());
        assertEquals("Peanut Butter and Jelly Sandwich", rl.getRecipe(0).getName());
        ArrayList<String> newIngredients = new ArrayList<String>();
        newIngredients.add("Bread");
        newIngredients.add("Peanut butter");
        newIngredients.add("Jelly");
        rl.getRecipe(0).setIngredients(newIngredients);
        assertEquals(3, rl.getRecipe(0).getIngredients().size());
        newIngredients.add("Butter");
        rl.getRecipe(0).setIngredients(newIngredients);
        assertEquals(4, rl.getRecipe(0).getIngredients().size());
        rl.getRecipe(0).setName("Peanut Butter and Jelly Sandwich with Butter");
        assertEquals("Peanut Butter and Jelly Sandwich with Butter", rl.getRecipe(0).getName());
    }

    @Test
    void RecipeListUsername() {
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Breakfast");
        RecipeList rl = new RecipeList();
        rl.setUsername("test");
        rl.addRecipe(r1);
        rl.addRecipe(r2);

        assertTrue(rl.toString().contains("test"));
    }

    @Test
    void MultipleUsers() {
        Recipe r1 = new Recipe(recipe, "Dinner");
        RecipeList rl1 = new RecipeList();
        rl1.setUsername("user one");
        rl1.addRecipe(r1);

        Recipe r2 = new Recipe(recipe, "Dinner");
        RecipeList rl2 = new RecipeList();
        rl2.setUsername("user two");
        rl2.addRecipe(r2);

        assertTrue(rl1.toString().contains("user one"));
        assertEquals(1, rl1.getSize());
        assertTrue(rl2.toString().contains("user two"));
        assertEquals(1, rl2.getSize());
    }

    @Test
    void EmptySort() {
        RecipeList rl = new RecipeList();
        rl.alphaSort();
        assertEquals(0 ,rl.getSize());
        rl.chronoSort();
        assertEquals(0 ,rl.getSize());
    }

    @Test
    void AlphabeticalSort() {
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        rl.addRecipe(r1);
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        rl.alphaSort();
        assertEquals(3 ,rl.getSize());
        assertEquals(rl.getRecipe(0), r3);
        assertEquals(rl.getRecipe(1), r2);
        assertEquals(rl.getRecipe(2), r1);
    }

    @Test
    void ChronoSort() {
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        rl.addRecipe(r1);
        rl.chronoSort();
        assertEquals(3 ,rl.getSize());
        assertEquals(rl.getRecipe(0), r1);
        assertEquals(rl.getRecipe(1), r3);
        assertEquals(rl.getRecipe(2), r2);
    }

    @Test
    void filterBreakfast() {
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        String tag = "";
        rl.addRecipe(r1);
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        for(int i = 0; i < 3; i++){
            if(rl.getRecipe(i).getTag().equals("Breakfast")){
                tag = rl.getRecipe(i).getTag();
            }
        }
        assertEquals(3, rl.getSize()); 
        assertEquals(tag, r3.getTag());      
    }
    
    @Test
    void filterLunch(){
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        String tag = "";
        rl.addRecipe(r1);
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        for(int i = 0; i < 3; i++){
            if(rl.getRecipe(i).getTag().equals("Lunch")){
                tag = rl.getRecipe(i).getTag();
            }
        }
        assertEquals(3, rl.getSize()); 
        assertEquals(tag, r2.getTag());
    }

    @Test
    void filterDinner(){
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        String tag = "";
        rl.addRecipe(r1);
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        for(int i = 0; i < 3; i++){
            if(rl.getRecipe(i).getTag().equals("Dinner")){
                tag = rl.getRecipe(i).getTag();
            }
        }
        assertEquals(3, rl.getSize()); 
        assertEquals(tag, r1.getTag());  
    }

    @Test
    void filterNone(){
        RecipeList rl = new RecipeList();
        Recipe r1 = new Recipe(recipe, "Dinner");
        Recipe r2 = new Recipe(recipe2, "Lunch");
        Recipe r3 = new Recipe(recipe3, "Breakfast");
        rl.addRecipe(r1);
        rl.addRecipe(r2);
        rl.addRecipe(r3);
        for(int i = 0; i < rl.getSize(); i++){
            if((rl.getRecipe(i).getTag().equals("None"))){
                rl.removeRecipe(rl.getRecipe(i));
            }
            else{
                continue;
            }
        }
        assertEquals(3, rl.getSize());
        assertEquals(rl.getRecipe(0), r3);
        assertEquals(rl.getRecipe(1), r2);
        assertEquals(rl.getRecipe(2), r1);
    }

    // create a mock test for recipe regenerator
    @Test
    void recipeRegenerationTest(){
        MockRecipeCreator mrc = new MockRecipeCreator();
        assertTrue(mrc.createRecipe().similar(recipe));
        mrc.regenerate();
        assertTrue(mrc.createRecipe().similar(recipe2));
    }

    @Test
    void imageGeneration(){
        MockDalle md = new MockDalle();
        String url;

        md.generateMockImageUrl();
        url = md.getMockUrl();

        assertTrue(url.equals("some new image url"));

    }
}
