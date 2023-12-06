package pantrypal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

// page for creating a new account
// accessed from SignInPage
// can move to SignInPage and HomePage
public class CreateAccountPage extends Display {
   CreateAccountPage() {
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setCenter(new CreateAccount());

      Text signature = new Text("produced by kmeksv");
      signature.setFont(PPFonts.makeFont(FF.Itim, 15));
      this.setMargin(signature, new Insets(0, 10, 10, 0));
      this.setAlignment(signature, Pos.BOTTOM_RIGHT);
      this.setBottom(signature);
   }
}

// VBox lays out entire page
// from top to bottom: logo, username password and password confirmation prompts,
// checkbox for auto sign-in, confirmation arrow, button back to SignInPage

// next button: moves to HomePage
// sign in button: moves to SignInPage
class CreateAccount extends VBox {
   private Button next;
   private Button signIn;
   private CreateAccountPrompts prompts;
   private autoSignIn auto;
   
   CreateAccount() {
      this.setAlignment(Pos.CENTER);
      this.setSpacing(20);

      PPLogo logo = new PPLogo();
      this.setMargin(logo, new Insets(15, 0, 0, 0));
      this.getChildren().add(logo);

      prompts = new CreateAccountPrompts();
      // this.setMargin(prompts, new Insets(175, 0, 0, 0));
      this.getChildren().add(prompts);

      auto = new autoSignIn();
      this.getChildren().add(auto);

      LabelledArrow arrow = new LabelledArrow();
      this.setMargin(arrow, new Insets(0, 0, 50, 0));
      // this.setMargin(arrow, new Insets(400, 0, 0, 0));
      this.getChildren().add(arrow);

      signIn = new PPButton("sign in");
      signIn.setPrefWidth(275);
      signIn.setFont(PPFonts.makeFont(FF.KoHo, 25));
      this.setMargin(signIn, new Insets(0, 0, 5, 0));
      this.getChildren().add(signIn);

      addListeners();
   }

   private void addListeners() {
      next.setOnAction( e-> {
         if (!prompts.getPassword().equals(prompts.getPassword2())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect Passwords");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match.");
            alert.showAndWait();
            return;
         }
         // check password and username
         /*
          * prompts.getUsername()
          * prompts.getPassword()
          */
         // get saved recipes

         try {
            String username = prompts.getUsername(); 
            String password = prompts.getPassword(); 
            if (username.contains(" ") || password.contains(" ")) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Invalid Username or Password");
               alert.setHeaderText(null);
               alert.setContentText("Enter a valid username and password!");
               alert.showAndWait();
               return;
            }
            String urlString = "http://localhost:8100/Account";
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            JSONObject newUser = new JSONObject("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
            byte[] out = (newUser.toString()).getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            conn.setFixedLengthStreamingMode(length);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // sends POST request with prompy and meal type
            try(OutputStream os = conn.getOutputStream()) {
               os.write(out);
               os.flush();
               os.close();
            }

            // account succesfully created
            if (conn.getResponseCode() == 200) {
               PantryPal.getRoot().setPage(Page.HOME);
            }
            // nothing happens if account already exists
            else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Duplicate Accounts");
               alert.setHeaderText(null);
               alert.setContentText("Account already exists.");
               alert.showAndWait();
               return;
            }
         }
         catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
      });
      signIn.setOnAction(e -> {
         PantryPal.getRoot().setPage(Page.SIGNIN);
      });
   }

   // arrow button with label for MAKING A NEW account
   class LabelledArrow extends HBox {
      LabelledArrow() {
         this.setAlignment(Pos.CENTER);
         this.setSpacing(20);

         Text text = new Text("make account!");
         text.setFont(PPFonts.makeFont(FF.Itim, 30));
         this.getChildren().add(text);

         ArrowButton button = new ArrowButton();
         this.getChildren().add(button);
         next = button.getButton();
      }
   }
}

// username, password, and password confirmation PPPrompts
// password and password2 are PasswordFields
class CreateAccountPrompts extends GridPane {
   PPPrompt username;
   PPPrompt password;
   PPPrompt password2;

   CreateAccountPrompts() {
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

      password2 = new PPPrompt("confirm password", true);

      this.add(password2.getPrompt(), 0, 2);
      this.add(password2.getResponse(), 1, 2);

      this.setHalignment(password2.getPrompt(), HPos.RIGHT);
   }

   public String getUsername() {
      return username.getText();
   }

   public String getPassword() {
      return password.getText();
   }

   public String getPassword2() {
      return password2.getText();
   }
}

// PantryPal logo with a yellow circle in the background
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