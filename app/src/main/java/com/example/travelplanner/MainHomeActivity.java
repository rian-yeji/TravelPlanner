package com.example.travelplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {
    TravelListAdapter adapter;
    ArrayList<Travel_Item> travel_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);



    }

    public void onPlusButtonClicked(View v){
        //리사이클러 뷰 추가
    }


}
