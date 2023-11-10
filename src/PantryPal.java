import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

enum Page {
    HOME, MEALTYPE, RECIPECREATOR, RECIPEGEN, RECIPEFULL;
}

class AppFrame extends BorderPane {
    private Display display;
    private HomePage home;
    private MealTypePage mealType;

    private Stage primaryStage;

    AppFrame(Stage primaryStage) {
        home = new HomePage(primaryStage, this);
        mealType = new MealTypePage(primaryStage, this);

        display = home;

        this.setCenter(display);
    }

    void setPage(Page page) {
        switch (page) {
            case HOME:
                this.setCenter(display);
                break;
            case MEALTYPE:
                this.setCenter(mealType);
                break;
            case RECIPECREATOR:
                this.setCenter(new RecipeCreatorPage(primaryStage, this));
                break;
            default:
                break;
        }
    }

    void setPage(Page page, Recipe recipe) {
        switch (page) {
            case RECIPEGEN:
                this.setCenter(new GeneratedRecipePage(primaryStage, this, recipe));
                break;
            case RECIPEFULL:
                this.setCenter(new RecipeFullPage(primaryStage, this, recipe));
                break;
            default:
                break;
        }
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

        primaryStage.setScene(new Scene(root, Consts.WIDTH, Consts.HEIGHT));

        // Make window non-resizable
        primaryStage.setResizable(true);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}