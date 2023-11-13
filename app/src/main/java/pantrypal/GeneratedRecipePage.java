package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;

class GeneratedRecipePage extends Display {
   private GeneratedRecipeView genView;
   private ScrollPane scroller;

   GeneratedRecipePage(Recipe recipe){
      header = new Header(recipe.getName());
      genView = new GeneratedRecipeView(recipe);
      footer = new GeneratedRecipeFooter(recipe);

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
   
      //TODO: IMPLEMENT WAY TO ADD INGREDIENTS AS PARSED FROM recipGEN
      ingredientsHeader = new Text("Ingredients");
      ingredientsHeader.setUnderline(true);
      ingredientsHeader.setFont(Consts.V40);
      ingredientsHeader.setFill(Consts.DARK);
      this.getChildren().add(ingredientsHeader);
      this.setMargin(ingredientsHeader, new Insets(20,0,0,20));
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

   GeneratedRecipeFooter(Recipe recipe) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 480, 20, 20));  
      this.add(backButton, 0, 0);

      saveButton = new PPButton("Save");
      this.setMargin(saveButton, new Insets(20, 20, 20, 20));  
      this.add(saveButton, 1, 0);

      addListeners(recipe);
   }

   private void addListeners (Recipe recipe) {
      backButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.RECIPECREATOR);
      });
      saveButton.setOnAction( e-> {
         //CODE FOR RECIPE GENERATED FROM CHATGPT
         //TEMP TEST RECIPES
         System.out.println("Save Button Pressed");
         PantryPal.getRoot().setPage(Page.HOME);
         PantryPal.getRoot().getRecipeList().addRecipe(recipe);
         PantryPal.getRoot().getHome().renderRecipes(recipe);
      });
   }
}