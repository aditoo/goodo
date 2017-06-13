package com.example.aditopaz.goodo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements VolAdapter.EntryClickListener{

    RecyclerView recyclerView;
    ArrayList<VolEntry> volList = new ArrayList<VolEntry>();
    ProgressDialog  progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mainActivity", "created");

        progressDialog = new ProgressDialog(this);


        getRequest();

        setContentView(R.layout.activity_main);
        ImageButton fab = (ImageButton) findViewById(R.id.megaphone);


        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {   Intent i = new Intent(getApplicationContext(),volReg1.class);
                startActivity(i);

            }
        });

        //generateData();

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
        bundle.putString("DATE", entry.getDate());
        bundle.putString("TIME", entry.getTime());
        bundle.putString("LOCATION", entry.getLoctaion());
        bundle.putString("DESCRIPTION", entry.getDescription());
        bundle.putString("ID", entry.getID());
        bundle.putString("CREATOR", entry.getCreator());


        Intent intent = new Intent(this, VolInformation.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
    /*
    public void generateData(){
        volList = new ArrayList<VolEntry>();
        volList.add(new VolEntry(0, "גן ילדים של פליטים",100, 50, 20,"smilingboy","11", "26/04/17    12:30 - 08:30", "תל אביב","שלום רב, בכניסה לאשדוד ליד הצומת יש בית יתומים לעדה האתיופית. ראיתי כתבה בעיתון שהם מחפשים אנשים שיבואו חצי שעה ביום לשחק עם הילדים. מי רוצה?"));
        volList.add(new VolEntry(1, "חוף הרצליה", 100, 70, 50,"beach_cover","9", "28/04/17    11:30 - 09:30", "הרצליה","היי ש\"שינים, אתמול עברתי בחוף הרצליה ונחרדתי לגלות שקיות זבל, בקבוקים, סיגריות ועוד המון זוהמה. לפני שהקיץ מגיע תעזרו לי שיהיה לנו נעים לבלות."));
        volList.add(new VolEntry(2, "חלוקת מזון", 100,100, 80,"packing_cover","14", "29/04/17    17:30 - 14:00", "חדרה","היי חבר'ה, מי שמכיר אותי, אני גוזמן, החלטתי לארגן איסוף תרומות לנזקקים לקראת החג ליד הסופר של אהרל'ה. צריך מתנדבים גם לחלוקה של המזון."));
        volList.add(new VolEntry(3, "איציק החקלאי", 100, 20, 40,"forest_cover","17","29/04/17    08:30 - 06:00", "מושב גבעת חן","היוש, בעקבות החורף איציק החלקאי צריך עזרה בהצלת הגידולים, ליקוט הגידולים והעברתם לחממות על בטון. צריכה אנשים חזקים וטובי לב!"));
    }
    */

    protected RequestQueue getRequest() {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://arcane-earth-90335.herokuapp.com/volunteers";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);
                        progressDialog.hide();
                        getResponseParser(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        startActivity(new Intent(getApplicationContext(),ErrorPage.class));
                        Log.d("error", "in");
                        Log.d("MainActivityFragment", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        progressDialog.setMessage("בטעינה...");
        progressDialog.show();
        return queue;
    }

    protected void getResponseParser(JSONObject response) {

        try {
            JSONArray jsonarray = response.getJSONArray("vol");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String title = jsonobject.getString("title");
                int volNeeded = jsonobject.getInt("maxNumber");
                int volNum = jsonobject.getInt("currentNum");
                int volminNum = jsonobject.getInt("minNumber");
                String imageName = jsonobject.getString("imgName");
                String date = jsonobject.getString("date");
                String location = jsonobject.getString("address");
                String description = jsonobject.getString("description");
                String ID = jsonobject.getString("_id");
                String creator = jsonobject.getString("creator");

                String[] dateTime = date.split("T");
                Log.d("DateTime:", dateTime[0]);
                Log.d("DateTime:", dateTime[1]);

                Long currentTime = System.currentTimeMillis()/1000;

                SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-M-d hh:mm"));
                StringBuilder test = new StringBuilder();
                test.append(dateTime[0]).append(" ").append(dateTime[1]);
                Date startT = sdf.parse(test.toString());
                long startTime = startT.getTime()/1000L;

                String timeleft = Long.toString((startTime - currentTime) / 3600);

                volList.add(new VolEntry(ID, title ,volNeeded, volNum, volminNum,imageName,timeleft, dateTime[0], dateTime[1], location, description, creator));



                /*
                Log.d("Parser", "Parsed title - " + title);
                Log.d("Parser", "Parsed volNeeded - " + volNeeded);
                Log.d("Parser", "Parsed date - " + date);
                Log.d("Parser", "Parsed imageName - " + imageName);
                Log.d("Parser", "Parsed location - " + location);
                */

            }
            recyclerView.getAdapter().notifyDataSetChanged();
            setRevView();
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
