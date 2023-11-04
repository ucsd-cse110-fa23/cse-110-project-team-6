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

class RecipeUnitView extends StackPane {
    Recipe recipe;
    Rectangle background;
    Button detailView;
    Text recipeName;

    RecipeUnitView (Recipe recipe) {
        this.recipe = recipe;

        this.setWidth(700);
        this.setHeight(80);
        this.setAlignment(Pos.CENTER_LEFT);

        // round rectangle
        background = new Rectangle();
        background.setWidth(700);
        background.setHeight(80);
        background.setArcHeight(30);
        background.setArcWidth(30);

        //style
        background.setFill(Color.web("#cee6ba"));
        background.setStroke(Color.web("#ccbc97"));
        background.setStrokeWidth(5);
        this.getChildren().add(background);

        // invisible button
        detailView = new Button();
        detailView.setStyle("-fx-background-color: transparent");
        detailView.setPrefSize(700, 80);
        this.getChildren().add(detailView);

        // text
        recipeName = new Text(recipe.getName());
        this.setMargin(recipeName, new Insets(0, 0, 0, 20));        // top, right, bottom, left
        recipeName.setStyle("-fx-font-color: #2e2e2e; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-size: 30");
        this.getChildren().add(recipeName);
        
        addListeners();
        
    }

    private void addListeners() {
        detailView.setOnAction(e -> {
            System.out.println("clicked detail view");
        });
    }
}

class RecipeListView extends VBox {
    List<Recipe> recipes;

    RecipeListView(Stage primaryStage) {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setStyle("-fx-background-color: #defce6; -fx-border-width: 0; -fx-font-weight: bold;");
        this.setAlignment(Pos.TOP_CENTER);

        Recipe test = new Recipe("red wine potatoes", "2", "69", "potatoes, wine", "bake, eat");

        RecipeUnitView ex = new RecipeUnitView(test);
        this.setMargin(ex, new Insets(0, 0, 0, 20));


        this.getChildren().add(ex);
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
    public Button newRecipe;
    
    Footer(Stage primaryStage) {
        this.setPrefSize(750, 80);

        newRecipe = new Button("New Recipe");
        newRecipe.setPrefSize(100, 40);
        newRecipe.setStyle("-fx-background-color: #e3e3e3; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-color: #2e2e2e"); // set background color of texfield

        this.setMargin(newRecipe, new Insets(20, 20, 20, 20));
        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(newRecipe);

        addListeners(primaryStage);
    }

    private void addListeners (Stage primaryStage) {
        newRecipe.setOnAction(e -> {
            // fill in
        });
    }
}

class Header extends HBox { 
    Header() {
        this.setPrefSize(750, 80);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-background-color: #e3e3e3; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-size: 40; -fx-font-color: #2e2e2e");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class Homepage extends BorderPane {
    private RecipeListView recipeList;
    private ScrollPane scroller;

    private Header header;
    private Footer footer;

    Homepage (Stage primaryStage) {
        header = new Header();
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
        primaryStage.setScene(new Scene(root, 750, 1000));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}