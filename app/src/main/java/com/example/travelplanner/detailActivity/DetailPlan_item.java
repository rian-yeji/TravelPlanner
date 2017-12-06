package com.example.travelplanner.detailActivity;

import android.widget.Button;
import android.widget.LinearLayout;

import com.example.travelplanner.addTravelActivity.Travel;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class DetailPlan_item {

    String location;
    String memo;
    String time;
    Travel travel;
    Button mapBtn;


    int cost;
    LinearLayout costLinear;

    public DetailPlan_item(String location, String memo, String time, Button mapBtn, Travel travel/*, int cost, LinearLayout costLinear*/) {
        this.location = location;
        this.memo = memo;
        this.time = time;
        this.mapBtn = mapBtn;
        this.travel = travel;
      //  this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
