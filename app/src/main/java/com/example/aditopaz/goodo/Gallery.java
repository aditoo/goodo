package com.example.aditopaz.goodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by aditopaz on 28/06/2017.
 */

public class Gallery extends AppCompatActivity implements ImageAdapter.EntryClickListener {

    RecyclerView recyclerView;
    ArrayList<ImageEntry> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        generateData();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageAdapter(this, imageList));
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
    @Override
    public void entryClicked(View view, int position) {

        ImageEntry entry = imageList.get(position);

        Bundle infoBund = getIntent().getExtras();
        infoBund.putString("IMAGENAME", entry.getName());

        Intent intent = new Intent(this, volReg2.class);
        intent.putExtras(infoBund);
        startActivity(intent);
    }
}
