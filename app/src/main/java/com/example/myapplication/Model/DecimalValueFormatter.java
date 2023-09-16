package com.example.myapplication.Model;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class DecimalValueFormatter extends ValueFormatter {
    private final DecimalFormat decimalFormat;

    public DecimalValueFormatter() {
        decimalFormat = new DecimalFormat("###.##");
    }

    @Override
    public String getFormattedValue(float value) {
        return decimalFormat.format(value) + "%";
    }
}

