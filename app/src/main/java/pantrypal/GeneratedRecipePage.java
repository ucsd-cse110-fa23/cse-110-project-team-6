package pantrypal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class GeneratedRecipePage extends Display {
   private GeneratedRecipeView genView;
   private ScrollPane scroller;

   GeneratedRecipePage(Recipe recipe, String input){
      // header = new Header(recipe.getName());
      ImageView imageView = new ImageView();
      Image image = new Image(Consts.logoURL, Consts.WIDTH, 350, true, true); //TODO ADD IMAGE
      imageView.setImage(image);
      imageView.setFitWidth(Consts.PIC_WIDTH);
      imageView.setFitHeight(Consts.PIC_HEIGHT);


      genView = new GeneratedRecipeView(recipe);
      footer = new GeneratedRecipeFooter(recipe, input);

      scroller = new ScrollPane(genView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);
      
      this.setTop(imageView);
      this.setCenter(scroller);
      this.setBottom(footer);
   }
}

class GeneratedRecipeView extends VBox{
   private TextField name;


   GeneratedRecipeView(Recipe recipe) {
      this.setSpacing(20);
         this.setPadding(new Insets(40, 0, 0, 40));
         this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

         name = new TextField(recipe.getName());
         name.setFont(PPFonts.makeFont(FF.Itim, 40));
         name.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         this.getChildren().add(name);
   
      RecipeViewSection ingredients = new RecipeViewSection("Ingredients", recipe.getIngredients());
      this.getChildren().add(ingredients);

      // //TODO: IMPLEMENT WAY TO ADD INGREDIENTS AS PARSED FROM recipGEN
      // ingredientsHeader = new Text("Ingredients");
      // ingredientsHeader.setUnderline(true);
      // ingredientsHeader.setFont(Consts.F40);
      // ingredientsHeader.setFill(Consts.DARK);
      // this.getChildren().add(ingredientsHeader);
      // this.setMargin(ingredientsHeader, new Insets(20,0,0,20));

      // // ADDING INGREDIENTS
      // for(int i = 0; i < recipe.getIngredients().size(); i++){
      //    ingredients = new Text();
      //    ingredients.setText(recipe.getIngredients().get(i));
      //    this.getChildren().add(ingredients);
      //    ingredients.setWrappingWidth(640);
      //    ingredients.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      //    ingredients.setFont(Consts.F20);
      //    ingredients.setFill(Consts.DARK);
      //    this.setMargin(ingredients, new Insets(0,0,0,60));
      // }

      RecipeViewSection instructions = new RecipeViewSection("Instructions", recipe.getSteps());
      this.getChildren().add(instructions);

      // instructionsHeader = new Text("Instructions");
      // instructionsHeader.setUnderline(true);
      // instructionsHeader.setFont(Consts.F40);
      // instructionsHeader.setFill(Consts.DARK);
      // this.getChildren().add(instructionsHeader);
      // this.setMargin(instructionsHeader, new Insets(20,0,0,20));
      // for(int i = 0; i < recipe.getSteps().size(); i++){
      //    steps = new Text();
      //    steps.setText(recipe.getSteps().get(i));
      //    this.getChildren().add(steps);
      //    steps.setWrappingWidth(640);
      //    steps.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      //    steps.setFont(Consts.F15);
      //    steps.setFill(Consts.DARK);
      //    this.setMargin(steps, new Insets(0,0,0,60));
      // }
   }
}

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

      deleteButton = new PPButton("Delete");
      this.add(deleteButton, 0, 0);
      this.setMargin(deleteButton, new Insets(20, 20, 20, 20));  
      this.setHalignment(deleteButton, HPos.LEFT);

      regenButton = new PPButton("Regenerate");
      // this.add(regenButton, 1, 0);
      // this.setMargin(regenButton,new Insets(20, 20, 20, 20));


      saveButton = new PPButton("Save");
      // this.add(saveButton, 1, 0);
      // this.setMargin(saveButton, new Insets(20, 20, 20, 20));  

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
            Recipe recipeGen = new Recipe(new JSONObject(sc.nextLine()));
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