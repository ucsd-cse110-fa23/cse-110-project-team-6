package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.text.*;

class MealTypePage extends Display {
    private MealOptionsView page;

   MealTypePage () {
      header = new Header("Meal Options");
      page = new MealOptionsView();
      footer = new MealTypeFooter();

      this.setTop(header);
      this.setCenter(page);
      this.setBottom(footer);
   }
}

class MealOptionsView extends VBox implements Observer {
   private MealUnitView breakfast;
   private MealUnitView lunch;
   private MealUnitView dinner;

   private PPMic mic;

   public void update() {
      parseMealType(mic.getRecordedText());
   }

   MealOptionsView() {
      this.setWidth(750);
      this.setPrefHeight(840);
      this.setSpacing(50);
      this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setAlignment(Pos.CENTER);

      mic = new PPMic();
      mic.registerObserver(this);
      this.getChildren().add(mic);
      
      breakfast = new MealUnitView("Breakfast");
      lunch = new MealUnitView("Lunch");
      dinner = new MealUnitView("Dinner");        
      this.getChildren().add(breakfast);
      this.getChildren().add(lunch);
      this.getChildren().add(dinner);
   }

   private void parseMealType(String input) {
      input = mic.getRecordedText().toLowerCase();
      int ifBreakfast = input.indexOf("breakfast");
      int ifLunch = input.indexOf("lunch");
      int ifDinner = input.indexOf("dinner");
      if (ifBreakfast == -1 && ifLunch == -1 && ifDinner == -1) {
         System.out.println("Invalid Meal Type");
      } else {
         if (ifBreakfast > ifLunch) {
            if (ifBreakfast > ifDinner) {
               PantryPal.getRoot().setPage(Page.RECIPECREATOR, "breakfast");
            } else {
               PantryPal.getRoot().setPage(Page.RECIPECREATOR, "dinner");
            }
         } else {
            if (ifLunch > ifDinner) {
               PantryPal.getRoot().setPage(Page.RECIPECREATOR, "lunch");
            } else {
               PantryPal.getRoot().setPage(Page.RECIPECREATOR, "dinner");
            }
         }
      }
   }
}

class MealUnitView extends StackPane {
   private Rectangle rectangle;
   private Text meal;

   // temp button fuctionality
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

      // invisible button
      // TEMP FUNCTIONALYLITY    
      button = new Button();
      button.setStyle("-fx-background-color: transparent");
      button.setPrefSize(600, 120);
      this.getChildren().add(button);
      
      addListeners();
   }

   // TEMP FUNCTIONALITY
   private void addListeners () {
      button.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.RECIPECREATOR, "breakfast");
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