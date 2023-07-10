package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class checkList extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    HashMap<String , ?> apt_checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        ArrayList<ApartmentInfo> aptname_list = new ArrayList<ApartmentInfo>();
        ListView chcklist_lv = findViewById(R.id.checklist_lv);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("checklist").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                apt_checklist = (HashMap<String, ?>) task.getResult().getValue();
                if (apt_checklist != null) {
                    for (String i : apt_checklist.keySet()){ //저장된 key값 확인
                        System.out.println("[Key]:" + i + " [Value]:" + apt_checklist.get(i));
                        aptname_list.add(new ApartmentInfo(i, "", "", firebaseUser.getUid()));
                    }

                    ListAdapter adapter = new ListAdapter(getApplicationContext(), aptname_list);
                    chcklist_lv.setAdapter(adapter);
                }

            }
        });


    }

}