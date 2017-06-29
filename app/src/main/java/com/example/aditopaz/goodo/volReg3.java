package com.example.aditopaz.goodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by adi on 24/05/2017.
 */

public class volReg3 extends AppCompatActivity{



    private static final int RESULT_PICK_CONTACT = 65535;
    private static final int PICK_CONTACT = 0;
    String phoneNo = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_reg3);

        Button  done = (Button) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {   Intent i = new Intent(getApplicationContext(),MainActivity.class);
                Bundle infoBund = getIntent().getExtras();
                Log.d("Bundle", infoBund.getString("VOL_NAME").toString());

                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                GoodoDoc.loadGoodoDocData(settings);

                StringBuilder url = new StringBuilder();
                url.append("https://arcane-earth-90335.herokuapp.com/volunteers?title=");
                url.append(infoBund.getString("VOL_NAME"));
                url.append("&maxNumber=");
                url.append(infoBund.getString("VOL_NUM"));
                url.append("&minNumber=");
                url.append(infoBund.getString("MIN_VOL_NUM"));
                // initialize with 0 - will be updated if joining
                url.append("&currentNum=");
                url.append("0");
                url.append("&address=");
                url.append(infoBund.getString("ADDRESS"));
                url.append("&date=");
                url.append(infoBund.getString("DATE"));
                url.append("&duration=");
                url.append(infoBund.getString("DURATION"));
                url.append("&description=");
                url.append(infoBund.getString("DESCRIPTION"));
                // currently deafult - need to change
                url.append("&imgName=");
                url.append(infoBund.getString("IMAGENAME"));
                url.append("&creator=");
                url.append(settings.getString("phone_number", null));

                getRequest(url.toString());

                i.putExtras(infoBund);
                if(phoneNo != null) {
                    String messageToSend = "היי, זו היא הזמנה להצטרפות להתנדבות חדשה שהעלתי לאפליקציית Goodo";
                    Log.d("phoneNumber", phoneNo);
                    SmsManager.getDefault().sendTextMessage(phoneNo, null, messageToSend.toString(), null, null);
                }
                startActivity(i);



            }
        });

       Button btn = (Button) findViewById(R.id.add_friends);
       btn.setOnClickListener(new View.OnClickListener(){
           public void onClick(View arg0)
           {
               Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
               startActivityForResult(intent, RESULT_PICK_CONTACT);


           }
       });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        }

    }
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {

            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);


        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected RequestQueue getRequest(String url) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Post-Response", "posted!");
                        Log.d("Post-Response", "Response - " + response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Post - Error", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }



}

