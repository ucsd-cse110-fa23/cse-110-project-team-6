package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import javafx.geometry.Insets;
import java.io.File;
import java.util.List;

// abstract class for all pages of PantryPal
abstract class Display extends BorderPane {
   protected Header header;
   protected VBox page;
   protected Footer footer;
}

// PantryPal Rectangle component 
// yellow background with rounded corners styling
class PPRectangle extends Rectangle {
   PPRectangle (int width, int height, int arc) {
      this.setWidth(width);
      this.setHeight(height);
      this.setArcHeight(arc);
      this.setArcWidth(arc);
      this.setFill(Consts.YELLOW);
   }
}

// PantryPal User Prompt TextField
class PPPrompt {
   private Label prompt;
   private TextResponse response;

   PPPrompt (String prompt, boolean isPassword) {
      this.prompt = new Label(prompt + ":");
      this.prompt.setFont(PPFonts.makeFont(FF.KoHo, 30));
      response = new TextResponse(prompt, isPassword);
   }

   public String getText() {
      return response.getText();
   }

   public Label getPrompt() {
      return prompt;
   }

   public TextResponse getResponse() {
      return response;
   }

   class TextResponse extends StackPane {
      private Rectangle background;
      private TextField text;

      TextResponse(String prompt, boolean isPassword) {
         background = new Rectangle();
         background.setWidth(350);
         background.setHeight(35);
         background.setArcWidth(25);
         background.setArcHeight(25);
         background.setFill(Consts.LIGHT);
         this.getChildren().add(background);

         if (isPassword) {
            text = new PasswordField();
         } else {
            text = new TextField();
         }
         text.setFont(PPFonts.makeFont(FF.KoHo, 15));
         text.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         text.setEditable(true);
         text.setPromptText(prompt);
         text.setMaxWidth(325);
         this.getChildren().add(text);
      }

      String getText() {
         return text.getText();
      }
   }
}

class autoSignIn extends HBox {
   private CheckBox cb;

   autoSignIn() {
      this.setAlignment(Pos.CENTER);
      this.setSpacing(10);

      cb = new CheckBox();
      cb.setStyle("-fx-border-color:#A6D69B; -fx-border-radius:3px; -fx-background-color: ");
      cb.setIndeterminate(false);
      this.getChildren().add(cb);

      Label label = new Label("auto sign-in");
      label.setFont(PPFonts.makeFont(FF.KoHo, 20));
      this.getChildren().add(label);
   }

   boolean isAutoSelected() {
      return cb.isSelected();
   }
}

// PantryPal Button component 
// yellow background styling
class PPButton extends Button {
   PPButton(String name) {
      this.setText(name);
      this.setFont(PPFonts.makeFont(FF.Itim, 15));
      this.setPrefSize(100, 38);
      this.setBackground(new Background(new BackgroundFill(Consts.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
      this.setTextFill(Consts.DARK);
      this.setStyle("-fx-border-width: 0"); 
   }
}

// PantryPal Header component
// green background, set height, centered title text styling
class Header extends HBox { 
    Header(String heading) {
        this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        Label titleText = new Label(heading); // Text of the Header
        titleText.setFont(PPFonts.makeFont(FF.Itim, 40));
        //titleText.setFill(Consts.DARK);
        this.getChildren().add(titleText);
    }
}

class RecipeViewSection extends VBox {
   private Text title;

   RecipeViewSection(String name, ArrayList<String> elements) {
      title = new Text(name);
      title.setFont(PPFonts.makeFont(FF.KoHo, 30));
      title.setFill(Consts.DARK);
      this.getChildren().add(title);
      this.setMargin(title, new Insets(0, 0, 0, 40));

      for (int i = 0; i < elements.size(); i++) {
         TextField step = new TextField();
         step.setText(elements.get(i));
         // step.setWrapText(true);
         step.setPrefWidth(640);
         step.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         step.setFont(Consts.F15);
         //steps.setFill(Consts.DARK);
         // this.setMargin(step, new Insets(0,0,0,60));
         step.setEditable(false);
         this.getChildren().add(step);
      }
   }

   public void editable() {
      for (int i = 0; i < this.getChildren().size(); i++) {
         if(this.getChildren().get(i) instanceof TextField){
            ((TextField)this.getChildren().get(i)).setEditable(true);
         }
      }
   }

   public ArrayList<String> save() {
      ArrayList<String> temp = new ArrayList<>();
      for (int i = 0; i < this.getChildren().size(); i++) {
         if(this.getChildren().get(i) instanceof TextField){
            ((TextField)this.getChildren().get(i)).setEditable(false);
            temp.add(((TextField)this.getChildren().get(i)).getText());
         }
      }
      return temp;
   }
}

// abstract class for all Footers
// green background styling
abstract class Footer extends GridPane {
   void setup() {
      this.setPrefSize(Consts.WIDTH, Consts.HF_HEIGHT);
      this.setBackground(new Background(new BackgroundFill(Consts.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(50);
      this.getColumnConstraints().addAll(col, col);
   }
}

// microphone component
// toggles between on microphone and off microphone images
class PPMic extends StackPane implements Subject {
   private final int BUTTON_SIZE = Consts.PIC_WIDTH - 10;

   private boolean isMicOn = false;
   private ImageView imageView = new ImageView();
   private Button button = new Button();

   private Image micOff = new Image(Consts.micURL, Consts.PIC_WIDTH, Consts.PIC_HEIGHT, true, true);
   private Image micOn = new Image(Consts.micRedURL, Consts.PIC_WIDTH, Consts.PIC_HEIGHT, true, true);

   Recording recording;
   private String recordedText = "";

   List<Observer> page = new ArrayList<>();

   public void registerObserver(Observer o) {
      page.add(o);
   }

   public void removeObserver(Observer o) {}

   public void notifyObservers() {
      for (Observer o : page) {
         o.update();
      }
   }

   PPMic() {
      // starts imageView with off microphone 
      imageView.setImage(micOff);
      imageView.setFitWidth(Consts.PIC_WIDTH);
      imageView.setFitHeight(Consts.PIC_HEIGHT);
      this.getChildren().add(imageView);

      // sets button for toggling microphone
      button = new Button();
      button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
      button.setStyle("-fx-background-color: transparent");
      this.getChildren().add(button);

      addListeners();

   }

   private void addListeners () {
      button.setOnAction(e -> {
         isMicOn = !isMicOn;
         if (isMicOn) {
            imageView.setImage(micOn);
            recording = new Recording();
            recording.startRecording();
            // recordedText = "";
         } else {
            imageView.setImage(micOff);
            recording.stopRecording();
            parseInput();
            notifyObservers();
         }
      });
   }

   private void parseInput() {
      WhisperAPI whisper = new WhisperAPI();
      File recordingWAV = recording.getWAV();
      String input = "";
      // integration for WhisperAPI
      try{
         input = whisper.readFile(recordingWAV);
      }
      catch(Exception e){
         System.out.println("Error reading file");
      }
      recordedText = input;
   }  

   public String getRecordedText() {
      return this.recordedText;
   }
}

// delete component
//  
class PPDelete extends StackPane {
   private final int BUTTON_SIZE = Consts.DELETE_WIDTH - 10;

   private Image delete = new Image(Consts.deleteURL, Consts.DELETE_WIDTH, Consts.DELETE_HEIGHT, true, true);
   private ImageView imageView = new ImageView(delete);
   private Button button;

   PPDelete(Recipe recipe) {
      // startes imageview
      imageView.setImage(delete);
      imageView.setFitWidth(Consts.DELETE_WIDTH);
      imageView.setFitHeight(Consts.DELETE_HEIGHT);
      this.getChildren().add(imageView);

      // sets button for recording button behavior
      button = new Button();
      button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
      button.setStyle("-fx-background-color: transparent");
      this.getChildren().add(button);
      addListeners(recipe);
   }

   private void addListeners (Recipe recipe) {
      button.setOnAction(e -> {
         System.out.println("Delete button pressed");
         PantryPal.getRoot().getHome().deleteRecipe(recipe); //removes recipe from database -- THIS IS GOOD
         System.out.print(PantryPal.getRoot().getRecipeList().getSize());
        // ArrayList<Node> recipeListView = PantryPal.getRoot().getHome().getRecipeListView().getChildren();


         //PantryPal.getRoot().getHome().getRecipeListView().getChildren().remove(0);  // removes recipe from homepage

         for(int i = 0; i <  PantryPal.getRoot().getHome().getRecipeListView().getChildren().size(); i++){
            if(PantryPal.getRoot().getHome().getRecipeListView().getChildren().get(i) instanceof RecipeUnitView){
               if (((RecipeUnitView) PantryPal.getRoot().getHome().getRecipeListView().getChildren().get(i)).getRecipe() == recipe) {
                  PantryPal.getRoot().getHome().getRecipeListView().getChildren().remove(i);
                  break;
               }
            }
         }

         PantryPal.getRoot().setPage(Page.HOME);
      });
   }
}
