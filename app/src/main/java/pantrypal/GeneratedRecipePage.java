package pantrypal;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.JSONObject;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// page for generated recipe prior to being saved
// accessed from RecipeCreatorPage
// can move HomePage and alternate GeneratedRecipePage
class GeneratedRecipePage extends Display {
   private GeneratedRecipeView genView;
   private ScrollPane scroller;
   private ImageHeader header;
   private RecipeImage image;
   Footer footer;

   GeneratedRecipePage(Recipe recipe, String input){
      header = new ImageHeader(recipe);
      genView = new GeneratedRecipeView(recipe);
      footer = new GeneratedRecipeFooter(recipe, input);
      
      image = new RecipeImage(recipe);

      scroller = new ScrollPane(genView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);
      
      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);

      image.renderImage();
   }
}

// VBox lays out information in setCenter scroller
class GeneratedRecipeView extends VBox{
   private TextField name;

   GeneratedRecipeView(Recipe recipe) {
      // VBox styling
      this.setSpacing(20);
      this.setPadding(new Insets(40, 0, 0, 40));
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      // title of the recipe
      name = new TextField(recipe.getName());
      name.setFont(PPFonts.makeFont(FF.Itim, 40));
      name.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      this.getChildren().add(name);
   
      // ingredients
      RecipeViewSection ingredients = new RecipeViewSection("Ingredients", recipe.getIngredients());
      this.getChildren().add(ingredients);

      // instructions
      RecipeViewSection instructions = new RecipeViewSection("Instructions", recipe.getSteps());
      this.getChildren().add(instructions);
   }
}

// delete button: moves to HomePage without saving recipe
// regenerate button: creates alt GeneratedRecipePage with regenerated recipe
// save button: moves to HomePage and saves recipe
class GeneratedRecipeFooter extends Footer{
   private Button deleteButton;
   private Button saveButton;
   private Button regenButton;

   private Recipe recipe;
   private String input;

   GeneratedRecipeFooter(Recipe recipe, String input) {
      this.input = input;
      this.recipe = recipe;

      setup();
      this.setAlignment(Pos.CENTER_LEFT);

      // left buttons
      deleteButton = new PPButton("Delete");
      this.add(deleteButton, 0, 0);
      this.setMargin(deleteButton, new Insets(20, 20, 20, 20));  
      this.setHalignment(deleteButton, HPos.LEFT);

      // right buttons
      regenButton = new PPButton("Regenerate");
      saveButton = new PPButton("Save");

      HBox rightButtons = new HBox(regenButton, saveButton);
      rightButtons.setAlignment(Pos.CENTER_RIGHT);
      rightButtons.setSpacing(20);
      this.add(rightButtons, 1, 0);
      this.setMargin(rightButtons,new Insets(20, 20, 20, 20));
      this.setHalignment(rightButtons, HPos.RIGHT);

      addListeners();
   }

   private void addListeners () {
      deleteButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.HOME);
         System.out.println("Delete Generated Recipe button pressed");
      });
      saveButton.setOnAction( e-> {
         System.out.println("Save Button Pressed");
         PantryPal.getRoot().getRecipeList().addRecipe(recipe);
         PantryPal.getRoot().getHome().renderLoadedRecipes(PantryPal.getRoot().getRecipeList());
         PantryPal.getRoot().setPage(Page.HOME);
         
      });
      regenButton.setOnAction(e -> {
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
            Recipe recipeGen = new Recipe(new JSONObject(sc.nextLine()), PantryPal.getRoot().getMeal().getMealType());
            PantryPal.getRoot().setPage(Page.RECIPEGEN, recipeGen, input);
            System.out.println("Generated a new recipe");
            System.out.println(recipeGen.toJson());
         } 
         catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
      });
   }
}