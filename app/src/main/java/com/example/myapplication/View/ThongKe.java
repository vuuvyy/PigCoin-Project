package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassVi;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.WalletAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    TextView txtTongSoTien;
    BarChart barChart;
    RecyclerView recyViSuDung;
    ImageButton imgMore;
    ArrayList<ClassVi> lsVi = new ArrayList<>();
    LayoutInflater inflater;
    String user ;
    WalletAdapter walletAdapter;
    List<ClassVi> viList;
    FloatingActionButton floatingActionButton;
    public ThongKe() {
        // Required empty public constructor
    }
    public ThongKe(String user) {
        this.user = user;
    }
    public static ThongKe newInstance(String user) {
        return new ThongKe(user);
    }


    public static ThongKe newInstance() {
        ThongKe fragment = new ThongKe();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_thong_ke,container,false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());//để kết nối db
//             databaseHelper.deleteDatabase(getActivity());

        db = databaseHelper.getWritableDatabase();
        addControl(view);
        //---------------------------------------------------------------------
        //tinh tổng tiền
        double totalAmount = getTotalAmountForUser(user);
        txtTongSoTien.setText(totalAmount + " VNĐ");

        // Get wallet data for "user1" from the database
        List<ClassVi> getListWalletsByUser = createViList();
        walletAdapter = new WalletAdapter(getListWalletsByUser);
        recyViSuDung.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Thiết lập Adapter cho RecyclerView
        recyViSuDung.setAdapter(walletAdapter);

        // Xử lý sự kiện nhấp vào một mục trong RecyclerView
        walletAdapter.setItemClickListener(new WalletAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ClassVi selectedWallet) {
                // Xử lý sự kiện tại đây
                // Ví dụ: Mở FragmentEditWallet và chuyển dữ liệu của mục được nhấp vào qua Bundle
                ChiTietViThuNhi fragmentEditWallet = ChiTietViThuNhi.newInstance(selectedWallet.getTenVi(), user);

                // Đoạn code để mở FragmentEditWallet và chuyển dữ liệu qua Bundle
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentThongKe, fragmentEditWallet); // Thay R.id.fragment_container bằng ID của container để chứa FragmentEditWallet
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        registerForContextMenu(recyViSuDung);


        addEvent(view);

        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_edit) {
            // Handle the "Edit" action here
            return true;
        } else if (id == R.id.menu_delete) {
            // Handle the "Delete" action here
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
    private List<ClassVi> createViList() {
        List<ClassVi> viList = new ArrayList<>();
        List<ClassVi> allVi = getAllViNames(user);

        for (int i = 0; i < allVi.size(); i++) {
            ClassVi vi = allVi.get(i);
            viList.add(vi);
        }

        return viList;
    }

    private List<ClassVi> getAllViNames(String user) {
        List<ClassVi> viList = new ArrayList<>();

        // Câu lệnh SELECT để lấy tên các ví
        String selectQuery = "SELECT lv.TenLoaiVi, vi.TenVi, vi.tientrongvi FROM Vi vi, Loai_Vi lv, Account ac WHERE Vi.AccountID = ac.AccountID and ac.UserName = '"+user+"' and lv.MaLoaiVi = vi.MaLoaiVi";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMuc
                @SuppressLint("Range") String tenLoaiVi = cursor.getString(cursor.getColumnIndex("TenLoaiVi"));
                @SuppressLint("Range") String sotien = cursor.getString(cursor.getColumnIndex("TienTrongVi"));
                @SuppressLint("Range") String tenVi = cursor.getString(cursor.getColumnIndex("TenVi"));

                BigDecimal tienTrongVi = new BigDecimal(sotien);

                // Tạo một đối tượng ClassVi và thêm nó vào danh sách
                ClassVi vi = new ClassVi(tenVi ,tienTrongVi, tenLoaiVi);
                vi.setTenLoaiVi(tenLoaiVi);
                viList.add(vi);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();

        return viList;
    }
    private void addControl(View view) {
        txtTongSoTien = view.findViewById(R.id.txtTongSoTien);
        barChart = view.findViewById(R.id.barChart);
        recyViSuDung = view.findViewById(R.id.recyViSuDung);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }
    @SuppressLint("MissingInflatedId")
    private void addEvent(View view){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddWallet fragmentAddWallet = new FragmentAddWallet();
                //fragmentLoaiVi.itemSelectedListener = this;
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentThongKe, fragmentAddWallet);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    public double getTotalAmountForUser(String user){
        int totalAmount = 0;

        String query = "SELECT SUM(TienTrongVi) FROM Vi vi, Account ac WHERE Vi.AccountID = ac.AccountID and ac.UserName = '"+user+"'";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            totalAmount = cursor.getInt(0);
        }
        cursor.close();
        return totalAmount;
    }
    private void updateRecyclerView() {
        // Cập nhật lại RecyclerView sau khi có dữ liệu mới
        List<ClassVi> getListWalletsByUser = createViList();
        walletAdapter.setData(getListWalletsByUser);
        walletAdapter.notifyDataSetChanged();
    }
}