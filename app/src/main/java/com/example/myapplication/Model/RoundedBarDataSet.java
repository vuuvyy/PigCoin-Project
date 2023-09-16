package com.example.myapplication.Model;

import com.example.myapplication.Adapter.CustomBarValueFormatter;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.BarChartRenderer;

import java.util.List;

public class RoundedBarDataSet extends BarDataSet {

    private float cornerRadius;

    public RoundedBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void setBarBorderWidth(float width) {
        super.setBarBorderWidth(width);
    }

    @Override
    public float getBarBorderWidth() {
        return super.getBarBorderWidth();
    }

    @Override
    public int getHighLightAlpha() {
        return super.getHighLightAlpha();
    }


    public float getBarBorderRadius() {
        return cornerRadius;
    }

    @Override
    public ValueFormatter getValueFormatter() {
        return new CustomBarValueFormatter();
    }



}

