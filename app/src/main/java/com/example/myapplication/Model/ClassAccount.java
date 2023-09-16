package com.example.myapplication.Model;

public class ClassAccount {
    int accountID;
    String userName;
    String email;
    String password;
    String tenNguoiDung;
    int avatar;
    int maVi;

    public ClassAccount(int accountID, String userName, String email, String password, String tenNguoiDung, int avatar, int maVi) {
        this.accountID = accountID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.tenNguoiDung = tenNguoiDung;
        this.avatar = avatar;
        this.maVi = maVi;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public ClassAccount() {
    }
}
