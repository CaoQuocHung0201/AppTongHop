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

import java.util.ArrayList;

public class ActivityDanhSachMap extends AppCompatActivity {

    ListView lvDanhsachMap;
    ArrayList<ObjectMap> arrListMap;
    AdapterMap adapterMap;

    ImageView imgAddMap;


    public static Database databaseMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_map);

        databaseMap=new Database(this, "Map.sqlite", null, 1);
        databaseMap.QueryData("CREATE TABLE IF NOT EXISTS MapDB (Id INTEGER PRIMARY KEY AUTOINCREMENT, TenVitri VARCHAR(150), MoTa VARCHAR(250), HinhAnh BLOB, KinhDo VARCHAR(150), VyDo VARCHAR(150))");

        lvDanhsachMap=(ListView)findViewById(R.id.listviewDanhsachMap);


        arrListMap=new ArrayList<>();
        adapterMap=new AdapterMap(this, R.layout.dong_vitri, arrListMap);
        lvDanhsachMap.setAdapter(adapterMap);

        imgAddMap=(ImageView)findViewById(R.id.imgAddMap);
        imgAddMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityDanhSachMap.this, ViTri.class);
                startActivity(intent);
            }
        });


        GetDataMap();

        lvDanhsachMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ActivityDanhSachMap.this, MapsActivity2.class);
                Bundle bundle=new Bundle();
                bundle.putString("toadoLat", arrListMap.get(position).getLat());
                bundle.putString("toadoLong", arrListMap.get(position).getLongt());
                intent.putExtra("dulieuvitri", bundle);
                startActivity(intent);
            }
        });


    }

    public void DialogSuaMap(int id, String ten, String mota){

        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suamap);

        EditText editTenmap=(EditText)dialog.findViewById(R.id.edtTenmapnew);
        EditText editMotamap=(EditText)dialog.findViewById(R.id.edtMotamapnew);

        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextMap);


        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTenmap.setText("");
                editMotamap.setText("");
            }
        });


        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhanSuaMap);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuySuaMap);


        editTenmap.setText(ten);
        editMotamap.setText(mota);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tenmoi=editTenmap.getText().toString().trim();
                String Motamoi=editMotamap.getText().toString().trim();
                if(Tenmoi.equals(""))
                    Toast.makeText(ActivityDanhSachMap.this, "Nhập tên vị trí vô!!", Toast.LENGTH_LONG).show();
                else {
                    databaseMap.QueryData("UPDATE MapDB SET TenViTri='"+Tenmoi+" 'WHERE ID='"+id+"'");
                    databaseMap.QueryData("UPDATE MapDB SET MoTa='"+Motamoi+" 'WHERE ID='"+id+"'");
                    Toast.makeText(ActivityDanhSachMap.this, "Đã sửa map :)", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetDataMap();
                }

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

    public void DialogXoaMap(int id, String ten){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa vị trí: "+ten+" không? :(");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseMap.QueryData("DELETE FROM MapDB WHERE ID='"+id+"'");
                Toast.makeText(ActivityDanhSachMap.this, "Đã xóa:"+ten+" :(", Toast.LENGTH_LONG).show();
                GetDataMap();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void GetDataMap(){
        Cursor cursor=databaseMap.GetData("SELECT * FROM MapDB");
        int total=cursor.getCount();
        arrListMap.clear();
        while (cursor.moveToNext()){
            arrListMap.add(new ObjectMap(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }

        adapterMap.notifyDataSetChanged();
    }

}