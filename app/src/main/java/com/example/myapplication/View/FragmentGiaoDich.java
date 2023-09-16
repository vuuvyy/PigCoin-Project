package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.HangMucAdapter;
import com.example.myapplication.Adapter.LoaiHangMucAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGiaoDich#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGiaoDich extends Fragment implements LoaiHangMucAdapter.OnHangMucItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "mparam3";
    private static final String ARG_PARAM4 = "mparam4";

    // TODO: Rename and change types of parameters
    SQLiteDatabase db;

    DatabaseHelper databaseHelper;
    private String mParam1;
    private String mParam2;
    private String mparam3;
    private String mparam4;

    private Date dateSelected; // Biến để lưu ngày đã chọn
    String user = "user1";

    //---- set dữ liệu để thực hiện nút lưu
    String maHangMucStr = "1";

    int maHangMuc = Integer.parseInt(maHangMucStr);

    LocalDate ngayLocalDate = LocalDate.parse("2023-07-13");
    Date ngayDate = null;

    //----

    EditText editMoney;
    EditText editNote;
    TextView tvVi,tvFile;
    Button btnSave;
    int maHMuc, maVi;

    LinearLayout layout_File;
    LinearLayout layout_Wallet;
    LinearLayout layout_Note;
    LinearLayout layout_Calendar;
    FragmentManager fragmentManager;
    FrameLayout frame_ThemGiaoDich;


    public FragmentGiaoDich() {
        // Required empty public constructor
    }

    public FragmentGiaoDich(int maVi,int maHMuc){
        this.maVi = maVi;
        this.maHMuc = maHMuc;
    }
    public static FragmentGiaoDich newInstance(int maVi, int maHMuc){
        return new FragmentGiaoDich(maVi,maHMuc);
    }

    public static Fragment_LoaiVi_GiaoDich newInstance(String tenHangMuc, String user) {
        Fragment_LoaiVi_GiaoDich fragment = new Fragment_LoaiVi_GiaoDich();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tenHangMuc);
        args.putString(ARG_PARAM3, user);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giao_dich, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        db = databaseHelper.getWritableDatabase();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
        addControl(view);

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        int maHm = sharedPreferences.getInt("maHm", -1);
//        int maVi = sharedPreferences.getInt("maVi", -1);

        // Kiểm tra và hiển thị dữ liệu lên TextView tvVi và tvFile
        if (maHMuc != -1) {
            String tenh = FindNameHM(maHMuc);
            tvFile.setText(tenh);
        }

        if (maVi != -1) {
            String tenh = FindNameVi(maVi);
        }


        addEvent();

        Bundle args = getArguments();
        if (args != null) {
            int idVi = args.getInt("idVi", -1);
            if (idVi != -1) {

            }
        }

        try {
            // Chuyển đổi từ LocalDate sang Date bằng cách sử dụng SimpleDateFormat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngayDate = sdf.parse(ngayLocalDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;

    }


    @SuppressLint("Range")
    private String FindNameHM(int id){

String tenHM = null;
        String selectQuery = "SELECT hm.tenhangmuc FROM HangMuc hm WHERE hm.maHangMuc = '"+id+"'";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy mã ví từ cột MaVi
            tenHM = cursor.getString(cursor.getColumnIndex("TenHangMuc"));
        }

        // Đóng con trỏ
        cursor.close();

        return tenHM;
    }
    @SuppressLint("Range")
    private String FindNameVi(int id){

        String tenHM = null;
        String selectQuery = "SELECT vi.tenVI FROM vi vi WHERE vi.mavi = '"+id+"'";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy mã ví từ cột MaVi
            tenHM = cursor.getString(cursor.getColumnIndex("TenVi"));
        }

        // Đóng con trỏ
        cursor.close();

        return tenHM;
    }



    private void addEvent() {
        fragmentManager = requireActivity().getSupportFragmentManager();

        //---- Nhấn vào file
        layout_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("maVi", maVi);
                editor.apply();

                // Hiển thị tên ví lên TextView tvVi
                String tenh = FindNameVi(maVi);
                tvVi.setText(tenh);


                Fragment_TabLayout_GiaoDich  fragmentB = Fragment_TabLayout_GiaoDich .newInstance(user);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_ThemGiaoDich, fragmentB)
                        .commit();
            }
        });

        //---- Nhấn vào ví
        layout_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("maHm", maHMuc);
                editor.apply();

                // Hiển thị tên hạng mục lên TextView tvFile
                String tenh = FindNameHM(maHMuc);
                tvFile.setText(tenh);
                Fragment_LoaiVi_GiaoDich fragmentB = Fragment_LoaiVi_GiaoDich.newInstance(user);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameMain, fragmentB)
                        .commit();


            }
        });



        //---- Nhấn vào layout_calendar
        layout_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        List<ClassGiaoDich> giaoDichList = new ArrayList<>();
        //---- lấy số tiền được nhập vào editMoney
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputMoney = editMoney.getText().toString();
                BigDecimal sotien;
                try {
                    sotien = new BigDecimal(inputMoney);
                } catch (NumberFormatException e) {

                    Toast.makeText(getActivity(), "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                    return; // Dừng việc thực hiện phần còn lại của onClick
                }
                String dienGiai = new String(editNote.getText().toString());
                Date ngayGiaoDich;

                if (dateSelected != null) {
                    ngayGiaoDich = dateSelected;
                } else {
                    ngayGiaoDich = Calendar.getInstance().getTime();
                }

maVi = 2;

                insertGiaoDich(maHMuc, dienGiai, sotien, maVi, ngayGiaoDich);

                // Tạo một đối tượng ClassVi và thêm nó vào danh sách
                ClassGiaoDich vi = new ClassGiaoDich(maHangMuc, ngayGiaoDich, dienGiai, sotien, maVi);
                giaoDichList.add(vi);
                int IdGiaoDich = getMaxGiaoDichID();
                BigDecimal soTienTrongVi = getTienTrongViByMaVi(maVi);
                BigDecimal updateTien = soTienTrongVi.subtract(sotien);
                updateGiaoDichSotien(maVi,updateTien);
                Toast.makeText(getActivity(), "thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public BigDecimal getTienTrongViByMaVi(int maVi) {
        BigDecimal tienTrongVi = BigDecimal.ZERO; // Giá trị mặc định khi không tìm thấy số tiền trong ví

        // Chuẩn bị câu lệnh SQL truy vấn số tiền trong ví dựa vào mã ví (MaVi)
        String selectQuery = "SELECT tientrongvi FROM Vi WHERE MaVi = " + maVi;

        // Thực hiện câu lệnh SQL truy vấn và lấy kết quả số tiền trong ví
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String tienTrongViString = cursor.getString(cursor.getColumnIndex("TienTrongVi"));
            tienTrongVi = new BigDecimal(tienTrongViString);
            cursor.close();
        }

        return tienTrongVi;
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog với ngày hiện tại làm mặc định
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Xử lý khi người dùng chọn một ngày trong DatePickerDialog
                // Gán giá trị ngày đã chọn vào biến dateSelected
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, monthOfYear, dayOfMonth);
                dateSelected = selectedCalendar.getTime();

                // Ví dụ: Hiển thị thông báo với ngày đã chọn
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                Toast.makeText(requireContext(), "Ngày đã chọn: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
    public void insertGiaoDich(int maHangMuc, String dienGiai, BigDecimal sotien, int ViGiaoDich, Date ngayGiaoDich) {

        // Định dạng ngày giao dịch thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayGiaoDichString = dateFormat.format(ngayGiaoDich);

        // Tạo câu truy vấn insert bằng raw SQL
        String query = "INSERT INTO GiaoDich (MaHangMuc, DienGiai, Sotien, ViGiaoDich, NgayGiaoDich) " +
                "VALUES (" + maHangMuc + ", '" + dienGiai + "', " + sotien.toString() + ", " + ViGiaoDich + ", '" + ngayGiaoDichString + "')";

        // Thực thi câu truy vấn insert
        db.execSQL(query);

        // Đóng kết nối với cơ sở dữ liệu

    }



    public void updateGiaoDichSotien(int maGiaoDich, BigDecimal sotien) {
        // Chuẩn bị câu lệnh SQL update bằng raw query
        String sotienString = sotien.toString(); // Chuyển BigDecimal thành chuỗi
        String updateQuery = "UPDATE vi SET TienTrongVi = '"+ sotienString +"' WHERE MaVi = '"+ maGiaoDich +"'";

        // Thực hiện câu lệnh SQL update bằng raw query
        try {
            db.execSQL(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getMaxGiaoDichID() {
        int maxGiaoDichID = -1; // Giá trị mặc định khi không tìm thấy ID lớn nhất

        // Chuẩn bị câu lệnh SQL truy vấn ID lớn nhất của bảng GiaoDich bằng raw query
        String maxIDQuery = "SELECT MAX(MaGiaoDich) FROM GiaoDich";

        // Thực hiện câu lệnh SQL truy vấn và lấy kết quả ID lớn nhất
        Cursor cursor = db.rawQuery(maxIDQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            maxGiaoDichID = cursor.getInt(0); // Lấy giá trị ID lớn nhất từ cột đầu tiên (index 0)
            cursor.close();
        }

        return maxGiaoDichID;
    }



    private void addControl(View view){
        editMoney = view.findViewById(R.id.editMoney);
        editNote = view.findViewById(R.id.editNote);
        tvVi = view.findViewById(R.id.tvVi);
        tvFile = view.findViewById(R.id.tvFile);

        btnSave = view.findViewById(R.id.btnSave);
        layout_File = view.findViewById(R.id.layout_File);
        layout_Wallet = view.findViewById(R.id.layout_Wallet);
        layout_Note = view.findViewById(R.id.layout_Note);
        frame_ThemGiaoDich = view.findViewById(R.id.frame_ThemGiaoDich);
        layout_Calendar = view.findViewById(R.id.layout_Calendar);
    }

    @Override
    public void onHangMucItemClick(String tenHangMuc) {
        Log.d("onHangMucItdsemClick: ", tenHangMuc);
    }
}