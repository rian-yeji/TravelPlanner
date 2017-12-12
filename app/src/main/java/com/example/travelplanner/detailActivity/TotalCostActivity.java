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

    private TextView transport;
    private TextView sleep;
    private TextView eat;
    private TextView etc;

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

        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue

        myRef = database.getReference(DBKey);


        transport = (TextView)findViewById(R.id.transportText);
        sleep = (TextView)findViewById(R.id.sleepText);
        eat = (TextView)findViewById(R.id.eatText);
        etc = (TextView)findViewById(R.id.etcText);

        setting();


    }

    public void setting() {
        String url = "https://travelplanner-42f43.firebaseio.com/"+DBKey+"/"+travel.getTitle()+"/TotalCost";
        DatabaseReference costRef = database.getReferenceFromUrl(url);

        costRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
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
    }
}
