package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTravelActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Travels");
    Button insertButton;
    String title,country,region,dates;
    String key;
    String Tag = "AddTravelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);

        insertButton = (Button)findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ((EditText)findViewById(R.id.title_editText)).getText().toString();
                country = ((EditText)findViewById(R.id.country_editText)).getText().toString();
                region = ((EditText)findViewById(R.id.region_editText)).getText().toString();
                dates = ((EditText)findViewById(R.id.dates_editText)).getText().toString();

                addTravel();
                Toast.makeText(getApplicationContext(),"새 여행 추가",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //새로운 여행 초기 시작값 및 생성
    private void addTravel(){
        DatabaseReference titleRef = database.getReference(title);

        myRef.child(titleRef.getKey()).child("region").setValue(region);
        myRef.child(titleRef.getKey()).child("country").setValue(country);
        myRef.child(titleRef.getKey()).child("dates").setValue(dates);
        myRef.child(titleRef.getKey()).child("costs").setValue("경비");

    }
}
