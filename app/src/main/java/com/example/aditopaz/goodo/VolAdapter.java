package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {

        VolEntry entry = volList.get(position);
        holder.volImageView.setBackgroundResource(R.mipmap.kidsmiling);
        holder.descriptionTextView.setText(entry.description);
        holder.numVolTextView.setText(String.format("%d",entry.volNum));
        holder.durationTextView.setText(String.format("%d",entry.duration));
        holder.cityTextView.setText(entry.city);


        // entry background changer
        if (position % 2 == 0)
            holder.itemView.setSelected(true);
        else
            holder.itemView.setSelected(false);


    }

    @Override
    public int getItemCount() {
        return volList.size();
    }

    public void generateData(){

        volList = new ArrayList<VolEntry>();
        volList.add(new VolEntry(0,"חוף הרצליה","איכות הסביבה", 4, "הרצליה"));

    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        ImageView volImageView;
        TextView descriptionTextView;
        TextView numVolTextView;
        TextView durationTextView;
        TextView cityTextView;


        public MyViewHolder(View itemView) {
            super(itemView);

            volImageView = (ImageView) itemView.findViewById(R.id.content_img);
            descriptionTextView = (TextView) itemView.findViewById(R.id.content_txt);
            numVolTextView = (TextView) itemView.findViewById(R.id.num_vol_txt);
            durationTextView = (TextView) itemView.findViewById(R.id.dur_txt);
            cityTextView = (TextView) itemView.findViewById(R.id.loc_txt);


        }
    }
}




