package pantrypal;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;



import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class SignInPage extends Display {
   SignInPage() {
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setCenter(new SignIn());

      Text signature = new Text("produced by kmeksv");
      this.setMargin(signature, new Insets(0, 10, 10, 0));
      this.setAlignment(signature, Pos.BOTTOM_RIGHT);
      this.setBottom(signature);
   }
}

class SignIn extends VBox {
   private Button next;
   private Button newAccount;
   private SignInPrompts prompts;

   SignIn() {
      this.setAlignment(Pos.CENTER);
      this.setSpacing(50);

      PPLogoName logo = new PPLogoName();
      // this.setMargin(logo, new Insets(0, 0, 175, 0));
      this.getChildren().add(logo);

      prompts = new SignInPrompts();
      // this.setMargin(prompts, new Insets(125, 0, 0, 0));
      this.getChildren().add(prompts);

      ArrowButton button = new ArrowButton();
      // this.setMargin(button, new Insets(305, 0, 0, 0));
      this.getChildren().add(button);
      next = button.getButton();

      newAccount = new PPButton("create new account");
      newAccount.setPrefSize(250, 35);
      // this.setMargin(newAccount, new Insets(550, 0, 0, 0));
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
         try {
            String username = prompts.getUsername();
            String password = prompts.getPassword();
            String urlString = "http://localhost:8100/Account";
            urlString = urlString + "?username=" + username + "&" + "?password=" + password;
            System.out.println(urlString);
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            if (conn.getResponseCode() == 200) {
               PantryPal.getRoot().setPage(Page.HOME);
            }
            else if (conn.getResponseCode() == 400) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Invalid Sign In");
               alert.setHeaderText(null);
               alert.setContentText("Invalid Username");
               alert.showAndWait();
               return;
            } 
            else if (conn.getResponseCode() == 404) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Invalid Sign In");
               alert.setHeaderText(null);
               alert.setContentText("Incorrect Password");
               alert.showAndWait();
               return;
            }
            PantryPal.getRoot().addUsername(username);//username;
            PantryPal.getRoot().loadRecipes();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }

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
   private Image logo = new Image(Consts.logoWithCircleURL, 469, 345, true, true);

   PPLogoName() {
      this.setAlignment(Pos.CENTER);

      ImageView imageView = new ImageView();
      imageView.setImage(logo);
      imageView.setFitWidth(469);
      imageView.setFitHeight(345);
      this.getChildren().add(imageView);

      Text text = new Text("PantryPal!");
      text.setFont(Consts.V65);
      this.getChildren().add(text);
      this.setMargin(text, new Insets(250, 0, 0, 0));
   }
}