package com.example.aditopaz.goodo;

import android.graphics.Bitmap;

/**
 * Created by aditopaz on 28/12/2016.
 */

public class VolEntry {

    int id = 0; //complete later
    Bitmap image;
    String description;
    int volNum;
    String city;
    int duration;

    public VolEntry(int id, Bitmap image, String description, int volNum, int duration, String city) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.volNum = volNum;
        this.duration = duration;
        this.city = city;
    }


}
