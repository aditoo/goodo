package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        holder.cityTextView.setText(entry.city);
        holder.nameTextView.setText(entry.name);
        holder.categoryTextView.setText(entry.category);

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
        volList.add(new VolEntry(0,"דימה","הכנת אוכל", 2, "רעננה"));
        volList.add(new VolEntry(0,"עדיטו","לעשות כושר", 6, "כפר סבא"));
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        LinearLayout volImageView;
        TextView descriptionTextView;
        TextView numVolTextView;
        TextView durationTextView;
        TextView cityTextView;
        TextView nameTextView;
        TextView categoryTextView;


        public MyViewHolder(View itemView) {
            super(itemView);

            volImageView = (LinearLayout) itemView.findViewById(R.id.content_img);
            durationTextView = (TextView) itemView.findViewById(R.id.dur_txt);
            cityTextView = (TextView) itemView.findViewById(R.id.loc_txt);
            nameTextView = (TextView) itemView.findViewById(R.id.name_vol_txt);
            categoryTextView = (TextView) itemView.findViewById(R.id.category_vol_txt);
        }
    }
}




