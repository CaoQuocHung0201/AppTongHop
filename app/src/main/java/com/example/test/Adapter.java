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

public class Adapter extends BaseAdapter {
    ActivityThoiKhoaBieu context;
    private int layout;
    List<ThoiKhoaBieu> thoiKhoaBieuList;

    public Adapter(ActivityThoiKhoaBieu context, int layout, List<ThoiKhoaBieu> thoiKhoaBieuList) {
        this.context = context;
        this.layout = layout;
        this.thoiKhoaBieuList = thoiKhoaBieuList;
    }

    @Override
    public int getCount() {
        return thoiKhoaBieuList.size();
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
        TextView txtThu, txtTKBSang, txtTKBChieu;
        ImageView imgSua, imgXoa;
//        ImageView imgXoatextTKBsang, imgXoatextTKBchieu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.txtThu=(TextView)convertView.findViewById(R.id.textViewThu);
            viewHolder.txtTKBSang=(TextView)convertView.findViewById(R.id.textViewTKBBuoisang);
            viewHolder.txtTKBChieu=(TextView)convertView.findViewById(R.id.textViewTKBchieu);
            viewHolder.imgSua=(ImageView) convertView.findViewById(R.id.imgEdit);
            viewHolder.imgXoa=(ImageView) convertView.findViewById(R.id.imgDelete);
//            viewHolder.imgXoatextTKBsang=(ImageView)convertView.findViewById(R.id.xoatextTKBSang);
//            viewHolder.imgXoatextTKBchieu=(ImageView)convertView.findViewById(R.id.xoatextTKBChieu);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        final ThoiKhoaBieu thoiKhoaBieu=thoiKhoaBieuList.get(position);

        viewHolder.txtThu.setText(thoiKhoaBieu.getThu());
        viewHolder.txtTKBSang.setText(thoiKhoaBieu.getThoiKhoaBieuSang());
        viewHolder.txtTKBChieu.setText(thoiKhoaBieu.getThoiKhoaBieuChieu());

        viewHolder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaTKB(thoiKhoaBieu.getId(), thoiKhoaBieu.getThoiKhoaBieuSang(), thoiKhoaBieu.getThoiKhoaBieuChieu());
            }
        });

        viewHolder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaTKB(thoiKhoaBieu.getId(), thoiKhoaBieu.getThu());
            }
        });




        return convertView;
    }
}
