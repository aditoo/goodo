package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aditopaz on 28/12/2016.
 */
public class VolAdapter extends RecyclerView.Adapter {

    Context activity;
    ArrayList<VolEntry> volList;

    public VolAdapter(Context mainActivity, ArrayList<VolEntry> o) {
        activity = mainActivity;
        volList = o;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

