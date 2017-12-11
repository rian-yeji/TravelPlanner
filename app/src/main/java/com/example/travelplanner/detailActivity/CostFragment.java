package com.example.travelplanner.detailActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private Button saveBtn;
    private EditText costEdit;
    private EditText costDetailEdit;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String DBKey;

    private Travel travel;
    private int dayposition;
    private int planposition;

    private DetailPlan_item plan_item;
    private String cost;
    private String costDetail;
    private Spinner spinner;



    public CostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cost, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue
        myRef = database.getReference(DBKey);

        saveBtn = (Button)view.findViewById(R.id.costSaveBtn);
        costEdit = (EditText)view.findViewById(R.id.costEdit);
        costDetailEdit = (EditText)view.findViewById(R.id.costDetail);
        spinner = (Spinner)view.findViewById(R.id.costSpinner);

        Bundle bundle = getArguments();
        travel = (Travel)bundle.getSerializable("travel");
        dayposition = bundle.getInt("dayposition");
        planposition = bundle.getInt("planposition");

        setting();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // detailFragment.setCostText(cost);
              //  DatabaseReference titleRef = database.getReference(travel.getTitle());
                //날짜 추가 수정 필요
                Log.e("dadf",spinner.getSelectedItem().toString());
                myRef.child(travel.getTitle()).child("Plan").child("Day"+dayposition).child("plan"+planposition).child("costValue").setValue(spinner.getSelectedItem().toString());
                myRef.child(travel.getTitle()).child("Plan").child("Day"+dayposition).child("plan"+planposition).child("cost").setValue(costEdit.getText().toString());
                myRef.child(travel.getTitle()).child("Plan").child("Day"+dayposition).child("plan"+planposition).child("costDetail").setValue(costDetailEdit.getText().toString());
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

    public void setting() {

        String url = "https://travelplanner-42f43.firebaseio.com/+" + DBKey+"/"+travel.getTitle()+"/Plan/Day"+dayposition+ "/plan" + planposition;
        final DatabaseReference planRef = database.getReferenceFromUrl(url);

        planRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                //items.clear();//초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();

                    if(key == "cost")
                        cost = snapshot.getValue().toString();
                    else if(key =="costDetail")
                        costDetail = snapshot.getValue().toString();

                    costEdit.setText(cost);
                    costDetailEdit.setText(costDetail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
