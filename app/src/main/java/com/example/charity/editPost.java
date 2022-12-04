package com.example.charity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class editPost extends AppCompatActivity {
    ImageView backButton;
    TextView editLastDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        editLastDate=findViewById(R.id.editLastDate);
        backButton=findViewById(R.id.backButton);
        final Calendar calendar =Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editPost.this,posts.class );
                startActivity(intent);
                finish();
            }
        });

        editLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        editPost.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date =dayOfMonth+"-"+month+"-"+year;
                        editLastDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        getIncomingIntent();

    }


    private void getIncomingIntent(){
        if(getIntent().hasExtra("location1") && getIntent().hasExtra("des1")&& getIntent().hasExtra("lastDate1")&& getIntent().hasExtra("name1")&& getIntent().hasExtra("title1")&& getIntent().hasExtra("cat1"))
        {
            String location=getIntent().getStringExtra("location1");
            String des=getIntent().getStringExtra("des1");
            String lastDate=getIntent().getStringExtra("lastDate1");
            String name=getIntent().getStringExtra("name1");
            String title=getIntent().getStringExtra("title1");
            String cat=getIntent().getStringExtra("cat1");
            setValue(location,des,lastDate,name,title,cat);
        }
    }

    private void setValue(String location,String des,String lastDate,String name,String title,String cat){
        EditText addDonationTo=findViewById(R.id.editorganizationName);
        addDonationTo.setText(name);
        EditText addDonationTitle =findViewById(R.id.editTitle);
        addDonationTitle.setText(title);
        EditText addDonationDescription=findViewById(R.id.editDescription);
        addDonationDescription.setText(des);
        EditText addLastDateDetails=findViewById(R.id.editLastDate);
        addLastDateDetails.setText(lastDate);
        EditText addContactPageLocation=findViewById(R.id.editLocation);
        addContactPageLocation.setText(location);
        EditText category=findViewById(R.id.editchoseCategory);
        category.setText(cat);


    }
}