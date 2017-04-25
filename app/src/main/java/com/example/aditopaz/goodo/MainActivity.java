package com.example.aditopaz.goodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolAdapter.EntryClickListener{

    RecyclerView recyclerView;
    ArrayList<VolEntry> volList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton fab = (ImageButton) findViewById(R.id.megaphone);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {   Intent i = new Intent(getApplicationContext(),volReg1.class);
                startActivity(i);

            }
        });

        generateData();
        setRevView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRevView(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VolAdapter(this, volList));
    }

    @Override
    public void entryClicked(View view, int position) {

        VolEntry entry = volList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("NAME",entry.getName());
        bundle.putString("TIMELEFT", entry.getTimeLeft());
        bundle.putInt("VOLNUM", entry.getVolNum());
        bundle.putString("IMAGENAME", entry.getImageName());
        bundle.putInt("VOLNEEDED", entry.getVolNeeded());
        bundle.putString("STARTTIME", entry.getStartTime());
        bundle.putString("LOCATION", entry.getLoctaion());
        bundle.putString("DESCRIPTION", entry.getDescription());


        Intent intent = new Intent(this, VolInformation.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    public void generateData(){
        volList = new ArrayList<VolEntry>();
        volList.add(new VolEntry(0, "גן ילדים של פליטים",100, 50, 20,"smilingboy","11", "26/04/17    12:30 - 08:30", "תל אביב","הקראת סיפור לילדי הגן."));
        volList.add(new VolEntry(1, "חוף הרצליה", 100, 70, 50,"beach_cover","9", "28/04/17    11:30 - 09:30", "הרצליה","עזרה בפינוי הפסולת בחוף."));
        volList.add(new VolEntry(2, "חלוקת מזון", 100,100, 80,"packing_cover","14", "29/04/17    17:30 - 14:00", "חדרה","עזרה בחלוקת מזון לנזקקים באמצעות רכבים."));
        volList.add(new VolEntry(3, "איציק החקלאי", 100, 20, 40,"forest_cover","17","29/04/17    08:30 - 06:00", "מושב גבעת חן","עזרה בקטיף ואריזה של תותים."));
    }
}
