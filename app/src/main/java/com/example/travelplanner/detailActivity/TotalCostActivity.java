package com.example.travelplanner.detailActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TotalCostActivity extends AppCompatActivity {

    private TextView transportText;
    private TextView sleepText;
    private TextView eatText;
    private TextView etcText;

    int trans=0,sleep=0,eat=0,etc=0;
    Travel travel;
    public DatabaseReference myRef;
    String DBKey;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_cost);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        transportText = (TextView)findViewById(R.id.transportText);
        sleepText = (TextView)findViewById(R.id.sleepText);
        eatText = (TextView)findViewById(R.id.eatText);
        etcText = (TextView)findViewById(R.id.etcText);


        dataSet();


        //setting();


    }

    public void dataSet(){
        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue

        myRef = database.getReference(DBKey);
        DatabaseReference eatRef = myRef.child(travel.getTitle()).child("TotalCost").child("eat");
        eatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String temp = snapshot.getValue().toString();
                    eat += Integer.parseInt(temp);

                    eatText.setText(eat+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference etcRef = myRef.child(travel.getTitle()).child("TotalCost").child("etc");
        etcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String temp = snapshot.getValue().toString();
                    etc += Integer.parseInt(temp);

                    etcText.setText(etc+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference sleepRef = myRef.child(travel.getTitle()).child("TotalCost").child("sleep");
        sleepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String temp = snapshot.getValue().toString();
                    sleep += Integer.parseInt(temp);

                    sleepText.setText(sleep+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference transRef = myRef.child(travel.getTitle()).child("TotalCost").child("transport");
        transRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String temp = snapshot.getValue().toString();
                    trans += Integer.parseInt(temp);

                    transportText.setText(trans+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        transportText.setText(trans+"");
        sleepText.setText(sleep+"");
        //eatText.setText(eat+"");
        etcText.setText(etc+"");
    }

    /*public void setting() {
        String url = "https://travelplanner-42f43.firebaseio.com/"+DBKey+"/"+travel.getTitle()+"/TotalCost";
        DatabaseReference costRef = database.getReferenceFromUrl(url);

        costRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                *//*DB로딩*//*
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String key = snapshot.getKey();
                    Log.i("adfasdf",key);

                    if(key.equals("transport")){
                        transport.setText(snapshot.child("transport").getValue(String.class));
                    }
                    else if(key.equals("eat")){
                        eat.setText(snapshot.child("eat").getValue(String.class));
                    }
                    else if(key.equals("sleep")) {
                        sleep.setText(snapshot.child("sleep").getValue(String.class));
                    }

                    else if(key.equals("etc")) {
                        etc.setText(snapshot.child("etc").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
