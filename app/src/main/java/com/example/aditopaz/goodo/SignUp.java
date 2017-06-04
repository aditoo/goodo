package com.example.aditopaz.goodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aditopaz on 02/06/2017.
 */

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final EditText phoneNumber = (EditText) findViewById(R.id.user_phone_number);
        final EditText userName = (EditText) findViewById(R.id.user_name);
        Button button = (Button) findViewById(R.id.sign_up);




        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {   Intent i = new Intent(getApplicationContext(),MainActivity.class);

                Log.d("PhoneNumebr", phoneNumber.getText().toString());
                StringBuilder url = new StringBuilder();
                url.append("https://arcane-earth-90335.herokuapp.com/users?userName=");
                url.append(userName.getText().toString());
                url.append("&phoneNumber=");
                url.append(phoneNumber.getText().toString());

                createUser(url.toString());
                SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("phone_number", phoneNumber.getText().toString());
                editor.putString("username", userName.getText().toString());
                editor.commit();

                startActivity(i);


            }
        });
    }

    protected RequestQueue createUser(String url) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Create User", "Response - " + response);
                        try {
                            Log.d("Create User", "ID - " + response.getString("id"));
                            SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_id", response.getString("id"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error", "in");
                        Log.d("MainActivityFragment", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }
}
