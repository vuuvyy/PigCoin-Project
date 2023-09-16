package com.example.myapplication.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Misa_Database1.db";
    static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Loai_Vi (MaLoaiVi INTEGER PRIMARY KEY AUTOINCREMENT, TenLoaiVi NVARCHAR)");
        db.execSQL("CREATE TABLE Vi (MaVi INTEGER PRIMARY KEY AUTOINCREMENT, MaLoaiVi INTEGER, TenVi NVARCHAR, TienTrongVi REAL, AccountID INTEGER, FOREIGN KEY (MaLoaiVi) REFERENCES Loai_Vi(MaLoaiVi), FOREIGN KEY (AccountID) REFERENCES Account(AccountID))");
        db.execSQL("CREATE TABLE Account (AccountID INTEGER PRIMARY KEY AUTOINCREMENT, UserName NVARCHAR, Password NVARCHAR, TenNguoiDung NVARCHAR, Avatar NVARCHAR)");
        db.execSQL("CREATE TABLE LoaiGiaoDich (MaLoaiGiaoDich INTEGER PRIMARY KEY AUTOINCREMENT, TenLoaiGiaoDich NVARCHAR)");
        db.execSQL("CREATE TABLE LoaiHangMuc (MaLoaiHangMuc INTEGER PRIMARY KEY AUTOINCREMENT, TenLoaiHangMuc NVARCHAR, MaLoaiGiaoDich INTEGER, FOREIGN KEY (MaLoaiGiaoDich) REFERENCES LoaiGiaoDich(MaLoaiGiaoDich))");
        db.execSQL("CREATE TABLE HangMuc (MaHangMuc INTEGER PRIMARY KEY AUTOINCREMENT, LoaiHangMuc INTEGER, TenHangMuc NVARCHAR, FOREIGN KEY (LoaiHangMuc) REFERENCES LoaiHangMuc(MaLoaiHangMuc))");
        db.execSQL("CREATE TABLE GiaoDich (MaGiaoDich INTEGER PRIMARY KEY AUTOINCREMENT, MaHangMuc INTEGER, NgayGiaoDich DATETIME, DienGiai NVARCHAR, Sotien REAL, ViGiaoDich INTEGER, FOREIGN KEY (MaHangMuc) REFERENCES HangMuc(MaHangMuc), FOREIGN KEY (ViGiaoDich) REFERENCES Vi(MaVi))");
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS GiaoDich");
        db.execSQL("DROP TABLE IF EXISTS LoaiGiaoDich");
        db.execSQL("DROP TABLE IF EXISTS HangMuc");
        db.execSQL("DROP TABLE IF EXISTS LoaiHangMuc");
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS Vi");
        db.execSQL("DROP TABLE IF EXISTS Loai_Vi");
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample data for Loai_Vi table
        db.execSQL("INSERT INTO Loai_Vi (TenLoaiVi) VALUES ('Thẻ ngân hàng');");
        db.execSQL("INSERT INTO Loai_Vi (TenLoaiVi) VALUES ('Ví tiền mặt');");
        db.execSQL("INSERT INTO Loai_Vi (TenLoaiVi) VALUES ('Ví điện tử');");

        // Insert sample data for Account table
        db.execSQL("INSERT INTO Account (UserName, Password, TenNguoiDung, Avatar) VALUES ('user1', 'password1', 'Người dùng 1', 'avatar1.png');");
        db.execSQL("INSERT INTO Account (UserName, Password, TenNguoiDung, Avatar) VALUES ('user2', 'password2', 'Người dùng 2', 'avatar2.png');");

        // Insert sample data for Vi table
        db.execSQL("INSERT INTO Vi (MaLoaiVi, TenVi, TienTrongVi, AccountID) VALUES (1, 'Ví 1', 1000000.0 , 1);");
        db.execSQL("INSERT INTO Vi (MaLoaiVi, TenVi, TienTrongVi, AccountID) VALUES (2, 'Ví 2', 2000000.0 , 1);");
        db.execSQL("INSERT INTO Vi (MaLoaiVi, TenVi, TienTrongVi, AccountID) VALUES (1, 'Ví 2', 2000000.0 , 2);");


        // Insert sample data for LoaiGiaoDich table
        db.execSQL("INSERT INTO LoaiGiaoDich (TenLoaiGiaoDich) VALUES ('Chi');");
        db.execSQL("INSERT INTO LoaiGiaoDich (TenLoaiGiaoDich) VALUES ('Thu');");

        // Insert sample data for LoaiHangMuc table
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Ăn uống', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Dịch vụ sinh hoạt', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Đi lại', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Con cái', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Trang phục',1 )");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Sự kiện', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Sức khoẻ', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Nhà cửa', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Hưởng thụ', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Phát triển bản thân', 1)");
        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Vật nuôi', 1)");

        db.execSQL("INSERT INTO LoaiHangMuc (TenLoaiHangMuc, MaLoaiGiaoDich) VALUES ('Thu vào', 2)");

        // Insert sample data for HangMuc table
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Đi chợ/ Siêu thị')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Ăn tiệm')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Cà phê')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Ăn sáng')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Ăn trưa')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (1, 'Ăn tối')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (2, 'Điện')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (2, 'Nước')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (2, 'Wifi')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (2, 'Gas')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (2, 'Điện thoại di động')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (3, 'Xăng')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (3, 'Bảo hiểm')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (3, 'Gửi xe')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (3, 'Thuê xe')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (4, 'Học phí')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (4, 'Sữa')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (4, 'Đồ chơi')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (4, 'Tiền tiêu vặt')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (5, 'Quần áo')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (5, 'Giày dép')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (5, 'Phụ kiện')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (6, 'Thăm hỏi')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (6, 'Biếu tặng')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (7, 'Khám chữa bệnh')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (7, 'Thuốc men')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (7, 'Thể thao')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (8, 'Mua sắm nội thất')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (8, 'Thuê nhà')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (9, 'Du lịch')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (9, 'Vui chơi giả trí')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (9, 'Phim ảnh, ca nhạc')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (10, 'Học hành')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (10, 'Giao lưu')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (10, 'Spa & Massage')");

        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (11, 'Chó')");
        db.execSQL("INSERT INTO HangMuc (LoaiHangMuc, TenHangMuc) VALUES (11, 'Mèo')");

        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Lương')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Thưởng')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Tiền lãi')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Học bổng')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Tiền trợ cấp')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Tiền chúc mừng')");
        db.execSQL("INSERT INTO HangMuc  (LoaiHangMuc, TenHangMuc) VALUES (12,'Kinh doanh riêng')");



        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (1,'2023-07-01','Giao dịch 1',1000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (2,'2023-06-02','Giao dịch 2',2000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-04-04','Giao dịch 4',4000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-01-05','Giao dịch 5',5000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-03-06','Giao dịch 6',6000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-02-07','Giao dịch 7',7000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-07-08','Giao dịch 8',8000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-08-09','Giao dịch 9',9000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-09-10','Giao dịch 10',100.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-10-11','Giao dịch 11',11000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-11-12','Giao dịch 12',12000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-07-13','Giao dịch 13',13000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-12-14','Giao dịch 14',14000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-04-15','Giao dịch 15',15000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-03-16','Giao dịch 16',16000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (1,'2023-02-17','Giao dịch 17',17000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (2,'2023-02-20','Giao dịch 35',35000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-01-18','Giao dịch 18',18000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-12-05','Giao dịch 19',5000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-11-06','Giao dịch 20',6000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-05-06','Giao dịch 21',6000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (1,'2023-05-06','Giao dịch 21',5000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-04-06','Giao dịch 21',100.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-01-19','Giao dịch 22',22000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-02-20','Giao dịch 23',23000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (3,'2023-03-21','Giao dịch 24',24000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (4,'2023-04-22','Giao dịch 25',25000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (4,'2023-05-23','Giao dịch 26',26000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (4,'2023-06-24','Giao dịch 27',27000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-07-25','Giao dịch 28',28000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-08-26','Giao dịch 29',29000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (7,'2023-09-27','Giao dịch 30',30000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-10-28','Giao dịch 31',31000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (6,'2023-11-29','Giao dịch 32',32000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (1,'2023-10-29','Giao dịch 32',10000.0,1);");

        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (13,'2023-07-13','Giao dịch 33',3000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (16,'2023-12-14','Giao dịch 34',10000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (14,'2023-04-15','Giao dịch 35',5000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (12,'2023-02-17','Giao dịch 37',7000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (14,'2023-02-20','Giao dịch 38',15000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (12,'2023-01-18','Giao dịch 39',8000.0,2);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (16,'2023-12-05','Giao dịch 40',3000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (12,'2023-11-06','Giao dịch 41',4000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (15,'2023-05-06','Giao dịch 42',2000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (13,'2023-05-06','Giao dịch 43',9000.0,1);");

        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42,'2023-04-08','Giao dịch 44',3000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43,'2023-02-09','Giao dịch 45',2500.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43,'2023-02-20','Giao dịch 89',23000.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (44,'2023-07-10','Giao dịch 46',1800.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42,'2023-07-11','Giao dịch 47',2200.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43,'2023-03-12','Giao dịch 48',1900.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42,'2023-07-13','Giao dịch 49',2800.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41,'2023-09-14','Giao dịch 50',1700.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41,'2023-02-15','Giao dịch 51',2400.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42,'2023-01-16','Giao dịch 52',3100.0,1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-05-20', 'Giao dịch 53', 25000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-06-15', 'Giao dịch 54', 38000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-07-10', 'Giao dịch 55', 20000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-04-30', 'Giao dịch 56', 30000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-09-12', 'Giao dịch 57', 40000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-08-05', 'Giao dịch 58', 15000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-10-18', 'Giao dịch 59', 45000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-11-23', 'Giao dịch 60', 30000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-03-07', 'Giao dịch 61', 50000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-12-31', 'Giao dịch 62', 20000.0, 1);");

        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-04-15', 'Giao dịch 63', 28000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-05-28', 'Giao dịch 64', 32000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-06-07', 'Giao dịch 65', 22000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-07-19', 'Giao dịch 66', 36000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-08-03', 'Giao dịch 67', 42000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-09-21', 'Giao dịch 68', 48000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-10-09', 'Giao dịch 69', 18000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-11-13', 'Giao dịch 70', 49000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-03-05', 'Giao dịch 72', 15000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-05-10', 'Giao dịch 73', 42000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-07-11', 'Giao dịch 74', 30000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-09-23', 'Giao dịch 75', 28000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-11-19', 'Giao dịch 76', 44000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-03-02', 'Giao dịch 77', 42000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-04-30', 'Giao dịch 78', 50000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (42, '2023-06-27', 'Giao dịch 79', 28000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (43, '2023-08-04', 'Giao dịch 80', 33000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (40, '2023-09-01', 'Giao dịch 81', 47000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (41, '2023-10-18', 'Giao dịch 82', 25000.0, 1);");
        db.execSQL("INSERT INTO GiaoDich (MaHangMuc, NgayGiaoDich, DienGiai, Sotien, ViGiaoDich) VALUES (5,'2023-04-02','Giao dịch 21',29400.0,1);");



    }

}

