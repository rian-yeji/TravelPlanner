package com.example.travelplanner.checkActivity;

/**
 * Created by 이예지 on 2017-12-02.
 */

public class CheckItem {
    private String checkItem;
    private String isChecked;

    public CheckItem(String checkItem){
        this.checkItem = checkItem;
        this.isChecked = "false";
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
