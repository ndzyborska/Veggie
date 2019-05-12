package com.gluon.Veg.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class recipeButtons {

    private final StringProperty buttonId = new SimpleStringProperty();
    private ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
    private String name;
    private String imageURL;
    private boolean added =false;

    public final void setText(String value) { buttonId.set(value); }
    public final String getText() { return buttonId.get(); }
    public final StringProperty textProperty() { return buttonId; }
    public final String getName() { return name; }
    public final String getImage() { return imageURL; }
    public final void setName(String value) { name = value; }
    public final void setImage(String value) { imageURL = value; }
    public final ArrayList<Ingredients> getIngredients() {return ingredients;}
    public final void setIngredients(ArrayList<Ingredients> i) {ingredients = i;}
    public final void setTrue() {added = true;}
    public final void setFalse() {added = false;}
    public boolean isTrue() {return added;}

}
