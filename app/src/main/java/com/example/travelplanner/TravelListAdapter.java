package com.example.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 이예지 on 2017-11-09.
 */

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView DdayTextView, titleTextView, countryTextView, regionTextView, datesTextView, costsTextView;
        public ImageButton detailTravelButton;
        public ImageButton deleteTravelButton;

        private TravelListAdapter mTravels;

        private Context context;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView, TravelListAdapter Travels) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            DdayTextView = (TextView) itemView.findViewById(R.id.Dday_textView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            countryTextView = (TextView) itemView.findViewById(R.id.country_textView);
            regionTextView = (TextView) itemView.findViewById(R.id.region_textView);
            datesTextView = (TextView) itemView.findViewById(R.id.dates_textView);
            costsTextView = (TextView) itemView.findViewById(R.id.cost_textView);

            detailTravelButton = (ImageButton) itemView.findViewById(R.id.detailTravelButton);
            deleteTravelButton = (ImageButton) itemView.findViewById(R.id.deleteTravelButton);
            this.context = context;
            this.mTravels = Travels;

            detailTravelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition(); // gets item position
                    // We can access the data within the views
                    Toast.makeText(context, "Detail click!", Toast.LENGTH_SHORT).show();
                    mTravels.updateItem(position);
                }
            });

            deleteTravelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition(); // gets item position
                    // We can access the data within the views
                    Toast.makeText(context, "Remove click!", Toast.LENGTH_SHORT).show();
                    mTravels.removeItem(position);
                }
            });
        }

    }

    // Store a member variable for the contacts
    private List<Travel> mTravels;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TravelListAdapter(Context context, List<Travel> travels) {
        mTravels = travels;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    public TravelListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_travel, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, contactView, this);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TravelListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Travel travel = mTravels.get(position);

        viewHolder.titleTextView.setText(travel.getTitle());
        viewHolder.countryTextView.setText(travel.getCountry());
        viewHolder.regionTextView.setText(travel.getRegion());
        viewHolder.datesTextView.setText(travel.getDates());
        viewHolder.costsTextView.setText(travel.getCosts());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTravels.size();
    }

    public void updateItem(int p) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Travels");
        Travel travel = mTravels.get(p);
        Intent intent= new Intent(mContext, DetailTravelActivity.class);
        intent.putExtra("TravelDetail",travel);
        mContext.startActivity(intent);

    }


    public void removeItem(int p) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Travels");
        //DB에서 삭제
        if (mTravels != null) {
            Travel travel = mTravels.get(p);
            String childs = travel.getTitle();
            Log.i("haneulhaneul",childs);
            myRef.child(childs).setValue(null);

            mTravels.remove(p);
            notifyItemRemoved(p);

            final Query travels = myRef.orderByPriority();
        }
    }

   /* public String getItem(int position) {
        String item;

        return item;
    }*/
}
