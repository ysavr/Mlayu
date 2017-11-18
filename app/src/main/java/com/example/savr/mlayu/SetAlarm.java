package com.example.savr.mlayu;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SetAlarm extends AppCompatActivity {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView textViewhour,textViewdate;
    Button btn_get_datetime;

    Calendar mcurrentTime;
    Calendar calSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Schedule");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_get_datetime=(Button) findViewById(R.id.btn_get_datetime);
        textViewhour = (TextView) findViewById(R.id.texthour);
        textViewdate = (TextView) findViewById(R.id.textdate);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        textViewdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(SetAlarm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
/*
//        *//*txt_jam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//
//                mTimePicker = new TimePickerDialog(SetAlarm.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        txt_jam.setText(selectedHour + ":" + selectedMinute);
//                        textViewhour.setText(selectedHour + ":" + selectedMinute);
//                        Calendar calNow = Calendar.getInstance();
//                        calSet = (Calendar) calNow.clone();
//
//                        calSet.set(Calendar.HOUR_OF_DAY, selectedHour);
//                        calSet.set(Calendar.MINUTE, selectedMinute);
//                        calSet.set(Calendar.SECOND, 0);
//                        calSet.set(Calendar.MILLISECOND, 0);
//
//                        if (calSet.compareTo(calNow) <= 0) {
//
//                            calSet.add(Calendar.DATE, 1);
//                        }
//
////                        Calendar cal = Calendar.getInstance();
////                        cal.add(Calendar.SECOND, 10);
//
////                        Intent i = new Intent(SetAlarm.this,Alarm.class);
////                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
////                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
////                        alarmManager.set(AlarmManager.RTC_WAKEUP,mcurrentTime.getTimeInMillis(),pendingIntent);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//            }
//        });*/

        textViewhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(SetAlarm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textViewhour.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        Calendar calNow = Calendar.getInstance();
                        calSet = (Calendar) calNow.clone();

                        calSet.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calSet.set(Calendar.MINUTE, selectedMinute);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);

                        if (calSet.compareTo(calNow) <= 0) {
                            calSet.add(Calendar.DATE, 1);
                        }
//                        Calendar cal = Calendar.getInstance();
//                        cal.add(Calendar.SECOND, 10);

//                        Intent i = new Intent(SetAlarm.this,Alarm.class);
//                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
//                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                        alarmManager.set(AlarmManager.RTC_WAKEUP,mcurrentTime.getTimeInMillis(),pendingIntent);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btn_get_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SetAlarm.this,"Alarm Success Set ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SetAlarm.this,Alarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP,myCalendar.getTimeInMillis(),pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calSet.getTimeInMillis(),pendingIntent);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        textViewdate.setText(sdf.format(myCalendar.getTime()));
    }
}
