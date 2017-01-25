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
        holder.nameTextView.setText(entry.name);

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
        volList.add(new VolEntry(0, "ילידים סודנים"));
        volList.add(new VolEntry(1, "חוף הרצליה"));
        volList.add(new VolEntry(2, "חלוקת מזון"));
        volList.add(new VolEntry(3, "גן ילדים"));
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        LinearLayout volImageView;
        TextView nameTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            volImageView = (LinearLayout) itemView.findViewById(R.id.content_img);
            nameTextView = (TextView) itemView.findViewById(R.id.name_vol_txt);
        }
    }
}
