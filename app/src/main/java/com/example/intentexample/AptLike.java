package com.example.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AptLike extends AppCompatActivity {

    private TextView aptNameTextView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_like);

        aptNameTextView = findViewById(R.id.aptNameTextView);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes").child("UserAccount");
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        // 파이어베이스에서 aptCode에 해당하는 아파트명 가져오기 여기 해결 필요!! (String aptCode = null; 및 하단에 child(aptcode) 에러남)
        String aptName;
        mDatabaseRef.child(firebaseUser.getUid()).child("favorite").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    // do something
                } else {
                    Log.d("firebase", "Data Already Added");
                }
            }
        });
    }
}