package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by adi on 28/06/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context activity;
    ArrayList<ImageEntry> imageList;


    public ImageAdapter(Context activity, ArrayList<ImageEntry> imageList){
        this.activity = activity;
        this.imageList = imageList;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_entry, parent, false);
        ImageViewHolder view = new ImageViewHolder(itemView);

        return view;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final ImageEntry entry = imageList.get(position);
        int imgId = activity.getResources().getIdentifier(entry.getName(), "mipmap", activity.getPackageName());
        holder.imageView.setBackgroundResource(imgId);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }




    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = (LinearLayout) itemView.findViewById(R.id.image_pic);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            EntryClickListener entryClick = (EntryClickListener) activity;
            entryClick.entryClicked(view, getAdapterPosition());
        }
    }

    public interface EntryClickListener {
        void entryClicked(View view, int position);
    }

}


