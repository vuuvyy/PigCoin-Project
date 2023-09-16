package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.DecimalValueFormatter;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    SQLiteDatabase database;
    TextView txtTongSoTienTrangHome, txtChaoTenTrangHome, txt_tongThu_tranghome,
            txt_tongChi_tranghome,txt_tongDu_tranghome,
            txtAnUong, txtDiChuyen, txtTrangPhuc, txtSinhHoat, txtKhac;
    BarChart barChart1 ;
    PieChart pieChart_trangHome;
    String user;
    LinearLayout linearBarChart,linearPie,linearPie2;
    FragmentManager fragmentManager;
    List<String> listTenLoaiHangMuc = new ArrayList<>();
    TextView txt_month_trangHome;
    ImageButton ic_imgbtn_eye, ic_imgbtn_refresh, ic_imgbtn_expand;
    int  monthIndex=-1;


    public FragmentHome(String user) {
        this.user = user;
        // Required empty public constructor
    }

    public FragmentHome() {
    }

    public static FragmentHome newInstance(String user) {
        return new FragmentHome(user);
    }
    public static FragmentHome newInstance(int position) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString("Thang", String.valueOf(position));

        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();

        AddControl(view);
        AddEvent(view);
        return view;

    }

    private void AddEvent(View view){

        //Tên người dùng trang home
        String TenNguoiDung = GetTenNguoiDungTrangHome(user);
        txtChaoTenTrangHome.setText("Chào " + TenNguoiDung);

        setupImgBtn_ic_eyeClick(view);
        // icon refresh trang home
        setupImgBtn_ic_refreshClick(view);
        // icon expand tháng trang home
        setIc_imgbtn_expand(view);

        //set tháng theo lịch hiện tại
        setDefaultMonth();


        //set click cho txt tháng
        txt_month_trangHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTxt_month_trangHome();
            }
        });
        linearBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentChart targetFragment = new FragmentChart(user);
                fragmentTransaction.replace(R.id.frameHome, targetFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        linearPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthIndex==-1){
                    monthIndex = getCurrentMonth();
                }
                // Create a new instance of TabLayout fragment and replace the current fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TabLayout fragmentB = TabLayout.newInstance(monthIndex-1, user, 1);
                fragmentTransaction.replace(R.id.frameHome, fragmentB);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        pieChart_trangHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthIndex==-1){
                    monthIndex = getCurrentMonth();
                }
                // Create a new instance of TabLayout fragment and replace the current fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TabLayout fragmentB = TabLayout.newInstance(monthIndex-1, user, 1);
                fragmentTransaction.replace(R.id.frameHome, fragmentB);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    public int getCurrentMonth() {
        // Get the current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current month (Note: Calendar.MONTH is zero-based, so January is represented by 0)
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        return currentMonth;
    }
    private void GetTongSoDuVi(){
        BigDecimal Tong1 = GetTongSoTien(user);
        txtTongSoTienTrangHome.setText(String.valueOf(Tong1.intValue() + " VNĐ"));
    }

    private void GetTongThuChiTheoMonth(int selectedMonth){


        BigDecimal TongThu = GetTongThu(selectedMonth, user);
        BigDecimal TongChi = GetTongChi(selectedMonth, user);
        txt_tongThu_tranghome.setText(String.valueOf(TongThu.intValue() + " đ"));
        txt_tongChi_tranghome.setText(String.valueOf(TongChi.intValue()+ " đ"));

        BigDecimal TongDu = TongThu.subtract(TongChi);
        txt_tongDu_tranghome.setText(String.valueOf(TongDu.intValue()+ " đ"));

    }
    public void AddControl(View view){
        txtChaoTenTrangHome = view.findViewById(R.id.txtChaoTenTrangHome);
        txtTongSoTienTrangHome = view.findViewById(R.id.txtTongSoTienTrangHome);
        barChart1 = view.findViewById(R.id.barChart1);
        txt_month_trangHome = view.findViewById(R.id.txt_month_trangHome);
        ic_imgbtn_eye = view.findViewById(R.id.ic_imgbtn_eye);
        ic_imgbtn_refresh = view.findViewById(R.id.ic_imgbtn_refresh);
        ic_imgbtn_expand = view.findViewById(R.id.ic_imgbtn_expand);
        txt_tongThu_tranghome = view.findViewById(R.id.txt_tongThu_tranghome);
        txt_tongChi_tranghome = view.findViewById(R.id.txt_tongChi_tranghome);
        txt_tongDu_tranghome = view.findViewById(R.id.txt_tongDu_tranghome);
        linearBarChart = view.findViewById(R.id.linearBarChart);
        pieChart_trangHome = view.findViewById(R.id.pieChart_trangHome);
        linearPie = view.findViewById(R.id.linearPie);
        linearPie2 = view.findViewById(R.id.linearPie2);

    }

    private BigDecimal GetTongThu( int i, String user){
        BigDecimal Tien = BigDecimal.ZERO;
        String month = (i < 10) ? "0" + i : String.valueOf(i);
        String TongTien = "SELECT sum(sotien) FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm ,LoaiGiaoDich lgd, account ac, vi vi" +
                " WHERE strftime('%m', ngaygiaodich) = '" + month + "' and lgd.MaLoaiGiaoDich =2 and ac.UserName = '" + user + "' " +
                "and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc   " +
                "and gd.ViGiaoDich = vi.MaVi  and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and vi.AccountID = ac.AccountID";

        Cursor cursor = database.rawQuery(TongTien, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            Tien = BigDecimal.valueOf(sum);
        }
        cursor.close();

        return Tien;
    }

    private BigDecimal GetTongChi(int i, String user){
        BigDecimal Tien = BigDecimal.ZERO;
        String month = (i < 10) ? "0" + i : String.valueOf(i);
        String TongTien = "SELECT sum(sotien) FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm,  LoaiGiaoDich lgd, account ac, vi vi" +
                " WHERE strftime('%m', ngaygiaodich) = '" + month + "' and lgd.MaLoaiGiaoDich =1 and ac.UserName = '" + user + "' " +
                "and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and gd.ViGiaoDich = vi.MaVi  and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich  " +
                "and vi.AccountID = ac.AccountID";

        Cursor cursor = database.rawQuery(TongTien, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            Tien = BigDecimal.valueOf(sum);
        }
        cursor.close();

        return Tien;
    }

    private String GetTenNguoiDungTrangHome(String user) {
        String Ten = "";
        String TenNguoiDung = "SELECT TenNguoiDung FROM account ac WHERE ac.UserName = '"+ user +"'";
        Cursor cursor = database.rawQuery(TenNguoiDung, null);
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(0);
                Ten += data ;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return Ten + ",";
    }
    private BigDecimal GetTongSoTien( String user){
        BigDecimal Tien = BigDecimal.ZERO;
        String TongTien = "SELECT sum(tientrongvi) FROM vi, account ac WHERE  ac.Username = '"+ user +"'  and  ac.accountid = vi.accountid";
        Cursor cursor = database.rawQuery(TongTien, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            Tien = BigDecimal.valueOf(sum);
        }
        cursor.close();
        return Tien;
    }

    private void setupImgBtn_ic_eyeClick(View view) {
        final boolean[] isClicked = {false};
        ic_imgbtn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi ImageButton được click
                if (!isClicked[0]) {
                    // Nếu đã click
                    GetTongSoDuVi();
                    isClicked[0] = true;
                } else {
                    // Nếu click lần nữa, hiển thị dãy *
                    txtTongSoTienTrangHome.setText("**********");
                    isClicked[0] = false;
                }            }
        });
    }

    private void setupImgBtn_ic_refreshClick(View view){
        ic_imgbtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi ImageButton được click
                GetTongSoDuVi();
                Toast.makeText(getActivity(), "Đã cập nhật", Toast.LENGTH_SHORT).show();            }
        });
    }

    private void setIc_imgbtn_expand(View view){

        ic_imgbtn_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi ImageButton được click
                setTxt_month_trangHome();

            }
        });
    }
    private BigDecimal GetThu(int i, String user) {
        BigDecimal thuTien = BigDecimal.ZERO;
        String month = (i < 10) ? "0" + i : String.valueOf(i);
        String posNum = "SELECT sum(sotien) FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm,  LoaiGiaoDich lgd, account ac, vi vi" +
                " WHERE strftime('%m', ngaygiaodich) = '" + month + "' and lgd.MaLoaiGiaoDich =2 and ac.UserName = '" + user + "' " +
                "and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and gd.ViGiaoDich = vi.MaVi  and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich  " +
                "and vi.AccountID = ac.AccountID";
        Cursor cursor = database.rawQuery(posNum, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            thuTien = BigDecimal.valueOf(sum);

        }
        cursor.close();

        return thuTien;
    }

    private BigDecimal GetChi(int i, String user) {
        BigDecimal chiTien = BigDecimal.ZERO;
        String month = (i < 10) ? "0" + i : String.valueOf(i);
        String neNum = "SELECT sum(sotien) FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm,  LoaiGiaoDich lgd, account ac, vi vi " +
                "WHERE strftime('%m', ngaygiaodich) = '" + month + "' and lgd.MaLoaiGiaoDich =1 " +
                "and ac.UserName = '" + user + "' " +
                "and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc " +
                "and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and gd.ViGiaoDich = vi.MaVi  " +
                "and vi.AccountID = ac.AccountID";

        Cursor cursor = database.rawQuery(neNum, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            chiTien = BigDecimal.valueOf(sum);
        }
        cursor.close();
        return chiTien;
    }
    private void setChart(int selectedMonth) {
        BigDecimal chiTien = GetChi(selectedMonth, user);
        BigDecimal thuTien = GetThu(selectedMonth, user);

        // Tạo danh sách các cột dữ liệu cho biểu đồ thu
        List<BarEntry> entriesThu = new ArrayList<>();
        entriesThu.add(new BarEntry(0, thuTien.floatValue())); // Cột thứ nhất cho số tiền thu

        // Tạo dataset cho biểu đồ thu
        BarDataSet dataSetThu = new BarDataSet(entriesThu, "Data Label");
        dataSetThu.setColor(Color.parseColor("#38C976"));
        dataSetThu.setDrawValues(true); // Tắt hiển thị số liệu trên các cột
        dataSetThu.setValueTextColor(Color.parseColor("#ffffff")); // Set màu số liệu trên các cột

        // Tạo danh sách các cột dữ liệu cho biểu đồ chi
        List<BarEntry> entriesChi = new ArrayList<>();
        entriesChi.add(new BarEntry(1, chiTien.floatValue())); // Cột thứ hai cho số tiền chi

        // Tạo dataset cho biểu đồ chi
        BarDataSet dataSetChi = new BarDataSet(entriesChi, "Data Label");
        dataSetChi.setColor(Color.parseColor("#FF7474"));
        dataSetChi.setDrawValues(true); // Tắt hiển thị số liệu trên các cột
        dataSetChi.setValueTextColor(Color.parseColor("#ffffff")); // Set màu số liệu trên các cột


        // Tạo dữ liệu biểu đồ
        BarData barData = new BarData(dataSetThu, dataSetChi);

        // Cấu hình trục X
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setDrawAxisLine(false); // Tắt đường kẻ trục X
        xAxis.setDrawGridLines(false); // Tắt đường kẻ dưới chân biểu đồ
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục X ở dưới biểu đồ

        // Cấu hình trục Y
        YAxis yAxis = barChart1.getAxisLeft();
        yAxis.setDrawGridLines(false); // Tắt đường kẻ dọc trục Y
        yAxis.setValueFormatter(new LargeValueFormatter()); // Định dạng giá trị trục Y (nếu cần)
        yAxis.setEnabled(false); // Tắt chú giải trục Y
        // Đảm bảo rằng giá trị tối thiểu của trục Y là 0
        yAxis.setAxisMinimum(0f);
        barChart1.animateY(1000);

        // Tắt trục Y bên phải
        barChart1.getAxisRight().setEnabled(false);

        // Tắt chú giải và mô tả
        barChart1.getLegend().setEnabled(false);
        barChart1.getDescription().setEnabled(false);

        // Tắt các số liệu (labels) hiển thị trên trục X
        xAxis.setDrawLabels(false);

        // Tắt khả năng tự điều chỉnh tỷ lệ biểu đồ và tương tác
        barChart1.setScaleEnabled(false);
        barChart1.setTouchEnabled(false);
        // Áp dụng dữ liệu biểu đồ và làm mới
        barChart1.setData(barData);
        barChart1.invalidate();


    }

    private void setDefaultMonth() {

        // Lấy thông tin tháng hiện tại từ Calendar
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Lưu ý rằng tháng trong Calendar bắt đầu từ 0, nên cần cộng thêm 1

        // Hiển thị tháng hiện tại trong TextView
        setChart(currentMonth);
        txt_month_trangHome.setText(String.format(Locale.getDefault(), "Tháng %d", currentMonth));
        GetTongThuChiTheoMonth(currentMonth);
        //
        setPieChart_trangHome(currentMonth);
    }

    private void setTxt_month_trangHome() {

        String[] months = new String[]{"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Danh mục theo tháng");
        builder.setItems(months, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy tháng được chọn
                String selectedMonth = months[which].toString();

                // Hiển thị tháng được chọn lên TextView
                txt_month_trangHome.setText(selectedMonth);

                // Chạy biểu đồ cột tương ứng với tháng được chọn
                monthIndex = which + 1;
                setChart(monthIndex);

                //Chạy Tổng thu chi tương ứng với tháng được chọn
                GetTongThuChiTheoMonth(monthIndex);

                //Chạy biểu đồ tròn tương ứng với tháng được chọn
                setPieChart_trangHome(monthIndex);

            }
        });
        builder.show();
    }
    private BigDecimal getSoTienTungHanMucChi(String TenLoaiHangMuc, int thang, String user, int MaLoaiGiaoDich){
        BigDecimal TongSoTien = BigDecimal.ZERO;
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);
        String querry = "select sum(Sotien) from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi " +
                "where lhm.TenLoaiHangMuc='"+TenLoaiHangMuc+"' and ac.UserName = '"+ user+ "' " +
                "and strftime('%m', ngaygiaodich) = '"+month+"' " +
                "and lgd.MaLoaiGiaoDich ='"+MaLoaiGiaoDich+"' and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc " +
                "and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich  GROUP BY lhm.TenLoaiHangMuc";
        Cursor cursor = database.rawQuery(querry, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            TongSoTien = BigDecimal.valueOf(sum);

        }
        cursor.close();
        return TongSoTien;
    }

    private BigDecimal getTongSoTienChi(int thang, String user, int MaLoaiGiaoDich){
        BigDecimal TongSoTien = BigDecimal.ZERO;
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);
        String querry = "select sum(Sotien) from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, Vi vi " +
                "where ac.UserName = '"+user+"'and strftime('%m', ngaygiaodich) =  '"+month+"' " +
                "and lgd.MaLoaiGiaoDich ='"+MaLoaiGiaoDich+"' and gd.ViGiaoDich = vi.MaVi  and " +
                "  gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich ";
        Cursor cursor = database.rawQuery(querry, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            TongSoTien = BigDecimal.valueOf(sum);

        }
        cursor.close();
        return TongSoTien;
    }
    private void getTenLoaiHangMucChi(int thang, String user,int MaLoaiGiaoDich) {

        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);
        String query = "SELECT DISTINCT lhm.TenLoaiHangMuc FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac , vi vi " +
                "where ac.UserName = '"+ user+ "' and strftime('%m', ngaygiaodich) = '"+month+"' and " +
                "lgd.MaLoaiGiaoDich ='"+MaLoaiGiaoDich+"' AND gd.MaHangMuc = hm.MaHangMuc AND hm.LoaiHangMuc = lhm.MaLoaiHangMuc AND lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and vi.AccountID = ac.AccountID";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tenLoaiHangMuc = cursor.getString(cursor.getColumnIndex("TenLoaiHangMuc"));
                listTenLoaiHangMuc.add(tenLoaiHangMuc);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
    private void setPieChart_trangHome(int selectedMonth){
        listTenLoaiHangMuc.clear();
        List<PieEntry> entries = new ArrayList<>();
        BigDecimal TongSoTienCuaTungHangMuc = BigDecimal.ZERO;
        BigDecimal TongSoTien = BigDecimal.ZERO;
        getTenLoaiHangMucChi(selectedMonth,user,1);
        PieDataSet dataSet = new PieDataSet(entries, "");

        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < listTenLoaiHangMuc.size(); i++) {
            String tenLoaiHangMuc = listTenLoaiHangMuc.get(i);


            // Create a list of colors using hexadecimal color strings

            if (tenLoaiHangMuc.equals("Ăn uống")) {
                colors.add(Color.parseColor("#3B82F6"));
            } else if (tenLoaiHangMuc.equals("Dịch vụ sinh hoạt")) {
                colors.add(Color.parseColor("#22C55E"));
            } else if (tenLoaiHangMuc.equals("Đi lại")) {
                colors.add(Color.parseColor("#695ACD"));
            } else if (tenLoaiHangMuc.equals("Con cái")) {
                colors.add(Color.parseColor("#F97316"));
            } else if (tenLoaiHangMuc.equals("Trang phục")) {
                colors.add(Color.parseColor("#EF4444"));
            } else if (tenLoaiHangMuc.equals("Sự kiện")) {
                colors.add(Color.parseColor("#925ACD"));
            } else if (tenLoaiHangMuc.equals("Sức khoẻ")) {
                colors.add(Color.parseColor("#FED979"));
            } else if (tenLoaiHangMuc.equals("Nhà cửa")) {
                colors.add(Color.parseColor("#925ACD"));
            } else if (tenLoaiHangMuc.equals("Hưởng thụ")) {
                colors.add(Color.parseColor("#EFC744"));
            } else if (tenLoaiHangMuc.equals("Phát triển bản thân")) {
                colors.add(Color.parseColor("#83AC0A"));
            } else if (tenLoaiHangMuc.equals("Vật nuôi")) {
                colors.add(Color.parseColor("#EE9CB9"));
            } else {
                colors.add(Color.parseColor("#E5F21D"));
            }

            TongSoTienCuaTungHangMuc = getSoTienTungHanMucChi(tenLoaiHangMuc, selectedMonth, user,1);
            TongSoTien = getTongSoTienChi(selectedMonth, user,1);
            BigDecimal percent = TongSoTienCuaTungHangMuc.divide(TongSoTien, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            entries.add(new PieEntry(percent.floatValue(), tenLoaiHangMuc));
        }
        List<LegendEntry> legendEntries = new ArrayList<>();
        float sum = 0f;
        for (PieEntry entry : entries) {
            sum += entry.getValue();
        }

        for (int i = 0; i < entries.size(); i++) {
            PieEntry entry = entries.get(i);
            String label = entry.getLabel();
            float value = entry.getValue();
            float percentValue = value / sum * 100f;
            String formattedPercent = String.format("%.1f%%", percentValue);
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = label + " (" + formattedPercent + ")";
            legendEntry.formColor = colors.get(i);
            legendEntries.add(legendEntry);
        }

        // Tạo DataSet và thiết lập màu sắc cho các phần tử trong biểu đồ
        dataSet.setColors(colors);
        dataSet.setDrawValues(false); // Tắt hiển thị giá trị trên biểu đồ
        dataSet.setValueFormatter(new DecimalValueFormatter());
        PieData pieData = new PieData(dataSet);



        // Apply the custom legend entries
        Legend legend = pieChart_trangHome.getLegend();



        // Tạo chú thích và đặt vị trí của nó ở bên trái dọc

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false); // Chú thích không vẽ trong biểu đồ
        legend.setXEntrySpace(0f); // Khoảng cách giữa các phần tử chú thích
        legend.setYEntrySpace(10f); // Khoảng cách dọc giữa các phần tử chú thích
        legend.setYOffset(0f); // Khoảng cách theo chiều dọc từ biểu đồ đến chú thích
        legend.setXOffset(10f); // Khoảng cách theo chiều ngang từ biểu đồ đến chú thích
        legend.setFormSize(10f); // Kích thước của các hình dạng trong chú thích
        legend.setForm(Legend.LegendForm.CIRCLE); // Hình dạng của biểu đồ trong chú thích
        legend.setTextColor(Color.parseColor("#FFFFFF"));
        legend.setTextSize(12);
        legend.setCustom(legendEntries);

        // Thiết lập màu và độ trong suốt cho lõi (ruột) của biểu đồ tròn
        pieChart_trangHome.setTransparentCircleColor(Color.TRANSPARENT); // Thiết lập màu trong suốt
        pieChart_trangHome.setTransparentCircleAlpha(0); // Thiết lập độ trong suốt (0 - 255, 0 là hoàn toàn trong suốt)


        // Thiết lập các thuộc tính cho biểu đồ tròn.
        pieChart_trangHome.setData(pieData);
        pieChart_trangHome.setUsePercentValues(true);
        pieChart_trangHome.getDescription().setEnabled(false);
        pieChart_trangHome.animateY(1000);
        pieChart_trangHome.setDrawEntryLabels(false); // tắt hiện dữ liệu
        pieChart_trangHome.getDescription().setEnabled(false);
        pieChart_trangHome.invalidate();



        // Đặt màu trong suốt cho tâm của biểu đồ và kích thước
        pieChart_trangHome.setHoleColor(Color.TRANSPARENT);
        pieChart_trangHome.setHoleRadius(60f);
        pieChart_trangHome.invalidate();


    }

}