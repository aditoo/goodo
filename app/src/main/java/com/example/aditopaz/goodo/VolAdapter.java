package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aditopaz on 28/12/2016.
 */
public class VolAdapter extends RecyclerView.Adapter {

    Context activity;
    //This is our data
    ArrayList<VolEntry> volList;

    public VolAdapter(Context mainActivity, ArrayList<VolEntry> o) {
        activity = mainActivity;
        volList = o;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // edited on sunday 8.1 by aditopaz
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vol_list_content,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}



