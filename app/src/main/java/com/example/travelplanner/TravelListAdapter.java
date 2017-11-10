package com.example.travelplanner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 이예지 on 2017-11-09.
 */

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder>{
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView DdayTextView;
        public ImageButton detailTravelButton;

        private TravelListAdapter mTravels;

        private Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView, TravelListAdapter Travels) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            DdayTextView = (TextView) itemView.findViewById(R.id.Dday_textView);
            detailTravelButton = (ImageButton) itemView.findViewById(R.id.detailTravelButton);

            this.context = context;
            this.mTravels = Travels;

            detailTravelButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            // We can access the data within the views
            Toast.makeText(context,"click!", Toast.LENGTH_SHORT).show();
            mTravels.removeItem(position);

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

        // Set item views based on your views and data model
        /*TextView textView = viewHolder.nameTextView;
        textView.setText(travel.getDates());
        Button button = viewHolder.messageButton;
        button.setText("Delete");*/
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTravels.size();
    }

    public void removeItem(int p) {
        mTravels.remove(p);
        notifyItemRemoved(p);

    }

}