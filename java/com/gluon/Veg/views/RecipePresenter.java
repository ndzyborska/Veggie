package com.gluon.Veg.views;

import com.gluon.Veg.Veggie;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.inject.Inject;

import static com.gluon.Veg.Veggie.PRIMARY_VIEW;

public class RecipePresenter {

    @FXML
    private View recipe;

    @FXML
    private ScrollPane Scroll;

    @FXML
    private AnchorPane myAnchor;

    @FXML
    private ImageView image;

    @FXML
    private VBox amount;

    @FXML
    private VBox ingredients;


    @FXML
    private VBox method;

    @FXML
    private VBox nutrients;

    @Inject
    ArrayList<recipeButtons> myRecipes;

    @Inject
    recipeButtons buttons;



    ExcelReader reader;
    ArrayList<Ingredients> i;
    String buttonId;





    public void initialize()  {
        recipe.setShowTransitionFactory(BounceInRightTransition::new);
        recipe.getStylesheets().add(getClass().getResource("recipe.css").toExternalForm());


        recipe.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {

                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Secondary");
                buttonId = (buttons.getText());
                appBar.getActionItems().add(MaterialDesignIcon.ADD.button(e ->
                        add()));

            }
        });
        buttonId = (buttons.getText());
        System.out.println("Recipe Pres: " + buttonId);

        try {
            initRecipe();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void add(){
        buttons.setIngredients(i);
        myRecipes.add(buttons);
       if(MobileApplication.getInstance().removeViewFactory(buttonId)){
           System.out.println("Removed");
       }
       buttons.setTrue();
       MobileApplication.getInstance().switchView(PRIMARY_VIEW);
    }

    @FXML
    void initRecipe()throws IOException, InvalidFormatException {

        File file = new File("src/main/resources/RecipeList/ingredients.xlsx");
        String path = file.getAbsolutePath();
        reader = new ExcelReader(path);

        ingredients();

        reader.close();

    }
    @FXML
    void goRight(SwipeEvent event) {

    }

    @FXML
    void ingredients() {

        int count = 1;

        i = reader.getFood(buttonId);

        for (Ingredients food : i) {
            Label amounts = new Label(food.getFood());
            Label ingredientList = new Label(food.getAmount() + " " + food.getMeasurement());
            ingredientList.setId("ingredientList");

            if (count%2==0) {
                ingredientList.setStyle("-fx-background-color: lightpink;");
                amounts.setStyle("-fx-background-color: lightpink;");
            }
            System.out.println("image: "+ buttons.getImage());

            image.setImage(new Image(buttons.getImage()));

            ingredientList.setPrefHeight(100);
            ingredientList.setPrefWidth(175);
            amounts.setPrefHeight(100);
            amounts.setPrefWidth(175);
            amounts.setWrapText(true);
            amounts.setId("amounts");
            ingredientList.setWrapText(true);
            ingredients.getChildren().add(new HBox(amounts, ingredientList));
            count++;
        }


    }


  /*  @FXML
    void setUp(String button) {


        this.currentButton = button;

        Label l = new Label("Hello");
        recipe.getChildren().add(l);



        System.out.println("Button reached " + button);

    }*/
}

