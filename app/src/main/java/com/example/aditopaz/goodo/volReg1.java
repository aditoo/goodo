package com.example.aditopaz.goodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



@SuppressWarnings("deprecation")
public class volReg1 extends AppCompatActivity {
    private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;
    static final int TIME_DIALOG_ID = 0;

    private TextView mDateDisplay;
    private Button mPickDate;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 1;
    TextView name;
    TextView volNum;
    TextView minVolNum;
    TextView duration;
    TextView address;
    StringBuilder time;
    StringBuilder date;
    StringBuilder dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_reg1);

        Button btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                Intent i = new Intent(getApplicationContext(),volReg2.class);
                Log.d("ClickListener", "I listen NOW");

                EditText volName = (EditText) findViewById(R.id.input_vol_name);
                String sVolName = volName.getText().toString();
                EditText address = (EditText) findViewById(R.id.input_address);
                String sAddress = address.getText().toString();
                TextView eVolNum = (TextView) findViewById(R.id.input_vol_num);
                String sVolNum = eVolNum.getText().toString();
                TextView tMinVolNum = (TextView) findViewById(R.id.input_min_vol_num);
                String sMinVolNum = tMinVolNum.getText().toString();
                TextView tDuration= (TextView) findViewById(R.id.input_duration_vol);
                String sDuration = tDuration.getText().toString();

                //validation for null cells
                if (sVolName.matches("") || sAddress.matches("") || sVolNum.equals("0") || sMinVolNum.equals("0") || sDuration.equals("0")) {
                    Toast.makeText(volReg1.this, "אנא מלא שדות ריקים", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(time != null && date != null)
                    dateTime = new StringBuilder().append(date).append(time);

                Bundle bundle = new Bundle();
                if(name.getText() != null)
                    bundle.putString("VOL_NAME", name.getText().toString());
                if(volNum != null)
                    bundle.putString("VOL_NUM", volNum.getText().toString());
                if(minVolNum != null)
                    bundle.putString("MIN_VOL_NUM",minVolNum.getText().toString());
                if(address != null)
                    bundle.putString("ADDRESS",address.getText().toString());
                if(dateTime != null)
                    bundle.putString("DATE", dateTime.toString());
                if(duration != null)
                    bundle.putString("DURATION",duration.getText().toString());
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        name = (TextView) findViewById(R.id.input_vol_name);
        address = (TextView) findViewById(R.id.input_address);

        ImageButton vol_plus = (ImageButton) findViewById(R.id.vol_plus);
        vol_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                TextView volNum = (TextView) findViewById(R.id.input_vol_num);
                volNum.setText(Integer.toString(Integer.parseInt(volNum.getText().toString())+1));

            }
        });

        ImageButton vol_minus = (ImageButton) findViewById(R.id.vol_minus);
        vol_minus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                volNum = (TextView) findViewById(R.id.input_vol_num);
                int num = Integer.parseInt(volNum.getText().toString());
                num -= num == 0 ? 0 : 1;
                volNum.setText(Integer.toString(num));
            }
        });


        ImageButton min_plus = (ImageButton) findViewById(R.id.min_plus);
        min_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                minVolNum = (TextView) findViewById(R.id.input_min_vol_num);
                minVolNum.setText(Integer.toString(Integer.parseInt(minVolNum.getText().toString())+1));

            }
        });

        ImageButton min_minus = (ImageButton) findViewById(R.id.min_minus);
        min_minus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                minVolNum = (TextView) findViewById(R.id.input_min_vol_num);
                int num = Integer.parseInt(minVolNum.getText().toString());
                num -= num == 0 ? 0 : 1;
                minVolNum.setText(Integer.toString(num));
            }
        });

        ImageButton dur_plus = (ImageButton) findViewById(R.id.dur_plus);
        dur_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                duration = (TextView) findViewById(R.id.input_duration_vol);
                duration.setText(Integer.toString(Integer.parseInt(duration.getText().toString())+1));

            }
        });

        ImageButton dur_minus = (ImageButton) findViewById(R.id.dur_minus);
        dur_minus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0)
            {
                duration = (TextView) findViewById(R.id.input_duration_vol);
                int num = Integer.parseInt(duration.getText().toString());
                num -= num == 0 ? 0 : 1;
                duration.setText(Integer.toString(num));
            }
        });

        mTimeDisplay = (TextView) findViewById(R.id.time_display);
        mPickTime = (Button) findViewById(R.id.pick_time);

        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        // get the current time
        final Calendar ct = Calendar.getInstance();
        mHour = ct.get(Calendar.HOUR_OF_DAY);
        mMinute = ct.get(Calendar.MINUTE);

        // display the current time
        updateDisplay(0);

        mDateDisplay = (TextView) findViewById(R.id.date_display);
        mPickDate = (Button) findViewById(R.id.pick_date);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date (this method is below)
        updateDisplay(1);
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    // updates the time we display in the TextView
    private void updateDisplay(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                mTimeDisplay.setText(
                        time = new StringBuilder()
                                .append(pad(mHour)).append(":")
                                .append(pad(mMinute)));
                break;


            case DATE_DIALOG_ID:
                mDateDisplay.setText(
                        date = new StringBuilder()
                                // Month is 0 based so add 1
                                .append(mYear).append("-")
                                .append(mMonth + 1).append("-")
                                .append(mDay).append("T"));
                break;
        }
    }

    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay(0);
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay(1);
                }
            };



}
