package com.gluon.Veg.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class IngredientsControl {

    private String buttonId;


    @FXML
    private View recipes;

    IngredientsControl(String buttonId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipes.fxml"));
        loader.setController(this);
        this.buttonId = buttonId;

    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    View getView() {
        return recipes;
    }




    }

