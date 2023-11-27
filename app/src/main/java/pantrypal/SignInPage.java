package pantrypal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SignInPage extends Display {
   SignInPage() {
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setCenter(new SignIn());
   }
}

class SignIn extends StackPane {
   private Button next;
   private Button newAccount;

   SignIn() {
      this.setAlignment(this, Pos.CENTER);

      PPLogoName logo = new PPLogoName();
      this.setMargin(logo, new Insets(0, 0, 175, 0));
      this.getChildren().add(logo);

      Rectangle mask = new Rectangle();
      mask.setWidth(475);
      mask.setHeight(200);
      mask.setFill(Consts.GREEN);
      this.setMargin(mask, new Insets(200, 0, 0, 0));
      this.getChildren().add(mask);

      SignInPrompts prompts = new SignInPrompts();
      this.setMargin(prompts, new Insets(125, 0, 0, 0));
      this.getChildren().add(prompts);

      Text signature = new Text("produced by kmeksv");
      this.setAlignment(signature, Pos.BOTTOM_RIGHT);
      this.setMargin(signature, new Insets(0, 10, 10, 0));
      this.getChildren().add(signature);

      ArrowButton button = new ArrowButton();
      this.setMargin(button, new Insets(305, 0, 0, 0));
      this.getChildren().add(button);
      next = button.getButton();

      newAccount = new PPButton("create new account");
      newAccount.setPrefSize(250, 35);
      this.setMargin(newAccount, new Insets(550, 0, 0, 0));
      this.getChildren().add(newAccount);

      addListeners();
   }

   private void addListeners() {
      next.setOnAction( e-> {
         // check password and username
         /*
          * prompts.getUsername()
          * prompts.getPassword()
          */
         // get saved recipes
         PantryPal.getRoot().setPage(Page.HOME);
      });
      newAccount.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.CREATEACCOUNT);
      });
   }
}

class ArrowButton extends StackPane {
   Button button;

   ArrowButton() {
      Polygon triangle = new Polygon();
      triangle.getPoints().addAll(new Double[]{
         0.0, 0.0,
         0.0, 50.0,
         48.0, 25.0});
      triangle.setFill(Consts.YELLOW);
      this.getChildren().add(triangle);

      button = new Button();
      button.setPrefSize(56, 56);
      button.setStyle("-fx-background-color: transparent");
      this.getChildren().add(button);
   }

   public Button getButton() {
      return button;
   }
}

class SignInPrompts extends GridPane {
   PPPrompt username;
   PPPrompt password;

   SignInPrompts() {
      this.setWidth(Consts.WIDTH);
      this.setHeight(Consts.HEIGHT);
      this.setHgap(10);
      this.setVgap(10);
      this.setAlignment(Pos.CENTER);

      username = new PPPrompt("username");
      password = new PPPrompt("password");

      this.add(username.getPrompt(), 0, 0);
      this.add(username.getResponse(), 1, 0);
      this.add(password.getPrompt(), 0, 1);
      this.add(password.getResponse(), 1, 1);

      this.setHalignment(username.getPrompt(), HPos.RIGHT);
      this.setHalignment(password.getPrompt(), HPos.RIGHT);
   }

   public String getUsername() {
      return username.getText();
   }

   public String getPassword() {
      return password.getText();
   }
}

class PPLogoName extends StackPane {
   PPLogoName() {
      this.setAlignment(Pos.CENTER);

      Circle circle = new Circle(235);
      circle.setFill(Consts.YELLOW);
      this.getChildren().add(circle);

      LogoName logoName = new LogoName();
      this.setMargin(logoName, new Insets(0, 0, 100, 0));
      this.getChildren().add(logoName);
   }

   class LogoName extends VBox {
      private Image logo = new Image(Consts.logoURL, 204, 182, true, true);

      LogoName() {
         this.setAlignment(Pos.CENTER);

         ImageView imageView = new ImageView();
         imageView.setImage(logo);
         imageView.setFitWidth(204);
         imageView.setFitHeight(182);
         this.getChildren().add(imageView);

         Text text = new Text("PantryPal!");
         text.setFont(Consts.V65);
         this.getChildren().add(text);
      }
   }
}
