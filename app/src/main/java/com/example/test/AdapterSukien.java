package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterSukien extends BaseAdapter {
    ActivitySuKien context;
    private int layout;
    List<Sukien> sukienList;


    public AdapterSukien(ActivitySuKien context, int layout, List<Sukien> sukienList) {
        this.context = context;
        this.layout = layout;
        this.sukienList = sukienList;
    }

    @Override
    public int getCount() {
        return sukienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtTenSukien, txtDiadiemSukien, txtMotaSukien, txtNgaySukien, txtGioSukien;
        ImageView imgSuaSukien, imgXoaSukien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.txtTenSukien=(TextView)convertView.findViewById(R.id.textviewTensukien);
            viewHolder.txtDiadiemSukien=(TextView)convertView.findViewById(R.id.textviewDiadiemsukien);
            viewHolder.txtMotaSukien=(TextView)convertView.findViewById(R.id.textviewMotasukien);
            viewHolder.txtNgaySukien=(TextView)convertView.findViewById(R.id.textviewNgaysukien);
            viewHolder.txtGioSukien=(TextView)convertView.findViewById(R.id.textviewGiosukien);



            viewHolder.imgSuaSukien=(ImageView) convertView.findViewById(R.id.imgSuasukien);
            viewHolder.imgXoaSukien=(ImageView) convertView.findViewById(R.id.imgXoasukien);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        final Sukien sukien=sukienList.get(position);

        viewHolder.txtTenSukien.setText("Tên sự kiện: "+sukien.getTensukien());
        viewHolder.txtMotaSukien.setText("Mô tả: "+sukien.getMota());
        viewHolder.txtDiadiemSukien.setText("Địa điểm: "+sukien.getDiadiem());
        viewHolder.txtNgaySukien.setText("Ngày: "+sukien.getNgay());
        viewHolder.txtGioSukien.setText("Giờ: "+sukien.getGio());


        viewHolder.imgSuaSukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaSuKien(sukien.getId(), sukien.getTensukien(), sukien.getMota(),sukien.getDiadiem(),sukien.getNgay(),sukien.getGio());
            }
        });

        viewHolder.imgXoaSukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaSuKien(sukien.getTensukien(), sukien.getId());
            }
        });
        return convertView;
    }
}
