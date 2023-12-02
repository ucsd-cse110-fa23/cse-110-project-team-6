package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.text.*;

class RecipeFullPage extends Display {
   private ScrollPane scroller;
   private RecipeFullView recipeFullView;

   RecipeFullPage (Recipe recipe) {
      header = new Header(recipe.getName());
      recipeFullView = new RecipeFullView(recipe);
      footer = new RecipeFullFooter(recipeFullView, recipe);

      scroller = new ScrollPane(recipeFullView); //fill in with class for recipe display
      scroller.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);
   }

   class RecipeFullView extends VBox{
      private TextField ingredients;
      private TextField steps;

      private Text ingredientsHeader;
      private Text instructionsHeader;

      RecipeFullView(Recipe recipe){
         this.setSpacing(10);
         this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

         //"INGREDIENTS HEADER"
         ingredientsHeader = new Text("Ingredients");
         ingredientsHeader.setUnderline(true);
         ingredientsHeader.setFont(Consts.V40);
         ingredientsHeader.setFill(Consts.DARK);
         this.getChildren().add(ingredientsHeader);
         this.setMargin(ingredientsHeader, new Insets(20,0,0,20));

         //ADDING INGRIDIENTS
         for(int i = 0; i < recipe.getIngredients().size(); i++){
            ingredients = new TextField();
            ingredients.setText(recipe.getIngredients().get(i));
            
            //ingredients.setWrapText(true);
            ingredients.setPrefWidth(640);
            ingredients.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
            ingredients.setFont(Consts.V20);
            //ingredients.setFill(Consts.DARK);
            this.setMargin(ingredients, new Insets(0,0,0,60));
            ingredients.setEditable(false);
            this.getChildren().add(ingredients);
      }

         //"INSTRUCTIONS HEADER"
         instructionsHeader = new Text("Instructions");
         instructionsHeader.setUnderline(true);
         instructionsHeader.setFont(Consts.V40);
         instructionsHeader.setFill(Consts.DARK);
         this.getChildren().add(instructionsHeader);
         this.setMargin(instructionsHeader, new Insets(20,0,0,20));
         for(int i = 0; i < recipe.getSteps().size(); i++){
            steps = new TextField();
            steps.setText(recipe.getSteps().get(i));
            //steps.setWrapText(true);
            steps.setPrefWidth(640);
            steps.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
            steps.setFont(Consts.V15);
            //steps.setFill(Consts.DARK);
            this.setMargin(steps, new Insets(0,0,0,60));
            steps.setEditable(false);
            this.getChildren().add(steps);
         }
      }
   }

   class RecipeFullFooter extends Footer{
      private Button backButton;
      private Button editButton;
      private Button saveButton;

      RecipeFullFooter(RecipeFullView recipeFullView, Recipe recipe){
         setup();
         this.setAlignment(Pos.CENTER_LEFT);
         backButton = new PPButton("Back");
         this.setMargin(backButton, new Insets(0, 0, 0, 20));  
         this.add(backButton, 1, 0);

         editButton = new PPButton("Edit");
          
         this.add(editButton, 2, 0);

         saveButton = new PPButton("Save");
          
         this.add(saveButton, 3, 0);

         addListeners(recipeFullView, recipe);
      }  

      private void addListeners (RecipeFullView recipeFullView, Recipe recipe) {
         backButton.setOnAction(e -> {
            PantryPal.getRoot().setPage(Page.HOME);
         });
         editButton.setOnAction(e -> {
            //allowing TextField children to be editable
            System.out.println("Edit button pressed");
            for(int i = 0; i < recipeFullView.getChildren().size(); i++){
               if(recipeFullView.getChildren().get(i) instanceof TextField){
                  ((TextField)recipeFullView.getChildren().get(i)).setEditable(true);
               }
            }
         });
         saveButton.setOnAction( e-> {
            //Saving new TextField strings to the recipe object in local database
            System.out.println("Save Button Pressed");
            PantryPal.getRoot().setPage(Page.HOME);
            int i = 1;
            ArrayList<String> newIngredients = new ArrayList<>();
            while (recipeFullView.getChildren().get(i) instanceof TextField) {
               newIngredients.add(((TextField)recipeFullView.getChildren().get(i)).getText());
               ((TextField)recipeFullView.getChildren().get(i)).setEditable(false);
               ++i;
            }
            ++i;
            ArrayList<String> newInstructions = new ArrayList<>();
            while (i < recipeFullView.getChildren().size()) {
               newInstructions.add(((TextField)recipeFullView.getChildren().get(i)).getText());
               ((TextField)recipeFullView.getChildren().get(i)).setEditable(false);
               ++i;
            }  
            recipe.setIngredients(newIngredients);
            recipe.setSteps(newInstructions);
         });
      }
   }


}