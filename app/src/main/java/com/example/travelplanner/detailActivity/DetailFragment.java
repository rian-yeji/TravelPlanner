package com.example.travelplanner.detailActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.example.travelplanner.interfaces.Callnack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private Travel travel;
    private RecyclerView recyclerView;
    private Plan_Recycler_adapter plan_Recycler_adapter;
    private ArrayList<DetailPlan_item> items ;
    private Button mapBtn;
    private CostFragment costFragment;
    private int dayposition;
    private DetailPlan_item plan_item;
    String cost;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Travels");
    FragmentManager fragmentManager;

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        dayposition = bundle.getInt("dayposition");
        Log.i("Test","fragment : "+ dayposition);
        travel = (Travel)bundle.getSerializable("travel");

        // mapBtn = (Button)view.findViewById(R.id.mapActivityBtn);

        items = new ArrayList<DetailPlan_item>();
        plan_Recycler_adapter = new Plan_Recycler_adapter(view.getContext(),items, callnack);
        recyclerView = (RecyclerView)view.findViewById(R.id.PlanRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(plan_Recycler_adapter);

        setting();
        return view;
    }

    public void replaceFragment() {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().hide(this);
        fragmentTransaction.addToBackStack("TT");
        fragmentTransaction.add(R.id.detailFragment, costFragment, "CostfragmentTag");
        fragmentTransaction.commit();
    }

    public void addItem() {
        items.add(new DetailPlan_item("","","",mapBtn,travel,dayposition));
        plan_Recycler_adapter.notifyDataSetChanged();
        Log.i("ADD","item ADD");
    }

    public void setting() {
        String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travel.getTitle()+"/Plan/Day"+dayposition;
        final DatabaseReference planRef = database.getReferenceFromUrl(url);

        planRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                items.clear();//초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    plan_item = snapshot.getValue(DetailPlan_item.class);

                    plan_item.setCost(plan_item.getCost());
                    plan_item.setTime(plan_item.getTime());
                    plan_item.setLocation(plan_item.getLocation());
                    plan_item.setMemo(plan_item.getMemo());
                    plan_item.setTravel(travel);
                    plan_item.setDayposition(dayposition);

                    items.add(plan_item);
                }


                plan_Recycler_adapter = new Plan_Recycler_adapter(getContext(), items,callnack);
                recyclerView.setAdapter(plan_Recycler_adapter);

                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setHasFixedSize(true);

                plan_Recycler_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private Callnack callnack = new Callnack() {
        @Override
        public void reFresh(int position) {
            costFragment = new CostFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("travel", travel);
            bundle.putInt("dayposition", dayposition);
            bundle.putInt("planposition",position);
            costFragment.setArguments(bundle);
            replaceFragment();
        }
    };

}