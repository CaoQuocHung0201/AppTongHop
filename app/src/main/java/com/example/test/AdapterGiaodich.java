package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AdapterGiaodich extends BaseAdapter {
    ActivityQuanLyChiTieu context;
    private int layout;
    List<ObLichSuSoDuVi> list;

    public AdapterGiaodich(ActivityQuanLyChiTieu context, int layout, List<ObLichSuSoDuVi> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView tenVi, soTien, nhom, ghiChu, ngayGiaodich, trangthai;
        ImageView imgSuaGD, imgXoaGD;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout, null);

            viewHolder.tenVi=convertView.findViewById(R.id.textViewTenViGiaodich);
            viewHolder.soTien=convertView.findViewById(R.id.textViewSotiengiaodich);
            viewHolder.nhom=convertView.findViewById(R.id.textViewNhom);
            viewHolder.ghiChu=convertView.findViewById(R.id.textViewGhichugiaodich);
            viewHolder.ngayGiaodich=convertView.findViewById(R.id.textViewNgaygiaodich);
            viewHolder.trangthai=convertView.findViewById(R.id.textviewtrangthaigiaodich);
            viewHolder.imgSuaGD=convertView.findViewById(R.id.imageViewSuaGiaodich);
            viewHolder.imgXoaGD=convertView.findViewById(R.id.imageViewXoagiaodich);

            convertView.setTag(viewHolder);


        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final ObLichSuSoDuVi gd=list.get(position);

        viewHolder.tenVi.setText("Tên ví: "+gd.getTenvi());
        viewHolder.soTien.setText("Số tiền: "+gd.getSotien());
        viewHolder.nhom.setText("Nhóm: "+gd.getNhom());
        viewHolder.ghiChu.setText("Ghi chú: "+gd.getGhichu());
        viewHolder.ngayGiaodich.setText("Thời gian: "+gd.getNgay());
        viewHolder.trangthai.setText("Trạng thái: "+gd.getTrangthai());

        viewHolder.imgSuaGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaGD(gd.getId(), gd.getSotien(), gd.getNhom(), gd.getGhichu(), gd.getNgay(), gd.getTrangthai());
            }
        });

        viewHolder.imgXoaGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaGiaoDich(gd.getId());
            }
        });

        return convertView;



    }
}
