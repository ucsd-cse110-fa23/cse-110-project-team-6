package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.text.*;

abstract class Display extends BorderPane {
   protected Header header;
   protected VBox page;
   protected Footer footer;
}

class PPRectangle extends Rectangle {
   PPRectangle (int width, int height, int arc) {
      this.setWidth(width);
      this.setHeight(height);
      this.setArcHeight(arc);
      this.setArcWidth(arc);
      this.setFill(Consts.YELLOW);
   }
}

class PPButton extends Button {
   PPButton(String name) {
      this.setText(name);
      this.setPrefSize(100, 40);
      this.setBackground(new Background(new BackgroundFill(Consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setTextFill(Consts.DARK);
      this.setStyle("-fx-border-width: 0"); 
   }
}

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

abstract class Footer extends GridPane {
   void setup() {
      this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
   }
}

class PPMic extends StackPane {
   private boolean micIsOn = false;
   private ImageView imageView = new ImageView();
   private Button button = new Button();
   private Image micOff = new Image("mic.png");
   private Image micOn = new Image("micred.png");

   PPMic() {
      imageView.setImage(micOff);
      imageView.setFitWidth(110);
      imageView.setFitHeight(110);

      this.getChildren().add(imageView);

      button = new Button();
      button.setPrefSize(100, 100);
      button.setStyle("-fx-background-color: transparent");

      this.getChildren().add(button);

      addListeners();
   }

   private void addListeners () {
      button.setOnAction(e -> {
         // TO DO mic button functionality
         if(micIsOn){
            //TODO: Implement stop recording + transcript to text box for Whisper API
            imageView.setImage(micOff);
            micIsOn = false;
         }else{
            // TODO: Implement start recording for Whisper API
            imageView.setImage(micOn);
            micIsOn = true;
         }
      });
   }

   //TODO: Implement the record function for WhisperAPI
}

class PPDelete extends StackPane {
      Image delete = new Image("/delete.png");
      ImageView imageView = new ImageView(delete);
      Button button;
      Button deleteButton = new Button();

   PPDelete(Recipe recipe) {
      imageView.setImage(delete);
      imageView.setFitWidth(50);
      imageView.setFitHeight(50);

      this.getChildren().add(imageView);

      button = new Button();
      button.setPrefSize(50, 50);
      button.setStyle("-fx-background-color: transparent");

      this.getChildren().add(button);

      addListeners(recipe);
   }

   private void addListeners (Recipe recipe) {
      button.setOnAction(e -> {
            System.out.println("Delete button pressed");
            PantryPal.getRoot().getHome().deleteRecipe(recipe); //removes recipe from database
            PantryPal.getRoot().getHome().getRecipeListView().getChildren().remove(0);  // removes recipe from homepage
            PantryPal.getRoot().setPage(Page.HOME);
      });
   }

   //TODO: Implement the record function for WhisperAPI
}
