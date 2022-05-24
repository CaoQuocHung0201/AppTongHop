package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViTri extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    ImageView btnHienthi, btnHuy;
    TextView txtVitriKinhdo,txtVitriVydo;
    EditText txtTen, txtMota;
    ImageView imgHinhvitri, imgMayanh, imgFolder, imgGetvitri;

    double latitude;
    double longitude;

    private Location location;

    private GoogleMap gm;

    // Đối tượng tương tác với Google API
    private GoogleApiClient gac;

    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi_tri);



        btnHienthi=(ImageView) findViewById(R.id.imgXacNhanCheckin);
        txtVitriKinhdo=(TextView) findViewById(R.id.textViewVitriKinhdo);
        txtVitriVydo=(TextView) findViewById(R.id.textViewVitriVydo);
        btnHuy=(ImageView) findViewById(R.id.imgHuyCheckin);
        txtTen=(EditText) findViewById(R.id.edtTenvitrine);
        txtMota=(EditText) findViewById(R.id.edtMotane);
        imgHinhvitri=(ImageView)findViewById(R.id.imageHinhvitrine);
        imgMayanh=(ImageView)findViewById(R.id.imgMayAnh);
        imgFolder=(ImageView)findViewById(R.id.imgFolder);
        imgGetvitri=(ImageView)findViewById(R.id.imgGetVitri);

        //getLocation();

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        imgGetvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


        imgMayanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,REQUEST_CODE_CAMERA);
                ActivityCompat.requestPermissions(
                        ViTri.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );
            }
        });

        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                ActivityCompat.requestPermissions(
                        ViTri.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_FOLDER
                );
            }
        });

        btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyen data imageview sang byte[]


                BitmapDrawable bitmapDrawable= (BitmapDrawable) imgHinhvitri.getDrawable();

                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh= byteArray.toByteArray();

                String ten=txtTen.getText().toString().trim();
                if(ten.equals(""))
                    Toast.makeText(ViTri.this, "Nhập tên vị trí vô!!", Toast.LENGTH_LONG).show();
                else {
                    ActivityDanhSachMap.databaseMap.insert_map(
                            txtTen.getText().toString().trim(),
                            txtMota.getText().toString().trim(),
                            hinhAnh,
                            txtVitriKinhdo.getText().toString().trim(),
                            txtVitriVydo.getText().toString().trim()
                    );

                    Toast.makeText(ViTri.this, "Đã thêm vị trí !!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ViTri.this, ActivityDanhSachMap.class));
                }



            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    public void dispLocation(View view) {
        getLocation();
    }
    /**
     * Phương thức này dùng để hiển thị trên UI
     * */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                // Hiển thị
                txtVitriKinhdo.setText(latitude+"");
                txtVitriVydo.setText(longitude+"");
            } else {
                txtVitriKinhdo.setText("Không thể hiển thị vị trí ");
                txtVitriVydo.setText("Bạn đã kích hoạt location trên thiết bị chưa?");
            }
        }
    }
    /**
     * Tạo đối tượng google api client
     * */
    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }
    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Đã kết nối với google api, lấy vị trí
        getLocation();
    }
    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
    protected void onStart() {
        gac.connect();
        super.onStart();
    }
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case  REQUEST_CODE_CAMERA:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }else Toast.makeText(this, "Bạn không cho phép mở camera!", Toast.LENGTH_LONG).show();
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDER);
                }else Toast.makeText(this, "Bạn không cho phép mở thư mục ảnh!", Toast.LENGTH_LONG).show();
                break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_CAMERA && resultCode==RESULT_OK && data!=null){
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            imgHinhvitri.setImageBitmap(bitmap);
        }
        if(requestCode==REQUEST_CODE_FOLDER && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imgHinhvitri.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}