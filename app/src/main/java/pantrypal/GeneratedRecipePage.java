package pantrypal;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.util.ArrayList;
import java.util.List;

class GeneratedRecipePage extends Display {
   private GeneratedRecipeView genView;
   private ScrollPane scroller;

   GeneratedRecipePage(Stage primaryStage, AppFrame frame, Recipe recipe){
      header = new Header(recipe.getName());
      genView = new GeneratedRecipeView();
      footer = new GeneratedRecipeFooter(frame);

      scroller = new ScrollPane(genView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(genView);
      this.setBottom(footer);
   }
}

class GeneratedRecipeView extends VBox{
   private Text ingredients;
   private Text steps;
   private Recipe recipeGen;

   GeneratedRecipeView() {
      this.setSpacing(50);
   
      //TODO: IMPLEMENT WAY TO ADD INGREDIENTS AS PARSED FROM recipGEN
      ingredients = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test Testing test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test  ");
      ingredients.setWrappingWidth(680);
      ingredients.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      ingredients.setFont(Consts.V30);
      ingredients.setFill(Consts.DARK);

      //TODO: IMPLEMENT WAY TO ADD STEPS AS PARSED FROM recipGEN
      steps = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test  ");
      steps.setWrappingWidth(680);
      steps.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      steps.setFont(Consts.V12);
      steps.setFill(Consts.DARK);


      this.getChildren().add(ingredients);
      this.getChildren().add(steps);
   }
}

class GeneratedRecipeFooter extends Footer{
   private Button backButton;
   private Button saveButton;

   GeneratedRecipeFooter(AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      saveButton = new PPButton("Save");
      this.setMargin(saveButton, new Insets(20, 20, 20, 510));  
      this.getChildren().add(saveButton);

      addListeners(frame);
   }

   private void addListeners (AppFrame frame) {
      backButton.setOnAction(e -> {
         frame.setPage(Page.RECIPECREATOR);
      });
      saveButton.setOnAction( e-> {
         //CODE FOR RECIPE GENERATED FROM CHATGPT
         //TEMP TEST RECIPES
         System.out.println("Save Button Pressed");
         frame.setPage(Page.HOME);
      });
   }
}