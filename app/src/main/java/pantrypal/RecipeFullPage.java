package pantrypal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class RecipeFullPage extends Display {
   private ScrollPane scroller;
   private ImageHeader header;
   private RecipeFullView recipeFullView;

   RecipeFullPage (Recipe recipe) {
      header = new ImageHeader(recipe);
      recipeFullView = new RecipeFullView(recipe);
      footer = new RecipeFullFooter(recipe);

      scroller = new ScrollPane(recipeFullView); //fill in with class for recipe display
      scroller.setBackground(new Background(new BackgroundFill(Consts.LIGHT, CornerRadii.EMPTY, Insets.EMPTY)));
      scroller.setFitToHeight(true);
      scroller.setFitToWidth(true);

      this.setTop(header);
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

   class RecipeFullFooter extends Footer{
      private Button backButton;
      private Button imageButton;
      private Button editButton;
      private Recipe recipe;

      private boolean isEditing = false;

      RecipeFullFooter(Recipe recipe){
         this.recipe = recipe;

         setup();
         this.setAlignment(Pos.CENTER_LEFT);

         backButton = new PPButton("Back");
         this.add(backButton, 0, 0);
         this.setMargin(backButton, new Insets(20, 20, 20, 20));  
         this.setHalignment(backButton, HPos.LEFT);

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
         this.setMargin(rightButtons, new Insets(20, 20, 20, 0));  

         addListeners();
      }  

      private void addListeners () {
         backButton.setOnAction(e -> {
            PantryPal.getRoot().setPage(Page.HOME);
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
         // PantryPal.getRoot().setPage(Page.HOME);
         // int i = 1;
         // ArrayList<String> newIngredients = new ArrayList<>();
         // while (recipeFullView.getChildren().get(i) instanceof TextField) {
         //    newIngredients.add(((TextField)recipeFullView.getChildren().get(i)).getText());
         //    ((TextField)recipeFullView.getChildren().get(i)).setEditable(false);
         //    ++i;
         // }
         // ++i;
         // ArrayList<String> newInstructions = new ArrayList<>();
         // while (i < recipeFullView.getChildren().size()) {
         //    newInstructions.add(((TextField)recipeFullView.getChildren().get(i)).getText());
         //    ((TextField)recipeFullView.getChildren().get(i)).setEditable(false);
         //    ++i;
         // }  
         // recipe.setIngredients(newIngredients);
         // recipe.setSteps(newInstructions);
         recipe = recipeFullView.notEditable();
      }
   }


}