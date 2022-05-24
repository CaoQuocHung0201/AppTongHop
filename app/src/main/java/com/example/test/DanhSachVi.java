package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachVi extends AppCompatActivity {

    ListView danhsachVi;
    public static AdapterDanhSachVi adapter;
    public static ArrayList<ObjectVi> arrVi;
    public static Database databaseVi;
    public static int b=1;

    ImageView imgThemvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_vi);

        danhsachVi=(ListView)findViewById(R.id.danhsachVi);

        arrVi=new ArrayList<>();
        adapter=new AdapterDanhSachVi(this,R.layout.dong_vi, arrVi);
        danhsachVi.setAdapter(adapter);
        GetDataVitien();

        danhsachVi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(DanhSachVi.this, ActivityQuanLyChiTieu.class);
                bundle.putSerializable("chonvi",(Serializable)arrVi);
                bundle.putInt("vitri", position);
                intent.putExtra("dulieuvi", bundle);
                ActivityQuanLyChiTieu.a=1;
                startActivity(intent);
            }
        });

        imgThemvi=(ImageView)findViewById(R.id.imgThemViLanSau);
        imgThemvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemVi();
            }
        });


    }


    public void DialogThemVi(){

        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themvitien);

        EditText editTenvi=(EditText)dialog.findViewById(R.id.edtTenvi);
        EditText editSotien=(EditText)dialog.findViewById(R.id.edtSotien);

        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextVi);

        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTenvi.setText("");
                editSotien.setText("");
            }
        });



        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhanVi);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuyVi);



        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenvi=editTenvi.getText().toString().trim();
                String sotien=editSotien.getText().toString().trim();
                databaseVi.insert_vi(tenvi, sotien);
                Toast.makeText(DanhSachVi.this, "Đã thêm ví tiền ^^!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                GetDataVitien();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void DialogSuaVi(int id, String ten, String sotien){

        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themvitien);

        EditText editTenvi=(EditText)dialog.findViewById(R.id.edtTenvi);
        EditText editSotien=(EditText)dialog.findViewById(R.id.edtSotien);

        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextVi);

        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTenvi.setText("");
                editSotien.setText("");
            }
        });

        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhanVi);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuyVi);

        editTenvi.setText(ten);
        editSotien.setText(sotien);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenvimoi=editTenvi.getText().toString().trim();
                String sotienmoi=editSotien.getText().toString().trim();
                databaseVi.QueryData("UPDATE ViTien SET TenVi='"+tenvimoi+" 'WHERE ID='"+id+"'");
                databaseVi.QueryData("UPDATE ViTien SET SoTien='"+sotienmoi+" 'WHERE ID='"+id+"'");
                Toast.makeText(DanhSachVi.this, "Đã sửa dữ liệu ví ^^!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                GetDataVitien();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void DialogXoaVi(String tenvi, int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa ví: "+tenvi+" không?");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseVi.QueryData("DELETE FROM ViTien WHERE ID='"+id+"'");
                Toast.makeText(DanhSachVi.this, "Đã xóa ví: "+tenvi, Toast.LENGTH_LONG).show();
                GetDataVitien();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public static void GetDataVitien(){
        Cursor cursor=databaseVi.GetData("SELECT * FROM ViTien");
        arrVi.clear();
        while (cursor.moveToNext()){
            arrVi.add(new ObjectVi(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            ));
        }
        adapter.notifyDataSetChanged();
    }
}