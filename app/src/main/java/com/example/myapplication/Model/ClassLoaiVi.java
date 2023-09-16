package com.example.myapplication.Model;

public class ClassLoaiVi {
    int maLoaiVi;
    String tenLoaiVi;

    public ClassLoaiVi(int maLoaiVi, String tenLoaiVi) {
        this.maLoaiVi = maLoaiVi;
        this.tenLoaiVi = tenLoaiVi;
    }
    public ClassLoaiVi(String tenLoaiVi){
        this.tenLoaiVi = tenLoaiVi;
    }

    public int getMaLoaiVi() {
        return maLoaiVi;
    }

    public void setMaLoaiVi(int maLoaiVi) {
        this.maLoaiVi = maLoaiVi;
    }

    public String getTenLoaiVi() {
        return tenLoaiVi;
    }

    public void setTenLoaiVi(String tenLoaiVi) {
        this.tenLoaiVi = tenLoaiVi;
    }

    public ClassLoaiVi() {
    }
}
