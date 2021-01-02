package com.example.bmi;

public class BmiModel {


    private int id;
    private String data;
    private String bmi;







    public BmiModel(int id, String data, String bmi) {
        this.id = id;
        this.data = data;
        this.bmi = bmi;

    }

    public BmiModel(){

    }
    @Override
    public String toString() {
        return

             data + ":  " +
                       bmi;

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

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
}
