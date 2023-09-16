package com.example.myapplication.View;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassLoaiVi;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditWallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditWallet extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    String user ;
    TextView tvChonLoaiVi;
    EditText edtDieuChinhSoDu, edtTenVi;
    ImageView imgBack;
    Button btnSave;
    LinearLayout ChonLoaiVi;
    List<ClassLoaiVi> loaiViList = new ArrayList<>();
    SQLiteDatabase database;
    public FragmentEditWallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEditWallet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditWallet newInstance(String param1, String param2,String user) {
        FragmentEditWallet fragment = new FragmentEditWallet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_wallet, container, false);
        // Trích xuất dữ liệu từ Bundle
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();
        String tienTrongViDaNhap= null;
        String tenViDaNhap =null, user = null;
        if (getArguments() != null) {
            tenViDaNhap = getArguments().getString(ARG_PARAM1);
            user = getArguments().getString(ARG_PARAM3);
            tienTrongViDaNhap = getArguments().getString(ARG_PARAM2);
        }
        edtDieuChinhSoDu = view.findViewById(R.id.edtDieuChinhSoDu);
        edtTenVi = view.findViewById(R.id.edtTenVi);
        btnSave = view.findViewById(R.id.btnSave);

        // Hiển thị dữ liệu đã chọn lên giao diện UI
        edtDieuChinhSoDu.setText(tienTrongViDaNhap);
        edtTenVi.setText(tenViDaNhap);
        addEvent();
        // Inflate the layout for this fragment
        return view;
    }

    public void addEvent(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenVi = edtTenVi.getText().toString();
                Double soDuVi = Double.parseDouble(edtDieuChinhSoDu.getText().toString());
                int maloaivi = 1;
                // Lấy AccountID từ cơ sở dữ liệu
                int idAccount = getAccountIDByUserName(user);

                saveWalletData(tenVi, soDuVi, maloaivi,idAccount);
                navigateToFragmentThongKe();
            }
        });
    }
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
            // Create a ContentValues object to hold the values to be updated
            ContentValues values = new ContentValues();
            values.put("MaLoaiVi", maLoaiVi);
            values.put("TenVi", tenVi);
            values.put("TienTrongVi", tienTrongVi);

            // Thực hiện lệnh UPDATE trong bảng "Vi" với điều kiện "AccountID = accountId"
            int affectedRows = database.update("Vi", values, "AccountID = ?", new String[]{String.valueOf(accountId)});

            if (affectedRows > 0) {
                // Lệnh UPDATE đã thành công
                Toast.makeText(getContext(), "Data updated for Vi with AccountID: " + accountId, Toast.LENGTH_SHORT).show();
            } else {
                // Lệnh UPDATE không thành công, do không tìm thấy bản ghi có AccountID là accountId
                Toast.makeText(getContext(), "Failed to update data for Vi with AccountID: " + accountId, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void navigateToFragmentThongKe() {
        ThongKe fragmentThongKe = ThongKe.newInstance();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentEditWallet, fragmentThongKe);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}