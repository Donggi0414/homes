package com.example.intentexample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.metrics.Event;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class WriteCheckList extends AppCompatActivity {

    private TextView tv_aptName;
//    int REQUEST_IMAGE_CODE = 1001;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    EditText parking, dong_dist, management, school_env, facilities, transport, dong, area, total_price, comment;
    Button save_btn, delete_btn, btn_addPhoto, btn_deletePhoto;
    ImageView imageView1, imageView2, imageView3;

    boolean isImage1Set = false;
    boolean isImage2Set = false;
    boolean isImage3Set = false;

    // 갤러리로부터 사진을 불러와 이미지 뷰에 set 하기 위한 런쳐
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) { // 사진 선택
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            // update the preview image in the layout
                            if (!isImage1Set) {
                                imageView1.setImageURI(selectedImageUri);
                                isImage1Set = true;
                            } else if (!isImage2Set) {
                                imageView2.setImageURI(selectedImageUri);
                                isImage2Set = true;
                            } else if (!isImage3Set) {
                                imageView3.setImageURI(selectedImageUri);
                                isImage3Set = true;
                            }
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_check_list);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        tv_aptName = findViewById(R.id.tv_aptName);
        parking = findViewById(R.id.parking);
        dong_dist = findViewById(R.id.dong_dist);
        management = findViewById(R.id.management);
        school_env = findViewById(R.id.school_env);
        facilities = findViewById(R.id.facilities);
        transport = findViewById(R.id.transport);
        dong = findViewById(R.id.dong);
        area = findViewById(R.id.area);
        total_price = findViewById(R.id.total_price);
        comment = findViewById(R.id.comment);

        Intent intent = getIntent();
        String str = intent.getStringExtra("aptName");
        tv_aptName.setText(str);

        // 외부 저장소 관리 권한이 허용되지 않았을 경우 권한 요청
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }

        // 카메라 권한과 외부 저장소 쓰기 권한 체크 및 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(WriteCheckList.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("주차장").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                parking.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("동간거리").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                dong_dist.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("관리,조경").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                management.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("학교환경").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                school_env.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("편의시설").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                facilities.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("교통").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                transport.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("동,호수").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                dong.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("평형(전용)").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                area.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("예상총액").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                total_price.setText((CharSequence) task.getResult().getValue());
            }
        });

        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(str).child("한줄평").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                comment.setText((CharSequence) task.getResult().getValue());
            }
        });


        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parking_txt = parking.getText().toString();
                String dong_dist_txt = dong_dist.getText().toString();
                String management_txt = management.getText().toString();
                String school_env_txt = school_env.getText().toString();
                String facilities_txt = facilities.getText().toString();
                String transport_txt = transport.getText().toString();
                String dong_txt = dong.getText().toString();
                String area_txt = area.getText().toString();
                String total_price_txt = total_price.getText().toString();
                String comment_txt = comment.getText().toString();

                String apartname = tv_aptName.getText().toString();
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("주차장").setValue(parking_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("동간거리").setValue(dong_dist_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("관리,조경").setValue(management_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("학교환경").setValue(school_env_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("편의시설").setValue(facilities_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("교통").setValue(transport_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("동,호수").setValue(dong_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("평형(전용)").setValue(area_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("예상총액").setValue(total_price_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(apartname).child("한줄평").setValue(comment_txt);
                // Checklist for sharing
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("주차장").setValue(parking_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("동간거리").setValue(dong_dist_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("관리,조경").setValue(management_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("학교환경").setValue(school_env_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("편의시설").setValue(facilities_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("교통").setValue(transport_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("동,호수").setValue(dong_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("평형(전용)").setValue(area_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("예상총액").setValue(total_price_txt);
                mDatabaseRef.child("Checklist").child(apartname).child(firebaseUser.getUid()).child("한줄평").setValue(comment_txt);

                Toast.makeText(WriteCheckList.this, "체크리스트 저장 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        delete_btn = findViewById(R.id.delete);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parking.setText("");
                dong_dist.setText("");
                management.setText("");
                school_env.setText("");
                facilities.setText("");
                transport.setText("");
                dong.setText("");
                area.setText("");
                total_price.setText("");
                comment.setText("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("주차장").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("동간거리").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("관리,조경").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("학교환경").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("편의시설").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("교통").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("동,호수").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("평형(전용)").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("예상총액").setValue("");
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("한줄평").setValue("");
            }
        });


        // 아래부터는 imageView에 갤러리 사진을 추가하기 위한 코드

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        btn_addPhoto = findViewById(R.id.btn_addPhoto);
        btn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { imageChooser(); }
        });

        btn_deletePhoto = findViewById(R.id.btn_deletePhoto);
        btn_deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImage3Set) {
                    imageView3.setImageDrawable(null);
                    isImage3Set = false;
                } else if (isImage2Set) {
                    imageView2.setImageDrawable(null);
                    isImage2Set = false;
                } else if (isImage1Set) {
                    imageView1.setImageDrawable(null);
                    isImage1Set = false;
                }
            }
        });
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"));

    }
}