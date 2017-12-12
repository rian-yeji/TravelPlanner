package com.example.travelplanner.detailActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.detailActivity.Map.AddMapActivity;
import com.example.travelplanner.interfaces.Callnack;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class Plan_Recycler_adapter extends RecyclerView.Adapter<Plan_Recycler_adapter.Viewholder> {
    private Context context;
    private ArrayList<DetailPlan_item> items;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String DBKey;

    private AlertDialog.Builder alertDialogBuilder;
    private  int dayposition;
    //아이템 클릭시 실행 함수
    private Plan_Recycler_adapter.ItemClick itemClick;
    private FragmentActivity activity;
    private Callnack callnack;

    public Plan_Recycler_adapter(Context context, ArrayList<DetailPlan_item> items, Callnack callnack,String DBKey) {
        this.context = context;
        this.items = items;
        this.callnack = callnack;
        this.DBKey = DBKey;
    }


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
    public void onBindViewHolder(final Viewholder holder, final int position) {

        holder.location.setText(items.get(position).getLocation());
        holder.time.setText(items.get(position).getTime());
        holder.memo.setText(items.get(position).getMemo());
        holder.cost.setText(items.get(position).getCost());

        holder.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMapActivity.class);
                intent.putExtra("TravelDetail",items.get(position).getTravel());
                intent.putExtra("dayposition",dayposition);
                intent.putExtra("planposition", position);
                context.startActivity(intent);
            }
        });

        holder.costLinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                callnack.reFresh(position);
            }
        });

        holder.planSaveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dayposition = items.get(position).getDayposition();
                Log.i("position123","//"+position);
                myRef = database.getReference(DBKey);
                DatabaseReference titleRef = myRef.child(items.get(position).getTravel().getTitle());
                notifyDataSetChanged();
                //날짜 추가 수정 필요
                myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("time").setValue(holder.time.getText().toString());
                myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("location").setValue(holder.location.getText().toString());
                myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("cost").setValue(holder.cost.getText().toString());
                myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("memo").setValue(holder.memo.getText().toString());

                Toast.makeText(context,"저장되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        holder.planDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete Plan");

                alertDialogBuilder
                        .setMessage("여행계획 삭제?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        DatabaseReference titleRef = database.getReference(items.get(position).getTravel().getTitle());
                                        myRef = database.getReference(DBKey);
                                        myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).setValue(null);
                                        items.remove(position);
                                        notifyItemRemoved(position);
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
        private ImageButton planSaveBtn;
        private ImageButton planDeleteBtn;
        private int dayposition;


        public Viewholder(View itemView) {
            super(itemView);
            location = (EditText) itemView.findViewById(R.id.plan_location);
            time = (EditText) itemView.findViewById(R.id.plan_time);
            memo = (EditText) itemView.findViewById(R.id.plan_memo);
            mapBtn = (Button) itemView.findViewById(R.id.mapActivityBtn);
            cost = (TextView) itemView.findViewById(R.id.planCost);
            costLinear = (LinearLayout) itemView.findViewById(R.id.costLinear);
            planSaveBtn = (ImageButton) itemView.findViewById(R.id.planSaveBtn);
            planDeleteBtn = (ImageButton) itemView.findViewById(R.id.planDeleteBtn);
        }
    }
}