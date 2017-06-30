package com.example.aditopaz.goodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aditopaz on 24/04/2017.
 */

public class VolInformation extends AppCompatActivity {

    private LinearLayout volImageView;
    private TextView nameTextView;
    private TextView timeLeft;
    private TextView numOfVols;
    private TextView maxNumOfVols;
    private int volNeeded;
    private ProgressBar progressBar;
    private TextView location;
    private TextView description;
    private TextView dateTime;
    private String id;
    private StringBuilder messageToSend = new StringBuilder();
    private String number;
    private String name;
    private boolean joined = false;
    private ArrayList<String> users;
    Button join;
    private static final int RESULT_PICK_CONTACT = 65535;
    String phoneNo = null;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.vol_info);

        linearLayout = (LinearLayout) findViewById(R.id.vol_info_linear);
        setViews();

        join = (Button) findViewById(R.id.join_button);
        if (!joined) {
            join.setText("אני בא!");
        } else {
            join.setText("הסר אותי מהתנדבות");
        }

        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!joined) {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_window, null);

                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);

                    if (Build.VERSION.SDK_INT >= 21) {
                        popupWindow.setElevation(5.0f);
                    }

                    Button invite = (Button) popupView.findViewById(R.id.invite_friends);
                    Button done = (Button) popupView.findViewById(R.id.done);

                    invite.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {


                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(intent, RESULT_PICK_CONTACT);

                        }
                    });

                    done.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            StringBuilder url = new StringBuilder();
                            SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            GoodoDoc.loadGoodoDocData(settings);

                            url.append("https://arcane-earth-90335.herokuapp.com/volunteers?vol=");
                            url.append(id);
                            url.append("&user=");
                            url.append(settings.getString("user_id", null));

                            updateVol(url.toString());
                            users.add(settings.getString("user_id", null));

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            Log.d("Update-URL", url.toString());
                            if (phoneNo != null) {
                                String messageToSend = "היי, זו היא הזמנה להצטרפות להתנדבות שנרשמתי אליה באפליקציית Goodo.";
                                Log.d("phoneNumber", phoneNo);
                                SmsManager.getDefault().sendTextMessage(phoneNo, null, messageToSend.toString(), null, null);
                            }
                            startActivity(i);

                        }
                    });


                    popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);


                } else {

                    StringBuilder url = new StringBuilder();
                    SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    GoodoDoc.loadGoodoDocData(settings);

                    url.append("https://arcane-earth-90335.herokuapp.com/volunteers?vol=");
                    url.append(id);
                    url.append("&user=");
                    url.append(settings.getString("user_id", null));

                    removeVol(url.toString());
                    users.remove(settings.getString("user_id", null));


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);


                    startActivity(i);
                }
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
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViews() {

        Bundle infoBund = getIntent().getExtras();

        if (infoBund != null) {

            id = infoBund.getString("ID");
            Log.d("Update-CuurentVolNum", id);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setRotation(180);
            volImageView = (LinearLayout) findViewById(R.id.content_img);
            nameTextView = (TextView) findViewById(R.id.name_vol_txt);
            timeLeft = (TextView) findViewById(R.id.hours_left);
            numOfVols = (TextView) findViewById(R.id.current_num_of_vol);
            location = (TextView) findViewById(R.id.location_txt);
            description = (TextView) findViewById(R.id.description_txt);
            dateTime = (TextView) findViewById(R.id.date_time_txt);
            maxNumOfVols = (TextView) findViewById(R.id.max_num_of_vol);


            users = infoBund.getStringArrayList("USERS");
            SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
            GoodoDoc.loadGoodoDocData(settings);
            String userId = settings.getString("user_id", null);
            for (String user : users) {
                Log.d("user: ", user);
                Log.d("usereId: ", userId);
                if (userId.equals(user)) {
                    joined = true;
                    break;
                }

            }


            number = infoBund.getString("CREATOR");
            Log.d("sms number", number);
            volNeeded = infoBund.getInt("VOLNEEDED");
            maxNumOfVols.setText(Integer.toString(volNeeded));

            int imgId = getResources().getIdentifier(infoBund.getString("IMAGENAME"), "mipmap", getPackageName());
            volImageView.setBackgroundResource(imgId);

            nameTextView.setText(infoBund.getString("NAME"));
            timeLeft.setText(infoBund.getString("TIMELEFT"));

            final int volNum = infoBund.getInt("VOLNUM");
            // remove /10
            numOfVols.setText(Integer.toString(volNum));

            location.setText(infoBund.getString("LOCATION"));
            description.setText(infoBund.getString("DESCRIPTION"));
            StringBuilder dt = new StringBuilder();
            String[] date = infoBund.getString("DATE").split("-");
            StringBuilder newDate = new StringBuilder().append(date[2]).append("-").append(date[1]).append("-").append(date[0]);
            dt.append(newDate.append(",  "));
            dt.append(infoBund.getString("TIME"));
            String[] time = infoBund.getString("TIME").split(":");
            int endTimeHour = Integer.parseInt(time[0]);
            endTimeHour += infoBund.getInt("DURATION");
            StringBuilder endTime = new StringBuilder();
            endTime.append(endTimeHour).append(":").append(time[1]);
            dt.append("-").append(endTime);
            dateTime.setText(dt);

            final Handler handler = new Handler();
            new Thread(new Runnable() {


                int i = 0;
                int progressStatus = 0;

                public void run() {
                    //holder.progressBar.setIndeterminate(false);
                    progressBar.setMax(volNeeded);
                    while (progressStatus < volNum) {

                        progressStatus += doWork();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Update the progress bar
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                i++;
                            }
                        });
                    }
                }

                private int doWork() {

                    return i * 2;
                }
            }).start();

        } else {
            Log.d("VolInformation", "Info Bundle is empty");
        }
    }

    protected RequestQueue updateVol(String url) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update-CuurentVolNum", "successed");
                        Log.d("Update-CuurentVolNum", "ID - " + id);
                        try {
                            getResponseParser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Update-CuurentVolNum", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }

    protected void getResponseParser(JSONObject response) throws JSONException {

        try {
            int modified = response.getInt("nModified");
            SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
            GoodoDoc.loadGoodoDocData(settings);
            name = settings.getString("username", null);
            messageToSend.append("היי, שמי ").append(name).append(" והצטרפתי להתנדבות שיצרת.");
            if (modified == 1) {
                SmsManager.getDefault().sendTextMessage(number, null, messageToSend.toString(), null, null);
            }
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected RequestQueue removeVol(String url) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update-CuurentVolNum", "successed");
                        Log.d("Update-CuurentVolNum", "ID - " + id);
                        Toast.makeText(VolInformation.this, "הוסרת מההתנדבות בהצלחה!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Update-CuurentVolNum", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }

}
