package com.example.travelplanner.diaryActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDiaryActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travelsRef = database.getReference("Travels");

    private final int contentsEditLimit = 150;

    private TextView diaryDateTextView,diaryCancelTextView,diarySaveTextView,diaryTextNumTextView;
    private EditText diaryTitleEditText,diaryContentsEditText;

    private Travel travel; //파이어베이스에 저장할 때 씀

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        diaryDateTextView = (TextView)findViewById(R.id.addDiaryDateTextView);
        diaryDateTextView.setText(getCurrentDate()); //현재날짜로 셋팅
        diaryCancelTextView = (TextView)findViewById(R.id.addDiaryCancelTextView);
        diarySaveTextView = (TextView)findViewById(R.id.addDiarySaveTextView);
        diaryTextNumTextView = (TextView)findViewById(R.id.addDiaryTextNumTextView);

        diaryTitleEditText = (EditText) findViewById(R.id.addDiaryTitleEditText);
        diaryContentsEditText = (EditText) findViewById(R.id.addDiaryContentsEditText);

        diaryCancelTextView.setOnClickListener(new View.OnClickListener() {
            //작성을 취소
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        diarySaveTextView.setOnClickListener(new View.OnClickListener() {
            //작성한 내용을 저장
            @Override
            public void onClick(View v) {
                diarySave();
                //finish();
            }
        });

        String count= String.valueOf(diaryContentsEditText.getText().toString().length());
        diaryTextNumTextView.setText("Number of Text : "+count+"/"+contentsEditLimit);

        diaryContentsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String count = String.valueOf(diaryContentsEditText.getText().toString().length());
                diaryTextNumTextView.setText("Number of Text : "+count+"/"+contentsEditLimit);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }
        });

    }

    public void diarySave(){
        String date = diaryDateTextView.getText().toString();
        String title = diaryTitleEditText.getText().toString();
        String contents = diaryContentsEditText.getText().toString();

        travelsRef.child(travel.getTitle()).child("Diary").child(title).child("date").setValue(date);
        travelsRef.child(travel.getTitle()).child("Diary").child(title).child("contents").setValue(contents);

        Toast.makeText(getApplicationContext(),"Diary Save Complete",Toast.LENGTH_LONG).show();
        finish();
    }

    public String getCurrentDate(){ //현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = simpleDateFormat.format(date);//현재 날짜
        return currentDate;
    }

}
