package com.example.travelplanner.detailActivity;

/**
 * Created by hscom-009 on 2017-12-05.
 */

public class Detail_item {
    private String date;
    private String day;

    public Detail_item(String date, String day) {
        this.date = date;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
