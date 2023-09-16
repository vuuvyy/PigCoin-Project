package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.R;

public class Login_Account extends AppCompatActivity {
    EditText editUserName;
    EditText editPassWord;
    ImageButton imgButton_SeePass;
    CheckBox checkBoxRemember;
    TextView tvForgetPass;
    Button btnLogin;
    TextView tvSignUp;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    boolean isPasswordVisible = false;
    SharedPreferences sharedPreferences;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);
        addControl();
        addEvent();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        loadSavedCredentials();
    }
    private void addControl(){
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassWord = (EditText) findViewById(R.id.editPassword);
        imgButton_SeePass = (ImageButton) findViewById(R.id.imgButton_SeePass);
        checkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);


        //lưu hình ảnh cũ của showpass
//        imgButton_SeePass.setImageResource(R.drawable.baseline_visibility_24);
    }

    public Boolean checkEmailPassword(String UserName, String Password) {

        String query = "SELECT * FROM Account WHERE UserName = ? AND Password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{UserName, Password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = editUserName.getText().toString();
                String pass = editPassWord.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login_Account.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkEmailPassword = checkEmailPassword(user, pass);
                    if (checkEmailPassword) {
                        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        // After successful login


                    } else {
                        Toast.makeText(Login_Account.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Lấy dữ liệu từ SharePreference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("remember_password", isChecked);
                editor.apply();
            }
        });


        imgButton_SeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility(isPasswordVisible);
            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Account_Info.class);

                //Shareferences
                startActivity(intent);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_Account.class);
                startActivity(intent);
            }
        });

    }

    //Xử lí checkbox Remember me
    @Override
    protected void onPause() {
        super.onPause();
        saveCredentials(editUserName.getText().toString(), editPassWord.getText().toString());
    }
    private void loadSavedCredentials() {
        boolean rememberPassword = sharedPreferences.getBoolean("remember_password", false);
        checkBoxRemember.setChecked(rememberPassword);
        if (rememberPassword) {
            String user = sharedPreferences.getString("user", "");
            String password = sharedPreferences.getString("password", "");
            editUserName.setText(user);
            editPassWord.setText(password);
        }
    }

    private void saveCredentials(String user, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", user);
        editor.putString("password", password);
        editor.apply();
    }

    //----Hàm để xử lí để hiển thị mật khẩu
    private void togglePasswordVisibility(boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Nếu đang hiển thị mật khẩu, chuyển đổi sang ảnh "off" và loại nhập liệu text
            imgButton_SeePass.setImageResource(R.drawable.baseline_visibility_off_24);
            editPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Nếu đang ẩn mật khẩu, chuyển đổi sang ảnh "baseline_remove_red_eye_24" và loại nhập liệu password
            imgButton_SeePass.setImageResource(R.drawable.baseline_visibility_24);
            editPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // Đặt con trỏ về cuối văn bản trong EditText
        editPassWord.setSelection(editPassWord.getText().length());
    }
}