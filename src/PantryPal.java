import javafx.application.Application;
import javafx.scene.Scene;
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
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter; 


class Consts {
    public final Color GREEN = Color.web("#A6D69B");
    public final Color YELLOW = Color.web("#F1E293");
    public final Color LIGHT = Color.web("F1EBE2");
    public final Color DARK = Color.web("17373A");

    public final int WIDTH = 750;
    public final int HEIGHT = 1000;
    public final int RECIPE_OFFSET = 5;
    public final int HF_HEIGHT = 80;
}

class RecipeUnitView extends StackPane {
    Consts consts = new Consts();

    Recipe recipe;
    Rectangle background;
    Button detailView;
    Text recipeName;

    
    private final int RECIPE_WIDTH = 600;
    private final int RECIPE_HEIGHT = 80;
    
    RecipeUnitView (Recipe recipe) {
        this.recipe = recipe;

        this.setWidth(RECIPE_WIDTH);
        this.setHeight(RECIPE_HEIGHT);

        this.setAlignment(Pos.CENTER_LEFT);

        // round rectangle
        background = new Rectangle();

        background.setWidth(RECIPE_WIDTH);
        background.setHeight(RECIPE_HEIGHT);
        background.setArcHeight(35);
        background.setArcWidth(35);

        //style
        background.setFill(consts.YELLOW);

        this.getChildren().add(background);

        // text
        recipeName = new Text(recipe.getName());
        this.setMargin(recipeName, new Insets(0, 0, 0, 20));        // top, right, bottom, left

        recipeName.setStyle("-fx-border-width: 0; -fx-font-size: 30");

        this.getChildren().add(recipeName);

        // invisible button
        detailView = new Button();
        detailView.setStyle("-fx-background-color: transparent");

        detailView.setPrefSize(RECIPE_WIDTH, RECIPE_HEIGHT);

        this.getChildren().add(detailView);
        
        addListeners();
        
    }

    private void addListeners() {
        detailView.setOnAction(e -> {
            // TO DO: add button functionality
            System.out.println("clicked detail view");
        });
    }
}

class RecipeListView extends VBox {
    Consts consts = new Consts();

    List<Recipe> recipes = new ArrayList<>();

    RecipeListView(Stage primaryStage) {
        this.setWidth(consts.WIDTH);
        this.setPrefHeight(840);
        this.setSpacing(consts.RECIPE_OFFSET);
        this.setBackground(new Background(new BackgroundFill(consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setAlignment(Pos.TOP_CENTER);

        Recipe test = new Recipe("red wine potatoes", "2", "69", "potatoes, wine", "bake, eat");


        recipes.add(test);

        renderRecipes();
    }

    private void renderRecipes() {
        for (int i = 0; i < 10; i++) {
            this.getChildren().add(new RecipeUnitView(recipes.get(0)));
            this.setMargin(this.getChildren().get(i), new Insets(0, 0, 0, 75));
        }
        this.setMargin(this.getChildren().get(0), new Insets(5, 0, 0, 75));

    }
}

class RecipeFullView extends BorderPane{
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

class Footer extends HBox {
    Consts consts = new Consts();
    
    public Button newRecipe;
    
    Footer(Stage primaryStage) {
        this.setPrefSize(consts.WIDTH, consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER_RIGHT);

        newRecipe = new Button("New Recipe");
        newRecipe.setPrefSize(100, 40);
        newRecipe.setBackground(new Background(new BackgroundFill(consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        newRecipe.setTextFill(consts.DARK);
        newRecipe.setStyle("-fx-border-width: 0"); 
        this.setMargin(newRecipe, new Insets(20, 20, 20, 20));  

        this.getChildren().add(newRecipe);

        addListeners(primaryStage);
    }

    private void addListeners (Stage primaryStage) {
        newRecipe.setOnAction(e -> {

            // TO DO add button functionality
            System.out.println("clicked add recipe");

        });
    }
}

class Header extends HBox { 
    Consts consts = new Consts();

    Header(String heading) {
        this.setPrefSize(consts.WIDTH, consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        Text titleText = new Text(heading); // Text of the Header
        titleText.setFont(Font.font("Verdana", 40));
        titleText.setFill(consts.DARK);
        this.getChildren().add(titleText);
    }
}

class MealDesign extends StackPane {
    private Rectangle background;
    private Text meal;

    //temp button fuctionality
    private Button mealButton;

    MealDesign (String mealType) {
        // rectangle
        background = new Rectangle();
        background.setWidth(400);
        background.setHeight(80);
        background.setArcHeight(35);
        background.setArcWidth(35);

        //style
        background.setFill(Color.web("#cee6ba"));
        background.setStroke(Color.web("#ccbc97"));
        background.setStrokeWidth(5);
        this.getChildren().add(background);

        // meal type
        meal = new Text(mealType);
        this.getChildren().add(meal);

        //invisible button
        //TEMP FUNCTIONALYLITY    
        mealButton = new Button();
        mealButton.setStyle("-fx-background-color: transparent");
        mealButton.setPrefSize(400, 80);
        this.getChildren().add(mealButton);
        
        addListeners();
    }

        private void addListeners () {
            mealButton.setOnAction(e -> {
            // TO DO add button functionality
            System.out.println("selected meal type");
        });
    }
}

class MealTypeOptions extends VBox {
    Consts consts = new  Consts();

    MealTypeOptions() {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setSpacing(5);
        this.setSpacing(5);
        this.setBackground(new Background(new BackgroundFill(consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.TOP_CENTER);
        
        MealDesign breakfast = new MealDesign("Breakfast");
        MealDesign lunch = new MealDesign("Lunch");
        MealDesign dinner = new MealDesign("Dinner");        
        this.getChildren().add(breakfast);
        this.getChildren().add(lunch);
        this.getChildren().add(dinner);
    }
}

class MealType extends BorderPane {
    private Header header;
    private MealTypeOptions page;

    MealType (Stage primaryStage) {


    }
}

class Homepage extends BorderPane {
    private RecipeListView recipeList;
    private ScrollPane scroller;

    private Header header;
    private Footer footer;

    Homepage (Stage primaryStage) {
        header = new Header("PantryPal");

        footer = new Footer(primaryStage);
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

class AppFrame extends BorderPane{
    private Homepage homepage;
    private Stage primaryStage;

    AppFrame(Stage primaryStage) {
        this.primaryStage = primaryStage;
        homepage = new Homepage(primaryStage);

        this.setCenter(homepage);
    }

    public void setRecipeFullView (Recipe recipe) {
        this.setCenter(new RecipeFullView(primaryStage, recipe));
    }

}

/*
 * The main class which extends the Application class and implements the start method to launch the mini-project app
 */
public class PantryPal extends Application {
    Consts consts = new Consts();


    /*
     * The start method launches the mini-project window with all the respective features
     * 
     * @param primaryStage The main window of the app
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame(primaryStage);

        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Create scene of mentioned size with the border pane

        primaryStage.setScene(new Scene(root, consts.WIDTH, consts.HEIGHT));

        // Make window non-resizable
        primaryStage.setResizable(true);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

