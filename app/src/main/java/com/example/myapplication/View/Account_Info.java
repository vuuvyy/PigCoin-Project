package com.example.myapplication.View;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account_Info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_Info extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imgAvatar;
    EditText editDisplayName;
    EditText editPassword;
    String user;
    EditText editConfirmPass;
    ImageView imgViewPass;
    ImageView imgView_ConfirmPass;
    Button btnUpdatePass,buttonChangeName;
    Button btnExit;
    boolean isPasswordVisible = false;

    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    public Account_Info() {
        // Required empty public constructor
    }
    public Account_Info(String user) {
        this.user = user;
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account_Info.
     */
    // TODO: Rename and change types and number of parameters
    public static Account_Info newInstance(String param1, String param2) {
        Account_Info fragment = new Account_Info();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account__info, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
        addControl(view);
        // Hiển thị Dialog thông báo
        // showDialog();
        addEvent();

        // Nhận thông tin displayName từ SharePreferences và hiển thị lên EditText "editDisplayName"
        SharedPreferences preferences = requireContext().getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String displayName = preferences.getString("displayName", "");
        String name = getDisplayNameByUser(user);
        editDisplayName.setText(name);
        return view;
    }
    private void addControl(View view){
        editDisplayName = view.findViewById(R.id.editDisplayName);
        editPassword = view.findViewById(R.id.editPassword);
        editConfirmPass = view.findViewById(R.id.editConfirmPass);
        imgViewPass = view.findViewById(R.id.imgViewPass);
        imgView_ConfirmPass = view.findViewById(R.id.imgView_ConfirmPass);
        btnUpdatePass = view.findViewById(R.id.btnUpdatePass);
        btnExit = view.findViewById(R.id.btnExit);
        buttonChangeName = view.findViewById(R.id.btnChange);
    }
    private void clearBackStackAndStartLoginActivity() {
        // Clear all fragments in the back stack
        getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Start the Login_Account activity
        Intent intent = new Intent(requireActivity(), Login_Account.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish(); // Optional: Close the current activity if needed
    }


    public void addEvent(){
        btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    clearBackStackAndStartLoginActivity();

                }
        });

        //--- Gắn sự kiện cho Img show pasword
        imgViewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility(isPasswordVisible);
            }
        });

        imgView_ConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility_Confirm(isPasswordVisible);
            }
        });

        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editDisplayName.getText().toString();
                updateDisplayName(user,name);
                Toast.makeText(getContext(), "Tên của bạn đã được đổi thành công", Toast.LENGTH_SHORT).show();
                editDisplayName.setText(name);

            }
        });
        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editPassword.getText().toString();
                String confirmPassword = editConfirmPass.getText().toString();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu và xác nhận mật khẩu.", Toast.LENGTH_SHORT).show();
                } else if (isPasswordMatch(newPassword, confirmPassword)) {
                    // Hai mật khẩu giống nhau, thực hiện cập nhật mật khẩu
                    boolean isPasswordUpdated = updatePassword(user, newPassword);
                    if (isPasswordUpdated) {
                        Toast.makeText(getContext(), "Mật khẩu đã được cập nhật thành công.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Có lỗi xảy ra khi cập nhật mật khẩu.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hai mật khẩu không khớp, hiển thị thông báo
                    Toast.makeText(getContext(), "Mật khẩu và xác nhận mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean updatePassword(String user, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("Password", newPassword);

        int rowsAffected = database.update("Account", values, "UserName=?", new String[]{user});

        return rowsAffected > 0;
    }
    public String getDisplayNameByUser(String user) {
        String displayName = null;

        // Select the display name from the database based on the given user name
        String selectQuery = "SELECT tennguoidung FROM Account WHERE UserName = ?";
        String[] selectionArgs = {user};

        // Perform the query
        Cursor cursor = database.rawQuery(selectQuery, selectionArgs);

        // Check if the cursor contains any data
        if (cursor.moveToFirst()) {
            // Get the display name from the cursor
            displayName = cursor.getString(cursor.getColumnIndexOrThrow("TenNguoiDung"));
        }

        // Close the cursor
        cursor.close();

        return displayName;
    }
    public void updateDisplayName(String user, String newDisplayName) {
        // Build the raw SQL query to update the display name
        String updateQuery = "UPDATE Account SET TenNguoiDung = '" + newDisplayName + "' WHERE UserName = '" + user + "'";

        // Execute the raw SQL query to update the display name
        try {
            database.execSQL(updateQuery);
            Toast.makeText(getContext(), "Tên người dùng đã được cập nhật thành công.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Không thể cập nhật tên người dùng.", Toast.LENGTH_SHORT).show();
        }
    }


    //---Hàm để hiển thị mật khẩu
    private void togglePasswordVisibility(boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Nếu đang hiển thị mật khẩu, chuyển đổi sang ảnh "off" và loại nhập liệu text
            imgViewPass.setImageResource(R.drawable.baseline_visibility_off_24);
            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Nếu đang ẩn mật khẩu, chuyển đổi sang ảnh "baseline_remove_red_eye_24" và loại nhập liệu password
            imgViewPass.setImageResource(R.drawable.baseline_visibility_24);
            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // Đặt con trỏ về cuối văn bản trong EditText
        editPassword.setSelection(editPassword.getText().length());
    }

    private void togglePasswordVisibility_Confirm(boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Hiển thị mật khẩu
            imgView_ConfirmPass.setImageResource(R.drawable.baseline_visibility_off_24);
            editConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Ẩn mật khẩu
            imgView_ConfirmPass.setImageResource(R.drawable.baseline_visibility_24);
            editConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // Đặt con trỏ về cuối văn bản trong EditText
        editConfirmPass.setSelection(editConfirmPass.getText().length());
    }

}