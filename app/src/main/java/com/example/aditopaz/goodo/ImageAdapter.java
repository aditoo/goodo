package com.example.aditopaz.goodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by adi on 28/06/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context activity;
    ArrayList<ImageEntry> imageList;


    public ImageAdapter(Context activity, ArrayList<ImageEntry> entries){
        this.activity = activity;
        this.imageList = entries;
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
        int imgId = activity.getResources().getIdentifier(entry.name, "mipmap", activity.getPackageName());
        holder.imageView.setImageResource(imgId);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void generateData(){
        imageList = new ArrayList<ImageEntry>();

        imageList.add(new ImageEntry("beach_clean"));
        imageList.add(new ImageEntry("beach_cover"));
        imageList.add(new ImageEntry("elderly1"));
        imageList.add(new ImageEntry("elderly2"));
        imageList.add(new ImageEntry("elderly3"));
        imageList.add(new ImageEntry("forest_cover"));
        imageList.add(new ImageEntry("kids1"));
        imageList.add(new ImageEntry("kids2"));
        imageList.add(new ImageEntry("kids3"));
        imageList.add(new ImageEntry("kids4"));
        imageList.add(new ImageEntry("smilingboy"));
        imageList.add(new ImageEntry("homeless1"));
        imageList.add(new ImageEntry("homeless2"));
        imageList.add(new ImageEntry("tractor"));
        imageList.add(new ImageEntry("ruins1"));
        imageList.add(new ImageEntry("ruins2"));


    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_pic);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}


