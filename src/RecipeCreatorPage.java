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

class RecipeCreatorPage extends Display {
   private RecipeCreatorView createView;

   RecipeCreatorPage(Stage primaryStage, AppFrame frame){
      header = new Header("Recipe Maker");
      createView = new RecipeCreatorView(frame);
      footer = new RecipeCreatorFooter(frame);

      //footer = new RecipeFooter(primaryStage, frame);

      this.setTop(header);
      this.setCenter(createView);
      this.setBottom(footer);
   }
}

class RecipeCreatorView extends VBox {
   private StackPane mic;
   private Text input;
   private ScrollPane scroller;

   private Rectangle inputBackground;

   RecipeCreatorView(AppFrame frame) {
      this.setSpacing(50);

      mic = new PPMic(frame);
      input = new Text("Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test Testing test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test testTesting test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test ");
      input.setWrappingWidth(530);
      input.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      input.setFont(Consts.V12);
      input.setFill(Consts.DARK);
      inputBackground = new PPRectangle(600, 450, 45);
      inputBackground.setFill(Color.TRANSPARENT);
      inputBackground.setStroke(Consts.YELLOW);
      inputBackground.setStrokeWidth(5);

      this.getChildren().add(mic);
      this.setMargin(mic, new Insets(45, 0, 0, 0));

      scroller = new ScrollPane(input);
      scroller.setMaxWidth(550);
      scroller.setMaxHeight(400);
      scroller.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      
      StackPane textInput = new StackPane();
      textInput.getChildren().add(inputBackground);
      textInput.getChildren().add(scroller);

      this.getChildren().add(textInput);
   }

   public void setInput(String s) {
      input.setText(s);
   }
}

class RecipeCreatorFooter extends Footer {
   private Button backButton;
   private Button doneButton;

   RecipeCreatorFooter(AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      doneButton = new PPButton("Done");
      // this.setMargin(doneButton, new Insets(20, 20, 20, 510));  
      this.getChildren().add(doneButton);
      doneButton.setVisible(false);

      addListeners(frame);
   }

   public void showDoneButton() {
      doneButton.setVisible(true);
   }

   private void addListeners (AppFrame frame) {
      backButton.setOnAction(e -> {
         frame.setPage(Page.MEALTYPE);
      });
      doneButton.setOnAction( e-> {
         System.out.println("DOne Button Pressed");
      });
   }
}