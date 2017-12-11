package com.example.travelplanner.detailActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class DetailPlanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Detail_Recycler_adapter detail_recycler_adapter;
    private ArrayList<Detail_item> items;
    private Travel travel;
    int countDay;
    private DetailFragment fragment;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plan);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("날짜 선택 X");

        countDay = 7;/*= intent.getIntExtra("countDay",0);*/
        Log.i("Ddddd", "detail//" + countDay);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(travel.getCountry() + "(으)로 여행");

        items = new ArrayList<Detail_item>();
        detail_recycler_adapter = new Detail_Recycler_adapter(getApplicationContext(), items);
        recyclerView = (RecyclerView) findViewById(R.id.DayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(detail_recycler_adapter);


        for (int i = 0; i < countDay; i++)
            items.add(new Detail_item(1 + i + ",", "일"));

        detail_recycler_adapter.setItemClick(new Detail_Recycler_adapter.ItemClick() {
            public void onClick(View view, int position) {
                Log.i("position", position + "//" + "Pressed");

                fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("dayposition", position); // Object 넘기기
                bundle.putSerializable("travel", travel);
                fragment.setArguments(bundle);
                Log.i("Test", "activity : " + position);

                replaceFragment();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /////////////////////////////////////어뎁터에 추가//////////////////////////////////////////
    public boolean onOptionsItemSelected(MenuItem item) {

        if (fragment != null) {
            fragment = ((DetailFragment) getSupportFragmentManager().findFragmentByTag("fragmentTag"));
            fragment.addItem();
            replaceFragment();
        } else {
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
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailFragment, fragment, "fragmentTag");
        fragmentTransaction.commit();
    }

}
