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

class HomePage extends Display {
    private RecipeListView recipeList;
    private ScrollPane scroller;

    HomePage (Stage primaryStage, AppFrame frame) {
        header = new Header("PantryPal");

        footer = new HomePageFooter(primaryStage, frame);
        recipeList = new RecipeListView(primaryStage);
        // this.setStyle("-fx-background-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;");

        scroller = new ScrollPane(recipeList);
        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scroller);
        this.setBottom(footer);
       
        // header with name of app
        // footer with add button
        // recipelist
    }
}


class RecipeListView extends VBox {
   List<Recipe> recipes = new ArrayList<>();

   RecipeListView(Stage primaryStage) {
      this.setWidth(Consts.WIDTH);
      this.setPrefHeight(840);
      this.setSpacing(Consts.RECIPE_OFFSET);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      this.setAlignment(Pos.TOP_CENTER);

      Recipe test = new Recipe("red wine potatoes", "2", "69", "potatoes, wine", "bake, eat");


      recipes.add(test);

      renderRecipes();
   }

    private void renderRecipes() {
      for (int i = 0; i < 10; i++) {
         this.getChildren().add(new RecipeUnitView(recipes.get(0))); // TO DO should be get(i)
         this.setMargin(this.getChildren().get(i), new Insets(0, 0, 0, 75));
      }
      this.setMargin(this.getChildren().get(0), new Insets(5, 0, 0, 75));
    }

}

class RecipeUnitView extends StackPane {
   private Rectangle rectangle;
   private Text recipeName;
   private Button button;

   private final int RECIPE_WIDTH = 600;
   private final int RECIPE_HEIGHT = 80;
   private final int RECIPE_ARC = 35;
   
   RecipeUnitView (Recipe recipe) {
      this.setAlignment(Pos.CENTER_LEFT);

      // round rectangle
      rectangle = new PPRectangle(RECIPE_WIDTH, RECIPE_HEIGHT, RECIPE_ARC);
      this.getChildren().add(rectangle);

      // text
      recipeName = new Text(recipe.getName());
      recipeName.setFill(Consts.DARK);
      recipeName.setFont(Consts.V30);
      this.setMargin(recipeName, new Insets(0, 0, 0, 20));        // top, right, bottom, left
      recipeName.setStyle("-fx-border-width: 0");
      this.getChildren().add(recipeName);

      // invisible button
      button = new Button();
      button.setStyle("-fx-background-color: transparent");
      button.setPrefSize(RECIPE_WIDTH, RECIPE_HEIGHT);

      this.getChildren().add(button);
      addListeners();
   }

   protected void addListeners() {
      button.setOnAction(e -> {
         // TO DO: add button functionality
         System.out.println("clicked detail view");
      });
   }
}


class HomePageFooter extends Footer {    
   private AppFrame frame;
   private Button recipeButton;
   
   HomePageFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_RIGHT);
      this.frame = frame;

      recipeButton = new PPButton("New Recipe");
      this.setMargin(recipeButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(recipeButton);

      addListeners();
   }

   private void addListeners () {
      recipeButton.setOnAction(e -> {
         frame.setPage(Page.MEALTYPE);

         // TO DO add button functionality
         System.out.println("clicked add recipe");

      });
   }
}