package com.example.myapplication.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClassGiaoDich {
    private int MaGiaoDich;
    private int MaHangMuc;
    private Date NgayGiaoDich;
    private String DienGiai;
    private BigDecimal Sotien;
    private int ViGiaoDich;
    BigDecimal tienThu;
    BigDecimal tienChi;
    BigDecimal tongTien;
    int thang;

    String tenHangMuc, ngay;
    private List<ClassHangMuc> giaoDichList;
    private List<ChildItem> childItemList;

    List<ClassGiaoDich> GiaoDichList;

    public ClassGiaoDich() {

    }
    public ClassGiaoDich(int maHangMuc, Date ngayGiaoDich, String dienGiai, BigDecimal sotien, int viGiaoDich) {
        MaHangMuc = maHangMuc;
        NgayGiaoDich = ngayGiaoDich;
        DienGiai = dienGiai;
        Sotien = sotien;
        ViGiaoDich = viGiaoDich;
    }
    public ClassGiaoDich(BigDecimal sotien, String tenHangMuc) {
        Sotien = sotien;
        this.tenHangMuc = tenHangMuc;
    }

    public ClassGiaoDich(Date ngayGiaoDich) {
        NgayGiaoDich = ngayGiaoDich;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public ClassGiaoDich(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public ClassGiaoDich(Date ngayGiaoDich, String dienGiai, BigDecimal sotien) {
        NgayGiaoDich = ngayGiaoDich;
        DienGiai = dienGiai;
        Sotien = sotien;
    }

    public ClassGiaoDich(int maGiaoDich, int maHangMuc, Date ngayGiaoDich, String dienGiai, BigDecimal sotien, int viGiaoDich, BigDecimal tienThu, BigDecimal tienChi, BigDecimal tongTien) {
        MaGiaoDich = maGiaoDich;
        MaHangMuc = maHangMuc;
        NgayGiaoDich = ngayGiaoDich;
        DienGiai = dienGiai;
        Sotien = sotien;
        ViGiaoDich = viGiaoDich;
        this.tienThu = tienThu;
        this.tienChi = tienChi;
        this.tongTien = tongTien;
    }
    public ClassGiaoDich(BigDecimal tienThu, BigDecimal tienChi, BigDecimal tongTien, int thang) {
        this.tienThu = tienThu;
        this.tienChi = tienChi;
        this.tongTien = tongTien;
        this.thang = thang;
    }
    public BigDecimal calculateTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ChildItem childItem : childItemList) {
            totalAmount = totalAmount.add(childItem.getAmount());
        }

        return totalAmount;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getMaGiaoDich() {
        return MaGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        MaGiaoDich = maGiaoDich;
    }

    public int getMaHangMuc() {
        return MaHangMuc;
    }

    public void setMaHangMuc(int maHangMuc) {
        MaHangMuc = maHangMuc;
    }

    public Date getNgayGiaoDich() {
        return  NgayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        NgayGiaoDich = ngayGiaoDich;
    }

    public String getDienGiai() {
        return DienGiai;
    }

    public void setDienGiai(String dienGiai) {
        DienGiai = dienGiai;
    }

    public BigDecimal getSotien() {
        return Sotien;
    }

    public void setSotien(BigDecimal sotien) {
        Sotien = sotien;
    }

    public int getViGiaoDich() {
        return ViGiaoDich;
    }

    public void setViGiaoDich(int viGiaoDich) {
        ViGiaoDich = viGiaoDich;
    }

    public BigDecimal getTienThu() {
        return tienThu;
    }

    public void setTienThu(BigDecimal tienThu) {
        this.tienThu = tienThu;
    }

    public BigDecimal getTienChi() {
        return tienChi;
    }

    public void setTienChi(BigDecimal tienChi) {
        this.tienChi = tienChi;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }
    public void setChildItemList(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }

    public List<ChildItem> getChildItemList() {
        return childItemList;
    }
    public List<ClassHangMuc> getHangMucList() {
        return giaoDichList;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}
