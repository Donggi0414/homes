package com.example.intentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SharedChecklist extends AppCompatActivity {

    private TextView tv_aptName;

    EditText parking, dong_dist, management, school_env, facilities, transport, dong, area, total_price, comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_checklist);

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



    }
}