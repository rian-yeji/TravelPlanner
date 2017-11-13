package com.example.travelplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {
    TravelListAdapter adapter;
    ArrayList<Travel> travel_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        travel_items = Travel.createTravelsList(1);

        adapter = new TravelListAdapter(this,travel_items);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setHasFixedSize(true);

        travel_items.add(0,new Travel());
        adapter.notifyItemInserted(0);



    }

    public void onPlusButtonClicked(View v){
        travel_items.add(0,new Travel());
        adapter.notifyDataSetChanged();
    }


}
