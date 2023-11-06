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
    public static final Color GREEN = Color.web("#A6D69B");
    public static final Color YELLOW = Color.web("#F1E293");
    public static final Color LIGHT = Color.web("F1EBE2");
    public static final Color DARK = Color.web("17373A");

    public static final int WIDTH = 750;
    public static final int HEIGHT = 1000;
    public static final int RECIPE_OFFSET = 5;
    public static final int HF_HEIGHT = 80;
}

class RecipeUnitView extends StackPane {
    private Consts consts = new Consts();

    private Rectangle background;
    private Button detailView;
    private Text recipeName;

    private final int RECIPE_WIDTH = 600;
    private final int RECIPE_HEIGHT = 80;
    
    RecipeUnitView (Recipe recipe) {
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
            this.getChildren().add(new RecipeUnitView(recipes.get(0))); // TO DO should be get(i)
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

class HomePageFooter extends HBox {
    private Consts consts = new Consts();
    
    private Button newRecipe;
    private AppFrame frame;
    
    HomePageFooter(Stage primaryStage, AppFrame frame) {
        this.setPrefSize(consts.WIDTH, consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER_RIGHT);
        this.frame = frame;

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

            frame.setMealTypePage();

            // TO DO add button functionality
            System.out.println("clicked add recipe");

        });
    }
}

class Header extends HBox { 
    private Consts consts = new Consts();

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
    private Consts consts = new Consts();

    private Rectangle background;
    private Text meal;

    //temp button fuctionality
    private Button mealButton;

    MealDesign (String mealType, AppFrame frame) {
        // rectangle
        background = new Rectangle();
        background.setWidth(600);
        background.setHeight(120);
        background.setArcHeight(45);
        background.setArcWidth(45);

        //style
        background.setFill(consts.YELLOW);
        this.getChildren().add(background);

        // meal type
        meal = new Text(mealType);
        meal.setFont(Font.font("Verdana", 40));
        meal.setFill(consts.DARK);
        this.getChildren().add(meal);

        //invisible button
        //TEMP FUNCTIONALYLITY    
        mealButton = new Button();
        mealButton.setStyle("-fx-background-color: transparent");
        mealButton.setPrefSize(600, 120);
        this.getChildren().add(mealButton);
        
        addListeners(frame);
    }

    private void addListeners (AppFrame frame) {
        mealButton.setOnAction(e -> {
            // TO DO add button functionality
            frame.setRecipeCreatorPage();
            System.out.println("selected meal type");
        });
    }
}

class MealTypeOptions extends VBox {
    private Consts consts = new Consts();

    private MealDesign breakfast;
    private MealDesign lunch;
    private MealDesign dinner;

    MealTypeOptions(AppFrame frame) {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setSpacing(100);
        this.setBackground(new Background(new BackgroundFill(consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        
        breakfast = new MealDesign("Breakfast", frame);
        lunch = new MealDesign("Lunch", frame);
        dinner = new MealDesign("Dinner", frame);        
        this.getChildren().add(breakfast);
        this.getChildren().add(lunch);
        this.getChildren().add(dinner);
    }
}

class MealFooter extends HBox{
    private Consts consts = new Consts();
    
    private Button backButton;
    
    MealFooter(Stage primaryStage, AppFrame frame) {
        this.setPrefSize(consts.WIDTH, consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER_LEFT);

        backButton = new Button("Back");
        backButton.setPrefSize(100, 40);
        backButton.setBackground(new Background(new BackgroundFill(consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        backButton.setTextFill(consts.DARK);
        backButton.setStyle("-fx-border-width: 0"); 
        this.setMargin(backButton, new Insets(20, 20, 20, 20));  

        this.getChildren().add(backButton);

        addListeners(primaryStage, frame);
    }

    private void addListeners (Stage primaryStage, AppFrame frame) {
        HomePage page = new HomePage(primaryStage, frame);
        backButton.setOnAction(e -> {

            frame.setHomePage();

        });
    }
}


class MealTypePage extends BorderPane {
    private Header header;
    private MealTypeOptions page;
    private MealFooter footer;

    MealTypePage (Stage primaryStage, AppFrame frame) {
        header = new Header("Meal Options");
        page = new MealTypeOptions(frame);
        footer = new MealFooter(primaryStage, frame);

        this.setTop(header);
        this.setCenter(page);
        this.setBottom(footer);
    }
}

class HomePage extends BorderPane {
    private RecipeListView recipeList;
    private ScrollPane scroller;

    private Header header;
    private HomePageFooter footer;

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

class RecipeCreatorView extends VBox{
    private Consts consts = new Consts();

    RecipeCreatorView() {




    }
}

class MicButton extends StackPane{

}

class RecipeFooter extends HBox{
    private Consts consts = new Consts();
    
    private Button backButton;
    
    RecipeFooter(Stage primaryStage, AppFrame frame) {
        this.setPrefSize(consts.WIDTH, consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER_LEFT);

        backButton = new Button("Back");
        backButton.setPrefSize(100, 40);
        backButton.setBackground(new Background(new BackgroundFill(consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        backButton.setTextFill(consts.DARK);
        backButton.setStyle("-fx-border-width: 0"); 
        this.setMargin(backButton, new Insets(20, 20, 20, 20));  

        this.getChildren().add(backButton);

        addListeners(primaryStage, frame);
    }

    private void addListeners (Stage primaryStage, AppFrame frame) {
        backButton.setOnAction(e -> {

            frame.setMealTypePage();

        });
    }
}

class RecipeCreatorPage extends BorderPane{
    private RecipeCreatorView createView;
    private ScrollPane scroller;
    private Header header;
    private RecipeFooter footer;

    RecipeCreatorPage(Stage primaryStage, AppFrame frame){
        header = new Header("Recipe Maker");
        footer = new RecipeFooter(primaryStage, frame);

        //footer = new RecipeFooter(primaryStage, frame);

        this.setTop(header);
        this.setCenter(createView);
        this.setBottom(footer);
    }
}

class AppFrame extends BorderPane{
    private HomePage homepage;
    private MealTypePage mealType;
    private RecipeCreatorPage createPage;
    private Stage primaryStage;

    AppFrame(Stage primaryStage) {
        this.primaryStage = primaryStage;
        homepage = new HomePage(primaryStage, this);
        mealType = new MealTypePage(primaryStage, this);
        createPage = new RecipeCreatorPage(primaryStage, this);

        this.setCenter(homepage);
    }

    public void setRecipeFullView (Recipe recipe) {
        this.setCenter(new RecipeFullView(primaryStage, recipe));
    }

    public void setMealTypePage() {
        this.setCenter(mealType);
    }

    public void setHomePage(){
        this.setCenter(homepage);
    }

    public void setRecipeCreatorPage(){
        this.setCenter(createPage);
    }

}

/*
 * The main class which extends the Application class and implements the start method to launch the mini-project app
 */
public class PantryPal extends Application {
    private Consts consts = new Consts();


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

