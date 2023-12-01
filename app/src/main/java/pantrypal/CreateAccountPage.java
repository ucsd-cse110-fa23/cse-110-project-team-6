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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CreateAccountPage extends Display {
   CreateAccountPage() {
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setCenter(new CreateAccount());

      Text signature = new Text("produced by kmeksv");
      this.setMargin(signature, new Insets(0, 10, 10, 0));
      this.setAlignment(signature, Pos.BOTTOM_RIGHT);
      this.setBottom(signature);
   }
}

class CreateAccount extends VBox {
   private Button next;
   private Button signIn;
   
   CreateAccount() {
      this.setAlignment(Pos.CENTER);
      this.setSpacing(50);

      PPLogo logo = new PPLogo();
      // this.setMargin(logo, new Insets(0, 0, 350, 0));
      this.getChildren().add(logo);

      CreateAccountPrompts prompts = new CreateAccountPrompts();
      // this.setMargin(prompts, new Insets(175, 0, 0, 0));
      this.getChildren().add(prompts);

      LabelledArrow arrow = new LabelledArrow();
      // this.setMargin(arrow, new Insets(400, 0, 0, 0));
      this.getChildren().add(arrow);

      signIn = new PPButton("sign in");
      signIn.setPrefSize(250, 35);
      // this.setMargin(signIn, new Insets(550, 0, 0, 0));
      this.getChildren().add(signIn);

      addListeners();
   }

   class LabelledArrow extends HBox {
      LabelledArrow() {
         this.setAlignment(Pos.CENTER);

         Text text = new Text("create account");
         this.getChildren().add(text);

         ArrowButton button = new ArrowButton();
         this.getChildren().add(button);
         next = button.getButton();
      }
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
      signIn.setOnAction(e -> {
         // switch to sign in page
      });
   }
}

class CreateAccountPrompts extends SignInPrompts {
   PPPrompt password2;

   CreateAccountPrompts() {
      this.setAlignment(Pos.CENTER);

      password2 = new PPPrompt("confirm password");

      this.add(password2.getPrompt(), 0, 2);
      this.add(password2.getResponse(), 1, 2);

      this.setHalignment(password2.getPrompt(), HPos.RIGHT);
   }

   public String getPassword2() {
      return password2.getText();
   }
}

class PPLogo extends StackPane {
   PPLogo() {
      this.setAlignment(Pos.CENTER);

      Circle circle = new Circle(142);
      circle.setFill(Consts.YELLOW);
      this.getChildren().add(circle);

      Image logo = new Image(Consts.logoURL, 204, 182, true, true);
      ImageView imageView = new ImageView();
      imageView.setImage(logo);
      imageView.setFitWidth(204);
      imageView.setFitHeight(182);
      this.getChildren().add(imageView);
   }
}