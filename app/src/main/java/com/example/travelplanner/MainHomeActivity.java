package com.example.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Travels");
    TravelListAdapter adapter;
    public ArrayList<Travel> travel_items = new ArrayList<Travel>();
    String Tag = "MainHomeActivity";
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
        recyclerView.addItemDecoration(itemDecoration);

        setting();//데이터베이스에서 데이터 불러와서 화면 설정(리사이클러뷰)
    }

    public void onPlusButtonClicked(View v){
        Intent intent = new Intent(MainHomeActivity.this,AddTravelActivity.class);
        startActivity(intent);
    }

    public void setting(){
        Query travels = myRef.orderByPriority();
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                travel_items.clear();//초기화
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Travel newTravel = new Travel();
                    newTravel.setTitle(snapshot.child("title").getValue(String.class));
                    newTravel.setCountury(snapshot.child("country").getValue(String.class));
                    newTravel.setRegion(snapshot.child("region").getValue(String.class));
                    newTravel.setDates(snapshot.child("dates").getValue(String.class));
                    newTravel.setCost(snapshot.child("costs").getValue(String.class));

                    travel_items.add(newTravel);
                }

                /*화면 셋팅(onCreate에서 하면 리스너가 나중에 불려서 데이터의 뷰셋팅이 안됨)*/
                if(travel_items.isEmpty())//기존의 데이터가 없다면 샘플 하나 생성
                    travel_items.add(new Travel());

                adapter = new TravelListAdapter(getApplicationContext(),travel_items);
                recyclerView.setAdapter(adapter);

                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);

                /*RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
                recyclerView.addItemDecoration(itemDecoration);*/

                recyclerView.setHasFixedSize(true);

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
