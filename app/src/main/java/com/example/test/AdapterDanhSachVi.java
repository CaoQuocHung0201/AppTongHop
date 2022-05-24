package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterDanhSachVi extends BaseAdapter {
    DanhSachVi context;
    private int layout;
    List<ObjectVi> listVi;

    public AdapterDanhSachVi(DanhSachVi context, int layout, List<ObjectVi> listVi) {
        this.context = context;
        this.layout = layout;
        this.listVi = listVi;
    }

    @Override
    public int getCount() {
        return listVi.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView Tenvi, Sotien;
        ImageView imgSuaVi, imgXoavi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.Tenvi=(TextView)convertView.findViewById(R.id.textviewSotiengd);
            viewHolder.Sotien=(TextView)convertView.findViewById(R.id.textviewSotien);

            viewHolder.imgSuaVi=(ImageView) convertView.findViewById(R.id.imageViewSuavi);
            viewHolder.imgXoavi=(ImageView) convertView.findViewById(R.id.imageViewXoavi);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        final ObjectVi vi=listVi.get(position);

        viewHolder.Tenvi.setText("Tên ví: "+vi.getTen());
        viewHolder.Sotien.setText("Số tiền còn lại: "+vi.getSotien());

        viewHolder.imgSuaVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaVi(vi.getId(), vi.getTen(), vi.getSotien());
            }
        });
        viewHolder.imgXoavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaVi(vi.getTen(), vi.getId());
            }
        });

        return convertView;
    }
}
