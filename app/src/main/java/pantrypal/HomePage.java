package pantrypal;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

class HomePage extends Display {
   private RecipeListView recipeListView;
   private ScrollPane scroller;
   private Header header;

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


class RecipeListView extends GridPane {
   //TEMP TEST RECIPES
   private MenuButton sort;

   private MenuButton filter;
   MenuItem breakfast;
   MenuItem lunch;
   MenuItem dinner;
   MenuItem none;

   RecipeListView() {
      this.setWidth(Consts.WIDTH);
      this.setPrefHeight(840);
      this.setVgap(Consts.RECIPE_OFFSET);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      sort = new MenuButton("Sort By:");
      this.add(sort,0,0);  // TODO: Create actual MenuButton (Sort)
      this.setMargin(this.getChildren().get(0), new Insets(10, 0, 0, 0));
      
      filter = new MenuButton("Filter By:");

      breakfast = new MenuItem("Breakfast");
      lunch = new MenuItem("Lunch");
      dinner = new MenuItem("Dinner");
      none = new MenuItem("None");

      filter.getItems().addAll(breakfast, lunch, dinner, none);
      this.add(filter,2,0);
      this.setMargin(this.getChildren().get(1), new Insets(10, 0, 0, 0));

      this.getColumnConstraints().add(new ColumnConstraints(75)); // column 0 is 75 wide
      this.getColumnConstraints().add(new ColumnConstraints(600)); // column 1 is 600 wide
      this.getColumnConstraints().add(new ColumnConstraints(80)); // column 2 is 80 wide --- App total frame = 850 width

      this.setGridLinesVisible(true);
      this.setAlignment(Pos.TOP_CENTER);
      addListeners();
      }

      protected void addListeners(){
         breakfast.setOnAction(e -> {
            this.filter.setText("Breakfast");
            PantryPal.getRoot().getHome().renderLoadedRecipes(PantryPal.getRoot().getRecipeList(), "Breakfast");
         });
         lunch.setOnAction(e -> {
            this.filter.setText("Lunch");
            PantryPal.getRoot().getHome().renderLoadedRecipes(PantryPal.getRoot().getRecipeList(), "Lunch");
         });
         dinner.setOnAction(e -> {
            this.filter.setText("Dinner");
            PantryPal.getRoot().getHome().renderLoadedRecipes(PantryPal.getRoot().getRecipeList(), "Dinner");
         });
         none.setOnAction(e -> {
            this.filter.setText("Filter By:");
            PantryPal.getRoot().getHome().renderLoadedRecipes(PantryPal.getRoot().getRecipeList());
         });
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
         PantryPal.getRoot().setPage(Page.RECIPEFULL, recipe);
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
      this.add(recipeButton,3,0);

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