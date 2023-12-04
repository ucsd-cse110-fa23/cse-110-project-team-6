package pantrypal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

class HomePage extends Display {
   private RecipeListView recipeListView;
   private ScrollPane scroller;
   private Header header;

   HomePage (RecipeList recipeList) {
      header = new Header("PantryPal");
      recipeListView = new RecipeListView();
      footer = new HomePageFooter();
      
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
      if (recipeList.getSize() > 0) {
         renderLoadedRecipes(recipeList);
      }
   }

   public void renderLoadedRecipes(RecipeList recipes) {
      clearRecipes();
      for (int i = 0; i < recipes.getRecipes().size(); i++) {
         recipeListView.add(new RecipeUnitView(recipes.getRecipe(i)), 1, i+1); 
      }
   }

   public void renderLoadedRecipes(RecipeList recipes, String mealType) {
      clearRecipes();
      for (int i = 0; i < recipes.getRecipes().size(); i++) {
         System.out.println(recipes.getRecipe(i).getMealType());
         if(recipes.getRecipe(i).getMealType() == mealType){
            recipeListView.add(new RecipeUnitView(recipes.getRecipe(i)), 1, i+1);
         }
      }
   }

   public RecipeListView getRecipeListView(){
      return this.recipeListView;
   }

   public void clearRecipes(){
         recipeListView.getChildren().remove(2, recipeListView.getChildren().size()); 
   }

   public void deleteRecipe(Recipe recipe) {
      PantryPal.getRoot().getRecipeList().removeRecipe(recipe);
   }
}

class sortAndFilter extends GridPane {
   sortAndFilter() {
      this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(50);
      this.getColumnConstraints().addAll(col, col);
   }
}

class RecipeListView extends GridPane {
   //TEMP TEST RECIPES

   RecipeListView() {
      this.setWidth(Consts.WIDTH);
      this.setPrefHeight(840);
      this.setVgap(Consts.RECIPE_OFFSET);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      this.add(new Button(),0,0);  // TODO: Create actual MenuButton (Sort)

      this.add(new MenuButton(),2,0); // TODO: Create actual MenuButton (Dropdown for Filter)

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
      recipeName.setFont(Consts.F30);
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
         PantryPal.getRoot().setPage(Page.RECIPEFULL, recipe);
         System.out.println("clicked detail view");
      });
   }

   public Recipe getRecipe(){
      return this.recipe;
   }
}


class HomePageFooter extends Footer {
   private Button signOutButton;
   private Button recipeButton;
   
   HomePageFooter() {
      setup();

      signOutButton = new PPButton("Sign Out");
      this.add(signOutButton,0,0);
      this.setMargin(signOutButton, new Insets(20, 20, 20, 20));
      this.setHalignment(signOutButton, HPos.LEFT);

      recipeButton = new PPButton("New Recipe");
      this.setMargin(recipeButton, new Insets(20, 20, 20, 20));
      this.setHalignment(recipeButton, HPos.RIGHT);
      this.add(recipeButton,1,0);

      addListeners();
   }

   private void addListeners () {
      signOutButton.setOnAction(e -> {
         // TODO CHECK FUNCTIONALITY
         PantryPal.getRoot().getRecipeList().saveRecipes();
         PantryPal.getRoot().getRecipeList().clear();
         PantryPal.getRoot().clearRecipes();
         PantryPal.getRoot().setPage(Page.SIGNIN);

      });
      recipeButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.MEALTYPE);
         System.out.println("clicked add recipe");
      });
   }
}