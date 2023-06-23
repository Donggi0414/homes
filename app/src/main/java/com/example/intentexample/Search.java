package com.example.intentexample;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Search extends AppCompatActivity{
    public List<String> apartment_list = new LinkedList<>();
    public HashMap<String, String> result = new HashMap<>();
    private List<String> searchResults = new LinkedList<>();
    private ArrayAdapter<String> adapter;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final SearchView searchView = findViewById(R.id.search_view);
        ListView listView = findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homes");
        mDatabaseRef.child("Apartments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "error retrieving data");
                }
                else {
                    result = (HashMap<String, String>) task.getResult().getValue();
                    for (String key : result.keySet() ) {
                        apartment_list.add(key);
                    }
                }
            }
        });

        // Set adapter for listView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        listView.setAdapter(adapter);

        // Set a click listener for the ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭된 아이템의 텍스트를 가져와 SearchView에 입력합니다.
                String selectedItem = searchResults.get(position);
                searchView.setQuery(selectedItem, false);
                System.out.println("aaaaaaa " + selectedItem);
                // Create an intent to start the second activity
                Intent intent = new Intent(Search.this, Search_result.class);
                // Optionally you can pass data to the new activity
                intent.putExtra("selectedItem", selectedItem);
                intent.putExtra("aptCodeList", (Serializable) result);
                // Start the new activity
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
    }
    private void search(String query) {
        searchResults.clear();
        // 검색어가 비어있지 않다면 일치하는 아이템만 결과 리스트에 추가
        if (!query.isEmpty()) {
            for (String item : apartment_list) {
                if (item.toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(item);
                }
            }
        }
        // ListView를 업데이트
        adapter.notifyDataSetChanged();
    }
}