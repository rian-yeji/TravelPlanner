package com.example.travelplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2017-12-02.
 */

public class CheckListAdapter extends BaseAdapter {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();;
    DatabaseReference travelsRef = database.getReference("Travels");

    Context context;
    ArrayList<CheckItem> checkList;
    TextView modifyCheckListBtn;
    TextView checkItemTextView;
    CheckBox checkItemCheckBox;
    EditText newCheckList_editText;
    Travel travel;

    public CheckListAdapter(Context context,EditText newCheckList_editText,ArrayList<CheckItem> checkList,Travel travel){
        this.context = context;
        this.newCheckList_editText = newCheckList_editText;
        this.checkList = checkList;
        this.travel = travel;
    }

    public void addCheckItem(CheckItem checkItem) {
        this.checkList.add(checkItem);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return checkList.size();
    }

    @Override
    public Object getItem(int position) {
        return checkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_check, parent, false);
        }

        final CheckItem checkItem = checkList.get(position);
        checkItemTextView = (TextView) convertView.findViewById(R.id.checkItemTextView);
        checkItemTextView.setText(checkItem.getCheckItem());

        modifyCheckListBtn = (TextView) convertView.findViewById(R.id.modifyCheckListBtn);
        modifyCheckListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCheckList(position);
            }
        });

        checkItemCheckBox = (CheckBox)convertView.findViewById(R.id.checkItemCheckBox);
        if(checkItem.getIsChecked().equals("true"))
            checkItemCheckBox.setChecked(true);

        checkItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                String isCheckedStr;
                if(isChecked==true)
                    isCheckedStr="true";
                else
                    isCheckedStr="false";
                changeCheck(checkItem,isCheckedStr);
                Log.i("CheckListAdapter","changeCheck");
            }
        });
        return convertView;
    }
    public void changeCheck(final CheckItem checkItem,final String isChecked){
        DatabaseReference CheckListRef = travelsRef.child(travel.getTitle()).child("checkList");
        CheckListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("CheckListAdapter","changeCheck value="+checkItem.getCheckItem());
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String key = snapshot.getKey(); //item이름이 나옴
                    Log.i("CheckListAdapter","changeCheck key="+key);
                    if(key.equals(checkItem.getCheckItem())){
                        DatabaseReference checkItemRef = database.getReference(key);
                        Log.i("CheckListAdapter","changeCheck item="+checkItemRef.getKey()+" check="+isChecked);
                        //checkItemRef.setValue(isChecked);
                        travelsRef.child(travel.getTitle()).child("checkList").child(checkItem.getCheckItem()).setValue(isChecked);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void modifyCheckList(int position) {
        CheckItem tmpCheckItem = checkList.get(position); //일단 임시로 받아놓고
        deleteCheckList(position); //목록에서는 삭제
        newCheckList_editText.setText(tmpCheckItem.getCheckItem()); //기존의 항목을 editText로 올려서 수정하여 다시 저장하도록 유도
    }

    public void deleteCheckList(int position) {
        DatabaseReference ref = travelsRef.child(travel.getTitle()).child("checkList");
        if(checkList != null){
            CheckItem item = checkList.get(position);
            String checkItemStr = item.getCheckItem();
            ref.child(checkItemStr).setValue(null);

            //checkList.remove(position);
            notifyDataSetChanged();
        }
    }

}
