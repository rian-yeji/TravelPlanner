package com.example.travelplanner.detailActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelplanner.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private Button saveBtn;
    private EditText costEdit;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Travels");

    public CostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cost, container, false);

        saveBtn = (Button)view.findViewById(R.id.costSaveBtn);
        costEdit = (EditText)view.findViewById(R.id.costEdit);

        final String cost = costEdit.getText().toString();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // detailFragment.setCostText(cost);
                replace();
            }

        });

        return view;
    }

    public void replace() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(CostFragment.this).commit();
        fragmentManager.popBackStack();
    }


}
