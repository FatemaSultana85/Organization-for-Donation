package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class createEvent extends AppCompatActivity {
    Button next;
    ImageView backButton;

    //for calenter and time
    //date
    TextView startEventDate;
    TextView endEventDate;
    //for time
    TextView startEventTime,endEventTime,location;
    int t1Hour,t1Minute,t2Hour,t2Minute;

    int my_req_code=101;

    String save_lat_lon,show_address;



    FirebaseFirestore db;
    //for add post
    private ProgressDialog loadingBar;
    private Button share;
    private Spinner category;
    private EditText title, details,sDate,eDate;
    private EditText sTime,eTime;
    private String userCategory;
    private String postTitle, description, userLocation, eventsDate,eventeDate,eventeTime,eventsTime;
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;
    private DatabaseReference UserRef, PostRef;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        //next = findViewById(R.id.next);
        backButton = findViewById(R.id.backButton);
        sendUserToDesignatedcreateButton();

        //For date
        startEventDate=findViewById(R.id.addStartDate);
        endEventDate=findViewById(R.id.addEndDate);
        //for time
        startEventTime=findViewById(R.id.addStartTime);
        endEventTime=findViewById(R.id.addEndTime);

        //for chosing start event date
        final Calendar calendar =Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);
        startEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        createEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date =dayOfMonth+"-"+month+"-"+year;
                        startEventDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //for chosing end event date
        final Calendar calendar2 =Calendar.getInstance();
        final int year2=calendar2.get(calendar.YEAR);
        final int month2=calendar2.get(calendar.MONTH);
        final int day2=calendar2.get(calendar.DAY_OF_MONTH);
        endEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        createEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2) {
                        month2=month2+1;
                        String date =dayOfMonth2+"-"+month2+"-"+year2;
                        endEventDate.setText(date);
                    }
                },year2,month2,day2);
                datePickerDialog.show();
            }
        });

        //    //for chosing start event time
        startEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        createEvent.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour=hourOfDay;
                                t1Minute=minute;
                                String time=t1Hour+":"+t1Minute;
                                SimpleDateFormat f24Hours= new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date=f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    startEventTime.setText(f12Hours.format(date));
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }
        });
        //for chosing end event time
        endEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        createEvent.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Hour=hourOfDay;
                                t2Minute=minute;
                                String time2=t2Hour+":"+t2Minute;
                                SimpleDateFormat f24Hours2= new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date2=f24Hours2.parse(time2);
                                    SimpleDateFormat f12Hours2 = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    endEventTime.setText(f12Hours2.format(date2));
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2Hour,t2Minute);
                timePickerDialog.show();
            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createEvent.this,myEvents.class );
                startActivity(intent);
                finish();
            }
        });

        category = (Spinner) findViewById(R.id.choseEventCategory);
        title = (EditText) findViewById(R.id.addEventTitle);
        details = (EditText) findViewById(R.id.addEventDescription);
        location = (TextView) findViewById(R.id.addeventLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(createEvent.this, SearchLocation.class);
                startActivityForResult(i,1);
            }
        });
        sDate = (EditText) findViewById(R.id.addStartDate);
        sTime = (EditText) findViewById(R.id.addStartTime);
        eDate = (EditText) findViewById(R.id.addEndDate);
        eTime = (EditText) findViewById(R.id.addEndTime);
        share = (Button) findViewById(R.id.createEventButton);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //choseEventCategory addEventTitle addEventDescription addStartDate addStartTime addEndDate   addEndTime
        //addeventLocation  createEventButton

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();


            }

        });


    }
    private void sendUserToDesignatedcreateButton() {

    }
    private void ValidatePostInfo() {

        userCategory = category.getSelectedItem().toString();
        description = details.getText().toString();
        userLocation = location.getText().toString();
        postTitle = title.getText().toString();

        eventsDate = sDate.getText().toString();
        eventeDate= eDate.getText().toString();
        eventeTime = sTime.getText().toString();
        eventsTime = eTime.getText().toString();

        if (userCategory.equals("Choose Category")) {
            ((TextView) category.getSelectedView()).setError("Please enter a category");
            category.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userLocation)) {
            location.setError("Please enter the location...");
            location.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(postTitle)) {
            title.setError("Please enter the Title...");
            title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            details.setError("Please enter the details");
            details.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eventsDate)) {
            sDate.setError("Please enter the Start date");
            sDate.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eventeDate)) {
            eDate.setError("Please enter the End Date");
            eDate.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eventsTime)) {
            sTime.setError("Please enter the start Time");
            sTime.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eventeTime)) {
            eTime.setError("Please enter the end Time");
            eTime.requestFocus();
            return;
        }
        else {
            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());

            Calendar callForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            saveCurrentTime = currentTime.format(callForTime.getTime());

            postRandomName = saveCurrentDate + saveCurrentTime;
            //StoringImageToFirebaseStorage();
            userID = mAuth.getCurrentUser().getUid();
            //String userFullName=mAuth.getCurrentUser().getDisplayName();
           // String userFullName = dataSnapshot.child("name").getValue().toString();
            final int min = 1;
            final int max = 1000;
            final int random = new Random().nextInt((max - min) + 1) + min;
            String EventpostId = userID+random;
            //String fullName=mAuth.getCurrentUser().getDisplayName();

            DocumentReference documentReference = fStore.collection("events").document(EventpostId);
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("userID", userID);
            postMap.put("date", saveCurrentDate);
            postMap.put("time", saveCurrentTime);
            postMap.put("category", userCategory);
            postMap.put("title", postTitle);
            postMap.put("description", description);
            postMap.put("Location", userLocation);
            postMap.put("startDate", eventsDate);
            postMap.put("startTime", eventsTime);
            postMap.put("endDate", eventeDate);
            postMap.put("endTime", eventeTime);
            postMap.put("EventpostId", EventpostId);
            //postMap.put("fullName", fullName);




            documentReference.set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(createEvent.this, "Post successfully created", Toast.LENGTH_LONG).show();
                }
            });
            Intent intent = new Intent(createEvent.this, myEvents.class );
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            save_lat_lon = data.getStringExtra("KEY");
            show_address = data.getStringExtra("KEY2");
            location.setText(show_address);
        }
    }



}
