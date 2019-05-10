package com.gluon.Veg.views;

public class Quantity {

    Measurment m;
    int amount;


    Quantity(Measurment m, int amount) {
        this.amount = amount;
        this.m = m;
    }

    int getAmount() {
        return this.amount;
    }
    Measurment getMeasurment() {
        return this.m;
    }

}
