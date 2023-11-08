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

class MealTypePage extends Display {
    private MealOptionsView page;

    MealTypePage (Stage primaryStage, AppFrame frame) {
        header = new Header("Meal Options");
        page = new MealOptionsView(frame);
        footer = new MealTypeFooter(primaryStage, frame);

        this.setTop(header);
        this.setCenter(page);
        this.setBottom(footer);
    }
}

class MealOptionsView extends VBox {
    private MealUnitView breakfast;
    private MealUnitView lunch;
    private MealUnitView dinner;

    MealOptionsView(AppFrame frame) {
        this.setWidth(750);
        this.setPrefHeight(840);
        this.setSpacing(100);
        this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        
        breakfast = new MealUnitView("Breakfast", frame);
        lunch = new MealUnitView("Lunch", frame);
        dinner = new MealUnitView("Dinner", frame);        
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

   MealUnitView (String mealType, AppFrame frame) {
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
      
      addListeners(frame);
   }

   private void addListeners (AppFrame frame) {
      button.setOnAction(e -> {
         // TO DO add button functionality
         frame.setPage(Page.MIC);
         System.out.println("selected meal type");
      });
   }
}

class MealTypeFooter extends Footer {
   private Button backButton;

   MealTypeFooter(Stage primaryStage, AppFrame frame) {
      setup();
      this.setAlignment(Pos.CENTER_LEFT);
      backButton = new PPButton("Back");
      this.setMargin(backButton, new Insets(20, 20, 20, 20));  
      this.getChildren().add(backButton);

      addListeners(frame);
   }

   private void addListeners (AppFrame frame) {
   backButton.setOnAction(e -> {
      frame.setPage(Page.HOME);
   });
   }
}