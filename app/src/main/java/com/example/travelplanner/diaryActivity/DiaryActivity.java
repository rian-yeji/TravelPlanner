package com.example.travelplanner.diaryActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.travelplanner.addTravelActivity.MarginItemDecoration;
import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travelsRef = database.getReference("Travles");

    DiaryListAdapter adapter;
    ArrayList<Diary> diaryList = new ArrayList<Diary>();
    RecyclerView diaryRecyclerView;
    ImageButton diaryAddBtn;

    private Travel travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        diaryRecyclerView = (RecyclerView)findViewById(R.id.diaryRecyclerView);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(10);
        diaryRecyclerView.addItemDecoration(itemDecoration);

        setting();//데이터베이스에서 데이터 불러와서 화면 설정(리사이클러뷰)

        diaryAddBtn = (ImageButton)findViewById(R.id.diaryAddBtn);
        diaryAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this,AddDiaryActivity.class);
                intent.putExtra("TravelDetail",travel);
                startActivity(intent);
            }
        });
    }

    public void setting(){
        //파이어베이스에서 데이터 얻어오기
        String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travel.getTitle()+"/Diary";
        DatabaseReference diaryRef = database.getReferenceFromUrl(url);

        //database.getReference("Travles").child("Travel2").child("Diary").child("하하").child("date").setValue("null");
        //database.getReference("Travles").child("Travel2").child("Diary").setValue("null");
        //travelsRef.child(travel.getTitle()).child("Diary").child(title).child("date").setValue(date);
        Log.i("DiaryActivity","Checking1//"+diaryRef.getKey()+"//"+travel.getTitle());
        diaryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diaryList.clear();
                // travelsRef.child(travel.getTitle()).child("Diary").child("하하").child("date").getRef()
                String test = dataSnapshot.child("하하").child("date").getValue(String.class);
                Log.i("DiaryActivity","Checking2//"+test);

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ////////
                    Log.i("DiaryActivity","Checking");
                    String title = snapshot.getKey();
                    String date = snapshot.child("date").getValue(String.class);
                    String contents = snapshot.child("contents").getValue(String.class);
                    Log.i("DiaryActivity",title+"/"+date+"/"+contents);

                    Diary newDiary = new Diary();
                    newDiary.setTitle(title);
                    newDiary.setDate(date);
                    newDiary.setContents(contents);
                    diaryList.add(newDiary);
                }
                /*if(diaryList.isEmpty()){
                    diaryList.add(new Diary("yyyy/MM/dd","Sample","새 일기를 작성 해 보세요."));
                }*/

                adapter = new DiaryListAdapter(getApplicationContext(),diaryList,travel);
                diaryRecyclerView.setAdapter(adapter);

                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                diaryRecyclerView.setLayoutManager(gridLayoutManager);

                diaryRecyclerView.setHasFixedSize(true);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
