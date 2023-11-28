package pantrypal;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class Tests {

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
    @Test
    void TestRecipeListAdd() throws Exception {
        RecipeList rList = new RecipeList("test");
        ArrayList<String> ingredients1 = new ArrayList();
        ingredients1.add("meat");
        ingredients1.add("cheese");
        ArrayList<String> steps1 = new ArrayList();
        steps1.add("wash");
        steps1.add("cook");

        Recipe r1 = new Recipe("burger", ingredients1, steps1);
        rList.addRecipe(r1);
        assertEquals(1, rList.getSize());

        ArrayList<String> ingredients2 = new ArrayList();
        ingredients1.add("flour");
        ingredients1.add("eggs");
        ArrayList<String> steps2 = new ArrayList();
        steps1.add("mix");
        steps1.add("bake");

        Recipe r2 = new Recipe("cake", ingredients2, steps2);
        rList.addRecipe(r2);
        assertEquals(2, rList.getSize());
        assertEquals(r1, rList.getRecipe(0));
        assertEquals(r2, rList.getRecipe(1));
    }

    // tests that recipe list adds recipes properly
    @Test
    void TestRecipeListRemove() throws Exception {
        RecipeList rList = new RecipeList("test");
        ArrayList<String> ingredients1 = new ArrayList();
        ingredients1.add("meat");
        ingredients1.add("cheese");
        ArrayList<String> steps1 = new ArrayList();
        steps1.add("wash");
        steps1.add("cook");

        Recipe r1 = new Recipe("burger", ingredients1, steps1);
        rList.addRecipe(r1);
        assertEquals(1, rList.getSize());
        rList.removeRecipe(r1);
        assertEquals(0, rList.getSize());
    }
    
    // // testing to make sure that the recipe creator is working
    // // alongside that the recipe list is being updated
    // @Test
    // void TestRecipeCreator() throws Exception {
    //     Thread.sleep(30000);
    //     RecipeCreator rc = new RecipeCreator();
    //     rc.createRecipe("I have ribeye steak, parsley, and russet potatoes.", "dinner");
    //     System.out.println(rc.getRecipeList().getSize());
    //     assertEquals(1, rc.getRecipeList().getSize());
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
    @Test
    void savingRecipe() throws Exception{
        RecipeCreator rc = new RecipeCreator();
        RecipeList rList = new RecipeList("test");
        
        Thread.sleep(20000);
        Recipe r1 = rc.createRecipe("I have ribeye steak, parsley, and russet potatoes.", "dinner");
        rList.addRecipe(r1);
        assertEquals(1, rList.getSize());

        Thread.sleep(20000);
        Recipe r2 = rc.createRecipe("I have walnuts, lettuce, watermleon, and spinach.", "lunch");
        rList.addRecipe(r2);
        assertEquals(2, rList.getSize());
    }

    // tests editing recipes within the recipe list
    @Test
    void editingRecipe() throws Exception {
        RecipeCreator rc = new RecipeCreator();
        RecipeList rList = new RecipeList("test");
        
        Thread.sleep(30000);
        Recipe r1 = rc.createRecipe("I have blueberries, chicken, lettuce, and strawberries.", "breakfast");
        rList.addRecipe(r1);

        assertEquals(1, rList.getSize());
        rList.getRecipe(0).setName("Blueberry Chicken Salad");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("bacon");
        ingredients.add("chicken");
        ingredients.add("orange");
        rList.getRecipe(0).setIngredients(ingredients);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("wash");
        steps.add("cook");
        steps.add("serve");
        rList.getRecipe(0).setSteps(steps);

        assertEquals("Blueberry Chicken Salad", rList.getRecipe(0).getName());
        assertEquals(ingredients, rList.getRecipe(0).getIngredients());
        assertEquals(steps, rList.getRecipe(0).getSteps());
    }
    
    // testing integration for creating recipe when getting meal type, then deleting it
    // based off the wav files we have created
    @Test
    void deletingRecipe() throws Exception{
        Thread.sleep(30000);
        File breakfast = new File("breakfast.wav");
        File breakfastIngredients = new File("breakfastFoods.wav");
        WhisperAPI whisper = new WhisperAPI();
        String mealtype = "";
        String ingredients = "";
        try{
            mealtype = whisper.readFile(breakfast);
            ingredients = whisper.readFile(breakfastIngredients);
        }
        catch (Exception e) {
            System.out.println("error reading file");
        }

        RecipeCreator rc = new RecipeCreator();
        Recipe recipe = rc.createRecipe(ingredients, mealtype);
        RecipeList rList = new RecipeList("test");
        rList.addRecipe(recipe);
        assertEquals(1, rList.getSize());
        rList.removeRecipe(recipe);
        assertEquals(0, rList.getSize());
        
    }

    
    // Integration test for .wav -> string -> recipe
    @Test
    void TestBDD1() throws Exception{
        Thread.sleep(60000);
        File breakfast = new File("breakfast.wav");
        File breakfastIngredients = new File("breakfastFoods.wav");
        WhisperAPI whisper = new WhisperAPI();
        String mealType = "";
        String ingredients = "";
        try {
            mealType = whisper.readFile(breakfast);
            ingredients = whisper.readFile(breakfastIngredients);
        } catch (Exception e) {
            System.out.println("error reading file");
        }

        RecipeCreator rc = new RecipeCreator();
        Recipe recipe = rc.createRecipe(ingredients, mealType);
        
        assertEquals(recipe.getName(), "Blueberry and Strawberry Pancakes with Whipped Cream");
        assertEquals(recipe.getIngredients().size(), 9);
    
    }
    
    // testing scenario 2, where it creates a recipe, saves it, then deletes it
    @Test
    void TestBDD2() throws Exception {
        Thread.sleep(60000);
        File dinner = new File("dinner.wav");
        File dinnerIngredients = new File("dinnerFoods.wav");
        WhisperAPI whisper = new WhisperAPI();
        String mealType = "";
        String ingredients = "";

        try{
            mealType = whisper.readFile(dinner);
            ingredients = whisper.readFile(dinnerIngredients);
        }
        catch (Exception e) {
            System.out.println("error reading file");
        }

        RecipeCreator rc = new RecipeCreator();

        Recipe recipe = rc.createRecipe(ingredients, mealType);
        RecipeList rList = new RecipeList("test");
        rList.addRecipe(recipe);
        assertEquals(1, rList.getSize());

        rList.getRecipe(0).setName("Steak and Potatoes");

        assertEquals(recipe.getName(), "Steak and Potatoes");

        rList.removeRecipe(recipe);
        assertEquals(0, rList.getSize());
    }

}