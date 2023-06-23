package com.example.intentexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentexample.SubActivity;
import com.example.intentexample.R;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_id;
    private String str;

    private int[] apartmentImages = {R.drawable.apartment1, R.drawable.apartment2, R.drawable.apartment3};
    private ImageView imageView;
    private int currentIndex = 0;
    private Handler handler;
    private Runnable runnable;

    ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        et_id = findViewById(R.id.et_id);    // 아이디 로그인



        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        Button btnNaverLogin = findViewById(R.id.btn_naverLogin);
        btnNaverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nid.naver.com/nidlogin.login?mode=form&url=https://www.naver.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }


}