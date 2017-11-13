package com.example.travelplanner;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2017-11-09.
 */

public class Travel {
    //여행 나라, 여행 지역, 여행 날짜, 예상 경비
    private String countury;
    private String region;
    private  String dates;
    private String cost;

    public Travel(){
        countury = "일본";
        region = "도쿄";
        dates="1/11";
        cost="100만원";
    }

    public Travel(String countury){

    }
    public static ArrayList<Travel> createTravelsList(int num){
        ArrayList<Travel> travel_items = new ArrayList<Travel>();

        //나중에 Travel_Item() 생성자 인자 넣어야됨
        for(int i=1;i<num;i++){
            travel_items.add(new Travel());
        }
        return travel_items;
    }
    public String getCountury() {
        return countury;
    }

    public void setCountury(String countury) {
        this.countury = countury;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
