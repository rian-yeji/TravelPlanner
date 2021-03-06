package com.example.travelplanner.detailActivity;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.travelplanner.addTravelActivity.Travel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class DetailPlan_item {

    String location;
    String memo;
    String time;
    Travel travel;
    Button mapBtn;
    String titile;


    ImageButton planSaveBtn;
    ImageButton planDeleteBtn;
    int dayposition;
    String cost;
    LinearLayout costLinear;
    String costValue;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String DBKey;

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getCostDetail() {
        return costDetail;
    }

    public void setCostDetail(String costDetail) {
        this.costDetail = costDetail;
    }

    String costDetail;

    public DetailPlan_item() {
    }

    public DetailPlan_item(String location, String memo, String time, String cost,String costValue ,String costDetail, Button mapBtn, Travel travel,int dayposition/*, int cost, LinearLayout costLinear*/) {
        this.location = location;
        this.memo = memo;
        this.time = time;
        this.mapBtn = mapBtn;
        this.travel = travel;
        this.dayposition  = dayposition;
        this.cost = cost;
        this.costValue= costValue;

        //날짜 추가 수정 필요
       /* myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("costDetail").setValue("detail");
        myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+position).child("costValue").setValue("기타");*/


        this.costDetail = costDetail;
        //  this.costLinear = costLinear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Button getMapBtn() {
        return mapBtn;
    }

    public void setMapBtn(Button mapBtn) {
        this.mapBtn = mapBtn;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public LinearLayout getCostLinear() {
        return costLinear;
    }

    public void setCostLinear(LinearLayout costLinear) {
        this.costLinear = costLinear;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ImageButton getPlanSaveBtn() {
        return planSaveBtn;
    }

    public void setPlanSaveBtn(ImageButton planSaveBtn) {
        this.planSaveBtn = planSaveBtn;
    }

    public ImageButton getPlanDeleteBtn() {
        return planDeleteBtn;
    }

    public void setPlanDeleteBtn(ImageButton planDeleteBtn) {
        this.planDeleteBtn = planDeleteBtn;
    }

    public int getDayposition() {
        return dayposition;
    }

    public void setDayposition(int dayposition) {
        this.dayposition = dayposition;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }




}