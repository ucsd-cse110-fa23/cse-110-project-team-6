package main.java.ppal;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;

class RecipeFullPage extends Display {
   private TextField recipeName;
   private TextField prepTime;
   private TextField cookTime;
   private TextField ingredients;
   private TextField steps;

   private ScrollPane scroller;
   private RecipeFullView recipeFullView;

   RecipeFullPage (Recipe recipe) {
      header = new Header(recipe.getName());
      recipeFullView = new RecipeFullView(recipe);
      footer = new RecipeFullFooter(recipe);

      scroller = new ScrollPane(recipeFullView); //fill in with class for recipe display
      //scroller.setFitToHeight(true);
      //scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);
   }

   class RecipeFullView extends VBox{
      private Text ingredients;
      private Text steps;
      private Recipe recipe;

      RecipeFullView(Recipe recipe){
         this.setSpacing(50);
   
         //TODO: IMPLEMENT WAY TO ADD INGREDIENTS AS PARSED FROM recipe
         ingredients = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testtest test test test test test test test test test test test test test test test test test test test test test test test test test testtest test test test test test test test test test test test test test test test test test test test test test test test test test testtest test test test test test test test test test test test test test test test test test test test test test test test test test testtest test test test test test test test test test test test test test test test test test test test test test test test test test testtest test test test test test test test test test test test test test test test test test test test test test test test test test test test  ");
         ingredients.setWrappingWidth(680);
         ingredients.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         ingredients.setFont(Consts.V30);
         ingredients.setFill(Consts.DARK);

         //TODO: IMPLEMENT WAY TO ADD STEPS AS PARSED FROM recipe
         steps = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test  ");
         steps.setWrappingWidth(680);
         steps.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         steps.setFont(Consts.V12);
         steps.setFill(Consts.DARK);


         this.getChildren().add(ingredients);
         this.getChildren().add(steps);
      }
   }

   class RecipeFullFooter extends Footer{
      private Button backButton;
      private Button editButton;

      RecipeFullFooter(Recipe recipe){
         setup();
         this.setAlignment(Pos.CENTER_LEFT);
         backButton = new PPButton("Back");
         this.setMargin(backButton, new Insets(20, 20, 20, 20));  
         this.getChildren().add(backButton);

         editButton = new PPButton("Edit");
         this.setMargin(editButton, new Insets(20, 20, 20, 490));  
         this.getChildren().add(editButton);

         addListeners(recipe);
      }  

      private void addListeners (Recipe recipe) {
         backButton.setOnAction(e -> {
            PantryPal.getRoot().setPage(Page.HOME);
      });
         editButton.setOnAction(e -> {
            //TODO: SETUP EDIT BUTTTON FUNCTIONALITY
            System.out.println("Edit button pressed");
      });
   }
}


}