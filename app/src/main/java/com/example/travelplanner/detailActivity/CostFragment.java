package com.example.travelplanner.detailActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.travelplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private CheckBox cbTransport;
    private CheckBox cbfood;
    private CheckBox cblodging;
    private CheckBox cbothers;

    public CostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cost, container, false);
/*

        cbTransport = (CheckBox)view.findViewById(R.id.transportation);
        cbfood = (CheckBox)view.findViewById(R.id.food);
        cblodging = (CheckBox)view.findViewById(R.id.lodging);
        cbothers = (CheckBox)view.findViewById(R.id.others);
*/


/*
        if(cbTransport.isChecked()) {
                Log.i("aaaA","Checked"+cbTransport.getText());

        }
        else if(cbfood.isChecked()) {

        }
        else if(cblodging.isChecked()) {

        }
        else if(cbothers.isChecked()) {

        }*/


        return view;
    }

}
