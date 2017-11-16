package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTravelActivity extends AppCompatActivity {

    ArrayList<Travel> travel_items;
    Button insertButton;
    String country,region,date;
    String Tag = "AddTravelActivity";
    TravelListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);

        Log.d(Tag,"AddTravelActivity");

        Intent intent = getIntent();
        travel_items = (ArrayList<Travel>)intent.getSerializableExtra("travel_items");

        insertButton = (Button)findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = ((EditText)findViewById(R.id.country_editText)).getText().toString();
                region = ((EditText)findViewById(R.id.region_editText)).getText().toString();
                date = ((EditText)findViewById(R.id.dates_editText)).getText().toString();

                Travel newTravel = new Travel(country,region,date);
                travel_items.add(newTravel);

                Toast.makeText(getApplicationContext(),"새 여행 추가 총 갯수 : "+travel_items.size(),Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}
