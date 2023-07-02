package com.example.btl_last;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MonAnChiTietActivity extends AppCompatActivity {

    EditText edName, edChuThich, edDiaChi;

    ImageView imgHinh;
    ImageButton btnCam, btnLib, btnSave, btnCancel;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_chi_tiet);

        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intent.getBundleExtra("bundle");

        id = bundle.getInt("id");
        edName.setText(bundle.getString("ten"));
        edChuThich.setText(bundle.getString("chuthich"));
        edDiaChi.setText(bundle.getString("diadiem"));
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
//
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenmoi = edName.getText().toString().trim();
                String chuThichMoi = edChuThich.getText().toString().trim();
                String diaChiMoi = edDiaChi.getText().toString().trim();

                //chuyen data cua imageview sang mang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG , 100 , byteArrayOutputStream );
                byte[] hinh = byteArrayOutputStream.toByteArray();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("ten" , tenmoi.trim());
                bundle.putInt("ID" , id);
                bundle.putString("chuThich" , chuThichMoi.trim());
                bundle.putString("diaChi" , diaChiMoi.trim());
                bundle.putByteArray("hinh" , hinh);
                intent.putExtras(bundle);

//                DanhSachActivity.database.UpDate(id ,edtName.getText().toString().trim(),
//                        edtChuThich.getText().toString().trim(),
//                        edtDiaChi.getText().toString().trim()
//                        );



//                DanhSachActivity.database.InsertMonAn(
//                        edtName.getText().toString().trim(),
//                        edtChuThich.getText().toString().trim(),
//                        edtDiaChi.getText().toString().trim(),
//                        hinh
//                );
                setResult(19 , intent);
                finish();
                MonAnChiTietActivity.super.onBackPressed();
            }
        });
//
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("ten" , edtName.getText().toString());
//                bundle.putString("chuThich" , edtChuThich.getText().toString());
//                bundle.putString("diaDiem" , edtDiaChi.getText().toString());
//
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG , 90 , byteArrayOutputStream );
//                byte[] hinh = byteArrayOutputStream.toByteArray();
//
//                bundle.putByteArray("hinhanh" , hinh);
//                intent.putExtra("bundle" , bundle);
//                setResult(RESULT_OK , intent);
//                startActivity(intent);
                finish();
            }
        });
//
//    }
//
    }
    private void AnhXa() {
        btnSave = (ImageButton) findViewById(R.id.ActiSave);
        btnCancel = (ImageButton) findViewById(R.id.ActiDel) ;
        edName = (EditText) findViewById(R.id.actiNam) ;
        edChuThich = (EditText) findViewById(R.id.actiChuThich) ;
        edDiaChi = (EditText) findViewById(R.id.actiDiaChi) ;
        imgHinh = (ImageView) findViewById(R.id.ActiImg);
        btnCam = (ImageButton) findViewById(R.id.ActiCam);
        btnLib = (ImageButton) findViewById(R.id.ActiLib) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}