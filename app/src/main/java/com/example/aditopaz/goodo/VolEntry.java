package com.example.aditopaz.goodo;

import android.graphics.Bitmap;

/**
 * Created by aditopaz on 28/12/2016.
 */

public class VolEntry {

    int id = 0; //complete later
    Bitmap image;
    String description;
    String name;
    String category;
    int startTime;
    int volNeeded;
    int volNum;
    int volMinNum;
    String city;
    int duration;
    String timeleft; //remove this field and replace it with calculation of the time left;
    String imageName;



    public VolEntry(int id, Bitmap image, String description, String name, String category,
                    int startTime, int volNum, int duration, String city) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.name = name;
        this.category = category;
        this.startTime = startTime;
        this.volNum = volNum;
        this.duration = duration;
        this.city = city;

    }

    public VolEntry(int id, String name, int volNeeded, int volNum, int volMinNum, String imageName, String timeleft){
        this.id = id;
        this.name = name;
        this.volNeeded = volNeeded;
        this.volNum = volNum;
        this.imageName = imageName;
        this.volMinNum = volMinNum;
        this.timeleft = timeleft;
    }


}