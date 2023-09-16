package com.example.myapplication.Model;

import java.util.List;

public class ClassLoaiHangMuc {
    private int MaLoaiHangMuc;
    private String TenLoaiHangMuc;
    private int MaLoaiGiaoDich;
    private List<ClassHangMuc> hangMucList;

    public ClassLoaiHangMuc(int maLoaiHangMuc, String tenLoaiHangMuc, int maLoaiGiaoDich) {
        MaLoaiHangMuc = maLoaiHangMuc;
        TenLoaiHangMuc = tenLoaiHangMuc;
        MaLoaiGiaoDich = maLoaiGiaoDich;
    }
    public ClassLoaiHangMuc(String tenLoaiHangMuc) {
        this.TenLoaiHangMuc = tenLoaiHangMuc;
    }

    public int getMaLoaiHangMuc() {
        return MaLoaiHangMuc;
    }

    public void setMaLoaiHangMuc(int maLoaiHangMuc) {
        MaLoaiHangMuc = maLoaiHangMuc;
    }
    public void setHangMucList(List<ClassHangMuc> hangMucList) {
        this.hangMucList = hangMucList;
    }
    public String getTenLoaiHangMuc() {
        return TenLoaiHangMuc;
    }

    public void setTenLoaiHangMuc(String tenLoaiHangMuc) {
        TenLoaiHangMuc = tenLoaiHangMuc;
    }

    public int getMaLoaiGiaoDich() {
        return MaLoaiGiaoDich;
    }
    public List<ClassHangMuc> getHangMucList() {
        return hangMucList;
    }
    public void setMaLoaiGiaoDich(int maLoaiGiaoDich) {
        MaLoaiGiaoDich = maLoaiGiaoDich;
    }
}

