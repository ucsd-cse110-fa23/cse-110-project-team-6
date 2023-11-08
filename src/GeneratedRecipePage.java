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

class GeneratedRecipePage extends Display {

   GeneratedRecipePage(Stage primaryStage, AppFrame frame, Recipe recipe){
      header = new Header(recipe.getName());
   }
}

class GeneratedRecipeView extends VBox{
    
    GeneratedRecipeView() {

    }
}