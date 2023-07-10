package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SharedChecklist extends AppCompatActivity {

    private TextView tv_aptName;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;

    EditText parking, dong_dist, management, school_env, facilities, transport, dong, area, total_price, comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_checklist);
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
        String user = intent.getStringExtra("user");
        tv_aptName.setText(str);


        mDatabaseRef.child("Checklist").child(str).child(user).child("주차장").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                parking.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("동간거리").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                dong_dist.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("관리,조경").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                management.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("학교환경").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                school_env.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("편의시설").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                facilities.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("교통").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                transport.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("동,호수").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                dong.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("평형(전용)").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                area.setText((CharSequence) task.getResult().getValue());
            }
        });
        mDatabaseRef.child("Checklist").child(str).child(user).child("예상총액").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                total_price.setText((CharSequence) task.getResult().getValue());
            }
        });

        mDatabaseRef.child("Checklist").child(str).child(user).child("한줄평").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                comment.setText((CharSequence) task.getResult().getValue());
            }
        });




    }
}