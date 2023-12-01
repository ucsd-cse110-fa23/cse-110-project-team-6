package pantrypal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.*;
import java.util.Scanner;
import java.io.InputStreamReader;

enum Page {
    SIGNIN, CREATEACCOUNT, HOME, MEALTYPE, RECIPECREATOR, RECIPEGEN, RECIPEFULL;
}

class AppFrame extends BorderPane {
    private HomePage home;
    private SignInPage signIn;
    private CreateAccountPage createAccount;

    private RecipeList recipeList;

    AppFrame(Stage primaryStage) {
        recipeList = new RecipeList();
        signIn = new SignInPage();
        createAccount = new CreateAccountPage();

        this.setCenter(signIn);
        addListeners(primaryStage);

        home = new HomePage(recipeList);

        pingServer();
    }

    private void addListeners(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            if (recipeList.getUsername() != null) {
                recipeList.saveRecipes();
            }
        });
    }

    HomePage getHome(){
        return this.home;
    }

    RecipeList getRecipeList(){
        return this.recipeList;
    }

    void setPage(Page page) {
        switch (page) {
            case HOME:
                this.setCenter(home);
                break;
            case MEALTYPE:
                this.setCenter(new MealTypePage());
                break;
            case SIGNIN:
                this.setCenter(signIn);
                break;
            case CREATEACCOUNT:
                this.setCenter(createAccount);
                break;
            default:
                break;
        }
    }

    void setPage(Page page, String mealType) {
        switch (page) {
            case RECIPECREATOR:
                this.setCenter(new RecipeCreatorPage(mealType));
                break;
            default:
                break;
        }
    }

    void setPage(Page page, Recipe recipe) {
        switch (page) {
            case RECIPEGEN:
                this.setCenter(new GeneratedRecipePage(recipe));
                break;
            case RECIPEFULL:
                this.setCenter(new RecipeFullPage(recipe));
                break;
            default:
                break;
        }
    }

    public void addUsername(String username) {
        recipeList.setUsername(username);
    }

    public void loadRecipes() {
        recipeList.loadRecipes();
        if (recipeList.getSize() > 0) {
            this.home.renderLoadedRecipes(recipeList);
        }
    }

    private void pingServer() {
        try {
            String urlString = "http://localhost:8100/";
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setDoOutput(true);
            conn.getResponseCode();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error 503");
            alert.setHeaderText(null);
            alert.setContentText("PantryPal is currently unavailable. Try again later.");
            alert.showAndWait();
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