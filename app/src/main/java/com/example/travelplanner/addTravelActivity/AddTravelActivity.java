package com.example.travelplanner.addTravelActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.detailActivity.PlanActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddTravelActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Travels");
    Button insertButton;
    String title,country,region;
    String startDates,endDates;
    TextView startDatesTextView,endDatesTextView;
    String Tag = "AddTravelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);

        startDatesTextView = (TextView)findViewById(R.id.addTravelStartDateTextView);
        endDatesTextView = (TextView)findViewById(R.id.addTravelEndDateTextView);

        startDatesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Context context = AddTravelActivity.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, startDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        endDatesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Context context = AddTravelActivity.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, endDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        insertButton = (Button)findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ((EditText)findViewById(R.id.title_editText)).getText().toString();
                country = ((EditText)findViewById(R.id.country_editText)).getText().toString();
                region = ((EditText)findViewById(R.id.region_editText)).getText().toString();
                /*startDates = ((TextView)findViewById(R.id.addTravelStartDateTextView)).getText().toString();
                endDates = ((TextView)findViewById(R.id.addTravelEndDateTextView)).getText().toString();*/

                if(title!=null&&country!=null&&region!=null&&startDates!=null&&endDates!=null){
                    addTravel();
                    Toast.makeText(getApplicationContext(),"새 여행 추가",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"빈칸을 모두 채워주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //새로운 여행 초기 시작값 및 생성
    private void addTravel(){
        DatabaseReference titleRef = database.getReference(title);

        myRef.child(titleRef.getKey()).child("Region").setValue(region);
        myRef.child(titleRef.getKey()).child("Country").setValue(country);
        myRef.child(titleRef.getKey()).child("Costs").setValue("0"); //초기값

        String array[] = startDates.split("/");
        myRef.child(titleRef.getKey()).child("Date").child("startDates").child("year").setValue(array[0]);
        myRef.child(titleRef.getKey()).child("Date").child("startDates").child("month").setValue(array[1]);
        myRef.child(titleRef.getKey()).child("Date").child("startDates").child("day").setValue(array[2]);


        String array2[] = endDates.split("/");
        myRef.child(titleRef.getKey()).child("Date").child("endDates").child("year").setValue(array2[0]);
        myRef.child(titleRef.getKey()).child("Date").child("endDates").child("month").setValue(array2[1]);
        myRef.child(titleRef.getKey()).child("Date").child("endDates").child("day").setValue(array2[2]);

    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDates = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
            startDatesTextView.setText(startDates);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDates = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;
            endDatesTextView.setText(endDates);
        }
    };
}
