package com.example.aditopaz.goodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adi on 24/05/2017.
 */

public class volReg2 extends AppCompatActivity {

    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_reg2);

        Button btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {   Intent i = new Intent(getApplicationContext(),volReg3.class);

                EditText description = (EditText) findViewById(R.id.input_description);
                String sDescription = description.getText().toString();

                if (sDescription.matches("")) {
                    Toast.makeText(volReg2.this, "אנא מלא שדות ריקים", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle infoBund = getIntent().getExtras();
                Log.d("Bundle", infoBund.getString("VOL_NAME").toString());
                infoBund.putString("DESCRIPTION",description.getText().toString());
                i.putExtras(infoBund);
                startActivity(i);

            }
        });

        description = (TextView) findViewById(R.id.input_description);


    }


}