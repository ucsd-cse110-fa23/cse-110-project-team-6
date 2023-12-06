package pantrypal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;

class RecipeFullPage extends Display {
   private ScrollPane scroller;
   private RecipeFullView recipeFullView;

   RecipeFullPage (Recipe recipe) {
      // Header header = new header(recipe.getName());

      // ImageView imageView = new ImageView();
      // Image image = new Image(Consts.logoURL, Consts.WIDTH, 350, true, true); //TODO ADD IMAGE
      // imageView.setImage(image);
      // imageView.setFitWidth(Consts.PIC_WIDTH);
      // imageView.setFitHeight(Consts.PIC_HEIGHT);
      
      recipeFullView = new RecipeFullView(recipe);
      scroller = new ScrollPane(recipeFullView); //fill in with class for recipe display
      scroller.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      Footer footer = new RecipeFullFooter(recipeFullView, recipe);
      System.out.println("recipe full view footer created");

      // this.setTop(header);
      this.setCenter(scroller);
      this.setBottom(footer);
   }

   class RecipeFullView extends VBox{
      private TextField name;
      private RecipeViewSection ingredients;
      private RecipeViewSection instructions;
      private Recipe recipe;

      RecipeFullView(Recipe recipe){
         this.recipe = recipe;

         this.setSpacing(20);
         this.setPadding(new Insets(40, 0, 0, 40));
         this.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));

         name = new TextField(recipe.getName());
         name.setFont(PPFonts.makeFont(FF.Itim, 40));
         name.setPadding(new Insets(0, 5, 0, 5));
         name.setStyle("-fx-background-color: transparent; -fx-border-width: 0");
         this.getChildren().add(name);

         //"INGREDIENTS HEADER"
         ingredients = new RecipeViewSection("Ingredients", recipe.getIngredients());
         this.getChildren().add(ingredients);

         //"INSTRUCTIONS HEADER"
         instructions = new RecipeViewSection("Instructions", recipe.getSteps());
         this.getChildren().add(instructions);
      }

      public void editable() {
         name.setEditable(true);
         ingredients.editable();
         instructions.editable();
      }

      public Recipe notEditable() {
         name.setEditable(false);
         return new Recipe(name.getText(), ingredients.save(), instructions.save(), recipe.getTag());
      }
   }

   class RecipeFullFooter extends Footer {
      private Button backButton;
      private Button shareButton;
      private Button imageButton;
      private Button editButton;
      private Recipe recipe;

      private boolean isEditing = false;

      RecipeFullFooter(RecipeFullView recipeFullView, Recipe recipe){
         this.recipe = recipe;

         setup();
         this.setAlignment(Pos.CENTER_LEFT);

         backButton = new PPButton("Back");

         shareButton = new PPButton("Share");
         System.out.println("SHARE BUTTON CREATED");

         HBox leftButtons = new HBox(backButton, shareButton);
         leftButtons.setAlignment(Pos.CENTER_LEFT);
         leftButtons.setSpacing(20);
         this.add(leftButtons, 0, 0);
         this.setHalignment(leftButtons, HPos.LEFT);
         this.setMargin(leftButtons, new Insets(20, 20, 20, 20));  

         imageButton = new PPButton("View Image");
         // this.add(regenButton, 1, 0);
         // this.setMargin(regenButton, new Insets(20, 20, 20, 0));  

         editButton = new PPButton("Edit");
         // this.add(saveButton, 1, 0);
         // this.setMargin(editButton, new Insets(20, 20, 20, 10));  

         HBox rightButtons = new HBox(imageButton, editButton);
         rightButtons.setAlignment(Pos.CENTER_RIGHT);
         rightButtons.setSpacing(20);
         this.add(rightButtons, 1, 0);
         this.setHalignment(rightButtons, HPos.RIGHT);
         this.setMargin(rightButtons, new Insets(20, 20, 20, 20));  

         addListeners();
      }  

      private void addListeners () {
         backButton.setOnAction(e -> {
            PantryPal.getRoot().setPage(Page.HOME);
         });
         shareButton.setOnAction(e -> {
            // TODO SHARE BUTTON FUNCTIONALITY
            String urlImage = recipe.getURL();
            String username = PantryPal.getRoot().getRecipeList().getUsername();
            String title = recipe.getName();

            String urlString =  "http://localhost:8100/Recipe?username=" + username + "&title=" + title + "&image=" + urlImage;
            
            // get the image of the recipe
            System.out.println(urlString);
            System.out.println("Share Button Pressed");
         });
         imageButton.setOnAction(e -> {
            System.out.println("Image Button Pressed");
      
            RecipeImage ri = new RecipeImage(recipe);
            ri.renderImage();;
         });
         editButton.setOnAction(e -> {
            if (!isEditing) {
               edit();
               editButton.setText("Save");
            } else {
               save();
               editButton.setText("Edit");
            }

            isEditing = !isEditing;

            //allowing TextField children to be editable
            
         });
      }

      private void edit() {
         System.out.println("Edit button pressed");
         recipeFullView.editable();
      }

      private void save() {
         //Saving new TextField strings to the recipe object in local database
         System.out.println("Save Button Pressed");
         recipe = recipeFullView.notEditable();
      }
   }
}