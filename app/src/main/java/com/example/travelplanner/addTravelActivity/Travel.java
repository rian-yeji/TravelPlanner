package com.example.travelplanner.addTravelActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 이예지 on 2017-11-09.
 */

public class Travel implements Serializable{
    //여행 이름, 나라, 여행 지역, 여행 날짜, 예상 경비
    private String title;
    private String country;
    private String region;
    private String dates;
    private String costs;



    private int dDay;

    public Travel(){
        title="새 여행 샘플";
        country = "일본";
        region = "도쿄";
        dates="1/11";
        costs="100만원";
        dDay=0;
    }

    public Travel(String countury,String region,String date){
        this.country = countury;
        this.region = region;
        this.dates = date;
    }

    public static ArrayList<Travel> createTravelsList(int num){
        ArrayList<Travel> travel_items = new ArrayList<Travel>();

        //나중에 Travel_Item() 생성자 인자 넣어야됨
        for(int i=1;i<=num;i++){
            travel_items.add(new Travel());
        }
        return travel_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String cost) {
        this.costs = cost;
    }
    public int getdDay() {
        return dDay;
    }

    public void setdDay(int dDay) {
        this.dDay = dDay;
    }

}
