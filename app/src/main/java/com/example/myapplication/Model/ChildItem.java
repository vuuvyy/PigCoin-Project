package com.example.myapplication.Model;

import java.math.BigDecimal;
import java.util.Date;

public class ChildItem {
    private String name;
    Date ngay;
    private int imageResId;
    private BigDecimal amount;


    public ChildItem(String name, int imageResId, BigDecimal amount) {
        this.name = name;
        this.imageResId = imageResId;
        this.amount = amount;
    }

    public ChildItem(Date ngay) {
        this.ngay = ngay;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public ChildItem(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
