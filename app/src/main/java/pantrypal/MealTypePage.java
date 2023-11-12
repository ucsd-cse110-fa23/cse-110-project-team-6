package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.File;

class MealTypePage extends Display {
    private MealOptionsView page;

   MealTypePage () {
      header = new Header("Meal Options");
      page = new MealOptionsView();
      footer = new MealTypeFooter();

      this.setTop(header);
      this.setCenter(page);
      this.setBottom(footer);

      while (true) {
         // integration for WhisperAPI
         WhisperAPI whisper = new WhisperAPI();
         Recording recording = new Recording();
         recording.createRecording();
         File recordingWAV = new File("Recording.wav");
         String input = "";
         try{
            input = whisper.readFile(recordingWAV);
         }
         catch(Exception e){
            System.out.println("Error reading file");
         }
         
         input.toLowerCase();
         int ifBreakfast = input.indexOf("breakfast");
         int ifLunch = input.indexOf("lunch");
         int ifDinner = input.indexOf("dinner");
         if (ifBreakfast == -1 && ifLunch == -1 && ifDinner == -1) {
            continue;
         } else {
            if (ifBreakfast > ifLunch) {
               if (ifBreakfast > ifDinner) {
                  // breakfast is mealtype
               } else {
                  // dinner is mealtype
               }
            } else {
               if (ifLunch > ifDinner) {
                  // lunch is mealtype
               } else {
                  // dinner is mealtype
               }
            }
            PantryPal.getRoot().setPage(Page.RECIPECREATOR);
            break;
         }
      }
   }
}

class MealOptionsView extends VBox {
   private MealUnitView breakfast;
   private MealUnitView lunch;
   private MealUnitView dinner;

   MealOptionsView() {
      this.setWidth(750);
      this.setPrefHeight(840);
      this.setSpacing(100);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setAlignment(Pos.CENTER);
      
      breakfast = new MealUnitView("Breakfast");
      lunch = new MealUnitView("Lunch");
      dinner = new MealUnitView("Dinner");        
      this.getChildren().add(breakfast);
      this.getChildren().add(lunch);
      this.getChildren().add(dinner);
   }
}

class MealUnitView extends StackPane {
   private Rectangle rectangle;
   private Text meal;
   //temp button fuctionality

   private Button button;

   private final int MEAL_WIDTH = 600;
   private final int MEAL_HEIGHT = 120;
   private final int MEAL_ARC = 45;

   MealUnitView (String mealType) {
      // rectangle
      rectangle = new PPRectangle(MEAL_WIDTH, MEAL_HEIGHT, MEAL_ARC);
      this.getChildren().add(rectangle);

      // meal type
      meal = new Text(mealType);
      meal.setFont(Consts.V40);
      meal.setFill(Consts.DARK);
      this.getChildren().add(meal);

      //invisible button
      //TEMP FUNCTIONALYLITY    
      button = new Button();
      button.setStyle("-fx-background-color: transparent");
      button.setPrefSize(600, 120);
      this.getChildren().add(button);
      
      addListeners();
   }

   private void addListeners () {
      button.setOnAction(e -> {
         // TO DO add button functionality
         PantryPal.getRoot().setPage(Page.RECIPECREATOR);
         System.out.println("selected meal type");
      });
   }
}

class MealTypeFooter extends Footer {
   private Button backButton;

   MealTypeFooter() {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      addListeners();
   }

   private void addListeners () {
   backButton.setOnAction(e -> {
      PantryPal.getRoot().setPage(Page.HOME);
   });
   }
}