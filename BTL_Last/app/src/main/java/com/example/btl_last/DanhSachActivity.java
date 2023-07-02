package com.example.btl_last;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class DanhSachActivity extends AppCompatActivity {
    FloatingActionButton addMonAn;
    public static Database database;
    ArrayList<monAn> arrayMonAn;
    ListView lvDanhsach;
    AdapterMonAn adapterMonAn;
    ImageView imgDelete;
    int REQUEST_CODE_LV = 789;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    ImageView imgGan;

    View screenView;
    TextView txtDem;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 19){
                            Intent intent = result.getData();
                            if (intent != null){
                                Bundle bundle = new Bundle();
                                bundle = intent.getExtras();
                                int id = bundle.getInt("ID");
                                String tenmoi = bundle.getString("ten");
                                String chuThich = bundle.getString("chuThich");
                                String diaChi = bundle.getString("diaChi");
                                byte[] hinh = bundle.getByteArray("hinh");
                                database.UpDate( id , tenmoi , chuThich , diaChi , hinh);
                                getDataMonAn();


                            }
                            Toast.makeText(DanhSachActivity.this,"Đã sửa!!!" , Toast.LENGTH_SHORT).show();
                        }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);

        addMonAn = (FloatingActionButton) findViewById(R.id.addMonAn);
        lvDanhsach = (ListView) findViewById(R.id.lvDanhsach);
      //  imgDelete = findViewById(R.id.imgDelete);
//        edSearch = (EditText) findViewById(R.id.search);

//        ArrayAdapter<String> adapter = new ArrayAdapter<monAn>(DanhSachActivity.this,
//                android.R.layout.simple_dropdown_item_1line,adapterMonAn.get);
//        edSearch.setAdapter(adapter);

        arrayMonAn = new ArrayList<>();
        adapterMonAn = new AdapterMonAn(this , R.layout.dong_mon_an , arrayMonAn);
        lvDanhsach.setAdapter(adapterMonAn);
        screenView = findViewById(R.id.activity_danh_sach);
      //  txtDem = findViewById(R.id.txtCount);

        database = new Database(this , "QuanLyMonAn.sqlite" , null , 1);
//        database.drop();
        database.QueryData("CREATE TABLE IF NOT EXISTS monAn(ID INTEGER PRIMARY KEY AUTOINCREMENT , tenMonAn varchar(150) , chuThich varchar(250) , diaChi varchar(250) , hinh BLOB )");
//private static final String DATABASE_CREATE_NEWS= "create table news (_id integer primary key autoincrement, "title text not null, description text not null, date text not null, LastModified text not null, UNIQUE(title, date) ON CONFLICT REPLACE);";
//        String[] ten ;

        //get do an
        addMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachActivity.this, AddMonAnActivity.class);

                startActivity(intent);
            }
        });

        lvDanhsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(DanhSachActivity.this , MonAnChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id" , arrayMonAn.get(i).getID());
                bundle.putString("ten" , arrayMonAn.get(i).getName() );
                bundle.putString("chuthich" , arrayMonAn.get(i).getChuThich());
                bundle.putString("diadiem" , arrayMonAn.get(i).getDiaChi());

                bundle.putByteArray("anh" , arrayMonAn.get(i).getHinh());
                intent.putExtras(bundle);
                activityResultLauncher.launch(intent);
            }
        });


        getDataMonAn();
        registerForContextMenu(lvDanhsach);

        getDataMonAn();
        adapterMonAn.notifyDataSetChanged();
    }


    private void getDataMonAn (){
        Cursor cursor = database.getData("SELECT * FROM monAn");
        arrayMonAn.clear();
        while (cursor.moveToNext()){
            arrayMonAn.add(new monAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        adapterMonAn.notifyDataSetChanged();
    }





    public void dialogXoa(String ten , int ID){
        AlertDialog.Builder diaLogXoa = new AlertDialog.Builder(this);
        diaLogXoa.setMessage("Bạn có muốn xóa " + ten + " không ? ");

        diaLogXoa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM monAn WHERE tenMonAn ='" + ten +"' AND ID = " + ID );
                getDataMonAn();
                Toast.makeText(DanhSachActivity.this , "Đã Xóa" + ten + "!!!!!" , Toast.LENGTH_SHORT ).show();
            }
        });
        diaLogXoa.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        diaLogXoa.show();
    }

//    public void xoaALL(){
//        AlertDialog.Builder diaLogAll = new AlertDialog.Builder(this);
//        diaLogAll.setMessage("Bạn có muốn xóa hết ? ");
//        diaLogAll.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                database.delAll();
//                getDataMonAn();
//                adapterMonAn.notifyDataSetChanged();
//                Toast.makeText(DanhSachActivity.this , "Đã xóa hết" , Toast.LENGTH_SHORT ).show();
//            }
//        });
//        diaLogAll.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        diaLogAll.show();
//    }



    private void doiNen() {

        screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dachinh));



    }

    private void sapXepDiaChi (){
        Cursor cursor = database.getData("SELECT * FROM monAn ORDER BY diaChi COLLATE NOCASE ASC");
        arrayMonAn.clear();
        while (cursor.moveToNext()){
            arrayMonAn.add(new monAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        Toast.makeText(DanhSachActivity.this,"Sắp xếp theo địa chỉ" , Toast.LENGTH_SHORT).show();
        adapterMonAn.notifyDataSetChanged();
    }















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo ,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

           case R.id.defau:
                getDataMonAn();
              Toast.makeText(DanhSachActivity.this,"Mặc Định" , Toast.LENGTH_SHORT).show();
               break;

            case R.id.timTheoTen:
                dialogTim();
                break;
            case R.id.sapXep:
                sapXepTheoTen();
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    public void dialogTim(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tim);

        EditText diaLogTim = (EditText) dialog.findViewById(R.id.dialogedTim);
        Button diaLogbtnTim = (Button) dialog.findViewById(R.id.dialogbtnTim);


        diaLogbtnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenTim = diaLogTim.getText().toString().trim();
                Cursor cursor = database.getData("SELECT * FROM monAn WHERE tenMonAn like '%" + tenTim +"%'");
                arrayMonAn.clear();
                while (cursor.moveToNext()){
                    arrayMonAn.add(new monAn(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getBlob(4)
                    ));
                }
                dialog.dismiss();
                adapterMonAn.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

//    public void Search(String s) {
//        s = s.toUpperCase();
//        int k = 0;
//        for (int i = 0; i < arrayMonAn.size(); i++) {
//            monAn g = arrayMonAn.get(i);
//            String td = g.getName().toUpperCase();
//            if (td.indexOf(s) >= 0) {
//                arrayMonAn.set(i, arrayMonAn.get(k));
//                arrayMonAn.set(k, g);
//                k++;
//            }
//        }
//        adapterMonAn.notifyDataSetChanged();
//    }


    private void sapXepTheoTen (){
        Cursor cursor = database.getData("SELECT * FROM monAn ORDER BY tenMonAn COLLATE NOCASE ASC");
        arrayMonAn.clear();
        while (cursor.moveToNext()){
            arrayMonAn.add(new monAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        adapterMonAn.notifyDataSetChanged();
    }

}

//        public void LocDiaChi(String s) {
//        s = s.toUpperCase();
//        int k = 0;
//        for (int i = 0; i < arrayMonAn.size(); i++)
//        {
//            for (int j = i + 1; j < arrayMonAn.size(); j++) {
//                if (arrayMonAn.get(i).getDiaChi().compareTo(arrayMonAn.get(j).getDiaChi())>0)
//                {
//                    arrayMonAn.set(i , arrayMonAn.get(j));
//                    arrayMonAn.set(j, arrayMonAn.get(i));
//
//                }
//            }
//        }
//        adapterMonAn.notifyDataSetChanged();
//    }


    //        edSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                String enteredText = edSearch.getText().toString();
////                refreshList(enteredText);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                String s = edSearch.getText().toString();
////                Search(s);
//            }
//        });



//    public void LocDiaChi(String s) {
//        s = s.toUpperCase();
//        int k = 0;
//        for (int i = 0; i < arrayMonAn.size(); i++)
//        {
//            for (int j = i + 1; j < arrayMonAn.size(); j++) {
//                if (arrayMonAn.get(i).getDiaChi().compareTo(arrayMonAn.get(j).getDiaChi())>0)
//                {
//                    arrayMonAn.set(i , arrayMonAn.get(j));
//                    arrayMonAn.set(j, arrayMonAn.get(i));
//
//                }
//            }
//        }
//        adapterMonAn.notifyDataSetChanged();
//    }



//    public void refreshList(String text) {
//        arrayMonAn.get() = SomeClassWithStaticMethods.getSomeData(text);
//
//        list.setAdapter(new ArrayAdapter<String>(this, R.layout.single_row, R.id.singleRow, items));
//    }
//}

    /*ListView lvMonAn ;
    ArrayList<monAn> ArrayMonAn;
    AdapterMonAn adapterMonAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);

        AnhXa();

        adapterMonAn = new AdapterMonAn(this , R.layout.dong_mon_an , ArrayMonAn);
        lvMonAn.setAdapter(adapterMonAn);
    }

    private void AnhXa() {
        lvMonAn = (ListView) findViewById(R.id.lvDanhsach);

        ArrayMonAn = new ArrayList<>();


        ArrayMonAn.add(new monAn( "Cá hồi hộp" , "Trình bày 10 điểm", "Vincom Royal City", R.drawable.mon1 ));
        ArrayMonAn.add(new monAn("Salad cá hồi tưởng" , "Dịch vụ ổn" , "Tòa Lê Văn Lương", R.drawable.mon2 ));
        ArrayMonAn.add(new monAn("Su cái shi" , "Chụp ảnh đẹp" , "96 Định Công", R.drawable.mon3 ));
        ArrayMonAn.add(new monAn( "Pizza không phải Hut" , "Cực kì ngon và đảm bảo" , "616 Trương Định", R.drawable.mon4 ));

        ArrayMonAn.add(new monAn( "Cá hồi hộp" , "Trình bày 10 điểm", "Vincom Royal City", R.drawable.mon1 ));
        ArrayMonAn.add(new monAn("Salad cá hồi tưởng" , "Dịch vụ ổn" , "Tòa Lê Văn Lương", R.drawable.mon2 ));
        ArrayMonAn.add(new monAn("Su cái shi" , "Chụp ảnh đẹp" , "96 Định Công", R.drawable.mon3 ));
        ArrayMonAn.add(new monAn( "Pizza không phải Hut" , "Cực kì ngon và đảm bảo" , "616 Trương Định", R.drawable.mon4 ));

    public void dialogSuaMonAn( int id , String ten , String chuThich , String diaChi , byte[] hinh){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_sua_mon_an);

        ImageView imghinh = dialog.findViewById(R.id.dialogImgHinh);
        EditText edName = dialog.findViewById(R.id.dialogedName);
        EditText edchuThich = dialog.findViewById(R.id.dialogedChuThich);
        EditText edDiaChi = dialog.findViewById(R.id.dialogedDiaChi);
        ImageButton Save = (ImageButton) dialog.findViewById(R.id.dialogSave);
        ImageButton Huy = (ImageButton) dialog.findViewById(R.id.dialogHuy);
        ImageButton Cam = (ImageButton) dialog.findViewById(R.id.dialogCam);
        ImageButton Lib = (ImageButton) dialog.findViewById(R.id.dialogLib);


        edName.setText(ten);
        edchuThich.setText(chuThich);
        edDiaChi.setText(diaChi);
//        //chuyen data cua imageview sang mang byte
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) imghinh.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG , 90 , byteArrayOutputStream );
//        byte[] hinh = byteArrayOutputStream.toByteArray();

        //Chuyển mảng byte qua bitmap
        byte[] hinhanh = hinh;
        Bitmap bitmapFac = BitmapFactory.decodeByteArray(hinhanh , 0 , hinhanh.length);
        imghinh.setImageBitmap(bitmapFac);

        //        //chuyen data cua imageview sang mang byte

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmoi = edName.getText().toString().trim();
                String chuThichMoi = edchuThich.getText().toString().trim();
                String diaChiMoi = edDiaChi.getText().toString().trim();
                //chuyen data cua imageview sang mang byte
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgGan.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG , 90 , byteArrayOutputStream );
//                byte[] hinhAnh = byteArrayOutputStream.toByteArray();
                database.QueryData("UPDATE monAn SET tenMonAn ='" + tenmoi + "' , chuThich ='" + chuThichMoi
                        + "' , diaChi = '"+ diaChiMoi   +"' WHERE ID = "+ id +"");
                Toast.makeText(DanhSachActivity.this,"Đã cập nhật" , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataMonAn();
            }
        });

        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sapXepTheoTen (){
        Cursor cursor = database.getData("SELECT * FROM monAn ORDER BY tenMonAn COLLATE NOCASE ASC");
        arrayMonAn.clear();
        while (cursor.moveToNext()){
            arrayMonAn.add(new monAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        adapterMonAn.notifyDataSetChanged();
    }

public void suaMonAn (String ten , int id){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String chuThich = bundle.getString("chuThich").trim();
        String diaDiem = bundle.getString("diaDiem").trim();
        byte[] hinh = bundle.getByteArray("hinhanh");
        database.QueryData("UPDATE MonAn SET tenMonAn = '" + ten
                + "', chuThich = '" + chuThich + "' , diaChi = '" + diaDiem  +"' WHERE ID = "+ id +"");
    }


//                                database.QueryData("UPDATE monAn SET tenMonAn ='" + tenmoi + "' , chuThich ='" + chuThich
//                                        + "' , diaChi = '"+ diaChi  +"' , hinh = " + hinh +" WHERE ID = "+ id +"");

     */