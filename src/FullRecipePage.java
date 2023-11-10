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

class RecipeFullPage extends Display {
   private TextField recipeName;
   private TextField prepTime;
   private TextField cookTime;
   private TextField ingredients;
   private TextField steps;

   private ScrollPane scroller;
   private RecipeFullView recipeFullView;

   RecipeFullPage (Stage primaryStage, AppFrame frame, Recipe recipe) {
      header = new Header(recipe.getName());
      recipeFullView = new RecipeFullView(recipe);
      footer = new RecipeFullFooter(frame);

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
      private Button saveButton;

      RecipeFullFooter(AppFrame frame){
         setup();
         this.setAlignment(Pos.CENTER_LEFT);
         backButton = new PPButton("Back");
         this.setMargin(backButton, new Insets(20, 20, 20, 20));  
         this.getChildren().add(backButton);

         editButton = new PPButton("Edit");
         this.setMargin(editButton, new Insets(20, 20, 20, 490));  
         this.getChildren().add(editButton);

         saveButton = new PPButton("Save");
         this.setMargin(saveButton, new Insets(20, 20, 20, 510));  
         this.getChildren().add(saveButton);


         addListeners(frame);
   }

      private void addListeners (AppFrame frame) {
         backButton.setOnAction(e -> {
            frame.setPage(Page.HOME);
      });
         editButton.setOnAction(e -> {
            //TODO: SETUP EDIT BUTTTON FUNCTIONALITY
            System.out.println("Edit button pressed");
      });
         saveButton.setOnAction( e-> {
            //TODO: CODE FOR RECIPE GENERATED FROM CHATGPT
            //TEMP TEST RECIPES
            System.out.println("Save Button Pressed");
            frame.setPage(Page.HOME);
      });
   }
}


}