package com.example.travelplanner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PlanActivity extends AppCompatActivity {
    /*  title="새 여행 샘플";
      country = "일본";
      region = "도쿄";
      dates="1/11";
      costs="100만원";*/
    private EditText country;
    private EditText region;
    private EditText date;
    private EditText costs;
    private TextView dday;
    private ImageButton DetailPlanBtn;
    private ImageButton CostBtn;
    private ImageButton checkListBtn;
    private ImageButton diaryBtn;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private  Travel travel;
    private Context mContext;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mContext = this;
        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(travel.getCountry() + "(으)로 여행");

        country = (EditText) findViewById(R.id.Travelcountry);
        region = (EditText) findViewById(R.id.Travelregion);
        date = (EditText) findViewById(R.id.Traveldate);
        costs = (EditText) findViewById(R.id.Travelcost);
        dday = (TextView) findViewById(R.id.Traveldday);

        country.setText(travel.getCountry());
        region.setText(travel.getRegion());
        date.setText(travel.getDates());
        costs.setText(travel.getCosts());
        dday.setText("D-" + countdday(2018, 11, 10));

        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Context context = PlanActivity.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, year, month, day);
                datePickerDialog.show();
                return true;
            }
        });

        DetailPlanBtn = (ImageButton) findViewById(R.id.DetailBtn);
        CostBtn = (ImageButton)findViewById(R.id.CostBtn);

        DetailPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, AddMapActivity.class);
                intent.putExtra("TravelDetail",travel);
                mContext.startActivity(intent);

            }
        });

        CostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkListBtn = (ImageButton) findViewById(R.id.checkListBtn);
        diaryBtn = (ImageButton) findViewById(R.id.diaryBtn);

        checkListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, CheckListActivity.class);
                intent.putExtra("TravelDetail",travel);
                mContext.startActivity(intent);

            }
        });

        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, DiaryActivity.class);
                intent.putExtra("TravelDetail",travel);
                mContext.startActivity(intent);

            }
        });

    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String editdate = year + "/" + monthOfYear + "/" + dayOfMonth;
            date.setText(editdate);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Travels");
            myRef.child(travel.getTitle()).child("dates").setValue(editdate);

        }
    };

    public int countdday(int myear, int mmonth, int mday) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear,mmonth,mday);// D-day의 날짜를 입력
            long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis()/86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int)count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}