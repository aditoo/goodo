package com.example.aditopaz.goodo;

import android.graphics.Bitmap;

/**
 * Created by aditopaz on 28/12/2016.
 */

public class VolEntry {

    String id;
    Bitmap image;
    String description;
    String name;
    String category;
    String date;
    //int startTime;
    int volNeeded;
    int volNum;
    int volMinNum;
    String loctaion;
    int duration;
    String timeleft; //remove this field and replace it with calculation of the time left;
    String imageName;
    String time;
    String creator;



    public VolEntry(String id, Bitmap image, String description, String name, String category,
                    int startTime, int volNum, int duration, String loctaion) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.name = name;
        this.category = category;
        //this.startTime = startTime;
        this.volNum = volNum;
        this.duration = duration;
        this.loctaion = loctaion;

    }

    public VolEntry(String id, String name, int volNeeded, int volNum, int volMinNum, String imageName, String timeleft){
        this.id = id;
        this.name = name;
        this.volNeeded = volNeeded;
        this.volNum = volNum;
        this.imageName = imageName;
        this.volMinNum = volMinNum;
        this.timeleft = timeleft;
    }

    public VolEntry(String id, String name, int volNeeded, int volNum, int volMinNum, String imageName,
                    String timeleft, String date, String time, String loctaion, String description, String creator){
        this.id = id;
        this.name = name;
        this.volNeeded = volNeeded;
        this.volNum = volNum;
        this.imageName = imageName;
        this.volMinNum = volMinNum;
        this.timeleft = timeleft;
        this.date = date;
        this.time = time;
        this.loctaion = loctaion;
        this.description = description;
        this.creator = creator;
    }

    public  String getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public String getTimeLeft(){
        return this.timeleft;
    }

    public int getVolNum(){
        return volNum;
    }

    public String getImageName(){
        return imageName;
    }

    public int getVolNeeded(){
        return volNeeded;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getLoctaion(){
        return loctaion;
    }

    public String getCreator(){ return creator; }
}