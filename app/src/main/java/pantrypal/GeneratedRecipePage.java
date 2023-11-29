package pantrypal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.HttpURLConnection;
import java.util.*;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class GeneratedRecipePage extends Display {
   private GeneratedRecipeView genView;
   private ScrollPane scroller;

   GeneratedRecipePage(Recipe recipe, String input){
      header = new Header(recipe.getName());
      genView = new GeneratedRecipeView(recipe);
      footer = new GeneratedRecipeFooter(recipe, input);

      scroller = new ScrollPane(genView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);
      
      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);
   }
}

class GeneratedRecipeView extends VBox{
   private Text ingredients;
   private Text steps;

   private Text ingredientsHeader;
   private Text instructionsHeader;

   GeneratedRecipeView(Recipe recipe) {
      this.setSpacing(10);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
   
      //TODO: IMPLEMENT WAY TO ADD INGREDIENTS AS PARSED FROM recipGEN
      ingredientsHeader = new Text("Ingredients");
      ingredientsHeader.setUnderline(true);
      ingredientsHeader.setFont(Consts.V40);
      ingredientsHeader.setFill(Consts.DARK);
      this.getChildren().add(ingredientsHeader);
      this.setMargin(ingredientsHeader, new Insets(20,0,0,20));

      // ADDING INGREDIENTS
      for(int i = 0; i < recipe.getIngredients().size(); i++){
         ingredients = new Text();
         ingredients.setText(recipe.getIngredients().get(i));
         this.getChildren().add(ingredients);
         ingredients.setWrappingWidth(640);
         ingredients.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         ingredients.setFont(Consts.V20);
         ingredients.setFill(Consts.DARK);
         this.setMargin(ingredients, new Insets(0,0,0,60));
      }

      instructionsHeader = new Text("Instructions");
      instructionsHeader.setUnderline(true);
      instructionsHeader.setFont(Consts.V40);
      instructionsHeader.setFill(Consts.DARK);
      this.getChildren().add(instructionsHeader);
      this.setMargin(instructionsHeader, new Insets(20,0,0,20));
      for(int i = 0; i < recipe.getSteps().size(); i++){
         steps = new Text();
         steps.setText(recipe.getSteps().get(i));
         this.getChildren().add(steps);
         steps.setWrappingWidth(640);
         steps.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         steps.setFont(Consts.V15);
         steps.setFill(Consts.DARK);
         this.setMargin(steps, new Insets(0,0,0,60));
      }
   }
}

class GeneratedRecipeFooter extends Footer{
   private Button backButton;
   private Button saveButton;
   private Button regenerateButton;

   private String input;

   GeneratedRecipeFooter(Recipe recipe, String input) {
      setup();
      this.setAlignment(Pos.CENTER_RIGHT);
      this.setAlignment(Pos.CENTER_LEFT);

      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 480, 20, 20));  
      this.add(backButton, 0, 0);

      regenerateButton = new PPButton("Regenerate");
      this.setMargin(regenerateButton,new Insets(20, 20, 20, 20));
      this.add(regenerateButton, 1, 0);

      saveButton = new PPButton("Save");
      this.setMargin(saveButton, new Insets(20, 20, 20, 20));  
      this.add(saveButton, 2, 0);

      this.input = input;


      addListeners(recipe, input);
   }

   private void addListeners (Recipe recipe, String input) {
      backButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.RECIPECREATOR);
         System.out.println("Back button pressed AFSFA");
      });
      saveButton.setOnAction( e-> {
         System.out.println("Save Button Pressed");
         PantryPal.getRoot().setPage(Page.HOME);
         PantryPal.getRoot().getRecipeList().addRecipe(recipe);
         PantryPal.getRoot().getHome().renderRecipes(recipe);
      });
      regenerateButton.setOnAction(e -> {
          // connects to server
          System.out.println("Regenerate button pressed");
          try{
            System.out.println("Connecting to server...");
            String urlString = "http://localhost:8100/NewRecipe";
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // generate JSON object to send
            System.out.println(input + " " + PantryPal.getRoot().getMeal().getMealType());
            JSONObject test = new JSONObject("{\"prompt\":\"" + input + "\",\"mealType\":\"" + PantryPal.getRoot().getMeal().getMealType() + "\",\"regenerate\":\"" + true +"\"}");
            byte[] out = (test.toString()).getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            System.out.println(test);

            conn.setFixedLengthStreamingMode(length);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // sends POST request with prompy and meal type
            try(OutputStream os = conn.getOutputStream()) {
               os.write(out);
               os.flush();
               os.close();
            }
            // obtains response from server
            Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
            Recipe recipeGen = new Recipe(new JSONObject(sc.nextLine()));
            PantryPal.getRoot().setPage(Page.RECIPEGEN, recipeGen);
            System.out.println("Generated a new recipe");
         } 
         catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
      });
   }
}