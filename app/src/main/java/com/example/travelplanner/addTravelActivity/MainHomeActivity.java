package com.example.travelplanner.addTravelActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.travelplanner.R;
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
    RecyclerView travelsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        String DBKey = preferences.getString("DBKey",""); //key,defaultValue

        travelsRecyclerView = (RecyclerView) findViewById(R.id.travelsRecyclerView);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
        travelsRecyclerView.addItemDecoration(itemDecoration);

        setting();//데이터베이스에서 데이터 불러와서 화면 설정(리사이클러뷰)

        Toast.makeText(this,"preference값 : "+DBKey,Toast.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(MainHomeActivity.this, AddTravelActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }



    public void setting() {
        final Query travels = myRef.orderByPriority();

        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                travel_items.clear();//초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Travel newTravel = snapshot.getValue(Travel.class);

                    String getTitle = snapshot.getRef().getKey();

                    newTravel.setTitle(getTitle);
                    newTravel.setCountry(newTravel.getCountry());
                    newTravel.setRegion(newTravel.getRegion());
                    newTravel.setDates(newTravel.getDates());
                    newTravel.setCosts(newTravel.getCosts());

                    travel_items.add(newTravel);
                }

                /*화면 셋팅(onCreate에서 하면 리스너가 나중에 불려서 데이터의 뷰셋팅이 안됨)*/
                if (travel_items.isEmpty())//기존의 데이터가 없다면 샘플 하나 생성
                    travel_items.add(new Travel());

                adapter = new TravelListAdapter(getApplicationContext(), travel_items);
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

}
