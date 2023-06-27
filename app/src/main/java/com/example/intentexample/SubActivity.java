package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intentexample.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SubActivity extends AppCompatActivity {

    private TextView tv_sub;

    private DrawerLayout drawerLayout;
    private View drawerView;
//    public static final int REQUEST_CODE_MENU = 101;

    private FirebaseAuth mFirebaseAuth;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE_MENU) {
//            Toast.makeText(getApplicationContext(),
//                    "아직 준비되지 않은 기능입니다.", Toast.LENGTH_LONG).show();
//
//            if (resultCode == RESULT_OK) {
//                String name = data.getStringExtra("name");
//                Toast.makeText(getApplicationContext(), "출시되면 이용해주세요 : " + name,
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

//        tv_sub = findViewById(R.id.tv_sub);
//
//        Intent intent = getIntent();
//        String str = intent.getStringExtra("str");

//        tv_sub.setText(str);
//
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerView = (View) findViewById(R.id.drawer);

        Button btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색
                Intent intent = new Intent(SubActivity.this, Search.class);
                startActivity(intent);

            }
        });

//        Button btn_close = (Button) findViewById(R.id.btn_close);
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawers();
//
//            }
//        });

        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃
                mFirebaseAuth.signOut();
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        Button btn_mapTrace = findViewById(R.id.btn_mapTrace);
        btn_mapTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MapTrace.class);
                startActivity(intent);
            }
        });

        Button btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, PhotoSearch.class);
                startActivity(intent);
            }
        });


        Button btn_checkList = findViewById(R.id.btn_checkList);
        btn_checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, checkList.class);
                startActivity(intent);
            }
        });


        Button btn_aptLike = (Button) findViewById(R.id.btn_aptLike);
        btn_aptLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, AptLike.class);
                startActivity(intent);
            }
        });





//        drawerLayout.setDrawerListener(listener);
//        drawerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });



    }

//    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
//        @Override
//        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//
//        }
//
//        @Override
//        public void onDrawerOpened(@NonNull View drawerView) {
//
//        }
//
//        @Override
//        public void onDrawerClosed(@NonNull View drawerView) {
//
//        }
//
//        @Override
//        public void onDrawerStateChanged(int newState) {
//
//        }
//    };

}