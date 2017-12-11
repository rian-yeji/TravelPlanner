package com.example.travelplanner.detailActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.example.travelplanner.checkActivity.CheckListActivity;
import com.example.travelplanner.diaryActivity.DiaryActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private TextView startDate;
    private TextView endDate;
    private TextView dDay;
    private TextView planUpdateTextBtn;

    private ImageButton DetailPlanBtn;
    private ImageButton CostBtn;
    private ImageButton checkListBtn;
    private ImageButton diaryBtn;

    private int countDay=0;
    private int startdd;
    private int enddd;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private Travel travel;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("prefDB",MODE_PRIVATE);
        String DBKey = preferences.getString("DBKey",""); //key,defaultValue

        myRef = database.getReference(DBKey);


        country = (EditText) findViewById(R.id.Travelcountry);
        region = (EditText) findViewById(R.id.Travelregion);
        startDate = (TextView) findViewById(R.id.TravelStartdate);
        endDate = (TextView) findViewById(R.id.TravelEnddate);
        dDay = (TextView) findViewById(R.id.Traveldday);

        dataSetting();


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Context context = PlanActivity.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, startDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Context context = PlanActivity.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, endDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        DetailPlanBtn = (ImageButton) findViewById(R.id.DetailBtn);
        CostBtn = (ImageButton)findViewById(R.id.CostBtn);

        DetailPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, DetailPlanActivity.class);
                intent.putExtra("TravelDetail",travel);
                intent.putExtra("countDay",countDay);
                Log.i("Ddddd","//"+countDay);
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

        planUpdateTextBtn = (TextView)findViewById(R.id.planUpdateTextBtn);
        planUpdateTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTravel();
                Toast.makeText(getApplicationContext(),"Update Complete",Toast.LENGTH_SHORT).show();
            }
        });
    }


    String endDates,startDates;
    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDates = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
            startDate.setText(startDates);
        }
    };
    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDates = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
            endDate.setText(endDates);
        }
    };

    ////////////////////////////////////////////
    ///                                      ///
    ///(저장 버튼 누를 시에 이 함수 부르면 됨!!!)///
    ////////////////////////////////////////////
    private void updateTravel(){
        //DB에 저장
        myRef.child(travel.getTitle()).child("Country").setValue(country.getText().toString());
        myRef.child(travel.getTitle()).child("Region").setValue(region.getText().toString());
        myRef.child(travel.getTitle()).child("Country").setValue(country.getText().toString());

        String array[] = startDate.getText().toString().split("/");
        myRef.child(travel.getTitle()).child("Date").child("startDates").child("year").setValue(array[0]);
        myRef.child(travel.getTitle()).child("Date").child("startDates").child("month").setValue(array[1]);
        myRef.child(travel.getTitle()).child("Date").child("startDates").child("day").setValue(array[2]);

        String array2[] = endDate.getText().toString().split("/");
        myRef.child(travel.getTitle()).child("Date").child("endDates").child("year").setValue(array2[0]);
        myRef.child(travel.getTitle()).child("Date").child("endDates").child("month").setValue(array2[1]);
        myRef.child(travel.getTitle()).child("Date").child("endDates").child("day").setValue(array2[2]);

    }

    /*private DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String editdate = year + "/" + monthOfYear+1 + "/" + dayOfMonth;
            startDate.setText(editdate);

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Travels");
            DatabaseReference dayRef = database.getReference("Date");
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("startDate").child("year").setValue(year);
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("startDate").child("month").setValue(monthOfYear+1);
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("startDate").child("day").setValue(dayOfMonth);

            startdd  = countdday(year,monthOfYear+1,dayOfMonth);
            myRef.child(travel.getTitle()).child("dDay").setValue(startdd);

            if(startdd<0) {
                startdd = -startdd;
                dDay.setText("D+" + startdd);
            }
            else if(startdd>0)
                dDay.setText("D-"+startdd);

            else
                dDay.setText("D-DAY!!");


        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String editdate = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
            endDate.setText(editdate);

            *//*database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Travels");
            DatabaseReference dayRef = database.getReference("Date");
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("endDate").child("year").setValue(year);
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("endDate").child("month").setValue(monthOfYear+1);
            myRef.child(travel.getTitle()).child(dayRef.getKey()).child("endDate").child("day").setValue(dayOfMonth);*//*

            enddd  = countdday(year,monthOfYear+1,dayOfMonth);
            countDay = enddd - startdd;
            Log.i("aaaa","//"+countDay);
        }
    };*/

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

    public void dataSetting() {
        country.setText(travel.getCountry());
        region.setText(travel.getRegion());
        startDate.setText(travel.getStartDates());
        endDate.setText(travel.getEndDates());

        startdd = Integer.parseInt(travel.getdDay());

        if(startdd<0) {
            startdd = -startdd;
            dDay.setText("D+" + startdd);
        }
        else if(startdd>0)
            dDay.setText("D-"+startdd);

        else dDay.setText("D-DAY!!");

        Log.i("AAa","plan//" + countDay);

        String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travel.getTitle()+"/Date";
        DatabaseReference daryRef = database.getReferenceFromUrl(url);
        daryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //diaryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String year = snapshot.child("year").getValue().toString();
                    String month = snapshot.child("month").getValue().toString();
                    String day = snapshot.child("day").getValue().toString();

                    if(snapshot.getKey().equals("endDate")) {
                        endDate.setText(year + "/" + month + "/" + day);
                    }
                    else if(snapshot.getKey().equals("startDate")) {
                        startDate.setText(year + "/" + month + "/" + day);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        countDay = enddd-startdd;
        Log.i("Ddddd",enddd+"//" + countDay);
    }


}