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

class PPRectangle extends Rectangle {
   PPRectangle (int width, int height, int arc) {
      this.setWidth(width);
      this.setHeight(height);
      this.setArcHeight(arc);
      this.setArcWidth(arc);
      this.setFill(Consts.YELLOW);
   }
}

abstract class Display extends BorderPane {
   protected Header header;
   protected VBox page;
   protected Footer footer;
}

class PPButton extends Button {
   PPButton(String name) {
      this.setText(name);
      this.setPrefSize(100, 40);
      this.setBackground(new Background(new BackgroundFill(Consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setTextFill(Consts.DARK);
      this.setStyle("-fx-border-width: 0"); 
   }
}

abstract class Footer extends HBox {
   void setup() {
      this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
   }
}

class MicFooter extends Footer {
   private Button backButton;
   private Button doneButton;

   MicFooter(AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      doneButton = new PPButton("Done");
      // this.setMargin(doneButton, new Insets(20, 20, 20, 510));  
      this.getChildren().add(doneButton);
      doneButton.setVisible(false);

      addListeners(frame);
   }

   public void showDoneButton() {
      doneButton.setVisible(true);
   }

   private void addListeners (AppFrame frame) {
      backButton.setOnAction(e -> {
         frame.setPage(Page.MEALTYPE);
      });
      doneButton.setOnAction( e-> {
         System.out.println("DOne Button Pressed");
      });
   }
}

class MealFooter extends Footer {
   private Button backButton;

   MealFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      addListeners(frame);
   }

   private void addListeners (AppFrame frame) {
   backButton.setOnAction(e -> {
      frame.setPage(Page.HOME);
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

class MealUnitView extends StackPane {
   private Rectangle rectangle;
   private Text meal;
   //temp button fuctionality

   private Button button;

   private final int MEAL_WIDTH = 600;
   private final int MEAL_HEIGHT = 120;
   private final int MEAL_ARC = 45;

   MealUnitView (String mealType, AppFrame frame) {
      // rectangle
      rectangle = new PPRectangle(MEAL_WIDTH, MEAL_HEIGHT, MEAL_ARC);
      this.getChildren().add(rectangle);

      // meal type
      meal = new Text(mealType);
      meal.setFont(Consts.V40);
      meal.setFill(Consts.DARK);
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
         frame.setPage(Page.MIC);
         System.out.println("selected meal type");
      });
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

class RecipeFullPage extends Display {
   private TextField recipeName;
   private TextField prepTime;
   private TextField cookTime;
   private TextField ingredients;
   private TextField steps;

   private ScrollPane scroller;

   RecipeFullPage (Stage primaryStage, Recipe recipe) {
      scroller = new ScrollPane(); //fill in with class for recipe display
      // make this a framework including the back, edit, delete button
   }
}

class Header extends HBox { 
    Header(String heading) {
        this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        Text titleText = new Text(heading); // Text of the Header
        titleText.setFont(Consts.V40);
        titleText.setFill(Consts.DARK);
        this.getChildren().add(titleText);
    }
}

class MealOptions extends VBox {
    private MealUnitView breakfast;
    private MealUnitView lunch;
    private MealUnitView dinner;

    MealOptions(AppFrame frame) {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setSpacing(100);
        this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        
        breakfast = new MealUnitView("Breakfast", frame);
        lunch = new MealUnitView("Lunch", frame);
        dinner = new MealUnitView("Dinner", frame);        
        this.getChildren().add(breakfast);
        this.getChildren().add(lunch);
        this.getChildren().add(dinner);
    }
}

class RecipeCreatorView extends VBox {
   private StackPane mic;
   private Text input;
   private ScrollPane scroller;

   private Rectangle inputBackground;

   RecipeCreatorView(AppFrame frame) {
      this.setSpacing(50);

      mic = new MicButton(frame);
      input = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test ");
      input.setWrappingWidth(530);
      input.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      input.setFont(Consts.V12);
      input.setFill(Consts.DARK);
      inputBackground = new PPRectangle(600, 450, 45);
      inputBackground.setFill(Color.TRANSPARENT);
      inputBackground.setStroke(Consts.YELLOW);
      inputBackground.setStrokeWidth(5);

      this.getChildren().add(mic);
      this.setMargin(mic, new Insets(45, 0, 0, 0));

      scroller = new ScrollPane(input);
      scroller.setMaxWidth(550);
      scroller.setMaxHeight(400);
      scroller.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      
      StackPane textInput = new StackPane();
      textInput.getChildren().add(inputBackground);
      textInput.getChildren().add(scroller);

      this.getChildren().add(textInput);
   }

   public void setInput(String s) {
      input.setText(s);
   }
}

class MicButton extends StackPane {
   private boolean micIsOn = false;
   private ImageView imageView = new ImageView();
   private Button button = new Button();
   private Image micOff = new Image("/mic.png");
   private Image micOn = new Image("/micred.png");
   MicButton(AppFrame frame) {
      imageView.setImage(micOff);
      imageView.setFitWidth(110);
      imageView.setFitHeight(110);

      this.getChildren().add(imageView);

      button = new Button();
      button.setPrefSize(100, 100);
      button.setStyle("-fx-background-color: transparent");

      this.getChildren().add(button);

      addListeners(frame);
   }

   private void addListeners (AppFrame frame) {
      button.setOnAction(e -> {
         // TO DO mic button functionality
         if(micIsOn){
            //TODO: Implement stop recording + transcript to text box for Whisper API
            imageView.setImage(micOff);
            micIsOn = false;
         }else{
            // TODO: Implement start recording for Whisper API
            imageView.setImage(micOn);
            micIsOn = true;
         }
      });
   }

   //TODO: Implement the record function for WhisperAPI
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

   RecipeCreatorPage(Stage primaryStage, AppFrame frame){
      header = new Header("Recipe Maker");
      createView = new RecipeCreatorView(frame);
      footer = new MicFooter(frame);

      //footer = new RecipeFooter(primaryStage, frame);

      this.setTop(header);
      this.setCenter(createView);
      this.setBottom(footer);
   }
}

class GeneratedRecipePage extends Display {

   GeneratedRecipePage(Stage primaryStage, AppFrame frame, Recipe recipe){
      header = new Header(recipe.getName());
   }
}