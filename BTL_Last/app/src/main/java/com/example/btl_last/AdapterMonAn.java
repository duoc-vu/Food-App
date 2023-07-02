package com.example.btl_last;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterMonAn extends BaseAdapter {

    private DanhSachActivity context;
    private int layout;
    private List<monAn> monAnList;

    public AdapterMonAn(DanhSachActivity context, int layout, List<monAn> monAnList) {
        this.context = context;
        this.layout = layout;
        this.monAnList = monAnList;
    }

    @Override
    public int getCount() {
        return monAnList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        ImageView hinh , imgDelete , imgSua;
        TextView txtName , txtChuThich, txtDiaChi;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();

            viewHolder.imgSua = (ImageView) view.findViewById(R.id.imgSua);
            viewHolder.txtName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.txtChuThich = (TextView) view.findViewById(R.id.txtChuThich);
            viewHolder.txtDiaChi = (TextView) view.findViewById(R.id.txtDiaChi);
            viewHolder.imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            viewHolder.hinh = (ImageView)view.findViewById(R.id.hinh);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        monAn monAn = monAnList.get(i);
        viewHolder.txtName.setText(monAn.getName());
        viewHolder.txtChuThich.setText(monAn.getChuThich());
        viewHolder.txtDiaChi.setText(monAn.getDiaChi());


        //Chuyển mảng byte qua bitmap
        byte[] hinhanh = monAn.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh , 0 , hinhanh.length);

        viewHolder.hinh.setImageBitmap(bitmap);
//        viewHolder.imgSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.dialogSuaMonAn(monAn.getID() , monAn.getName() , monAn.getChuThich() , monAn.getDiaChi() , monAn.getHinh());
//                Toast.makeText(context,"Đã sửa" + monAn.getName() , Toast.LENGTH_SHORT).show();
//            }
//        });



        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogXoa(monAn.getName() , monAn.getID());
                Toast.makeText(context,"Đã xóa " + monAn.getName() + "!!!" , Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
