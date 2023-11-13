package pantrypal;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class Tests {

    // testing to make sure that the chatbot is working,
    // and returning the correct format of the recipe creator
    @Test
    void TestChatGPT1() throws Exception {
        Thread.sleep(30000);
        ChatGPT bot = new ChatGPT("I have meatballs, spaghetti noodles, and pasta sauce.", "dinner");
        assertEquals(JSONObject.class, bot.getResponse().getClass());
        assertTrue(bot.getResponse().has("recipe title"));
        assertTrue(bot.getResponse().has("ingredients"));
        assertTrue(bot.getResponse().has("instructions"));
    }

    // tests the chatbot making sure an error is not thrown and a
    // recipe is still being returned
    @Test
    void TestChatGPT2() throws Exception {
        Thread.sleep(30000);
        boolean error = false;
        try {
            ChatGPT bot = new ChatGPT("I have everything, give me your best course meal", "dinner");
        } catch (Exception e) {
            error = true;
        }
        assertFalse(error);
    }

    // tests that recipe list adds recipes properly
    @Test
    void TestRecipeListAdd() throws Exception {
        RecipeList rList = new RecipeList();
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

    @Test
    void TestWhisperAPI() throws Exception {
        WhisperAPI whisper = new WhisperAPI();
        File file = new File("TEST_INPUT.wav");
        String transcription = whisper.readFile(file);
        assertEquals(transcription, "This is a test.");
    }

    // testing integration for creating recipe when getting meal type
    // @Test
    // void creatingRecipeScenarioBreakfast() throws IOException{
        
    //     PPMic mic = new PPMic();
        
        
        
        
    // }
    
    // @Test
    // void creatingRecipeScenarioLunch() throws IOException{
        
        
        
    // }

    
}