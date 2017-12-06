package com.example.travelplanner.detailActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;

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

    public DetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        int position;

        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        Log.i("Test","fragment : "+position);
        travel = (Travel)bundle.getSerializable("travel");

        mapBtn = (Button)view.findViewById(R.id.mapActivityBtn);

        items = new ArrayList<DetailPlan_item>();
        plan_Recycler_adapter = new Plan_Recycler_adapter(view.getContext(),items);
        recyclerView = (RecyclerView)view.findViewById(R.id.PlanRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(plan_Recycler_adapter);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        plan_Recycler_adapter.setItemClick(new Plan_Recycler_adapter.ItemClick() {
            public void onClick(View view, int position) {
                costFragment = new CostFragment();
                replaceFragment();
            }
        });

        return view;
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().hide(this);
        fragmentTransaction.addToBackStack("TT");
        fragmentTransaction.add(R.id.detailFragment, costFragment, "CostfragmentTag");
        fragmentTransaction.commit();
    }


    public void addItem() {
        items.add(new DetailPlan_item("제주바다","수영","3:30",mapBtn,travel));
        plan_Recycler_adapter.notifyDataSetChanged();
        Log.i("ADD","item ADD");
    }

}
