package com.example.aditopaz.goodo;

import android.content.SharedPreferences;

/**
 * Created by aditopaz on 02/06/2017.
 */

public class GoodoDoc {

    static String userName = null;
    static String phoneNumber = null;
    static String userId = null;

    static void loadGoodoDocData(SharedPreferences preferences){

        userName = preferences.getString("username", null);
        phoneNumber = preferences.getString("phone_number", null);
        userId = preferences.getString("user_id", null);
    }
}
