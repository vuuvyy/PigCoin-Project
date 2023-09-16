package com.example.myapplication.View;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.Adapter.Custom_Adapter_Giao_Dich_ThongKe;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FragmentChart extends Fragment {

    SQLiteDatabase database;
    TextView txtTongSoTienTrangHome;
    BarChart Chart;
    RecyclerView reThongKe;
    ArrayList<ClassGiaoDich> classGiaoDichList;
    Custom_Adapter_Giao_Dich_ThongKe adapter;
    String user;
    FragmentManager fragmentManager;
    FrameLayout frameMain;

int MaLoaiGiaoDich;
    public FragmentChart(String user) {
        this.user = user;
    }
    public static FragmentChart newInstance(String user) {
        return new FragmentChart(user);
    }

    public FragmentChart() {
    }

    // TODO: Rename and change types and number of parameters
    public static fragment_chi_tiet_thog_ke newInstance(int position, String user, int MaLoaiGiaoDich) {
        fragment_chi_tiet_thog_ke fragment = new fragment_chi_tiet_thog_ke();
        Bundle args = new Bundle();
        args.putString("Thang", String.valueOf(position));
        args.putString("user", String.valueOf(user));
        args.putString("MaLoaiGiaoDich", String.valueOf(MaLoaiGiaoDich));

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
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.deleteDatabase(getContext());
        database = databaseHelper.getWritableDatabase();
        fragmentManager = getParentFragmentManager();

        AddControl(view);
        showListPerMonth();
        AddEvent();
        GetMoney();
        return view;
    }

    private void showListPerMonth() {
        classGiaoDichList = new ArrayList<>();

        BigDecimal tienThu = BigDecimal.ZERO;
        BigDecimal tienChi = BigDecimal.ZERO;
        BigDecimal tienConLai = BigDecimal.ZERO;
        for (int i = 1; i <= 12; i++) {
            tienThu = GetThu(i,user);
            tienChi = GetChi(i,user);
            tienConLai = tienThu.subtract(tienChi);
            classGiaoDichList.add(new ClassGiaoDich(tienThu, tienChi, tienConLai, i));

        }
        adapter = new Custom_Adapter_Giao_Dich_ThongKe(getActivity(), classGiaoDichList);

        // Cấu hình RecyclerView
        reThongKe.setLayoutManager(new LinearLayoutManager(getActivity()));
        reThongKe.setAdapter(adapter);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sự kiện nhấn nút back
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().onBackPressed(); // Quay lại Fragment đằng trước
                return true;
            }
            return false;
        });
    }
    private void AddControl(View view) {
        Chart = view.findViewById(R.id.barChart);
        reThongKe = view.findViewById(R.id.reThongKe);
        frameMain= view.findViewById(R.id.frameMain);

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
                "WHERE strftime('%m', ngaygiaodich) = '" + month + "' and lgd.MaLoaiGiaoDich =1 and ac.UserName = '" + user + "' " +
                "and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and gd.ViGiaoDich = vi.MaVi  and vi.AccountID = ac.AccountID";

        Cursor cursor = database.rawQuery(neNum, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            chiTien = BigDecimal.valueOf(sum);
            chiTien.negate();
        }
        cursor.close();
        return chiTien;
    }

    private void GetMoney() {
        BigDecimal chiTien = BigDecimal.ZERO;
        BigDecimal thuTien = BigDecimal.ZERO;
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            chiTien = GetChi(i, user).negate();
            thuTien = GetThu(i,user);
            BigDecimal[] values = new BigDecimal[]{thuTien, chiTien};
            float[] floatValues = new float[values.length];
            for (int u = 0; u < values.length; u++) {
                floatValues[u] = values[u].floatValue();
            }
            entries.add(new BarEntry(i - 1, floatValues));
        }

        setChart(entries);

    }

    public void setChart(List<BarEntry> entries) {

        BarDataSet dataSet = new BarDataSet(entries, "Data Label");
        dataSet.setColors(new int[]{Color.parseColor("#38C976"), Color.parseColor("#FF7474")});
        dataSet.setValueTextColor(Color.WHITE);
        BarData barData = new BarData(dataSet);
        XAxis xAxis = Chart.getXAxis();
        xAxis.setValueFormatter(new FragmentChart.XAxisValueFormatter());
        YAxis leftAxis = Chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.WHITE);
        Chart.getLegend().setEnabled(false);
        Chart.getDescription().setEnabled(false);
        Chart.setScaleEnabled(false);
        Chart.setTouchEnabled(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Chart.getXAxis().setTextColor(Color.WHITE);
        Chart.getAxisLeft().setTextColor(Color.WHITE);
        Chart.getAxisRight().setTextColor(Color.WHITE);
        Chart.getAxisRight().setEnabled(false);

        Chart.getDescription().setText("Biểu đồ thu chi");
        Chart.getDescription().setTextColor(Color.WHITE);
        Chart.getDescription().setTextSize(12f);
        Chart.getDescription().setPosition(50, 50);

        Chart.setData(barData);
        Chart.invalidate();

    }

    public class XAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            int intValue = (int) value;
            if (intValue >= 0 && intValue < 12) {
                return String.valueOf(intValue + 1);
            } else {
                return "";
            }
        }
    }

    private void AddEvent(){
        adapter.setOnItemClickListener(new Custom_Adapter_Giao_Dich_ThongKe.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//            loadFragment( new BlankFragment.newInstance(position));
                TabLayout fragmentB = TabLayout.newInstance(position, user, MaLoaiGiaoDich);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameMain, fragmentB)
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối với cơ sở dữ liệu khi không cần sử dụng nữa
        if (database != null) {
            database.close();
        }
    }

}