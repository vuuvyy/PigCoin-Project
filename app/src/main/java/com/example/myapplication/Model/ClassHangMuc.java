package com.example.myapplication.Model;

import java.math.BigDecimal;
import java.util.List;

public class ClassHangMuc {
    private int MaHangMuc;
    private int LoaiHangMuc;
    private String TenHangMuc;
    private BigDecimal soTien;
List<ClassGiaoDich> GiaoDichList;
    private boolean isExpanded;
    int idImage;
    public ClassHangMuc(int maHangMuc, int loaiHangMuc, String tenHangMuc) {
        MaHangMuc = maHangMuc;
        LoaiHangMuc = loaiHangMuc;
        TenHangMuc = tenHangMuc;
    }

    public ClassHangMuc(String tenHangMuc) {
        TenHangMuc = tenHangMuc;
    }

    public ClassHangMuc(String tenHangMuc, BigDecimal soTien) {
        TenHangMuc = tenHangMuc;
        this.soTien = soTien;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getMaHangMuc() {
        return MaHangMuc;
    }

    public void setMaHangMuc(int maHangMuc) {
        MaHangMuc = maHangMuc;
    }

    public int getLoaiHangMuc() {
        return LoaiHangMuc;
    }

    public void setLoaiHangMuc(int loaiHangMuc) {
        LoaiHangMuc = loaiHangMuc;
    }

    public String getTenHangMuc() {
        return TenHangMuc;
    }


    public void setTenHangMuc(String tenHangMuc) {
        TenHangMuc = tenHangMuc;
    }


    public void setGiaoDichList(List<ClassGiaoDich> GiaoDichList) {
        this.GiaoDichList = GiaoDichList;
    }
    public boolean isExpanded() {
        return isExpanded;
    }

    // Add a method to update the isExpanded state
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    public List<ClassGiaoDich> getGiaoDichList() {
        return GiaoDichList;
    }
}
