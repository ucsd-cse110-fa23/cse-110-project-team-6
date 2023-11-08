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