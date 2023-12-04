package pantrypal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import java.net.HttpURLConnection;
import java.util.*;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class RecipeCreatorPage extends Display {
   private RecipeCreatorView createView;
   private Header header;
   private ScrollPane scroller;
   private RecipeCreatorFooter footer;

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

   }

   public RecipeCreatorView getView(){
      return this.createView;
   }

   public RecipeCreatorFooter getFooter(){
      return this.footer;
   }

   public void clear(){
      createView.clearInput();
   }

}

class RecipeCreatorView extends VBox implements Observer {
   private PPMic mic;
   private Label input;
   private ScrollPane scroller;

   private Rectangle inputBackground;

   public void update() {
      input.setText(mic.getRecordedText());
   }
   
   RecipeCreatorView() {
      this.setSpacing(50);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

      mic = new PPMic();
      mic.registerObserver(this);
      this.getChildren().add(mic);
      this.setMargin(mic, new Insets(45, 0, 0, 0));
      
      //TEMP TEXT - SHOULD BE UPDATED BY MIC INPUT
      input = new Label();
      input.setWrapText(true);
      input.setMaxWidth(530);
      //input.setWrappingWidth(530);
      input.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      //input.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
      input.setFont(Consts.F12);
      //input.setFill(Consts.DARK);

      inputBackground = new PPRectangle(600, 450, 45);
      inputBackground.setFill(Consts.LIGHT);
      inputBackground.setStroke(Consts.YELLOW);
      inputBackground.setStrokeWidth(5);

      scroller = new ScrollPane(input);
      scroller.setMaxWidth(550);
      scroller.setMaxHeight(400);
      scroller.setStyle("-fx-background-color: F1EBE2; -fx-border-width: 0");
      
      StackPane textInput = new StackPane();
      textInput.getChildren().add(inputBackground);
      textInput.getChildren().add(scroller);

      this.getChildren().add(textInput);
   }

   public void clearInput(){
      input.setText("");
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
       if (!view.getInput().equals("")) {
         showDoneButton();
         System.out.println("done button revealed");
       }
   }

   RecipeCreatorFooter(RecipeCreatorView view) {
      this.view = view;
      view.getMic().registerObserver(this);
      setup();

      backButton = new PPButton("Back");
      this.add(backButton, 0, 0);
      this.setHalignment(backButton, HPos.LEFT);
      this.setMargin(backButton, new Insets(20, 20, 20, 20));
      //this.getChildren().add(backButton);

      doneButton = new PPButton("Done");
      this.add(doneButton, 1, 0);
      this.setHalignment(doneButton, HPos.RIGHT);
      this.setMargin(doneButton, new Insets(20, 20, 20, 20));

      doneButton.setVisible(false);

      addListeners();
   }

   public void showDoneButton() {
      doneButton.setVisible(true);
   }

   public void hideDoneButton() {
      doneButton.setVisible(false);
   }

   private void addListeners () {
      backButton.setOnAction(e -> {
         hideDoneButton();
         PantryPal.getRoot().setPage(Page.MEALTYPE);
      });
      doneButton.setOnAction( e-> {
         if(view.getInput() != null) {
            try {
               // connects to server
               System.out.println("Connecting to server...");
               System.out.println(PantryPal.getRoot().getMeal().getMealType());
               String urlString = "http://localhost:8100/NewRecipe";
               URL url = new URI(urlString).toURL();
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("POST");
               conn.setDoOutput(true);

               // generate JSON object to send
               JSONObject test = new JSONObject("{\"prompt\":\"" + view.getInput() + "\",\"mealType\":\"" + PantryPal.getRoot().getMeal().getMealType()+ "\",\"regenerate\":\"" + false +"\"}");
               byte[] out = (test.toString()).getBytes(StandardCharsets.UTF_8);
               int length = out.length;
               System.out.println(test);

               conn.setFixedLengthStreamingMode(length);
               conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // sends POST request with prompy and meal type
               try(OutputStream os = conn.getOutputStream()) {
                  os.write(out);
                  os.flush();
                  os.close();
               }
               // obtains response from server
               Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
               Recipe recipeGen = new Recipe(new JSONObject(sc.nextLine()), PantryPal.getRoot().getMeal().getMealType());
               System.out.println(recipeGen.toJson());
               System.out.println("GENERATED RECIPE");
               PantryPal.getRoot().setPage(Page.RECIPEGEN, recipeGen, view.getInput());
            } 
            catch (Exception ex) {
               ex.printStackTrace();
               System.out.println("Error: " + ex.getMessage());
            }
         }
      });
   }
}