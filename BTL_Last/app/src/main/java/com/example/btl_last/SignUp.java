package com.example.btl_last;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText edUser , edPass , edComfirm ;
    Button btnSignUp , btnBack;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AnhXa();
        DB = new DBHelper(this);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getText().toString();
                String pass = edPass.getText().toString();
                String Cfpass = edComfirm.getText().toString();


                Pattern pattern;
                Matcher matcher;

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=])(?=\\S+$).{6,}$";

                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(pass);

                if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass) || TextUtils.isEmpty(Cfpass)) {
                    Toast.makeText(SignUp.this, "Yêu cầu nhập đầy đủ" ,Toast.LENGTH_SHORT).show();
                }else {
                    if (matcher.matches() == true){
                        if (pass.equals(Cfpass)){
                            Boolean checkuser = DB.checkUser(user);
                            if (checkuser == false){
                                Boolean insert = DB.InSData(user , pass);
                                if(insert == true){
                                    Toast.makeText(SignUp.this,"Đăng kí thành công" ,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(SignUp.this,"Đăng kí thất bại" , Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SignUp.this,"Tên tài khoản đã tồn tại" , Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(SignUp.this,"Mật khẩu không trùng khớp" , Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUp.this,"Mật khẩu không đúng định dạng phải có chữ a-z, A-Z, chứ ký tự .@#$%^&+= và dài từ 6 ký tự trở lên " , Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        edUser = (EditText) findViewById(R.id.edUser);
        edPass = (EditText) findViewById(R.id.edPassword);
        edComfirm = (EditText) findViewById(R.id.edComfirmPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnBack = (Button) findViewById(R.id.btnBack);


    }
}