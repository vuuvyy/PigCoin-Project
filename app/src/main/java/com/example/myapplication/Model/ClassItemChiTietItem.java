package com.example.myapplication.Model;

import java.math.BigDecimal;

public class ClassItemChiTietItem {
    private BigDecimal soTien;
    private float percent;
    private String tenLoaiHangMuc;
    private String tenHangMuc;
    private int idAnh;

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public ClassItemChiTietItem() {
    }

    public ClassItemChiTietItem(BigDecimal soTien, float percent, String tenLoaiHangMuc, int idAnh) {
        this.soTien = soTien;
        this.percent = percent;
        this.tenLoaiHangMuc = tenLoaiHangMuc;
        this.idAnh = idAnh;
    }

    public ClassItemChiTietItem(BigDecimal soTien, String tenLoaiHangMuc, int idAnh) {
        this.soTien = soTien;
        this.tenLoaiHangMuc = tenLoaiHangMuc;
        this.idAnh = idAnh;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getTenLoaiHangMuc() {
        return tenLoaiHangMuc;
    }

    public void setTenLoaiHangMuc(String tenLoaiHangMuc) {
        this.tenLoaiHangMuc = tenLoaiHangMuc;
    }

    public int getIdAnh() {
        return idAnh;
    }

    public void setIdAnh(int idAnh) {
        this.idAnh = idAnh;
    }
}
