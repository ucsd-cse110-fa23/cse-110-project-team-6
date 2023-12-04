package pantrypal;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.*;

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
      signature.setFont(PPFonts.makeFont(FF.Itim, 15));
      this.setMargin(signature, new Insets(0, 10, 10, 0));
      this.setAlignment(signature, Pos.BOTTOM_RIGHT);
      this.setBottom(signature);
   }
}

class SignIn extends VBox {
   private Button next;
   private Button newAccount;
   private SignInPrompts prompts;
   private autoSignIn auto;

   SignIn() {
      this.setAlignment(Pos.CENTER);
      this.setSpacing(20);

      PPLogoName logo = new PPLogoName();
      // this.setMargin(logo, new Insets(0, 0, 175, 0));
      this.getChildren().add(logo);

      prompts = new SignInPrompts();
      // this.setMargin(prompts, new Insets(125, 0, 0, 0));
      this.getChildren().add(prompts);

      auto = new autoSignIn();
      this.getChildren().add(auto);
      // TODO auto.isAutoSelected() boolean 

      ArrowButton button = new ArrowButton();
      this.getChildren().add(button);
      this.setMargin(button, new Insets(0, 0, 50, 0));
      next = button.getButton();

      newAccount = new PPButton("create new account");
      newAccount.setPrefWidth(275);
      newAccount.setFont(PPFonts.makeFont(FF.KoHo, 25));
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
               CreateAutomaticSignIn(true, username, password);
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

   private void CreateAutomaticSignIn(boolean check, String username, String password) {
      File auto = new File("auto.txt");
      auto.delete();
      try {
         if (check) {
            auto.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(auto));
            bw.write(username);
            bw.write("\n");
            bw.write(password);
            bw.close();
         }
      }
      catch (Exception e) {
         System.out.println("Something went wrong!" + e);
      }
   }

   // checks if auto.txt exists and tries to sign in using that
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

      username = new PPPrompt("username", false);
      password = new PPPrompt("password", true);

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
      text.setFont(PPFonts.makeFont(FF.Itim, 65));
      text.setFill(Consts.DARK);
      this.getChildren().add(text);
      this.setMargin(text, new Insets(250, 0, 0, 0));
   }
}