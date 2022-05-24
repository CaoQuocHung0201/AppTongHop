package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityQuanLyChiTieu extends AppCompatActivity {


    ImageView DsVi;

    TextView TenVimain, Sotienmain;

    ListView listGiaodich;

    ImageView btnThemgiaodich;

    public static int a=0;

    ArrayList<ObLichSuSoDuVi> arrGD;
    AdapterGiaodich adapterGiaodich;

    int idViMain=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chi_tieu);


        DanhSachVi.databaseVi=new Database(this, "chitieu.sqlite", null,1);
        DanhSachVi.databaseVi.QueryData("CREATE TABLE IF NOT EXISTS ViTien(Id INTEGER PRIMARY KEY, TenVi VARCHAR(200), SoTien VARCHAR(200))");
        DanhSachVi.databaseVi.QueryData("CREATE TABLE IF NOT EXISTS LichSu(Id INTEGER PRIMARY KEY, TenVi VARCHAR(200), SoTien VARCHAR(200) , Nhom VARCHAR(200), GhiChu VARCHAR (200), Ngay VARCHAR(200), TrangThai VARCHAR(200))");

        DsVi=(ImageView)findViewById(R.id.imageViewDSVi);
        TenVimain=(TextView)findViewById(R.id.textViewTenViMain);
        Sotienmain=(TextView)findViewById(R.id.textViewSotienMain);
        listGiaodich=(ListView)findViewById(R.id.listviewGiaoDich);
        btnThemgiaodich=(ImageView) findViewById(R.id.buttonThemgiaodich);

        arrGD=new ArrayList<>();
        adapterGiaodich=new AdapterGiaodich(this, R.layout.dong_giaodich, arrGD);
        listGiaodich.setAdapter(adapterGiaodich);
        GetDataGiaoDich();

        DsVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityQuanLyChiTieu.this, DanhSachVi.class);
                startActivity(intent);
            }
        });

        Cursor cursor=DanhSachVi.databaseVi.GetData("SELECT * FROM ViTien");
        int total=cursor.getCount();
        if(total == 0){
            DialogThemViLanDau();
        }

        if(a == 1){
            laydulieuvi();
            a=0;
        }else {
            while (cursor.moveToNext()){
                idViMain=cursor.getInt(0);
                TenVimain.setText(cursor.getString(1));
                Sotienmain.setText(cursor.getString(2));
                break;
            }
        }

        btnThemgiaodich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemGD();
            }
        });


    }

    public void DialogThemGD(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themgiaodich);

        TextView txtTieude=(TextView)dialog.findViewById(R.id.textViewTieudegd);
        TextView txtTenvi=(TextView)dialog.findViewById(R.id.textViewTenvidialog);
        TextView txtSotien=(TextView)dialog.findViewById(R.id.textViewSotiendialog);
        EditText edtSotien=(EditText)dialog.findViewById(R.id.editTextSotiengd);
        EditText edtNhom=(EditText)dialog.findViewById(R.id.editTextNhomgd);
        EditText edtGhichu=(EditText)dialog.findViewById(R.id.editTextGhichugd);
        EditText edtNgay=(EditText)dialog.findViewById(R.id.editTextNgaygd);

        RadioButton checkBoxthu=(RadioButton)dialog.findViewById(R.id.checkBoxThu);
        RadioButton checkBoxchi =(RadioButton)dialog.findViewById(R.id.checkboxchi);


        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhangiaodich);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuySuagiaodich);
        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextgiaodich);

        ImageView chonngay=(ImageView)dialog.findViewById(R.id.btnChonNgaygiaodich);

        Calendar calendar=Calendar.getInstance();
        int Ngay=calendar.get(Calendar.DATE);
        int Thang=calendar.get(Calendar.MONTH);
        int Nam=calendar.get(Calendar.YEAR);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        edtNgay.setText(simpleDateFormat.format(calendar.getTime()));
        edtNgay.setEnabled(false);

        chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivityQuanLyChiTieu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        edtNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, Nam, Thang,Ngay);
                datePickerDialog.show();
            }
        });

        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtGhichu.setText("");
                edtNgay.setText("");
                edtNhom.setText("");
                edtSotien.setText("");
            }
        });

        txtTieude.setText("Thêm giao dịch");
        txtTenvi.setText(TenVimain.getText().toString().trim());
        txtSotien.setText(Sotienmain.getText().toString().trim());


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sotien=edtSotien.getText().toString().trim();
                String nhom=edtNhom.getText().toString().trim();
                String ghichu=edtGhichu.getText().toString().trim();
                String ngay=edtNgay.getText().toString().trim();
                String trangthai="";
                if(sotien.equals("")){
                    Toast.makeText(ActivityQuanLyChiTieu.this, "Nhập tiền vô!!", Toast.LENGTH_SHORT).show();
                }else {
                    if(checkBoxthu.isChecked()==true){
                        trangthai="Thu";
                        DanhSachVi.databaseVi.QueryData("INSERT INTO LichSu VALUES(null, '"+TenVimain.getText().toString().trim()+"', '"+sotien+"', '"+nhom+"', '"+ghichu+"', '"+ngay+"', '"+trangthai+"')");
                        String tenvitien=TenVimain.getText().toString().trim();
                        int sotiencu= Integer.parseInt(Sotienmain.getText().toString().trim());
                        int sotiengiaodich= Integer.parseInt(edtSotien.getText().toString().trim());
                        String total=String.valueOf(sotiencu+sotiengiaodich);
                        Sotienmain.setText(total);
                        DanhSachVi.databaseVi.QueryData("UPDATE ViTien SET SoTien='"+total+"' WHERE ID='"+idViMain+"'");
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Đã cộng số tiền: "+sotiengiaodich+" vào ví: "+tenvitien, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else if(checkBoxchi.isChecked()==true){
                        trangthai="Chi";
                        DanhSachVi.databaseVi.QueryData("INSERT INTO LichSu VALUES(null, '"+TenVimain.getText().toString().trim()+"', '"+sotien+"', '"+nhom+"', '"+ghichu+"', '"+ngay+"', '"+trangthai+"')");
                        String tenvitien=TenVimain.getText().toString().trim();
                        int sotiencu= Integer.parseInt(Sotienmain.getText().toString().trim());
                        int sotiengiaodich= Integer.parseInt(edtSotien.getText().toString().trim());
                        String total=String.valueOf(sotiencu-sotiengiaodich);
                        Sotienmain.setText(total);
                        DanhSachVi.databaseVi.QueryData("UPDATE ViTien SET SoTien='"+total+"' WHERE ID='"+idViMain+"'");
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Đã trừ số tiền: "+sotiengiaodich+" ra khỏi ví: "+tenvitien, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else if(checkBoxchi.isChecked()==false && checkBoxthu.isChecked()==false){
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Chọn trạng thái thu hoặc chi!!", Toast.LENGTH_LONG).show();
                    }
                }


                GetDataGiaoDich();
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

    public void DialogSuaGD(int idgd,String sotiencu, String nhomcu, String ghichucu, String ngaycu, final String trangthai){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themgiaodich);

        TextView txtTieude=(TextView)dialog.findViewById(R.id.textViewTieudegd);
        TextView txtTenvi=(TextView)dialog.findViewById(R.id.textViewTenvidialog);
        TextView txtSotien=(TextView)dialog.findViewById(R.id.textViewSotiendialog);
        EditText edtSotien=(EditText)dialog.findViewById(R.id.editTextSotiengd);
        EditText edtNhom=(EditText)dialog.findViewById(R.id.editTextNhomgd);
        EditText edtGhichu=(EditText)dialog.findViewById(R.id.editTextGhichugd);

        EditText edtNgay=(EditText)dialog.findViewById(R.id.editTextNgaygd);


        ImageView btnXacNhan=(ImageView)dialog.findViewById(R.id.imgXacNhangiaodich);
        ImageView btnHuy=(ImageView)dialog.findViewById(R.id.imgHuySuagiaodich);

        ImageView xoatext=(ImageView)dialog.findViewById(R.id.imgXoaTextgiaodich);

        ImageView chonngay=(ImageView)dialog.findViewById(R.id.btnChonNgaygiaodich);

        Calendar calendar=Calendar.getInstance();
        int Ngay=calendar.get(Calendar.DATE);
        int Thang=calendar.get(Calendar.MONTH);
        int Nam=calendar.get(Calendar.YEAR);


        chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivityQuanLyChiTieu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        edtNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, Nam, Thang,Ngay);
                datePickerDialog.show();
            }
        });

        RadioButton checkBoxthu=(RadioButton)dialog.findViewById(R.id.checkBoxThu);
        RadioButton checkBoxchi =(RadioButton)dialog.findViewById(R.id.checkboxchi);

        xoatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtGhichu.setText("");
                edtNgay.setText("");
                edtNhom.setText("");
            }
        });

        txtTieude.setText("Sửa giao dịch");
        txtTenvi.setText(TenVimain.getText().toString().trim());
        txtSotien.setText(Sotienmain.getText().toString().trim());

        edtSotien.setText(sotiencu);
        edtNhom.setText(nhomcu);
        edtGhichu.setText(ghichucu);
        edtNgay.setText(ngaycu);

        edtNgay.setEnabled(false);
        edtSotien.setEnabled(false);

        if (trangthai.equals("Thu")){
            checkBoxthu.setChecked(true);
        }else if(trangthai.equals("Chi")){
            checkBoxchi.setChecked(true);
        }

        String sotien=edtSotien.getText().toString().trim();

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sotien.equals("")){
                    Toast.makeText(ActivityQuanLyChiTieu.this, "Nhập tiền vô!!", Toast.LENGTH_LONG).show();
                }else {
                    if(checkBoxthu.isChecked()==true && trangthai.equals("Chi")){
                        String nhom=edtNhom.getText().toString().trim();
                        String ghichu=edtGhichu.getText().toString().trim();
                        String ngay=edtNgay.getText().toString().trim();
                        String trangthaimoi="Thu";
                        int sotientrongvi= Integer.parseInt(Sotienmain.getText().toString().trim());
                        int sotiengiaodich= Integer.parseInt(edtSotien.getText().toString().trim());
                        String total=String.valueOf(sotientrongvi+sotiengiaodich);
                        Sotienmain.setText(total);
                        DanhSachVi.databaseVi.QueryData("UPDATE ViTien SET SoTien='"+total+"' WHERE ID='"+idViMain+"'");

                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Nhom='"+nhom+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET GhiChu='"+ghichu+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Ngay='"+ngay+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET TrangThai='"+trangthaimoi+"' WHERE ID='"+idgd+"'");
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Đã cập nhật!!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else if(checkBoxchi.isChecked()==true && trangthai.equals("Thu")){
                        String nhom=edtNhom.getText().toString().trim();
                        String ghichu=edtGhichu.getText().toString().trim();
                        String ngay=edtNgay.getText().toString().trim();
                        String trangthaimoi="Chi";
                        int sotientrongvi= Integer.parseInt(Sotienmain.getText().toString().trim());
                        int sotiengiaodich= Integer.parseInt(edtSotien.getText().toString().trim());
                        String total=String.valueOf(sotientrongvi-sotiengiaodich);
                        Sotienmain.setText(total);
                        DanhSachVi.databaseVi.QueryData("UPDATE ViTien SET SoTien='"+total+"' WHERE ID='"+idViMain+"'");

                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Nhom='"+nhom+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET GhiChu='"+ghichu+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Ngay='"+ngay+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET TrangThai='"+trangthaimoi+"' WHERE ID='"+idgd+"'");
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Đã cập nhật!!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else if(checkBoxchi.isChecked()==false && checkBoxthu.isChecked()==false){
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Chọn trạng thái thu hoặc chi!!", Toast.LENGTH_LONG).show();
                    }else if((checkBoxchi.isChecked()==true && trangthai.equals("Chi")) || (checkBoxthu.isChecked()==true && trangthai.equals("Thu")) ){
                        String nhom=edtNhom.getText().toString().trim();
                        String ghichu=edtGhichu.getText().toString().trim();
                        String ngay=edtNgay.getText().toString().trim();

                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Nhom='"+nhom+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET GhiChu='"+ghichu+"' WHERE ID='"+idgd+"'");
                        DanhSachVi.databaseVi.QueryData("UPDATE LichSu SET Ngay='"+ngay+"' WHERE ID='"+idgd+"'");
                        Toast.makeText(ActivityQuanLyChiTieu.this, "Đã cập nhật!!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }

                GetDataGiaoDich();
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

    public void DialogXoaGiaoDich(int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa giao dịch này không?");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DanhSachVi.databaseVi.QueryData("DELETE FROM LichSu WHERE ID='"+id+"'");
                Toast.makeText(ActivityQuanLyChiTieu.this, "Đã xóa giao dịch TT", Toast.LENGTH_LONG).show();
                GetDataGiaoDich();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogXoa.show();
    }



     public void DialogThemViLanDau(){

        Dialog dialog=new Dialog(ActivityQuanLyChiTieu.this);
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
                DanhSachVi.databaseVi.insert_vi(tenvi, sotien);
                Toast.makeText(ActivityQuanLyChiTieu.this, "Đã thêm ví mới ^^", Toast.LENGTH_SHORT).show();
                TenVimain.setText(tenvi);
                Sotienmain.setText(sotien);
                dialog.dismiss();
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

    public void laydulieuvi(){
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("dulieuvi");
        int vitri=bundle.getInt("vitri");
        ArrayList<ObjectVi> arr = (ArrayList<ObjectVi>) bundle.getSerializable("chonvi");
        TenVimain.setText(arr.get(vitri).getTen());
        Sotienmain.setText(arr.get(vitri).getSotien());
        idViMain=arr.get(vitri).getId();
        Toast.makeText(this, "Đã chọn ví: "+ arr.get(vitri).getTen(), Toast.LENGTH_SHORT).show();
    }

    public void GetDataGiaoDich(){
        Cursor cursor=DanhSachVi.databaseVi.GetData("SELECT * FROM LichSu");
        arrGD.clear();
        while (cursor.moveToNext()){
            arrGD.add(new ObLichSuSoDuVi(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            ));
        }
        adapterGiaodich.notifyDataSetChanged();
    }

}