package com.example.a20075085.ifitness;

/**
 * Created by 20075085 on 16/03/2018.
 */

public class DailyTotal {
    private static DailyTotal sDailyTotal;
    private double mCalories;
    private double mFat;
    private double mCarbs;
    private double mProtein;

    public DailyTotal()
    {
        mCalories = 0;
        mFat = 0;
        mCarbs = 0;
        mProtein = 0;
    }

    public DailyTotal(double calories, double fat, double carbs, double protein)
    {
        mCalories=calories;
        mFat=fat;
        mCarbs=carbs;
        mProtein=protein;
    }

    //copy constructor
    public DailyTotal(DailyTotal dt)
    {
        mCalories = dt.getCalories();
        mFat = dt.getFat();
        mCarbs = dt.getCarbs();
        mProtein = dt.getProtein();
    }

    public void addCalories(double amount)
    {
        mCalories += amount;
    }

    public void addFat(double amount)
    {
        mFat += amount;
    }

    public void addCarbs(double amount)
    {
        mCarbs += amount;
    }

    public void addProtein(double amount)
    {
        mProtein += amount;
    }

    public double getCalories() {
        return mCalories;
    }

    public void setCalories(double calories) {
        mCalories = calories;
    }

    public double getFat() {
        return mFat;
    }

    public void setFat(double fat) {
        mFat = fat;
    }

    public double getCarbs() {
        return mCarbs;
    }

    public void setCarbs(double carbs) {
        mCarbs = carbs;
    }

    public double getProtein() {
        return mProtein;
    }

    public void setProtein(double protein) {
        mProtein = protein;
    }

    public void clear()
    {
        // setting data to a default of 0 //
        mCalories =0;
        mFat = 0;
        mCarbs = 0;
        mProtein = 0;
    }
}
