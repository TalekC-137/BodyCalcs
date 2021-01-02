package com.example.bmi;

public class BmrModel {


    private int id;
    private String data;
    private String bmr;
    private String needed;


    public BmrModel(int id, String data, String bmr, String needed) {
        this.id = id;
        this.data = data;
        this.bmr = bmr;
        this.needed = needed;

    }

    public BmrModel(){

    }
    @Override
    public String toString() {
        return
               data + ":  " +
                       bmr + "  " +
                       needed;


    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBmr() {
        return bmr;
    }

    public void setBmr(String bmr) {
        this.bmr = bmr;
    }

    public String getNeeded() {
        return needed;
    }

    public void setNeeded(String needed) {
        this.needed = needed;
    }
}

