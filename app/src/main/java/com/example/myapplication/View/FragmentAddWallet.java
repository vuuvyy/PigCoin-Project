package com.example.myapplication.View;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.LoaiViAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassLoaiVi;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddWallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddWallet extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SQLiteDatabase database;

    DatabaseHelper databaseHelper;
    String user = "user1";
    TextView tvChonLoaiVi;
    EditText edtSoDuBanDau, edtTenVi;
    ImageView imgBack;
    Button btnSave;
    LinearLayout ChonLoaiVi;
    List<ClassLoaiVi> loaiViList = new ArrayList<>();
    public FragmentAddWallet() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentAddWallet newInstance(String selectedItem) {
        FragmentAddWallet fragment = new FragmentAddWallet();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putString("selectedItem", selectedItem);
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
        View view= inflater.inflate(R.layout.fragment_add_wallet,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();

        addControl(view);
        ChonLoaiVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragmentLoaiVi();
            }
        });

        // Kiểm tra bundle có null hay không
        Bundle bundle = getArguments();
        if (bundle != null) {
            String selectedItem = bundle.getString("selectedItem");

            int idAccount = getAccountIDByUserName(user);
            // Tiến hành lưu dữ liệu vào cơ sở dữ liệu

            saveWalletData("tenVi", 100.0,1,idAccount);


            // Cập nhật TextView với dữ liệu đã chọn
            TextView tvChonLoaiVi = view.findViewById(R.id.tvChonLoaiVi);
            tvChonLoaiVi.setText(selectedItem);
        } else {
            Log.e("FragmentAddWallet", "Bundle is null or does not contain the expected data");
        }
        LoaiViAdapter loaiViAdapter = new LoaiViAdapter(loaiViList);


        loaiViAdapter.setOnItemSelectedListener(new LoaiViAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String selectedItem) {
                TextView tvChonLoaiVi = view.findViewById(R.id.tvChonLoaiVi);
                tvChonLoaiVi.setText(selectedItem);
            }
        });

        addEvent();
        return view;    }
    public interface OnDataInsertedListener {
        void onDataInserted();
    }

    private OnDataInsertedListener onDataInsertedListener;

    // Other code...

    public void setOnDataInsertedListener(OnDataInsertedListener listener) {
        this.onDataInsertedListener = listener;
    }
    private void navigateToFragmentLoaiVi() {
        FragmentLoaiVi fragmentLoaiVi = new FragmentLoaiVi();
        //fragmentLoaiVi.itemSelectedListener = this;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentAddWallet, fragmentLoaiVi);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void addControl(View view){
        tvChonLoaiVi = view.findViewById(R.id.tvChonLoaiVi);
        edtSoDuBanDau = view.findViewById(R.id.edtSoDuBanDau);
        edtTenVi = view.findViewById(R.id.edtTenVi);
        ChonLoaiVi = view.findViewById(R.id.ChonLoaiVi);
        btnSave = view.findViewById(R.id.btnSave);
    }

    public void addEvent(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenVi = edtTenVi.getText().toString();
                Double soDuVi = Double.parseDouble(edtSoDuBanDau.getText().toString());
                int maloaivi = 1;
                // Lấy AccountID từ cơ sở dữ liệu

                int idAccount = getAccountIDByUserName(user);

                saveWalletData(tenVi, soDuVi, maloaivi,idAccount);
                navigateToFragmentThongKe();
            }
        });

    }

    private void navigateToFragmentThongKe() {
        ThongKe fragmentThongKe = ThongKe.newInstance();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentAddWallet, fragmentThongKe);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //Phương thức này để dữ liệu tài khoản theo AccountID
    private int getAccountIDByUserName(String userName) {
        int accountID = -1; // Default value if the account is not found

        String query = "SELECT AccountID FROM Account WHERE UserName = ?";
        String[] selectionArgs = {userName};

        Cursor cursor = database.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            accountID = cursor.getInt(cursor.getColumnIndexOrThrow("AccountID"));
        }

        cursor.close();

        return accountID;
    }


    private void saveWalletData(String tenVi, double tienTrongVi, int maLoaiVi, int accountId) {
        try {
            String insertQuery = "INSERT INTO Vi (MaLoaiVi, TenVi, TienTrongVi, AccountID) VALUES (?, ?, ?, ?)";

            // Create a ContentValues object to hold the values to be inserted
            ContentValues values = new ContentValues();
            values.put("MaLoaiVi", maLoaiVi);
            values.put("TenVi", tenVi);
            values.put("TienTrongVi", tienTrongVi);
            values.put("AccountID", accountId);

            // Insert data into the database
            long insertedRowId = database.insert("Vi", null, values);
            if (insertedRowId != -1) {
                // The insertion was successful
                Toast.makeText(getContext(), "Data inserted into Vi table with ID: " + insertedRowId, Toast.LENGTH_SHORT).show();
            } else {
                // The insertion failed
                Toast.makeText(getContext(), "Failed to insert data into Vi table", Toast.LENGTH_SHORT).show();
            }

            // Close the database after inserting data
            database.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void onItemSelected(String selectedItem){
        // Nhận dữ liệu từ FragmentLoaiVi và cập nhật TextView trong FragmentAddWallet
        TextView tvChonLoaiVi = getView().findViewById(R.id.tvChonLoaiVi);
        tvChonLoaiVi.setText(selectedItem);
    }
    @Override
    public void onDestroyView() {
        // Close the database when the fragment is destroyed
        database.close();
        super.onDestroyView();
    }
}