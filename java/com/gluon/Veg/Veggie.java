package com.gluon.Veg;

import com.gluon.Veg.views.PrimaryView;
import com.gluon.Veg.views.RecipeView;
import com.gluon.Veg.views.SecondaryView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Veggie extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    public static final String SECONDARY_VIEW = "Secondary View";
  //  public static final String MY_RECIPES= "My Recipes";
    public static String image = null;


    
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> (View) new PrimaryView().getView());
        addViewFactory(SECONDARY_VIEW, () -> (View) new SecondaryView().getView());
     //   addViewFactory(MY_RECIPES, () -> (View)(new RecipeView().getView()));

        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Veggie.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Veggie.class.getResourceAsStream("/icon.png")));

    }



}
