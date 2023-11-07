import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.util.ArrayList;
import java.util.List;

interface View {
   static final Color GREEN = Color.web("#A6D69B");
   static final Color YELLOW = Color.web("#F1E293");
   static final Color LIGHT = Color.web("F1EBE2");
   static final Color DARK = Color.web("17373A");

   static final int WIDTH = 750;
   static final int HEIGHT = 1000;
   static final int RECIPE_OFFSET = 5;
   static final int HF_HEIGHT = 80;
}

abstract class Display extends BorderPane implements View {
   protected Header header;
   protected VBox page;
   protected Footer footer;
}

abstract class Footer extends HBox implements View {
   protected Button button;

   void setup() {
      this.setPrefSize(WIDTH, HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

      button = new Button();
      button.setPrefSize(100, 40);
      button.setBackground(new Background(new BackgroundFill(YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      button.setTextFill(DARK);
      button.setStyle("-fx-border-width: 0"); 

      this.getChildren().add(button);
   }
}

abstract class RectangeView extends StackPane implements View{
   protected Rectangle background = new Rectangle();
   protected Button button;

   void setup(int width, int height, int arc) {
      background.setWidth(width);
      background.setHeight(height);
      background.setArcHeight(arc);
      background.setArcWidth(arc);
      background.setFill(YELLOW);

      this.getChildren().add(this.background);
   }
}

class RecipeUnitView extends RectangeView {
   private Text recipeName;

   private final int RECIPE_WIDTH = 600;
   private final int RECIPE_HEIGHT = 80;
   private final int RECIPE_ARC = 35;
   
   RecipeUnitView (Recipe recipe) {
      this.setWidth(RECIPE_WIDTH);
      this.setHeight(RECIPE_HEIGHT);

      this.setAlignment(Pos.CENTER_LEFT);

      // round rectangle
      setup(RECIPE_WIDTH, RECIPE_HEIGHT, RECIPE_ARC);

      // text
      recipeName = new Text(recipe.getName());
      this.setMargin(recipeName, new Insets(0, 0, 0, 20));        // top, right, bottom, left
      recipeName.setStyle("-fx-border-width: 0; -fx-font-size: 30");
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

class MealUnitView extends RectangeView {
    private Text meal;

    //temp button fuctionality

    private final int MEAL_WIDTH = 600;
    private final int MEAL_HEIGHT = 120;
    private final int MEAL_ARC = 45;

    MealUnitView (String mealType, AppFrame frame) {
        // rectangle
        setup(MEAL_WIDTH, MEAL_HEIGHT, MEAL_ARC);

        // meal type
        meal = new Text(mealType);
        meal.setFont(Font.font("Verdana", 40));
        meal.setFill(DARK);
        this.getChildren().add(meal);

        //invisible button
        //TEMP FUNCTIONALYLITY    
        button = new Button();
        button.setStyle("-fx-background-color: transparent");
        button.setPrefSize(600, 120);
        this.getChildren().add(button);
        
        addListeners(frame);
    }

    private void addListeners (AppFrame frame) {
        button.setOnAction(e -> {
            // TO DO add button functionality
            frame.setPage(Page.RECIPEOPT);
            System.out.println("selected meal type");
        });
    }
}


class MicButton extends StackPane {

}

class RecipeListView extends VBox implements View {
    List<Recipe> recipes = new ArrayList<>();

    RecipeListView(Stage primaryStage) {
        this.setWidth(WIDTH);
        this.setPrefHeight(840);
        this.setSpacing(RECIPE_OFFSET);
        this.setBackground(new Background(new BackgroundFill(LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

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

class RecipeFullView extends Display {
    private TextField recipeName;
    private TextField prepTime;
    private TextField cookTime;
    private TextField ingredients;
    private TextField steps;

    private ScrollPane scroller;

    RecipeFullView (Stage primaryStage, Recipe recipe) {
        scroller = new ScrollPane(); //fill in with class for recipe display
        // make this a framework including the back, edit, delete button
    }
}

class HomePageFooter extends Footer {    
   private AppFrame frame;
   
   HomePageFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_RIGHT);
      this.frame = frame;

      button.setText("New Recipe");
      this.setMargin(button, new Insets(20, 20, 20, 20));  

      addListeners(primaryStage);
   }

    private void addListeners (Stage primaryStage) {
        button.setOnAction(e -> {
            frame.setPage(Page.MEALTYPE);

            // TO DO add button functionality
            System.out.println("clicked add recipe");

        });
    }
}

class Header extends HBox implements View{ 
    Header(String heading) {
        this.setPrefSize(WIDTH, HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        Text titleText = new Text(heading); // Text of the Header
        titleText.setFont(Font.font("Verdana", 40));
        titleText.setFill(DARK);
        this.getChildren().add(titleText);
    }
}

class MealOptions extends VBox implements View {
    private MealUnitView breakfast;
    private MealUnitView lunch;
    private MealUnitView dinner;

    MealOptions(AppFrame frame) {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setSpacing(100);
        this.setBackground(new Background(new BackgroundFill(LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        
        breakfast = new MealUnitView("Breakfast", frame);
        lunch = new MealUnitView("Lunch", frame);
        dinner = new MealUnitView("Dinner", frame);        
        this.getChildren().add(breakfast);
        this.getChildren().add(lunch);
        this.getChildren().add(dinner);
    }
}

class MealFooter extends Footer {
    
   MealFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);

      button.setText("Back");
      this.setMargin(button, new Insets(20, 20, 20, 20));  

      addListeners(primaryStage, frame);
   }

    private void addListeners (Stage primaryStage, AppFrame frame) {
        button.setOnAction(e -> {
            frame.setPage(Page.HOME);
        });
    }
}

class RecipeCreatorView extends VBox implements View{
    RecipeCreatorView() {

    }
}

class RecipeFooter extends Footer {        
   RecipeFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);

      button = new Button("Back");
      this.setMargin(button, new Insets(20, 20, 20, 20));  

      addListeners(primaryStage, frame);
   }

   private void addListeners (Stage primaryStage, AppFrame frame) {
      button.setOnAction(e -> {

         frame.setPage(Page.MEALTYPE);

      });
   }
}

class MealTypePage extends Display {
    private MealOptions page;

    MealTypePage (Stage primaryStage, AppFrame frame) {
        header = new Header("Meal Options");
        page = new MealOptions(frame);
        footer = new MealFooter(primaryStage, frame);

        this.setTop(header);
        this.setCenter(page);
        this.setBottom(footer);
    }
}

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

class RecipeCreatorPage extends Display {
    private RecipeCreatorView createView;
    private ScrollPane scroller;

    RecipeCreatorPage(Stage primaryStage, AppFrame frame){
        header = new Header("Recipe Maker");
        footer = new RecipeFooter(primaryStage, frame);

        //footer = new RecipeFooter(primaryStage, frame);

        this.setTop(header);
        this.setCenter(createView);
        this.setBottom(footer);
    }
}

