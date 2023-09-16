package com.example.myapplication.Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ClassVi {
    int maVi,maLoaiVi;
    String tenVi;
    //không có kiểu money nên sài đỡ bigdecimal đi
    BigDecimal tienTrongVi;
    String TenLoaiVi;


    public ClassVi() {
    }

    public ClassVi(int maVi, int maLoaiVi, String tenVi, BigDecimal tienTrongVi) {
        this.maVi = maVi;
        this.maLoaiVi = maLoaiVi;
        this.tenVi = tenVi;
        this.tienTrongVi = tienTrongVi;
    }
    public ClassVi(String tenVi, BigDecimal tienTrongVi, String tenLoaiVi) {
        this.tenVi = tenVi;
        this.tienTrongVi = tienTrongVi;
        TenLoaiVi = tenLoaiVi;
    }

    public String getTenLoaiVi() {
        return TenLoaiVi;
    }

    public void setTenLoaiVi(String tenLoaiVi) {
        TenLoaiVi = tenLoaiVi;
    }

    public String getFormattedTienTrongVi() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(tienTrongVi);
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public int getMaLoaiVi() {
        return maLoaiVi;
    }

    public void setMaLoaiVi(int maLoaiVi) {
        this.maLoaiVi = maLoaiVi;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public BigDecimal getTienTrongVi() {
        return tienTrongVi;
    }

    public void setTienTrongVi(BigDecimal tienTrongVi) {
        this.tienTrongVi = tienTrongVi;
    }
}
