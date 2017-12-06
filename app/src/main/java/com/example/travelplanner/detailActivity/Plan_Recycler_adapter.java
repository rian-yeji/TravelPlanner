package com.example.travelplanner.detailActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelplanner.R;

import java.util.ArrayList;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class Plan_Recycler_adapter extends RecyclerView.Adapter<Plan_Recycler_adapter.Viewholder> {
    private Context context;
    private ArrayList<DetailPlan_item> items;

    //아이템 클릭시 실행 함수
    private Plan_Recycler_adapter.ItemClick itemClick;


    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(Plan_Recycler_adapter.ItemClick itemClick) {
        this.itemClick = itemClick;
    }


    public Plan_Recycler_adapter(Context context, ArrayList<DetailPlan_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {

        holder.location.setText(items.get(position).getLocation());
        holder.time.setText(items.get(position).getTime());
        holder.memo.setText(items.get(position).getMemo());
//        holder.cost.setText(items.get(position).getCost());

        holder.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, AddMapActivity.class);
                    intent.putExtra("TravelDetail",items.get(position).getTravel());
                    context.startActivity(intent);

            }
        });

        holder.costLinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ///////////////////////costFragment띄우고 setCost - TextView
                if(itemClick != null){
                    itemClick.onClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private EditText location;
        private EditText time;
        private EditText memo;
        private Button mapBtn;
        private LinearLayout costLinear;
        private TextView cost;


        public Viewholder(View itemView) {
            super(itemView);
            location = (EditText) itemView.findViewById(R.id.plan_location);
            time = (EditText) itemView.findViewById(R.id.plan_time);
            memo = (EditText) itemView.findViewById(R.id.plan_memo);
            mapBtn = (Button) itemView.findViewById(R.id.mapActivityBtn);
            cost = (TextView) itemView.findViewById(R.id.planCost);
            costLinear = (LinearLayout) itemView.findViewById(R.id.costLinear);
        }
    }
}
