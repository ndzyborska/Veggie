package com.gluon.Veg.views;

import java.util.ArrayList;
import java.util.Map;

public class Ingredients {

    private final String food;
    private final String measurement;
    private  String amount;



    Ingredients(String food, String measurement, String amount) {
        this.food=food;
        this.measurement = measurement;
        this.amount = amount;

    }
    public String getFood() {return this.food; }
    public String getMeasurement() {return this.measurement; }
    public String getAmount() {return this.amount; }
    public void setAmount(String amount) {this.amount = amount;}

}
