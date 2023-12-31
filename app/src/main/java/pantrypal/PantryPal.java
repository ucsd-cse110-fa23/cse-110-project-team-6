package pantrypal;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// enum for changing pages
enum Page {
    SIGNIN, CREATEACCOUNT, HOME, MEALTYPE, RECIPECREATOR, CLEAREDRECIPECREATOR, RECIPEGEN, RECIPEFULL;
}

class AppFrame extends BorderPane {
    private HomePage home;
    private MealTypePage meal;
    private RecipeCreatorPage creator;
    private SignInPage signIn;
    private CreateAccountPage createAccount;

    RecipeList recipeList;

    AppFrame(Stage primaryStage) {
        if (pingServer()) {
            PPFonts.loadFonts();
            
            recipeList = new RecipeList();
            signIn = new SignInPage();
            createAccount = new CreateAccountPage();

            this.setCenter(signIn);
            addListeners(primaryStage);

            home = new HomePage(recipeList);
        }
        else {
            System.exit(0);
        }
    }

    private void addListeners(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            if (recipeList.getUsername() != null) {
                recipeList.saveRecipes();
            }
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

    // different types of setting pages
    void setPage(Page page) {
        switch (page) {
            case HOME:
                this.setCenter(home);
                break;
            case MEALTYPE:
                this.setCenter(meal);
                break;
            case SIGNIN:
                this.setCenter(signIn);
                break;
            case CREATEACCOUNT:
                this.setCenter(createAccount);
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
                System.out.println("FULL PAGE OPENED");
                this.setCenter(new RecipeFullPage(recipe));
                break;
            default:
                break;
        }
    }

    void setPage(Page page, Recipe recipe, String input) {
        switch (page) {
            case RECIPEGEN:
                System.out.println("GENERATING PAGE");
                this.setCenter(new GeneratedRecipePage(recipe, input));
                break;
            default:
                break;
        }
    }

    // change username associated with recipe list
    public void addUsername(String username) {
        recipeList.setUsername(username);
    }

    // get recipes from database and render
    public void loadRecipes() {
        recipeList.loadRecipes();
        if (recipeList.getSize() > 0) {
            this.home.renderLoadedRecipes(recipeList);
        }
    }

    // clear recipes from local list
    public void clearRecipes() {
        this.home.clearRecipes();
    }

    // check if server is active
    private boolean pingServer() {
        try {
            String urlString = "http://localhost:8100/Main";
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setDoOutput(true);
            conn.getResponseCode();
            return true;
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error 503");
            alert.setHeaderText(null);
            alert.setContentText("PantryPal is currently unavailable. Try again later.");
            alert.showAndWait();
            return false;
        }
    }   

    // checks if automatic sign in was previously selected
    void AutomaticSignIn() {
        File auto = new File("auto.txt");
        try {
            if (auto.isFile()) {
                Scanner sc = new Scanner(auto);
                String username = sc.nextLine();
                String password = sc.nextLine();
                sc.close();

                String urlString = "http://localhost:8100/Account";
                urlString = urlString + "?username=" + username + "&" + "?password=" + password;

                URL url = new URI(urlString).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                if (conn.getResponseCode() == 200) {
                    setPage(Page.HOME);
                    addUsername(username);
                    loadRecipes();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong!" + e);
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
        root.AutomaticSignIn();
        
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