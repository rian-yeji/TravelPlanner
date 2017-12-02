package com.example.travelplanner;

/**
 * Created by 이예지 on 2017-12-02.
 */

public class CheckItem {
    private String checkItem;
    private boolean isChecked;

    public CheckItem(String checkItem){
        this.checkItem = checkItem;
        this.isChecked = false;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
