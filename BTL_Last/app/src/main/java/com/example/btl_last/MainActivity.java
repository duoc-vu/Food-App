package com.example.btl_last;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imgLogo , imgFb , imgIns , imgSms  ;
    TextView txtLogin , txtResign;
    EditText edusser , edppass, edpass2;
    DBHelper Db;

    Button btnSignIn , btnResign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgFb = findViewById(R.id.imgFB);
        imgIns = findViewById(R.id.imgIns);
        imgLogo = findViewById(R.id.imgLogo);
        imgSms = findViewById(R.id.imgSms);


        btnResign = findViewById(R.id.btnResign);

        Db = new DBHelper(this);
        btnResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        edusser = (EditText) findViewById(R.id.edUserIn);
        edppass = (EditText) findViewById(R.id.edPasswordIn);
      //  edpass2 = (EditText) findViewById(R.id.edPasswordIn);


        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edusser.getText().toString();
                String pass = edppass.getText().toString();


                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this,"Không được bỏ trống" , Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkuserpass = Db.checkUserPassword(user, pass);
                    if (checkuserpass == true){
                        Toast.makeText(MainActivity.this,"Đằng nhập thành công" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DanhSachActivity.class);
                        startActivity(intent);
                    }else {
                       // edpass2.setBackgroundColor(Color.RED);
                        Toast.makeText(MainActivity.this,"Đăng nhập thất bại" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/duoc1912"));
                startActivity(intent);
            }
        });

        imgIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/duoc_vu_1912/"));
                startActivity(intent);
            }
        });

        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body" , "..." );
                intent.setData(Uri.parse("sms:0961018394"));
                startActivity(intent);
            }
        });


    }
}