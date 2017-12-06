package com.example.travelplanner.detailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travelplanner.R;

import java.util.ArrayList;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class Detail_Recycler_adapter extends RecyclerView.Adapter<Detail_Recycler_adapter.Viewholder>{
    private Context context;
    private ArrayList<Detail_item> items ;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }


    public Detail_Recycler_adapter(Context context, ArrayList<Detail_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
            holder.date.setText(items.get(position).getDate());
            holder.day.setText(items.get(position).getDay());

        holder.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView date;
        public TextView day;

        public Viewholder(View itemView) {
            super(itemView);
            day = (TextView)itemView.findViewById(R.id.detail_day);
            date = (TextView)itemView.findViewById(R.id.detail_date);


        }
    }


}
