package com.example.aditopaz.goodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by aditopaz on 24/04/2017.
 */

public class VolInformation extends AppCompatActivity {

    private LinearLayout volImageView;
    private TextView nameTextView;
    private TextView timeLeft;
    private TextView numOfVols;
    private int volNeeded;
    private ProgressBar progressBar;
    private TextView location;
    private TextView description;
    private TextView dateTime;
    private String id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.vol_info);
        Button join = (Button) findViewById(R.id.join_button);

        setViews();




        join.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                StringBuilder url = new StringBuilder();
                url.append("https://arcane-earth-90335.herokuapp.com/volunteers?id=");
                url.append(id);
                Log.d("Update-URL", url.toString());
                updateVol(url.toString());
                startActivity(i);

            }
        });


    }

    private void setViews(){

        Bundle infoBund = getIntent().getExtras();

        if(infoBund != null){

            id = infoBund.getString("ID");
            Log.d("Update-CuurentVolNum", id);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            volImageView = (LinearLayout) findViewById(R.id.content_img);
            nameTextView = (TextView) findViewById(R.id.name_vol_txt);
            timeLeft = (TextView) findViewById(R.id.hours_left);
            numOfVols = (TextView) findViewById(R.id.current_num_of_vol);
            location = (TextView) findViewById(R.id.location_txt);
            description = (TextView) findViewById(R.id.description_txt);
            dateTime = (TextView) findViewById(R.id.date_time_txt);

            volNeeded = infoBund.getInt("VOLNEEDED");

            int imgId = getResources().getIdentifier(infoBund.getString("IMAGENAME"), "mipmap",getPackageName());
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

        }
        else {
            Log.d("VolInformation","Info Bundle is empty");
        }
    }

    protected RequestQueue updateVol(String url) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update-CuurentVolNum", "successed");
                        Log.d("Update-CuurentVolNum", "ID - " + id);

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
