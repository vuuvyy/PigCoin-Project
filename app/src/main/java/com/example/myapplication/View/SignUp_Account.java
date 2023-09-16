package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.R;

public class SignUp_Account extends AppCompatActivity {
    EditText editUserName;
    EditText editDisplayName;
    EditText editPassword;
    ImageView imgView_ShowPass;
    EditText editConfirmPass;
    ImageView imgView_ShowPass_Confirm;
    Button btnSignUp;
    TextView tvSignIn;
    SQLiteDatabase database;
    boolean isPasswordVisible = false;

    // Định nghĩa giá trị minLength và maxLength
    int minLength = 5;
    int maxLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_account);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        addControl();
        addEvent();
    }

    private void addControl(){
        editUserName = (EditText) findViewById(R.id.editUserName);
        editDisplayName = (EditText) findViewById(R.id.editDisplayName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        imgView_ShowPass = (ImageView) findViewById(R.id.imgView_ShowPass);
        editConfirmPass = (EditText) findViewById(R.id.editConfirmPass);
        imgView_ShowPass_Confirm = (ImageView) findViewById(R.id.imgView_ShowPass_Confirm);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);
        editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public Boolean insertData(String user, String password, String displayName) {

        ContentValues values = new ContentValues();
        values.put("UserName", user);
        values.put("Password", password);
        values.put("TenNguoiDung", displayName); // Lưu tên người dùng vào cột "TenNguoiDung"
        long result = database.insert("Account", null, values);
//        if (result == -1)
//            return false;
//        else
//            return true;
        if (result == -1) {
            return false;
        }else {
            int maVi = 1; // Mã ví mặc định là 1
            // Gán mã ví là 1
            ContentValues accountValues = new ContentValues();
            accountValues.put("MaVi", maVi);
            database.update("Account", accountValues, "UserName = ?", new String[]{user});
            return true;
        }
    }

    public Boolean checkUserName(String UserName) {

        String query = "SELECT * FROM Account WHERE UserName = ?";
        Cursor cursor = database.rawQuery(query, new String[]{UserName});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkUserNamePassword(String UserName, String Password) {

        String query = "SELECT * FROM Account WHERE UserName = ? AND Password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{UserName, Password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public void addEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editUserName.getText().toString();
                String pass = editPassword.getText().toString();
                String repass = editConfirmPass.getText().toString();
                String displayName = editDisplayName.getText().toString(); // Lấy tên người dùng từ EditText
                String pass1 = editPassword.getText().toString();
                int passLength = pass1.length();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(displayName)) {
                    Toast.makeText(SignUp_Account.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (passLength < minLength || passLength > maxLength) {
                    Toast.makeText(SignUp_Account.this, "Mật khẩu phải từ 5 đến 10 ký tự", Toast.LENGTH_SHORT).show();}

                else {
                    if (pass.equals(repass)) {
                        Boolean checkeuser = checkUserName(user);
                        if (checkeuser == false) {
                            Boolean insert = insertData(user, pass, displayName); // Thêm displayName vào phương thức insertData
                            if (insert == true) {
                                Toast.makeText(SignUp_Account.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login_Account.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp_Account.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp_Account.this, "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp_Account.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgView_ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility(isPasswordVisible);
            }
        });
        imgView_ShowPass_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility(isPasswordVisible);
            }
        });
        imgView_ShowPass_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility_Confirm(isPasswordVisible);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = editDisplayName.getText().toString();

                //Lưu tên người dùng vào SharePreferences
                SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("displayName", displayName);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), Login_Account.class);
                startActivity(intent);
            }
        });
    }
    private void togglePasswordVisibility(boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Hiển thị mật khẩu
            imgView_ShowPass.setImageResource(R.drawable.baseline_visibility_off_24);
            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Ẩn mật khẩu
            imgView_ShowPass.setImageResource(R.drawable.baseline_visibility_24);
            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // Đặt con trỏ về cuối văn bản trong EditText
        editPassword.setSelection(editPassword.getText().length());
    }

    private void togglePasswordVisibility_Confirm(boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Hiển thị mật khẩu
            imgView_ShowPass_Confirm.setImageResource(R.drawable.baseline_visibility_off_24);
            editConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Ẩn mật khẩu
            imgView_ShowPass_Confirm.setImageResource(R.drawable.baseline_visibility_24);
            editConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // Đặt con trỏ về cuối văn bản trong EditText
        editConfirmPass.setSelection(editConfirmPass.getText().length());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}