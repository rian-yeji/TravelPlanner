package com.example.travelplanner.addTravelActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.travelplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {
    public DatabaseReference myRef;
    FirebaseDatabase database;
    TravelListAdapter adapter;
    public ArrayList<Travel> travel_items = new ArrayList<Travel>();
    RecyclerView travelsRecyclerView;
    String DBKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        ActionBar actionBar = getSupportActionBar();


        //============================================================================
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue

        myRef = database.getReference(DBKey);

        //=============================================================================

        actionBar.setTitle(DBKey + "님의 여행기록");

        travelsRecyclerView = (RecyclerView) findViewById(R.id.travelsRecyclerView);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
        travelsRecyclerView.addItemDecoration(itemDecoration);

        setting();//데이터베이스에서 데이터 불러와서 화면 설정(리사이클러뷰)

        //Toast.makeText(this,"preference값 : "+DBKey,Toast.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item2 = menu.findItem(R.id.action_map);
        item2.setVisible(false);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_add) {

            Intent intent = new Intent(MainHomeActivity.this, AddTravelActivity.class);
            startActivity(intent);
        }
            return super.onOptionsItemSelected(item);

    }



    public void setting() {
        //final Query travels = myRef.orderByPriority();
        //myRef = database.getReference(DBKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                travel_items.clear();//초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Travel newTravel = new Travel();

                    String getTitle = snapshot.getKey();
                    Log.i("yeji",getTitle);
                    String country = snapshot.child("Country").getValue(String.class);
                    String region = snapshot.child("Region").getValue(String.class);
                    String cost = snapshot.child("Costs").getValue(String.class);
                    String startDates = snapshot.child("Date").child("startDates").child("year").getValue(String.class)+"/";
                    startDates += snapshot.child("Date").child("startDates").child("month").getValue(String.class)+"/";
                    startDates += snapshot.child("Date").child("startDates").child("day").getValue(String.class);

                    String endDates = snapshot.child("Date").child("endDates").child("year").getValue(String.class)+"/";
                    endDates += snapshot.child("Date").child("endDates").child("month").getValue(String.class)+"/";
                    endDates += snapshot.child("Date").child("endDates").child("day").getValue(String.class);

                    String dDay = snapshot.child("Date").child("startDday").getValue(String.class);

                    newTravel.setTitle(getTitle);
                    newTravel.setCountry(country);
                    newTravel.setRegion(region);
                    newTravel.setStartDates(startDates);
                    newTravel.setEndDates(endDates);
                    newTravel.setCosts(cost);
                    newTravel.setdDay(dDay);

                    travel_items.add(newTravel);
                }

                /*화면 셋팅(onCreate에서 하면 리스너가 나중에 불려서 데이터의 뷰셋팅이 안됨)*/
                if (travel_items.isEmpty())//기존의 데이터가 없다면 샘플 하나 생성
                    travel_items.add(new Travel());

                adapter = new TravelListAdapter(getApplicationContext(), travel_items,DBKey);
                travelsRecyclerView.setAdapter(adapter);

                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                travelsRecyclerView.setLayoutManager(gridLayoutManager);

                /*RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
                recyclerView.addItemDecoration(itemDecoration);*/

                travelsRecyclerView.setHasFixedSize(true);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setting();
    }
}
