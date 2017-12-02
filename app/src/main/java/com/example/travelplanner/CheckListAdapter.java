package com.example.travelplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2017-12-02.
 */

public class CheckListAdapter extends BaseAdapter {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    Context context;
    ArrayList<CheckItem> checkList;
    ImageButton modifyCheckListBtn;
    TextView checkItemTextView;
    EditText newCheckList_editText;

    public CheckListAdapter(Context context,EditText newCheckList_editText,ArrayList<CheckItem> checkList){
        this.context = context;
        this.newCheckList_editText = newCheckList_editText;
        this.checkList = checkList;
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

        CheckItem checkItem = checkList.get(position);
        checkItemTextView = (TextView) convertView.findViewById(R.id.checkItemTextView);
        checkItemTextView.setText(checkItem.getCheckItem());

        modifyCheckListBtn = (ImageButton)convertView.findViewById(R.id.modifyCheckListBtn);
        modifyCheckListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCheckList(position);
            }
        });

        return convertView;
    }

    public void modifyCheckList(int position) {
        CheckItem tmpCheckItem = checkList.get(position); //일단 임시로 받아놓고
        deleteCheckList(position); //목록에서는 삭제
        newCheckList_editText.setText(tmpCheckItem.getCheckItem()); //기존의 항목을 editText로 올려서 수정하여 다시 저장하도록 유도
    }

    public void deleteCheckList(int position) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Travels");

        if(checkList != null){
            CheckItem checkItem = checkList.get(position);
            String childstr = checkItem.getCheckItem();
            myRef.child(childstr).setValue(null);

            notifyDataSetChanged();
        }
    }

}
