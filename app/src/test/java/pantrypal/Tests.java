package pantrypal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    // testing to make sure that the chatbot is working,
    // and returning the correct format of the recipe creator
    @Test
    void TestChatGPT1() throws Exception {
        Thread.sleep(30000);
        ChatGPT bot = new ChatGPT("I have meatballs, spaghetti noodles, and pasta sauce.");
        assertEquals(JSONObject.class, bot.getResponse().getClass());
        assertTrue(bot.getResponse().has("recipe title"));
        assertTrue(bot.getResponse().has("ingredients"));
        assertTrue(bot.getResponse().has("instructions"));
    }
     
    // testing to make sure that the recipe creator is working
    // alongside that the recipe list is being updated
    @Test
    void TestEverything() throws Exception {
        Thread.sleep(30000);
        RecipeCreator rc = new RecipeCreator();
        rc.createRecipe("I have ribeye steak, parsley, and russet potatoes.");
        System.out.println(rc.getRecipeList().getSize());
        assertEquals(1, rc.getRecipeList().getSize());
    }
    
    // tests the chatbot making sure an error is not thrown and a
    // recipe is still being returned
    @Test
    void TestChatGPT3() throws Exception {
        Thread.sleep(30000);
        boolean error = false;
        try {
            ChatGPT bot = new ChatGPT("I have everything, give me your best course meal");
        } catch (Exception e) {
            error = true;
        }
        assertFalse(error);
    }
    
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
    @Test
    void creatingRecipeScenarioOne() throws IOException{
        assertEquals("breakfast", MealTypePage.getMealType());
    }
}