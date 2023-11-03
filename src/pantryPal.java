import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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

class RecipeListView extends VBox {
    List<Recipe> recipes;
}

class RecipeUnitView extends HBox {
    private TextField recipeName;
}

class RecipeFullView extends BorderPane{
    RecipeFullView (Stage primaryStage) {

    }
}

class Homepage extends BorderPane {
    Homepage (Stage primaryStage) {

    }
}

class AppFrame extends BorderPane{
    private Homepage homepage;
    private RecipeFullView recipeFullView;


    AppFrame(Stage primaryStage) {

    }
}

/*
 * The main class which extends the Application class and implements the start method to launch the mini-project app
 */
public class pantryPal extends Application {

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