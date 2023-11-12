package pantrypal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


class RecipeCreatorPage extends Display {
   private RecipeCreatorView createView;
   private ScrollPane scroller;

   private String mealType;

   RecipeCreatorPage(String mealType){
      this.mealType = mealType;
      header = new Header("Recipe Maker");
      createView = new RecipeCreatorView();
      footer = new RecipeCreatorFooter(createView, mealType);

      scroller = new ScrollPane(createView);
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);

   }

   public String getMealType() {
      return this.mealType;
   }
}

class RecipeCreatorView extends VBox implements Observer {
   private PPMic mic;
   private Text input;
   private ScrollPane scroller;

   private Rectangle inputBackground;

   public void update() {
      input.setText(mic.getRecordedText());
   }
   
   RecipeCreatorView() {
      this.setSpacing(50);

      mic = new PPMic();
      mic.registerObserver(this);
      this.getChildren().add(mic);
      this.setMargin(mic, new Insets(45, 0, 0, 0));
      
      //TEMP TEXT - SHOULD BE UPDATED BY MIC INPUT
      input = new Text();
      input.setWrappingWidth(530);
      input.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      input.setFont(Consts.V12);
      input.setFill(Consts.DARK);

      inputBackground = new PPRectangle(600, 450, 45);
      inputBackground.setFill(Color.TRANSPARENT);
      inputBackground.setStroke(Consts.YELLOW);
      inputBackground.setStrokeWidth(5);

      scroller = new ScrollPane(input);
      scroller.setMaxWidth(550);
      scroller.setMaxHeight(400);
      scroller.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      
      StackPane textInput = new StackPane();
      textInput.getChildren().add(inputBackground);
      textInput.getChildren().add(scroller);

      this.getChildren().add(textInput);
   }

   public String getInput(){
      return input.getText();
   }

   public PPMic getMic() {
      return mic;
   }
}

class RecipeCreatorFooter extends Footer implements Observer {
   private Button backButton;
   private Button doneButton;

   private RecipeCreatorView view;

   public void update() {
      // if (!view.getInput().equals("")) {
         showDoneButton();
      // }
      System.out.println("done button revealed");
   }

   RecipeCreatorFooter(RecipeCreatorView view, String mealType) {
      this.view = view;
      view.getMic().registerObserver(this);

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
      doneButton.setVisible(false);

      addListeners(mealType);
   }

   public void showDoneButton() {
      doneButton.setVisible(true);
   }

   private void addListeners (String mealType) {
      backButton.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.MEALTYPE);
      });
      doneButton.setOnAction( e-> {
         RecipeCreator rc = new RecipeCreator();
         Recipe recipeGen = rc.createRecipe(view.getInput(), mealType);
         PantryPal.getRoot().setPage(Page.RECIPEGEN, recipeGen);
      });
   }
}