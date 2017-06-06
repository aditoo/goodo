package com.example.aditopaz.goodo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        //generateData();
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
        holder.nameTextView.setText(entry.name);
        holder.timeLeft.setText(entry.timeleft);
        // remove /10
        holder.numOfVols.setText(Integer.toString(entry.volNum));
        int imgId = activity.getResources().getIdentifier(entry.imageName, "mipmap",activity.getPackageName());
        holder.volImageView.setBackgroundResource(imgId);


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

        /*holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                holder.volImageView.setBackgroundColor(Color.BLACK);
            }
        });
        */

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



    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout volImageView;
        TextView nameTextView;
        ProgressBar progressBar;
        TextView timeLeft;
        TextView numOfVols;

        public MyViewHolder(View itemView) {
            super(itemView);

            volImageView = (LinearLayout) itemView.findViewById(R.id.content_img);
            nameTextView = (TextView) itemView.findViewById(R.id.name_vol_txt);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            timeLeft = (TextView) itemView.findViewById(R.id.hours_left);
            numOfVols = (TextView) itemView.findViewById(R.id.current_num_of_vol);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("EntryButton", "clicked");
            EntryClickListener entryClick = (EntryClickListener) activity;
            entryClick.entryClicked(view, getAdapterPosition());
        }
    }

    public interface EntryClickListener {
        void entryClicked(View view, int position);
    }
}
