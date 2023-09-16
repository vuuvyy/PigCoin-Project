package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.CustomAdapterChiTietDoanhMuc_ThongKe;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassItemChiTietItem;
import com.example.myapplication.Model.DecimalValueFormatter;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class ChiFragmentChiTietThongKe extends Fragment {
    PieChart pieChart;
    SQLiteDatabase database;
    TextView tongSoTienDaChiChiTietThongKe,txtThangChiTietChi;
    List<String> listTenLoaiHangMuc = new ArrayList<>();
    RecyclerView reChiTietHangMuc;
    FrameLayout FrameChi;
    int thang,MaLoaiGiaoDich;
    ArrayList<com.example.myapplication.Model.ClassItemChiTietItem> ClassItemChiTietItem;
    CustomAdapterChiTietDoanhMuc_ThongKe adapter;
    String user , tenDoanhMuc ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    FragmentManager fragmentManager;
    private Fragment currentFragment;
    FrameLayout frameTab;


    private int vitri;
    private static String ARG_POSITION = "vitri";



    public ChiFragmentChiTietThongKe() {

    }
    public static ChiFragmentChiTietThongKe newInstance(int param1, String user, int MaLoaiGiaoDich) {
        ChiFragmentChiTietThongKe fragment = new ChiFragmentChiTietThongKe();
        Bundle args = new Bundle();
        args.putString("thang", String.valueOf(param1));
        args.putString("user", String.valueOf(user));
        args.putString("MaLoaiGiaoDich", String.valueOf(MaLoaiGiaoDich));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();
        View view = inflater.inflate(R.layout.fragment_chi_chi_tiet_thong_ke, container, false);
        AddControl(view);
        currentFragment = this;

        Bundle bundle = getArguments();
        if (bundle != null) {
            thang = Integer.parseInt(bundle.getString("thang"));
            user = bundle.getString("user");
            MaLoaiGiaoDich = Integer.parseInt(bundle.getString("MaLoaiGiaoDich"));
            thang+=1;
        }
        getTenLoaiHangMucChi(thang,user,MaLoaiGiaoDich);
        BigDecimal TongSoTien = BigDecimal.ZERO;

        for (int i = 0; i < listTenLoaiHangMuc.size(); i++) {
            String tenLoaiHangMuc = listTenLoaiHangMuc.get(i);
            TongSoTien = getTongSoTienChi(thang,user,MaLoaiGiaoDich);
        }
        tongSoTienDaChiChiTietThongKe.setText(TongSoTien.toString());

        txtThangChiTietChi.setText("Tháng "+thang);


        CreateCircleChart();
        LoadChiTietDoanhMuc();
        AddEvent();


        return view;
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
    private void LoadChiTietDoanhMuc(){


        ClassItemChiTietItem = new ArrayList<>();
        BigDecimal soTien = BigDecimal.ZERO;
        BigDecimal TongSoTien = BigDecimal.ZERO;
        BigDecimal percent = BigDecimal.ZERO;


        String ItemCategory = "";
        float Percentage = 0;
        String[] tenLoaiHangMucArray = listTenLoaiHangMuc.toArray(new String[0]);
        for (int i = 0; i< listTenLoaiHangMuc.size(); i++){

            soTien= getSoTienTungHanMucChi(tenLoaiHangMucArray[i],thang, user,MaLoaiGiaoDich);
            ItemCategory = tenLoaiHangMucArray[i].toString();


            TongSoTien = getTongSoTienChi(thang,user,MaLoaiGiaoDich);
            percent = soTien.divide(TongSoTien, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            Percentage = percent.floatValue();
            ClassItemChiTietItem.add(new ClassItemChiTietItem(soTien,Percentage,ItemCategory,R.drawable.hamburger));

        }
        if (ClassItemChiTietItem.isEmpty()) {
            // Hiển thị thông báo hoặc hành động phù hợp khi không có dữ liệu
            Toast.makeText(getActivity(), "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
        } else {

        adapter = new CustomAdapterChiTietDoanhMuc_ThongKe(getActivity(), ClassItemChiTietItem);

        // Cấu hình RecyclerView
        reChiTietHangMuc.setLayoutManager(new LinearLayoutManager(getActivity()));
        reChiTietHangMuc.setAdapter(adapter);
        }

    }
    private BigDecimal getTongSoTienChi(int thang, String user, int MaLoaiGiaoDich){
        BigDecimal TongSoTien = BigDecimal.ZERO;
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);
        String querry = "select sum(Sotien) from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, Vi vi " +
                "where ac.UserName = '"+user+"'and strftime('%m', ngaygiaodich) =  '"+month+"' " +
                "and lgd.MaLoaiGiaoDich ='"+MaLoaiGiaoDich+"' and gd.ViGiaoDich = vi.MaVi  and " +
                "  gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and" +
                " lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich ";
        Cursor cursor = database.rawQuery(querry, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            TongSoTien = BigDecimal.valueOf(sum);

        }
        cursor.close();
        return TongSoTien;
    }
    private void CreateCircleChart() {
        List<PieEntry> entries = new ArrayList<>();
        BigDecimal TongSoTienCuaTungHangMuc = BigDecimal.ZERO;
        BigDecimal TongSoTien = BigDecimal.ZERO;
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

            TongSoTienCuaTungHangMuc = getSoTienTungHanMucChi(tenLoaiHangMuc, thang, user,MaLoaiGiaoDich);
            TongSoTien = getTongSoTienChi(thang, user,MaLoaiGiaoDich);
            BigDecimal percent = TongSoTienCuaTungHangMuc.divide(TongSoTien, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            entries.add(new PieEntry(percent.floatValue(), tenLoaiHangMuc));



        }
        dataSet.setColors(colors);
        dataSet.setValueFormatter(new DecimalValueFormatter()); // Use the custom formatter
//        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(13f);

        // Tạo PieData và cấu hình các thuộc tính
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new DecimalValueFormatter()); // Use the custom formatter
        pieData.setValueTextSize(12f);


        // Cấu hình biểu đồ tròn
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        pieChart.animateY(1000);
        pieChart.setTransparentCircleColor(Color.TRANSPARENT);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate();
    }
    private void AddEvent(){
        // Trong phương thức onCreateView hoặc bất kỳ phương thức khởi tạo nào khác
        fragmentManager = getParentFragmentManager();

        adapter.setOnItemClickListener(new CustomAdapterChiTietDoanhMuc_ThongKe.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ClassItemChiTietItem itemChiTiet) {
                String tenLoaiDoanhMuc = itemChiTiet.getTenLoaiHangMuc();

                TabLayout parentFragment = (TabLayout) getParentFragment();
                if (parentFragment != null) {
                    parentFragment.hideTabLayout();
                }

                BlankFragment2 fragmentB = BlankFragment2.newInstance(thang, tenLoaiDoanhMuc, MaLoaiGiaoDich,user);

                // Thay thế fragment hiện tại trong FrameLayout
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameTab, fragmentB)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    private void AddControl(View view){
        pieChart = view.findViewById(R.id.pieChartChi);
        txtThangChiTietChi= view.findViewById(R.id.txtThangChiTietChi);
        tongSoTienDaChiChiTietThongKe = view.findViewById(R.id.tongSoTienDaChiChiTietThongKe);
        reChiTietHangMuc = view.findViewById(R.id.reChiTietHangMucChi);
        FrameChi= view.findViewById(R.id.FrameChi);
    }
}