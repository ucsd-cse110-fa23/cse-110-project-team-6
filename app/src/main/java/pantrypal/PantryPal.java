package pantrypal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

enum Page {
    HOME, MEALTYPE, RECIPECREATOR, CLEAREDRECIPECREATOR, RECIPEGEN, RECIPEFULL;
}

class AppFrame extends BorderPane {
    private Display display;
    private HomePage home;
    private MealTypePage meal;
    private RecipeCreatorPage creator;

    private RecipeList recipeList;

    AppFrame(Stage primaryStage) {
        recipeList = new RecipeList();
        recipeList.loadRecipes();

        this.home = new HomePage(this.recipeList);

        display = home;

        this.setCenter(display);
        addListeners(primaryStage);
    }

    private void addListeners(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            recipeList.saveRecipes();
        });
    }

    void start(){
        this.meal = new MealTypePage();
        this.creator = new RecipeCreatorPage();
    }

    HomePage getHome(){
        return this.home;
    }

    MealTypePage getMeal(){
        return this.meal;
    }

    RecipeCreatorPage getCreator(){
        return this.creator;
    }

    RecipeList getRecipeList(){
        return this.recipeList;
    }

    void setPage(Page page) {
        switch (page) {
            case HOME:
                this.setCenter(display);
                break;
            case MEALTYPE:
                this.setCenter(meal);
                break;
            case RECIPECREATOR:
                PantryPal.getRoot().getCreator().getFooter().hideDoneButton();
                this.setCenter(creator);
            case CLEAREDRECIPECREATOR:
                PantryPal.getRoot().getCreator().getFooter().hideDoneButton();
                creator.clear();
                this.setCenter(creator);
            default:
                break;
        }
    }

    void setPage(Page page, Recipe recipe) {
        switch (page) {
            case RECIPEFULL:
                this.setCenter(new RecipeFullPage(recipe));
                break;
            default:
                break;
        }
    }

    void setPage(Page page, Recipe recipe, String input) {
        switch (page) {
            case RECIPEGEN:
                this.setCenter(new GeneratedRecipePage(recipe, input));
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
    private static Stage primaryStage;
    private static AppFrame root;

    /*
     * The start method launches the mini-project window with all the respective features
     *
     * @param primaryStage The main window of the app
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        root = new AppFrame(primaryStage);
        root.start();
        

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

    public static AppFrame getRoot() {
        return root;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}