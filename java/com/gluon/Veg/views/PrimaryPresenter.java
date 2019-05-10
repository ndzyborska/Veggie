package com.gluon.Veg.views;

import com.gluon.Veg.Veggie;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PrimaryPresenter {

    @FXML
    private View primary;

    @FXML
    VBox myView;

    @FXML
    HBox cheap, showstopper, quick, breakfast, deserts, salads;

    private ArrayList<HBox> categories = new ArrayList<>();
    private ArrayList<Recipe> recipes;
    private final int buttonHeight = 96;
    private final int buttonWidth = 106;


    public void initialize() {
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Primary");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        System.out.println("Search")));
            }
        });
        try {
            initRecipes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initRecipes() throws IOException, InvalidFormatException {

        File file = new File("src/main/resources/RecipeList/Recipes.xlsx");
        String path = file.getAbsolutePath();
        ExcelReader list = new ExcelReader(path);

        int i = 10;
        int sheetNo = 0;

            categories.add(cheap);
            categories.add(showstopper);
            categories.add(quick);
            categories.add(breakfast);
            categories.add(deserts);
            categories.add(salads);

            for (HBox h : categories) {
                for (int k = 0; k < list.getAmount(sheetNo); k++) {  // Can be presented better....
                    String buttonId = Integer.toString(i) + Integer.toString(k + 1);
                    Button button = new Button(list.getName(buttonId, sheetNo));
                    button.setId(buttonId);
                    button.setStyle("-fx-background-image: url('" + list.getImage(buttonId, sheetNo) + "')");
                    button.setPrefHeight(buttonHeight);
                    button.setPrefWidth(buttonWidth);
                    System.out.println("HERE: "+button.getId());

                  button.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            String s = ((Button)event.getSource()).getId();
                            System.out.println("HERES: "+s);
                            try {
                                changeScene(s);
                            } catch (IOException  ex) {
                                ex.printStackTrace();
                            }

                        }
                    });
                  /*  button.setOnMouseReleased(event -> {
                        try {
                            changeScene();
                        } catch (IOException  ex) {
                           ex.printStackTrace();
                        }
                    });*/
                    //Creating the mouse event handler

                    h.getChildren().add(button);
                    h.getParent().prefWidth(h.getPrefWidth());
                }

                sheetNo++;
                i += 10;

            }
            list.close();
        }

    void changeScene(String buttonId) throws IOException {

        System.out.println("HERE2: "+buttonId);

        IngredientsControl display = new IngredientsControl(buttonId);
       primary.getChildren().setAll(display.getView());



    }
}
