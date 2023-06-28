package com.example.intentexample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class WriteCheckList extends AppCompatActivity {

    private TextView tv_aptName;
//    int REQUEST_IMAGE_CODE = 1001;
    ImageView imageView1;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    EditText parking, dong_dist, management, school_env, facilities, dong, area, total_price;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_check_list);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");

        tv_aptName = findViewById(R.id.tv_aptName);
        parking = findViewById(R.id.parking);
        dong_dist = findViewById(R.id.dong_dist);
        management = findViewById(R.id.management);
        school_env = findViewById(R.id.school_env);
        facilities = findViewById(R.id.facilities);
        dong = findViewById(R.id.dong);
        area = findViewById(R.id.area);
        total_price = findViewById(R.id.total_price);

        Intent intent = getIntent();
        String str = intent.getStringExtra("aptName");

        tv_aptName.setText(str);

        imageView1 = findViewById(R.id.imageView1);
//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(in, REQUEST_IMAGE_CODE);
//            }
//        });
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parking_txt = parking.getText().toString();
                String dong_dist_txt = dong_dist.getText().toString();
                String management_txt = management.getText().toString();
                String school_env_txt = school_env.getText().toString();
                String facilities_txt = facilities.getText().toString();
                String dong_txt = dong.getText().toString();
                String area_txt = area.getText().toString();
                String total_price_txt = total_price.getText().toString();
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("주차장").setValue(parking_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("동간거리").setValue(dong_dist_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("관리,조경").setValue(management_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("학교환경").setValue(school_env_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("편의시설").setValue(facilities_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("동,호수").setValue(dong_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("평형(전용)").setValue(area_txt);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").child(tv_aptName.getText().toString()).child("예상총액").setValue(total_price_txt);


            }
        });
    }

}