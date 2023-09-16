package com.example.myapplication.Model;
public class ClassLoaiGiaoDich {
    private int MaLoaiGiaoDich;
    private String TenLoaiGiaoDich;

    public ClassLoaiGiaoDich(int maLoaiGiaoDich, String tenLoaiGiaoDich) {
        MaLoaiGiaoDich = maLoaiGiaoDich;
        TenLoaiGiaoDich = tenLoaiGiaoDich;
    }

    public int getMaLoaiGiaoDich() {
        return MaLoaiGiaoDich;
    }

    public void setMaLoaiGiaoDich(int maLoaiGiaoDich) {
        MaLoaiGiaoDich = maLoaiGiaoDich;
    }

    public String getTenLoaiGiaoDich() {
        return TenLoaiGiaoDich;
    }

    public void setTenLoaiGiaoDich(String tenLoaiGiaoDich) {
        TenLoaiGiaoDich = tenLoaiGiaoDich;
    }
}
