package com.example.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by 이예지 on 2017-12-02.
 */

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {

    private Context context;
    private List<Diary> diaryList;
    private Travel travel;

    public DiaryListAdapter(Context context,List<Diary> diaryList,Travel travel){
        this.context = context;
        this.diaryList = diaryList;
        this.travel = travel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView diaryDateTextView,diaryTitleTextView,diaryContentsTextView;
        private ImageButton diaryModifyBtn,diaryDeleteBtn;

        private Context context;
        private DiaryListAdapter adapter;

        public ViewHolder(final Context context, View itemView, DiaryListAdapter diaryListAdapter, final Travel travel){
            super(itemView);

            this.context = context;
            this.adapter = diaryListAdapter;

            diaryDateTextView = (TextView)itemView.findViewById(R.id.diaryDateTextView);
            diaryTitleTextView = (TextView)itemView.findViewById(R.id.diaryTitleTextView);
            diaryContentsTextView = (TextView)itemView.findViewById(R.id.diaryContentsTextView);
            diaryModifyBtn = (ImageButton)itemView.findViewById(R.id.diaryModifyBtn);
            diaryDeleteBtn = (ImageButton)itemView.findViewById(R.id.diaryDeleteBtn);

            diaryModifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Toast.makeText(context,position+"번째 일기 클릭",Toast.LENGTH_LONG).show();
                    //다이어리 수정하는 기능을 하는 액티비티
                    Intent intent = new Intent(context,ModifyDiaryActivity.class);
                    intent.putExtra("diaryDate",diaryDateTextView.getText().toString());
                    intent.putExtra("diaryTitle",diaryTitleTextView.getText().toString());
                    intent.putExtra("diaryContents",diaryContentsTextView.getText().toString());
                    intent.putExtra("TravelDetail",travel);

                    context.startActivity(intent);
                }
            });

            diaryDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String diaryTitle = diaryTitleTextView.getText().toString();
                    String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travel.getTitle()+"/Diary";
                    DatabaseReference diaryRef = database.getReferenceFromUrl(url);
                    diaryRef.child(diaryTitle).setValue(null);

                }
            });
        }

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }


    public DiaryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_diary,parent,false);
        ViewHolder viewHolder = new ViewHolder(context,contactView,this,travel);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiaryListAdapter.ViewHolder viewholder, int position) {
        Diary diary = diaryList.get(position);

        viewholder.diaryDateTextView.setText(diary.getDate());
        viewholder.diaryTitleTextView.setText(diary.getTitle());
        viewholder.diaryContentsTextView.setText(diary.getContents());
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }


}
