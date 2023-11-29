package pantrypal;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
//import

public class Tests {

    // changing string to json object
    String recipeJSON = "{\"recipe title\":\"Ribeye Steak with Garlic Potatoes\",\"instructions\":[\"1. Preheat the oven to 425 degrees Fahrenheit.\",\"2. Cut the russet potatoes into 1-inch cubes.\",\"3. In a bowl, combine the potatoes with 2 tablespoons of olive oil, 2 cloves of minced garlic, and salt and pepper to taste.\",\"4. Line a baking sheet with foil and spread the potatoes evenly on it.\",\"5. Bake for 25-30 minutes, or until the potatoes are soft and crispy.\",\"6. While the potatoes are cooking, heat a cast iron skillet over high heat.\",\"7. Season the ribeye steak with salt and pepper.\",\"8. Once the skillet is hot, add 1 tablespoon of olive oil and sear the steak for 3-4 minutes on each side for medium-rare, or adjust cooking time to desired level of doneness.\",\"9. Let the steak rest for 5 minutes before slicing it.\",\"10. Serve the steak with the roasted garlic potatoes and garnish with chopped parsley.\",\"11. Enjoy your delicious Ribeye Steak with Garlic Potatoes!\"],\"ingredients\":[\"Ribeye steak\",\"Parsley\",\"Russet potatoes\",\"Olive oil\",\"Garlic\",\"Salt\",\"Pepper\"]}";
    JSONObject recipe = new JSONObject(recipeJSON);

    String recipeJSON2 = "{\"recipe title\":\"Peanut Butter and Jelly Sandwich\",\"instructions\":[\"1. Filler 1\",\"2. Filler 2\",\"3. Filler 3\",\"4. Filler 4\"],\"ingredients\":[\"Bread\",\"Peanut butter\",\"Jelly\"]}";
    JSONObject recipe2 = new JSONObject(recipeJSON2);

    @Test
    void createRecipe() throws Exception {
        Recipe r = new Recipe(recipe);
        assertEquals("Ribeye Steak with Garlic Potatoes", r.getName());
        assertEquals(7, r.getIngredients().size());
        assertEquals(11, r.getSteps().size());
    }

    @Test
    void addRecipeToList() throws Exception {
        Recipe r = new Recipe(recipe);
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        assertEquals(1, rl.getSize());
    }

    @Test
    void test() throws Exception {
        //ChatGPT recipeCreator = new ChatGPT("Ribeye steak, parsley, russet potatoes, olive oil, garlic, salt, pepper", "dinner");
        //assertEquals(recipeCreator.getResponse(), "");
    }
    
    @Test
    void deleteRecipeFromList() throws Exception {
        Recipe r = new Recipe(recipe);
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        rl.removeRecipe(r);
        assertEquals(0, rl.getSize());
    }

    @Test
    void editRecipe() throws Exception{
        Recipe r = new Recipe(recipe);
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

    //tests that dall-e 
    @Test
    void TestFindImage() throws Exception{
        DallE dalle = new DallE("input", "test");

        assertEquals(true, dalle.foundImage);
    }

    //tests that dall-e correctly 
    @Test
    void TestImageDelete() {
        DallE dalle = new DallE("input", "test");
        
        assertEquals(false, Paths.get(("test.jpg")).toFile().exists());
    }

    // testing the end to end functionality of the program
    // using the add, delete, and edit functions
    @Test
    void EndToEnd() throws Exception{
        Recipe r = new Recipe(recipe);
        RecipeList rl = new RecipeList();
        rl.addRecipe(r);
        assertEquals(1, rl.getSize());
        Recipe r2 = new Recipe(recipe2);
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

    
    
    // testing to make sure that the chatbot is working,
    // and returning the correct format of the recipe creator
    // @Test
    // void TestChatGPT1() throws Exception {
    //     Thread.sleep(30000);
    //     ChatGPT bot = new ChatGPT("I have meatballs, spaghetti noodles, and pasta sauce.", "dinner");
    //     assertEquals(JSONObject.class, bot.getResponse().getClass());
    //     assertTrue(bot.getResponse().has("recipe title"));
    //     assertTrue(bot.getResponse().has("ingredients"));
    //     assertTrue(bot.getResponse().has("instructions"));
    // }

    // tests the chatbot making sure an error is not thrown and a
    // recipe is still being returned
    // @Test
    // void TestChatGPT2() throws Exception {
    //     Thread.sleep(30000);
    //     boolean error = false;
    //     try {
    //         ChatGPT bot = new ChatGPT("I have everything, give me your best course meal", "dinner");
    //     } catch (Exception e) {
    //         error = true;
    //     }
    //     assertFalse(error);
    // }

    // tests that recipe list adds recipes properly
    // @Test
    // void TestRecipeListAdd() throws Exception {
    //     RecipeList rList = new RecipeList();
    //     ArrayList<String> ingredients1 = new ArrayList();
    //     ingredients1.add("meat");
    //     ingredients1.add("cheese");
    //     ArrayList<String> steps1 = new ArrayList();
    //     steps1.add("wash");
    //     steps1.add("cook");

    //     Recipe r1 = new Recipe("burger", ingredients1, steps1);
    //     rList.addRecipe(r1);
    //     assertEquals(1, rList.getSize());

    //     ArrayList<String> ingredients2 = new ArrayList();
    //     ingredients1.add("flour");
    //     ingredients1.add("eggs");
    //     ArrayList<String> steps2 = new ArrayList();
    //     steps1.add("mix");
    //     steps1.add("bake");

    //     Recipe r2 = new Recipe("cake", ingredients2, steps2);
    //     rList.addRecipe(r2);
    //     assertEquals(2, rList.getSize());
    //     assertEquals(r1, rList.getRecipe(0));
    //     assertEquals(r2, rList.getRecipe(1));
    // }

    // // tests that recipe list adds recipes properly
    // @Test
    // void TestRecipeListRemove() throws Exception {
    //     RecipeList rList = new RecipeList();
    //     ArrayList<String> ingredients1 = new ArrayList();
    //     ingredients1.add("meat");
    //     ingredients1.add("cheese");
    //     ArrayList<String> steps1 = new ArrayList();
    //     steps1.add("wash");
    //     steps1.add("cook");

    //     Recipe r1 = new Recipe("burger", ingredients1, steps1);
    //     rList.addRecipe(r1);
    //     assertEquals(1, rList.getSize());
    //     rList.removeRecipe(r1);
    //     assertEquals(0, rList.getSize());
    // }
    
    // // testing to make sure that the recipe creator is working
    // // alongside that the recipe list is being updated
    // @Test
    // void TestRecipeCreator() throws Exception {
    //     //Thread.sleep(30000);
    //     RecipeCreator rc = new RecipeCreator();
    //     Recipe newRecipe = rc.createRecipe("Ribeye steak, parsley, and russet potatoes.", "dinner");
    //     String json = (newRecipe.toJson().toString());        
        
    // }
    
    
    // tests the whisper api making sure an error is not thrown
    // an actual recording was created

    //WILL NEVER WORK IN GITHUB ACTIONS
    // @Test
    // void TestRecording() throws Exception {
    //     Recording rec = new Recording();
    //     rec.createRecording();
    //     File file = new File("Recording.wav");
    //     assertTrue(file.exists());
    // }

    // @Test
    // void TestWhisperAPI() throws Exception {
    //     WhisperAPI whisper = new WhisperAPI();
    //     File file = new File("TEST_INPUT.wav");
    //     String transcription = whisper.readFile(file);
    //     assertEquals(transcription, "This is a test.");
    // }

    // testing integration for creating recipe when getting meal type
    // TEST THE FEATURES WITHIN THE DOCUMENT 
    // @Test
    // void savingRecipe() throws Exception{
    //     RecipeCreator rc = new RecipeCreator();
    //     RecipeList rList = new RecipeList();
        
    //     Thread.sleep(20000);
    //     Recipe r1 = rc.createRecipe("I have ribeye steak, parsley, and russet potatoes.", "dinner");
    //     rList.addRecipe(r1);
    //     assertEquals(1, rList.getSize());

    //     Thread.sleep(20000);
    //     Recipe r2 = rc.createRecipe("I have walnuts, lettuce, watermleon, and spinach.", "lunch");
    //     rList.addRecipe(r2);
    //     assertEquals(2, rList.getSize());
    // }

    // // tests editing recipes within the recipe list
    // @Test
    // void editingRecipe() throws Exception {
    //     RecipeCreator rc = new RecipeCreator();
    //     RecipeList rList = new RecipeList();
        
    //     Thread.sleep(30000);
    //     Recipe r1 = rc.createRecipe("I have blueberries, chicken, lettuce, and strawberries.", "breakfast");
    //     rList.addRecipe(r1);

    //     assertEquals(1, rList.getSize());
    //     rList.getRecipe(0).setName("Blueberry Chicken Salad");
    //     ArrayList<String> ingredients = new ArrayList<>();
    //     ingredients.add("bacon");
    //     ingredients.add("chicken");
    //     ingredients.add("orange");
    //     rList.getRecipe(0).setIngredients(ingredients);

    //     ArrayList<String> steps = new ArrayList<>();
    //     steps.add("wash");
    //     steps.add("cook");
    //     steps.add("serve");
    //     rList.getRecipe(0).setSteps(steps);

    //     assertEquals("Blueberry Chicken Salad", rList.getRecipe(0).getName());
    //     assertEquals(ingredients, rList.getRecipe(0).getIngredients());
    //     assertEquals(steps, rList.getRecipe(0).getSteps());
    // }
    
    // // testing integration for creating recipe when getting meal type, then deleting it
    // // based off the wav files we have created
    // @Test
    // void deletingRecipe() throws Exception{
    //     Thread.sleep(30000);
    //     File breakfast = new File("breakfast.wav");
    //     File breakfastIngredients = new File("breakfastFoods.wav");
    //     WhisperAPI whisper = new WhisperAPI();
    //     String mealtype = "";
    //     String ingredients = "";
    //     try{
    //         mealtype = whisper.readFile(breakfast);
    //         ingredients = whisper.readFile(breakfastIngredients);
    //     }
    //     catch (Exception e) {
    //         System.out.println("error reading file");
    //     }

    //     RecipeCreator rc = new RecipeCreator();
    //     Recipe recipe = rc.createRecipe(ingredients, mealtype);
    //     RecipeList rList = new RecipeList();
    //     rList.addRecipe(recipe);
    //     assertEquals(1, rList.getSize());
    //     rList.removeRecipe(recipe);
    //     assertEquals(0, rList.getSize());
        
    //}

    
    // Integration test for .wav -> string -> recipe
    // @Test
    // void TestBDD1() throws Exception{
    //     Thread.sleep(60000);
    //     File breakfast = new File("breakfast.wav");
    //     File breakfastIngredients = new File("breakfastFoods.wav");
    //     WhisperAPI whisper = new WhisperAPI();
    //     String mealType = "";
    //     String ingredients = "";
    //     try {
    //         mealType = whisper.readFile(breakfast);
    //         ingredients = whisper.readFile(breakfastIngredients);
    //     } catch (Exception e) {
    //         System.out.println("error reading file");
    //     }

    //     RecipeCreator rc = new RecipeCreator();
    //     Recipe recipe = rc.createRecipe(ingredients, mealType);
        
    //     assertEquals(recipe.getName(), "Blueberry and Strawberry Pancakes with Whipped Cream");
    //     assertEquals(recipe.getIngredients().size(), 9);
    
    // }
    
    // // testing scenario 2, where it creates a recipe, saves it, then deletes it
    // @Test
    // void TestBDD2() throws Exception {
    //     Thread.sleep(60000);
    //     File dinner = new File("dinner.wav");
    //     File dinnerIngredients = new File("dinnerFoods.wav");
    //     WhisperAPI whisper = new WhisperAPI();
    //     String mealType = "";
    //     String ingredients = "";

    //     try{
    //         mealType = whisper.readFile(dinner);
    //         ingredients = whisper.readFile(dinnerIngredients);
    //     }
    //     catch (Exception e) {
    //         System.out.println("error reading file");
    //     }

    //     RecipeCreator rc = new RecipeCreator();

    //     Recipe recipe = rc.createRecipe(ingredients, mealType);
    //     RecipeList rList = new RecipeList();
    //     rList.addRecipe(recipe);
    //     assertEquals(1, rList.getSize());

    //     rList.getRecipe(0).setName("Steak and Potatoes");

    //     assertEquals(recipe.getName(), "Steak and Potatoes");

    //     rList.removeRecipe(recipe);
    //     assertEquals(0, rList.getSize());
    // }

}