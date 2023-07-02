package com.example.btl_last;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SuaMonAnActivity extends AppCompatActivity {

    EditText edtName, edtChuThich, edtDiaChi;
    ImageView imgHinh;
    ImageButton btnCam, btnLib, btnSave, btnCancel;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intent.getBundleExtra("bundle");

        edtName.setText(bundle.getString("ten"));
        edtChuThich.setText(bundle.getString("chuthich"));
        edtDiaChi.setText(bundle.getString("diadiem"));
        //Chuyển mảng byte qua bitmap
        byte[] hinhanh = bundle.getByteArray("anh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh , 0 , hinhanh.length);
        imgHinh.setImageBitmap(bitmap);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE) ;

                startActivityForResult(intent,REQUEST_CODE_CAMERA) ;

            }
        });
        btnLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER) ;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //chuyen data cua imageview sang mang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG , 90 , byteArrayOutputStream );
                byte[] hinh = byteArrayOutputStream.toByteArray();

//                DanhSachActivity.database.UpDate(,edtName.getText().toString().trim(),
//                        edtChuThich.getText().toString().trim(),
//                        edtDiaChi.getText().toString().trim()
//                        );
//                DanhSachActivity.database.InsertMonAn(
//                        edtName.getText().toString().trim(),
//                        edtChuThich.getText().toString().trim(),
//                        edtDiaChi.getText().toString().trim(),
//                        hinh
//                );
                Toast.makeText(SuaMonAnActivity.this , " Đã Sửa " , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SuaMonAnActivity.this , DanhSachActivity.class));
            }
        });
    }

    private void AnhXa() {
        btnSave = (ImageButton) findViewById(R.id.dialogSave);
        btnCancel = (ImageButton) findViewById(R.id.dialogHuy) ;
        edtName = (EditText) findViewById(R.id.dialogedName) ;
        edtChuThich = (EditText) findViewById(R.id.dialogedChuThich) ;
        edtDiaChi = (EditText) findViewById(R.id.dialogedDiaChi) ;
        imgHinh = (ImageView) findViewById(R.id.dialogImgHinh);
        btnCam = (ImageButton) findViewById(R.id.dialogCam);
        btnLib = (ImageButton) findViewById(R.id.dialogLib) ;
    }
}