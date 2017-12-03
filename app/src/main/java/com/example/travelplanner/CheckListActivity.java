package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference travelsRef = database.getReference("Travels");

    private Travel travel;
    private ListView checkListView;
    private CheckListAdapter adapter;
    ArrayList<CheckItem> checkList = new ArrayList<CheckItem>();
    private EditText newCheckList_editText;
    private TextView checkItemAddBtn,checkListDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");

        checkListView = (ListView) findViewById(R.id.checkListView);

        //adapter = new CheckListAdapter(this,newCheckList_editText);
        checkListDataSetting(); //파이어베이스에서 목록 가져와서 셋팅

        newCheckList_editText = (EditText)findViewById(R.id.newCheckList_editText);
        newCheckList_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    inputCheckItem();
                }
                return false;
            }
        });

        checkItemAddBtn = (TextView) findViewById(R.id.checkItemAddBtn);
        checkItemAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCheckItem();
            }
        });

        checkListDeleteBtn = (TextView)findViewById(R.id.checkListDeleteBtn);
        checkListDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCheckItems();
            }
        });

    }

    public void inputCheckItem(){ //DB에 저장
        String input = newCheckList_editText.getText().toString();
        travelsRef.child(travel.getTitle()).child("checkList").child(input).setValue("false");
        newCheckList_editText.setText(""); //입력필드 초기화
        adapter.notifyDataSetChanged();
        Log.i("CheckList","input 성공");
    }

    public void deleteCheckItems(){
        String url = "https://travelplanner-42f43.firebaseio.com/Travels/"+travel.getTitle()+"/checkList";
        DatabaseReference diaryRef = database.getReferenceFromUrl(url);
        for (int i=0;i<checkList.size();i++){
            if(checkList.get(i).getIsChecked().equals("true")){
                diaryRef.child(checkList.get(i).getCheckItem()).setValue(null);
            }
        }
    }

    public void checkListDataSetting(){
        //final Query travels = travelsRef.orderByPriority();
        DatabaseReference ref = travelsRef.child(travel.getTitle()).child("checkList");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                checkList.clear();//초기화
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String itemValue = snapshot.getKey();
                    String itemChecked = snapshot.getValue().toString();

                    CheckItem newItem = new CheckItem(itemValue);
                    newItem.setIsChecked(itemChecked);

                    checkList.add(newItem);
                }

                /*화면 셋팅(onCreate에서 하면 리스너가 나중에 불려서 데이터의 뷰셋팅이 안됨)*/
                /*if(checkList.isEmpty()) {//기존의 데이터가 없다면 샘플 하나 생성
                    checkList.add(new CheckItem("checkItemSample"));
                    Log.i("CheckList","SmpleData Add");
                }*/

                adapter = new CheckListAdapter(getApplicationContext(),newCheckList_editText,checkList,travel);
                checkListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
