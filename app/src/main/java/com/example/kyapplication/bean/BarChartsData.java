package com.example.kyapplication.bean;

public class BarChartsData {

    public BarChartsData(String name,int number)
    {
        this.name = name;
        this.number = number;
    }

    public BarChartsData()
    {

    }
    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
