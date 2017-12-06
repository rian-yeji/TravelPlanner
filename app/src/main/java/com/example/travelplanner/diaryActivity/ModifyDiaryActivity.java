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

public class ModifyDiaryActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travelsRef = database.getReference("Travels");

    private final int contentsEditLimit = 150;
    private TextView diaryDateTextView,diaryCancelTextView,diarySaveTextView,diaryTextNumTextView;
    private EditText diaryTitleEditText,diaryContentsEditText;

    private Travel travel; //파이어베이스에 저장할 때 씀(이건 어뎁터에서 실행시킨거라 이거 다르게 받아와야함)
    private String diaryTitle,diaryDate,diaryContents;
    private String deleteDiaryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_diary);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        diaryDate = intent.getStringExtra("diaryDate");
        diaryTitle = intent.getStringExtra("diaryTitle");
        deleteDiaryTitle = diaryTitle;
        diaryContents = intent.getStringExtra("diaryContents");

        diaryDateTextView = (TextView)findViewById(R.id.modifyDiaryDateTextView);
        //diaryDateTextView.setText(getCurrentDate()); //이건 수정이라 현재 날짜로 셋팅하면 안됨!!
        diaryDateTextView.setText(diaryDate);

        diaryCancelTextView = (TextView)findViewById(R.id.modifyDiaryCancelTextView);
        diarySaveTextView = (TextView)findViewById(R.id.modifyDiarySaveTextView);
        diaryTextNumTextView = (TextView)findViewById(R.id.modifyDiaryTextNumTextView);

        diaryTitleEditText = (EditText) findViewById(R.id.modifyDiaryTitleEditText);
        diaryTitleEditText.setText(diaryTitle);
        diaryContentsEditText = (EditText) findViewById(R.id.modifyDiaryContentsEditText);
        diaryContentsEditText.setText(diaryContents);

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
                diarySave(travel.getTitle());
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String count2 = String.valueOf(diaryContentsEditText.getText().toString().length());
                diaryTextNumTextView.setText("Number of Text : "+count2);
            }
        });

    }

    public void diarySave(String travelTitle){
        diaryDelete(travelTitle);//기존의 것을 삭제하고 다시 추가

        String date = diaryDateTextView.getText().toString();
        String title = diaryTitleEditText.getText().toString();
        String contents = diaryContentsEditText.getText().toString();

        travelsRef.child(travelTitle).child("Diary").child(title).child("date").setValue(date);
        travelsRef.child(travelTitle).child("Diary").child(title).child("contents").setValue(contents);

        Toast.makeText(getApplicationContext(),"Diary Modify Complete",Toast.LENGTH_LONG).show();
        finish();

       //newDiary를 디비에 업데이트
    }

    public void diaryDelete(String travelTitle){
        String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travelTitle+"/Diary";
        DatabaseReference diaryRef = database.getReferenceFromUrl(url);
        diaryRef.child(deleteDiaryTitle).setValue(null);
    }

}
