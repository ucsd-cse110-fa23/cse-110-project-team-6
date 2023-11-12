package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.text.*;

class RecipeCreatorPage extends Display {
   private Text input;
   private RecipeCreatorView createView;
   private ScrollPane scroller;

   RecipeCreatorPage(){
      header = new Header("Recipe Maker");
      createView = new RecipeCreatorView();
      footer = new RecipeCreatorFooter(createView);

      scroller = new ScrollPane(createView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);

      WhisperAPI whisper = new WhisperAPI();
      Recording recording = new Recording();
      recording.createRecording();
      File recordingWAV = new File("Recording.wav");
      String ingredientList = "";
      try{
         ingredientList = whisper.readFile(recordingWAV);
      }
      catch(Exception e){
         System.out.println("Error reading file");
      }
   }
}

class RecipeCreatorView extends VBox {
   private StackPane mic;
   private Text input;
   private ScrollPane scroller;

   private Rectangle inputBackground;

   RecipeCreatorView() {
      this.setSpacing(50);
      

      mic = new PPMic();
      //TEMP TEXT - SHOULD BE UPDATED BY MIC INPUT
      input = new Text("Potatoes wine butter beef onions garlic water milk eggs ");
      input.setWrappingWidth(530);
      input.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      input.setFont(Consts.V12);
      input.setFill(Consts.DARK);

      inputBackground = new PPRectangle(600, 550, 45);
      inputBackground.setFill(Color.TRANSPARENT);
      inputBackground.setStroke(Consts.YELLOW);
      inputBackground.setStrokeWidth(5);

      this.getChildren().add(mic);
      this.setMargin(mic, new Insets(45, 0, 0, 0));

      scroller = new ScrollPane(input);
      scroller.setMaxWidth(550);
      scroller.setMaxHeight(500);
      scroller.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      
      StackPane textInput = new StackPane();
      textInput.getChildren().add(inputBackground);
      textInput.getChildren().add(scroller);

      this.getChildren().add(textInput);
   }

   public void setInput(String s) {
      input.setText(s);
   }

   public String getInput(){
      return input.getText();
   }
}

class RecipeCreatorFooter extends Footer {
   private Button backButton;
   private Button doneButton;

   RecipeCreatorFooter(RecipeCreatorView view) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.add(backButton, 0, 0);
      this.setMargin(backButton, new Insets(20, 480, 20, 20));  
      //this.getChildren().add(backButton);

      doneButton = new PPButton("Done");
      this.add(doneButton, 6, 0);
      this.setMargin(doneButton, new Insets(20, 20, 20, 20));  
      //this.getChildren().add(doneButton);
      //doneButton.setVisible(false);

      addListeners(view);
   }

   public void showDoneButton() {
      doneButton.setVisible(true);
   }

   private void addListeners (RecipeCreatorView view) {
      backButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.MEALTYPE);
      });
      doneButton.setOnAction( e-> {
         RecipeCreator rc = new RecipeCreator();
         Recipe recipeGen = rc.createRecipe(view.getInput());
         PantryPal.getRoot().setPage(Page.RECIPEGEN, recipeGen);
      });
   }
}