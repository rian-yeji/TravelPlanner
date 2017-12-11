package com.example.travelplanner.addTravelActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.travelplanner.R;
import com.example.travelplanner.detailActivity.PlanActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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

        private AlertDialog.Builder alertDialogBuilder;
        private Context context;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView, TravelListAdapter Travels) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("여행 삭제");


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
                    mTravels.updateItem(position);
                }
            });

            deleteTravelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // int position = getLayoutPosition(); // gets item position
                    alertDialogBuilder
                            .setMessage("여행계획 삭제?")
                            .setCancelable(false)
                            .setPositiveButton("삭제",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            int position = getLayoutPosition();
                                            mTravels.removeItem(position);
                                        }
                                    })
                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
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
        viewHolder.datesTextView.setText(travel.getStartDates()+"~"+travel.getEndDates());
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
        Intent intent= new Intent(mContext, PlanActivity.class);
        intent.putExtra("TravelDetail",travel);
        mContext.startActivity(intent);

    }


    public void removeItem(int p) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Travels");
        //DB에서 삭제
        if (mTravels != null) {
            Travel gettravel = mTravels.get(p);
            String childs = gettravel.getTitle();
            myRef.child(childs).setValue(null);

            mTravels.remove(p);
            notifyItemRemoved(p);
        }
    }
}
