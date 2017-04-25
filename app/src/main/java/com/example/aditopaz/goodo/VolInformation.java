package com.example.aditopaz.goodo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private TextView stratTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.vol_info);

        setViews();
    }

    private void setViews(){

        Bundle infoBund = getIntent().getExtras();

        if(infoBund != null){

            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            volImageView = (LinearLayout) findViewById(R.id.content_img);
            nameTextView = (TextView) findViewById(R.id.name_vol_txt);
            timeLeft = (TextView) findViewById(R.id.hours_left);
            numOfVols = (TextView) findViewById(R.id.current_num_of_vol);
            location = (TextView) findViewById(R.id.location_txt);
            description = (TextView) findViewById(R.id.description_txt);
            stratTime = (TextView) findViewById(R.id.duration_txt);

            volNeeded = infoBund.getInt("VOLNEEDED");

            int imgId = getResources().getIdentifier(infoBund.getString("IMAGENAME"), "mipmap",getPackageName());
            volImageView.setBackgroundResource(imgId);

            nameTextView.setText(infoBund.getString("NAME"));
            timeLeft.setText(infoBund.getString("TIMELEFT"));

            final int volNum = infoBund.getInt("VOLNUM");
            // remove /10
            numOfVols.setText(Integer.toString(volNum / 10));

            location.setText(infoBund.getString("LOCATION"));
            description.setText(infoBund.getString("DESCRIPTION"));
            stratTime.setText(infoBund.getString("STARTTIME"));

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
}
