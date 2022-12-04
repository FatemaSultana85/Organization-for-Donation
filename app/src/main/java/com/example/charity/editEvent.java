package com.example.charity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class editEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        getIncomingIntent();
    }
    private void getIncomingIntent(){

        if(getIntent().hasExtra("eventLocation1") && getIntent().hasExtra("eventCategory1")&& getIntent().hasExtra("eventDescription1")&& getIntent().hasExtra("endDate1")&& getIntent().hasExtra("endTime1")&& getIntent().hasExtra("startDate1")&& getIntent().hasExtra("startTime1")&& getIntent().hasExtra("eventTitle1"))
        {
            String loc=getIntent().getStringExtra("eventLocation1");
            String cat=getIntent().getStringExtra("eventCategory1");
            String des=getIntent().getStringExtra("eventDescription1");
            String eD=getIntent().getStringExtra("endDate1");
            String eT=getIntent().getStringExtra("endTime1");
            String sD=getIntent().getStringExtra("startDate1");
            String sT=getIntent().getStringExtra("startTime1");
            String title=getIntent().getStringExtra("eventTitle1");
            setValue(loc,cat,des,eD,eT,sD,sT,title);
        }
    }
    private void setValue(String loc,String cat,String des,String eD,String eT,String sD,String sT,String title){
        EditText showLocation=findViewById(R.id.editeventLocation);
        showLocation.setText(loc);
        EditText showEventCategory =findViewById(R.id.edit_cat);
        showEventCategory.setText(cat);
        EditText showEventDescription=findViewById(R.id.editEventDescription);
        showEventDescription.setText(des);
        EditText showEndDate=findViewById(R.id.editEndDate);
        showEndDate.setText(eD);
        EditText showEndTime=findViewById(R.id.editEndTime);
        showEndTime.setText(eT);

        EditText showStartDate=findViewById(R.id.editStartDate);
        showStartDate.setText(sD);
        EditText showStartTime=findViewById(R.id.editStartTime);
        showStartTime.setText(sT);
        EditText showEventTitle=findViewById(R.id.editEventTitle);
        showEventTitle.setText(title);


    }
}