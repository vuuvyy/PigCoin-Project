package com.example.myapplication.Adapter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class CustomBarValueFormatter extends ValueFormatter {
    private DecimalFormat format;

    public CustomBarValueFormatter() {
        format = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value) {
        return format.format(value) + " %";
    }
}
