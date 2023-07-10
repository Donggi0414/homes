package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AptLike extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    HashMap<String , String> apt_favoritelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_like);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        ListView favorite_lv = findViewById(R.id.favorite_lv);

        ArrayList<ApartmentInfo> favorite_list = new ArrayList<ApartmentInfo>();
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("favorite").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                apt_favoritelist = (HashMap<String, String>) task.getResult().getValue();
                System.out.println("aaaaaa " + apt_favoritelist);
                if (apt_favoritelist != null) {
                    for (String i : apt_favoritelist.keySet()){ //저장된 key값 확인
                        System.out.println("[Key]:" + i + " [Value]:" + apt_favoritelist.get(i));
                        favorite_list.add(new ApartmentInfo(i, "", apt_favoritelist.get(i), firebaseUser.getUid()));
                    }

                    FavoriteListAdapter adapter = new FavoriteListAdapter(getApplicationContext(), favorite_list);
                    favorite_lv.setAdapter(adapter);
                }

            }
        });
    }
}