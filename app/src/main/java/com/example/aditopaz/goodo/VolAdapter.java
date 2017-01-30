package com.example.aditopaz.goodo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aditopaz on 28/12/2016.
 */
public class VolAdapter extends RecyclerView.Adapter<VolAdapter.MyViewHolder> {

    Context activity;
    //This is our data
    ArrayList<VolEntry> volList;

    public VolAdapter(Context mainActivity, ArrayList<VolEntry> entries) {
        activity = mainActivity;
        volList = entries;
    }

    //testing constructor
    public VolAdapter(Context mainActivity) {
        activity = mainActivity;
        generateData();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vol_list_content,parent,false);
        MyViewHolder view = new MyViewHolder(itemView);
        return view;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final VolEntry entry = volList.get(position);
        holder.volImageView.setBackgroundResource(R.mipmap.kidsmiling);
        holder.nameTextView.setText(entry.name);

        final Handler handler = new Handler();
        new Thread(new Runnable() {

            int volNum = entry.volNum;
            int volNeeded = entry.volNeeded;
            int i = 0;
            int progressStatus = 0;

            public void run() {
                //holder.progressBar.setIndeterminate(false);
                holder.progressBar.setMax(volNeeded);
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
                            holder.progressBar.setProgress(progressStatus);
                            i++;
                        }
                    });
                }
            }
            private int doWork() {

                return i * 2;
            }
        }).start();


        /*
        // entry background changer
        if (position % 2 == 0)
            holder.itemView.setSelected(true);
        else
            holder.itemView.setSelected(false);
        */
    }

    @Override
    public int getItemCount() {
        return volList.size();
    }

    public void generateData(){

        volList = new ArrayList<VolEntry>();
        volList.add(new VolEntry(0, "ילידים סודנים",100, 50));
        volList.add(new VolEntry(1, "חוף הרצליה", 100, 70));
        volList.add(new VolEntry(2, "חלוקת מזון", 100,100));
        volList.add(new VolEntry(3, "גן ילדים", 100, 20));
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        LinearLayout volImageView;
        TextView nameTextView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            volImageView = (LinearLayout) itemView.findViewById(R.id.content_img);
            nameTextView = (TextView) itemView.findViewById(R.id.name_vol_txt);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
