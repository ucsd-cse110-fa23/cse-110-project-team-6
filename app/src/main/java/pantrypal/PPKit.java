package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.text.*;

// abstract class for all pages of PantryPal
abstract class Display extends BorderPane {
   protected Header header;
   protected VBox page;
   protected Footer footer;
}

// PantryPal Rectangle component 
// yellow background with rounded corners styling
class PPRectangle extends Rectangle {
   PPRectangle (int width, int height, int arc) {
      this.setWidth(width);
      this.setHeight(height);
      this.setArcHeight(arc);
      this.setArcWidth(arc);
      this.setFill(Consts.YELLOW);
   }
}

// PantryPal Button component 
// yellow background styling
class PPButton extends Button {
   PPButton(String name) {
      this.setText(name);
      this.setPrefSize(100, 40);
      this.setBackground(new Background(new BackgroundFill(Consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setTextFill(Consts.DARK);
      this.setStyle("-fx-border-width: 0"); 
   }
}

// PantryPal Header component
// green background, set height, centered title text styling
class Header extends HBox { 
    Header(String heading) {
        this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        Text titleText = new Text(heading); // Text of the Header
        titleText.setFont(Consts.V40);
        titleText.setFill(Consts.DARK);
        this.getChildren().add(titleText);
    }
}

// abstract class for all Footers
// green background styling
abstract class Footer extends GridPane {
   void setup() {
      this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
   }
}

// microphone component
// toggles between on microphone and off microphone images
class PPMic extends StackPane {
   private final int BUTTON_SIZE = Consts.PIC_WIDTH - 10;

   private boolean isMicOn = false;
   private ImageView imageView = new ImageView();
   private Button button = new Button();

   private Image micOff = new Image(Consts.micURL, Consts.PIC_WIDTH, Consts.PIC_HEIGHT, true, true);
   private Image micOn = new Image(Consts.micRedURL, Consts.PIC_WIDTH, Consts.PIC_HEIGHT, true, true);

   PPMic() {
      // starts imageView with off microphone 
      imageView.setImage(micOff);
      imageView.setFitWidth(Consts.PIC_WIDTH);
      imageView.setFitHeight(Consts.PIC_HEIGHT);
      this.getChildren().add(imageView);

      // sets button for toggling microphone
      button = new Button();
      button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
      button.setStyle("-fx-background-color: transparent");
      this.getChildren().add(button);

      addListeners();
   }

   private void addListeners () {
      button.setOnAction(e -> {
         isMicOn = !isMicOn;
         // TODO mic button functionality
         if (isMicOn) {
            //TODO: Implement stop recording + transcript to text box for Whisper API
            imageView.setImage(micOn);
            recordIngredients();
         } else {
            // TODO: Implement start recording for Whisper API
            imageView.setImage(micOff);
         }
      });
   }

   //TODO: Implement the record function for WhisperAPI
   private void recordIngredients() {

   }
}

// delete component
//  
class PPDelete extends StackPane {
   private final int BUTTON_SIZE = Consts.DELETE_WIDTH - 10;

   private Image delete = new Image(Consts.deleteURL, Consts.DELETE_WIDTH, Consts.DELETE_HEIGHT, true, true);
   private ImageView imageView = new ImageView(delete);
   private Button button;

   PPDelete(Recipe recipe) {
      // startes imageview
      imageView.setImage(delete);
      imageView.setFitWidth(Consts.DELETE_WIDTH);
      imageView.setFitHeight(Consts.DELETE_HEIGHT);
      this.getChildren().add(imageView);

      // sets button for recording button behavior
      button = new Button();
      button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
      button.setStyle("-fx-background-color: transparent");
      this.getChildren().add(button);
      addListeners(recipe);
   }

   private void addListeners (Recipe recipe) {
      button.setOnAction(e -> {
         System.out.println("Delete button pressed");
         PantryPal.getRoot().getHome().deleteRecipe(recipe); //removes recipe from database -- THIS IS GOOD
         System.out.print(PantryPal.getRoot().getRecipeList().getSize());
        // ArrayList<Node> recipeListView = PantryPal.getRoot().getHome().getRecipeListView().getChildren();


         //PantryPal.getRoot().getHome().getRecipeListView().getChildren().remove(0);  // removes recipe from homepage

         for(int i = 0; i <  PantryPal.getRoot().getHome().getRecipeListView().getChildren().size(); i++){
            if(PantryPal.getRoot().getHome().getRecipeListView().getChildren().get(i) instanceof RecipeUnitView){
               if (((RecipeUnitView) PantryPal.getRoot().getHome().getRecipeListView().getChildren().get(i)).getRecipe() == recipe) {
                  PantryPal.getRoot().getHome().getRecipeListView().getChildren().remove(i);
                  break;
               }
            }
         }


         PantryPal.getRoot().setPage(Page.HOME);
      });
   }

   //TODO: Implement the record function for WhisperAPI
}
