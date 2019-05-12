package com.gluon.Veg.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SecondaryPresenter {

    @FXML
    private View secondary;

    @FXML
    VBox shoppingList;

    ArrayList<Ingredients> list;

    @Inject
    ArrayList<recipeButtons> myRecipes;

    @Inject
    recipeButtons buttons;


    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(secondary);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Secondary");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });


            initRecipes();



    }

    void initRecipes()  {

        initShoppingList();

    }

    void initShoppingList() {

        list = new ArrayList<>();

        for (recipeButtons recipe: myRecipes) {
           for (Ingredients allRecipes: recipe.getIngredients()) {
               for (Ingredients cumulative : list) {
                   if (cumulative.getFood() == allRecipes.getFood()) {
                       cumulative.setAmount(addStrings(cumulative.getAmount(), allRecipes.getAmount()));
                   }
               }

           }

        }
        printShoppingList();


    }
    void printShoppingList() {
        int count =1;

        for (Ingredients food : list) {
            Label amounts = new Label(food.getFood());
            Label ingredientList = new Label(food.getAmount() + " " + food.getMeasurement());
            ingredientList.setId("ingredientList");

            if (count%2==0) {
                ingredientList.setStyle("-fx-background-color: lightpink;");
                amounts.setStyle("-fx-background-color: lightpink;");
            }
            System.out.println("SECONDARY!: "+ buttons.getImage());


            ingredientList.setPrefHeight(100);
            ingredientList.setPrefWidth(175);
            amounts.setPrefHeight(100);
            amounts.setPrefWidth(175);
            amounts.setWrapText(true);
            amounts.setId("amounts");
            ingredientList.setWrapText(true);
            shoppingList.getChildren().add(new HBox(amounts, ingredientList));
            count++;
        }
    }

    String addStrings(String a, String b) {
        double d = Double.parseDouble(a) + Double.parseDouble(b);

        return Double.toString(d);

    }
}
