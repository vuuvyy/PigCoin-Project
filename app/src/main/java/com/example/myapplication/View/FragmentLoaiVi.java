package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Adapter.LoaiViAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassLoaiVi;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentLoaiVi extends Fragment {

 SQLiteDatabase database;
         List<String> listTenLoaiVi = new ArrayList<>();
        RecyclerView reLoaiVi;
        ImageView imgBack;
        String user = "user1";
        LoaiViAdapter loaiViAdapter;

private List<ClassLoaiVi> loaiViList;
public interface OnItemSelectedListener {
    void onItemSelected(String selectedItem);
}



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment currentFragment;

    public FragmentLoaiVi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentLoaiVi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLoaiVi newInstance(String user) {
        FragmentLoaiVi fragment = new FragmentLoaiVi();
        //Bundle args = new Bundle();
        //args.putString("MaLoaiVi", String.valueOf(MaLoaiVi));
        // fragment.setArguments(args);
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


    private void getTenLoaiVi(int MaLoaiVi, String tenvi) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_vi, container, false);
        addControl(view);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();


        loaiViList = getAllLoaiViNames();
        List<ClassLoaiVi> loaiViList = getAllLoaiViNames();

        LoaiViAdapter loaiViAdapter = new LoaiViAdapter(loaiViList);
        reLoaiVi.setLayoutManager(new LinearLayoutManager(getActivity()));
        reLoaiVi.setAdapter(loaiViAdapter);

        // Kiểm tra xem danh sách loại ví có dữ liệu hay không

        // Tạo adapter và đặt nó vào RecyclerView
        loaiViAdapter = new LoaiViAdapter(loaiViList);
        reLoaiVi.setLayoutManager(new LinearLayoutManager(getActivity()));
        reLoaiVi.setAdapter(loaiViAdapter);


        return view;
    }



    private void addControl(View view) {
        reLoaiVi = view.findViewById(R.id.reLoaiVi);


    }

    public List<ClassLoaiVi> getAllLoaiViNames() {
        List<ClassLoaiVi> loaiViList = new ArrayList<>();

        String selectQuery = "SELECT * FROM Loai_Vi";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMu
                @SuppressLint("Range") String tenLoaiVi = cursor.getString(cursor.getColumnIndex("TenLoaiVi"));
                // Thêm tên loại hạng mục vào danh sách
                ClassLoaiVi loaiVi = new ClassLoaiVi(tenLoaiVi);
                loaiViList.add(loaiVi);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return loaiViList;
    }

    public void onItemClicked(String itemValue) {
        // Lấy ra Activity chứa FragmentLoaiVi
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            // Tạo một instance của FragmentAddWallet
            FragmentAddWallet fragmentAddWallet = new FragmentAddWallet();

            // Tạo một bundle để chứa dữ liệu cần gửi
            Bundle bundle = new Bundle();
            bundle.putString("selectedItem", itemValue);

            // Đặt bundle vào FragmentAddWallet
            fragmentAddWallet.setArguments(bundle);

            // Thay thế FragmentAddWallet hiện tại bằng FragmentAddWallet mới (đã chứa dữ liệu)
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLoaiVi, fragmentAddWallet)
                    .commit();
        }
    }
    public void onItemClick(String itemValue) {

        // Call the onItemClicked method of the parent activity (assuming the activity implements OnItemSelectedListener interface)
        if (getActivity() instanceof OnItemSelectedListener) {
            ((OnItemSelectedListener) getActivity()).onItemSelected(itemValue);
        }
    }
}