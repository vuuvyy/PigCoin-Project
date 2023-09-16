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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.LoaiViGiaoDichAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassVi;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_LoaiVi_GiaoDich#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_LoaiVi_GiaoDich extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewLoaiVi;
    SQLiteDatabase database;
    LoaiViGiaoDichAdapter loaiViGiaoDichAdapter;
    String user;

    FragmentManager fragmentManager;

    public Fragment_LoaiVi_GiaoDich() {
        // Required empty public constructor
    }



    public static Fragment_LoaiVi_GiaoDich newInstance(String user) {
        Fragment_LoaiVi_GiaoDich fragment = new Fragment_LoaiVi_GiaoDich();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__loai_vi__giao_dich, container, false);
        if (getArguments() != null) {
            user = getArguments().getString(ARG_PARAM1);
            // Sử dụng tham số param1 nếu cần thiết
        }
        // Lấy "RecyclerView" bằng ID từ view được inflate
        recyclerViewLoaiVi = view.findViewById(R.id.recyclerViewLoaiVi);
        // Tạo và mở cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();


        List<ClassVi> loaiViList = createViList();
        // Khởi tạo adapter cho RecyclerView chính (hạng mục thu)

        loaiViGiaoDichAdapter = new LoaiViGiaoDichAdapter(loaiViList);
        // Thiết lập adapter cho RecyclerView chính (loại hạng mục)
//        if (recyclerViewLoaiVi != null) {
        recyclerViewLoaiVi.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLoaiVi.setAdapter(loaiViGiaoDichAdapter);
        //LinearLayout layoutNote = view.findViewById(R.id.layout_Note);
//        }
        // Gán sự kiện click cho adapter của LoaiViGiaoDichAdapter
        loaiViGiaoDichAdapter.setOnViItemClickListener(new LoaiViGiaoDichAdapter.OnViItemClickListener() {
            @Override
            public void onViItemClick(String tenVi) {
                // Xử lý khi item ví được click
                // Ở đây, tên ví đã được lấy ra và có thể hiển thị bằng Toast hoặc xử lý tiếp theo
                int idVi =  findMaViByUser(user, tenVi);
                Toast.makeText(getActivity(), String.valueOf(idVi), Toast.LENGTH_SHORT).show();
                // Truy cập FragmentManager bằng cách sử dụng getChildFragmentManager()
                FragmentManager fragmentManager = getChildFragmentManager();
                loadFragment(new FragmentGiaoDich(idVi, 0));
            }
        });

        return view;
    }
    protected void loadFragment(Fragment fragment) {
        // Sử dụng getChildFragmentManager() để lấy đối tượng FragmentManager từ Fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameVi, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private List<ClassVi> createViList() {
        List<ClassVi> viList = new ArrayList<>();
        List<ClassVi> allVi = getAllViNames(user);

        for (int i = 0; i < allVi.size(); i++) {
            ClassVi vi = allVi.get(i);
            String formattedTienTrongVi = vi.getFormattedTienTrongVi();
            // Log.d("TienTrongVi", "Formatted value: " + formattedTienTrongVi);
            //Log.d("hèdhfj", tenHangMuc);
            viList.add(vi);
        }

        return viList;
    }

    public List<ClassVi> getAllViNames(String user) {
        List<ClassVi> viList = new ArrayList<>();

        // Câu lệnh SELECT để lấy tên các ví
        String selectQuery = "SELECT lv.TenLoaiVi, vi.TenVi, vi.tientrongvi FROM Vi vi, Loai_Vi lv, Account ac WHERE Vi.AccountID = ac.AccountID and ac.UserName = '"+user+"' and lv.MaLoaiVi = vi.MaLoaiVi";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMuc
                @SuppressLint("Range") String tenLoaiVi = cursor.getString(cursor.getColumnIndex("TenLoaiVi"));
                @SuppressLint("Range") String sotien = cursor.getString(cursor.getColumnIndex("TienTrongVi"));
                @SuppressLint("Range") String tenVi = cursor.getString(cursor.getColumnIndex("TenVi"));


                BigDecimal tienTrongVi = new BigDecimal(sotien);
                // Integer idVi = new Integer(maVi);

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

    @SuppressLint("Range")
    public int findMaViByUser(String user, String tenVi) {
        int maVi = -1; // Giá trị mặc định khi không tìm thấy mã ví

        // Câu lệnh SELECT để lấy mã ví từ tên người dùng
        String selectQuery = "SELECT vi.MaVi FROM Vi vi, Account ac WHERE vi.TenVi = '"+tenVi+"' and vi.AccountID = ac.AccountID AND ac.UserName = '" + user + "'";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy mã ví từ cột MaVi
            maVi = cursor.getInt(cursor.getColumnIndex("MaVi"));
        }

        // Đóng con trỏ
        cursor.close();

        return maVi;
    }
}