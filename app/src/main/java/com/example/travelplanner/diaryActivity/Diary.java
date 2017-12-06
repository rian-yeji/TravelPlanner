package com.example.travelplanner.diaryActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 이예지 on 2017-12-03.
 */

public class Diary {
    private String date;
    private String title;
    private String contents;

    public Diary(){
        this.date = getCurrentDate();
        this.title = "new Diary";
        this.contents = "Here is diary contents";
    }
    public Diary(String date,String title,String contents){
        this.date = date;
        this.title = title;
        this.contents = contents;
    }

    public String getCurrentDate(){ //현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = simpleDateFormat.format(date);//현재 날짜
        return currentDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
