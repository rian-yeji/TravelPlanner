package com.example.travelplanner.detailActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.example.travelplanner.detailActivity.Map.DayMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailPlanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Detail_Recycler_adapter detail_recycler_adapter;
    private ArrayList<Detail_item> items;
    private Travel travel;
    int countDay;
    private DayMapFragment dayMapFragment = new DayMapFragment();
    private DetailFragment detailFragment;
    private AlertDialog.Builder alertDialogBuilder;
    public DatabaseReference myRef;
    String DBKey;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private int dayposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plan);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");


        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue

        myRef = database.getReference(DBKey);

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("날짜 선택 X");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(travel.getCountry() + "(으)로 여행");

        items = new ArrayList<Detail_item>();
        detail_recycler_adapter = new Detail_Recycler_adapter(getApplicationContext(), items);
        recyclerView = (RecyclerView) findViewById(R.id.DayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(detail_recycler_adapter);

        setting();


        detail_recycler_adapter.setItemClick(new Detail_Recycler_adapter.ItemClick() {
            public void onClick(View view, int position) {

                dayposition = position;
                Log.i("position", dayposition + "//" + "Pressed");

                detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("dayposition", dayposition); // Object 넘기기
                bundle.putSerializable("travel", travel);
                detailFragment.setArguments(bundle);
                Log.i("Test", "activity : " + position);

                replaceFragment(detailFragment);
            }
        });

    }
    String startDday,endDday;
    int start,end;

    public void setting() {
        String url = "https://travelplanner-42f43.firebaseio.com/"+DBKey+"/"+travel.getTitle()+"/Date";
        DatabaseReference dateRef = database.getReferenceFromUrl(url);

        dateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    /*String startDday,endDday;
                    int start,end,countDay;*/
                    if(snapshot.getKey().equals("endDday")){
                        endDday = snapshot.getValue(String.class);
                        end = Integer.parseInt(endDday);
                    }
                    else if(snapshot.getKey().equals("startDday")){
                        startDday = snapshot.getValue(String.class);
                        start = Integer.parseInt(startDday);
                    }
                }
                countDay = end-start;
                Log.e("aaaa","count"+countDay);
                for (int i = 0; i < countDay; i++)
                    items.add(new Detail_item("DAY", i+1+""));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {

            if (detailFragment != null) {
                detailFragment = ((DetailFragment) getSupportFragmentManager().findFragmentByTag("fragmentTag"));
                detailFragment.addItem();
                replaceFragment(detailFragment);
            } else {
                alert();
            }
        }

        else if (id == R.id.action_map) {
            if(detailFragment != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("dayposition", dayposition); // Object 넘기기
                bundle.putSerializable("travel",travel);
                dayMapFragment.setArguments(bundle);
                addToBackFragment(dayMapFragment);
            } else {
                alert();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void alert() {
        alertDialogBuilder
                .setMessage("날짜를 선택하세요.")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailFragment, fragment, "fragmentTag");
        fragmentTransaction.commit();
    }

    public void addToBackFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction().hide(detailFragment);
        fragmentTransaction.addToBackStack("mapback");
        fragmentTransaction.add(R.id.detailFragment, fragment, "MapfragmentTag");
        fragmentTransaction.commit();
    }

}
