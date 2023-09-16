package com.example.myapplication.Model;

import java.util.List;

public class ParentItem {
    private String date;
    private double totalAmount;

    public ParentItem(String date, double totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public ParentItem() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
