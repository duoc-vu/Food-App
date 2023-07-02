package com.example.btl_last;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;


public class AddMonAnActivity extends AppCompatActivity {

    EditText edtName, edtChuThich, edtDiaChi;
    ImageView imgHinh;


    ImageButton btnCam, btnLib, btnSave, btnCancel;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_mon_an);

        AnhXa();
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
                Intent intent = new Intent(AddMonAnActivity.this , DanhSachActivity.class);
                //chuyen data cua imageview sang mang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap  bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG , 100 , byteArrayOutputStream );
                byte[] hinh = byteArrayOutputStream.toByteArray();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DanhSachActivity.database.InsertMonAn(
                            edtName.getText().toString().trim(),
                            edtChuThich.getText().toString().trim(),
                            edtDiaChi.getText().toString().trim(),

                            hinh
                    );
                }
                Toast.makeText(AddMonAnActivity.this , " Da them " , Toast.LENGTH_SHORT).show();

                startActivity(intent);


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    private void AnhXa() {
        btnSave = (ImageButton) findViewById(R.id.dialogSave);
            btnCancel = (ImageButton) findViewById(R.id.dialogHuy) ;
            edtName = (EditText) findViewById(R.id.actiNam) ;
            edtChuThich = (EditText) findViewById(R.id.actiChuThich) ;
            edtDiaChi = (EditText) findViewById(R.id.actiDiaChi) ;
            imgHinh = (ImageView) findViewById(R.id.dialogImgHinh);
            btnCam = (ImageButton) findViewById(R.id.dialogCam);
            btnLib = (ImageButton) findViewById(R.id.dialogLib) ;
        }


//   EditText etName, etChuThich, etDiaChi;
//    ImageView imageView;
//    ImageButton imageButton, imageButton2;
//    String msg ="";
//

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mon_an_chi_tiet);
//       getViews();
//        //khai bao 1 doi tuong intent de chua doi tuong intent duoc truyen sang
//        //thong qua phuong thuc getIntent()
//        Intent intentReceipt = getIntent();
//        msg = intentReceipt.getStringExtra("action");
//        if(msg.equals("update")){
//            //set du lieu len cac doi tuong view
//            monAn monAn =(monAn) intentReceipt.getSerializableExtra("monAn");
//            etName.setText(monAn.getName());
//            etChuThich.setText(monAn.getChuThich());
//            etDiaChi.setText(monAn.getDiaChi());
//            imgHinh.setImageResource(monAn.getImg());
//        }
 // }
//
//    public void onSaveNote(View view) {
//        if(msg.equals("insert")){
//            //put du lieu ve phia MainActivity
//            Intent intentResult= new Intent();
//            intentResult.putExtra("action", "insert");
//            //lay ra doi tuong ghi chu va truyen ve MainActivity
//            monAn monAn = getMonAn();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("monAn", monAn);
//            //day doi tuong  bundle vao intent
//            intentResult.putExtras(bundle);
//            setResult(RESULT_OK,intentResult);
//            finish();
//        }else if(msg.equals("update")){
//            //day du lieu ve MainActivity
//            Intent intent = new Intent();
//            intent.putExtra("action","update");
//            monAn monAn = getMonAn();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("monAn", monAn);
//            //day doi tuong  bundle vao intent
//            intent.putExtras(bundle);
//            setResult(RESULT_OK,intent);
//            finish();
//        }
//    }
//  private void getViews(){
//        etName = findViewById(R.id.etName);
//        etChuThich = findViewById(R.id.etChuThich);
//       etDiaChi = findViewById(R.id.etDiaChi);
//       imageView = findViewById(R.id.imageView);
//
//       imageButton = findViewById(R.id.imageButton);
//      imageButton2 = findViewById(R.id.imageButton2);
//    }
//    //phuong thuc lay ra doi tuong ghi chu - note
//    private monAn getMonAn(){
//        String Name = etName.getText().toString();
//        String chuThich = etChuThich.getText().toString();
//        String diaChi = etDiaChi.getText().toString();
//
//        int img = imgHinh.setImageResource(monAn.getImg());
//
//
//        return new monAn(Name,chuThich,diaChi,img);
     }


