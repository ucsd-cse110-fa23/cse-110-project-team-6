package pantrypal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

class HomePage extends Display {
   private RecipeListView recipeListView;
   private ScrollPane scroller;

   HomePage (RecipeList recipeList) {
      header = new Header("PantryPal");
      recipeListView = new RecipeListView();
      footer = new HomePageFooter();
      
      // this.setStyle("-fx-background-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;");

      scroller = new ScrollPane(recipeListView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);
      
      // header with name of app
      // footer with add button
      // recipelist
      System.out.println(recipeList.getSize());
   }

   public RecipeListView getRecipeListView(){
      return this.recipeListView;
   }

   public void renderRecipes(Recipe recipe) {
      if(recipeListView.getChildren().size() > 0){
            recipeListView.setMargin(recipeListView.getChildren().get(0), new Insets(0, 0, 0, 75));
         }
         recipeListView.getChildren().add(0,new RecipeUnitView(recipe)); // TO DO should be get(i)
         recipeListView.setMargin(recipeListView.getChildren().get(0), new Insets(5, 0, 0, 75));
   }

   public void deleteRecipe(Recipe recipe) {
      PantryPal.getRoot().getRecipeList().removeRecipe(recipe);
   }
}


class RecipeListView extends VBox {
   //TEMP TEST RECIPES

   RecipeListView() {
      this.setWidth(Consts.WIDTH);
      this.setPrefHeight(840);
      this.setSpacing(Consts.RECIPE_OFFSET);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));


      this.setAlignment(Pos.TOP_CENTER);
      }

}

class RecipeUnitView extends StackPane {
   private Rectangle rectangle;
   private Label recipeName;
   private Button button;
   private StackPane deleteButton;
   private Recipe recipe;


   private final int RECIPE_WIDTH = 600;
   private final int RECIPE_HEIGHT = 80;
   private final int RECIPE_ARC = 35;
   
   RecipeUnitView (Recipe recipe) {
      this.setAlignment(Pos.CENTER_LEFT);
      this.recipe = recipe;

      // round rectangle
      rectangle = new PPRectangle(RECIPE_WIDTH, RECIPE_HEIGHT, RECIPE_ARC);
      this.getChildren().add(rectangle);

      // text
      recipeName = new Label(this.recipe.getName());
      recipeName.setFont(Consts.V30);
      recipeName.setMaxWidth(400);
      this.setMargin(recipeName, new Insets(0, 0, 0, 20));        // top, right, bottom, left
      recipeName.setStyle("-fx-border-width: 0");
      this.getChildren().add(recipeName);

      


      // invisible button
      button = new Button();
      button.setStyle("-fx-background-color: transparent");
      button.setPrefSize(RECIPE_WIDTH, RECIPE_HEIGHT);
      this.getChildren().add(button);

      //delete button
      deleteButton = new PPDelete(recipe);
      this.setMargin(deleteButton, new Insets(0, 0, 0, 420));
      this.getChildren().add(deleteButton);

      addListeners(this.recipe);
   }

   protected void addListeners(Recipe recipe) {
      button.setOnAction(e -> {
         // TO DO: add button functionality
         PantryPal.getRoot().setPage(Page.RECIPEFULL, recipe);;
         System.out.println("clicked detail view");
      });
   }

   public Recipe getRecipe(){
      return this.recipe;
   }
}


class HomePageFooter extends Footer {
   private Button recipeButton;
   
   HomePageFooter() {
      setup();
      this.setAlignment(Pos.CENTER_RIGHT);

      recipeButton = new PPButton("New Recipe");
      this.setMargin(recipeButton, new Insets(20, 25, 20, 0));
      this.add(recipeButton,0,0);

      addListeners();
   }

   private void addListeners () {
      recipeButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.MEALTYPE);

         // TO DO add button functionality
         System.out.println("clicked add recipe");

      });
   }
}