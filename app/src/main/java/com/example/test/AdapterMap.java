package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterMap extends BaseAdapter {


    ActivityDanhSachMap context;
    private int layout;
    List<ObjectMap> listMap;

    public AdapterMap(ActivityDanhSachMap context, int layout, List<ObjectMap> listMap) {
        this.context = context;
        this.layout = layout;
        this.listMap = listMap;
    }

    @Override
    public int getCount() {
        return listMap.size();
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
        TextView txtTenvitri, txtMota;
        ImageView imgHinhanhmap, imgSuamap, imgXoamap;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.txtTenvitri=(TextView)convertView.findViewById(R.id.textviewTenvitri);
            viewHolder.txtMota=(TextView)convertView.findViewById(R.id.textviewMotavitri);
            viewHolder.imgHinhanhmap=(ImageView)convertView.findViewById(R.id.imageHinhvitri);
            viewHolder.imgSuamap=(ImageView)convertView.findViewById(R.id.imageviewSuavitri);
            viewHolder.imgXoamap=(ImageView)convertView.findViewById(R.id.imageviewXoavitri);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        ObjectMap map=listMap.get(position);

        viewHolder.txtTenvitri.setText("Tên vị trí: "+map.getTen());
        viewHolder.txtMota.setText("Mô tả: "+map.getMota());


        //chuuyển mảng byte[] -> bitmap

        byte[]hinhAnh=map.getHinh();

        Bitmap bitmap= BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);

        viewHolder.imgHinhanhmap.setImageBitmap(bitmap);

        viewHolder.imgSuamap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaMap(map.getId(),map.getTen(),map.getMota());
            }
        });

        viewHolder.imgXoamap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaMap(map.getId(), map.getTen());
            }
        });



        return convertView;
    }
}
