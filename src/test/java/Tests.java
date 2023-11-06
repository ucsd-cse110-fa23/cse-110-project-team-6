import ppal.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {
    @Test
    void TestChatGPT1() throws Exception {
        ChatGPT bot = new ChatGPT("I have meatballs, spaghetti noodles, and pasta sauce.");
        assertEquals(JSONObject.class, bot.getResponse().getClass());
        assertTrue(bot.getResponse().has("recipe title"));
        assertTrue(bot.getResponse().has("ingredients"));
        assertTrue(bot.getResponse().has("instructions"));
    }
     
    @Test
    void TestEverything() {
        RecipeCreator rc = new RecipeCreator();
        rc.createRecipe("I have ribeye steak, parsley, russet potatoes, garlic, butter, rosemary, and sauerkraut.");
        assertEquals(1, rc.getRecipeList().getSize(), 1);
        //rc.createRecipe("I have spaghetti, tomato sauce, and meatballs");
        //assertEquals(2, rc.getRecipeList().getSize());
    }
    
    @Test
    void TestChatGPT3(){
        boolean error = false;
        try {
            ChatGPT bot = new ChatGPT("I have everything, give me your best course meal");
        } catch (Exception e) {
            error = true;
        }
        assertFalse(error);
    }
}