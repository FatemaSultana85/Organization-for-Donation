package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class myEvents extends AppCompatActivity {
    Button h1, h2, h3;
    RecyclerView Eventrecview;
    ArrayList<eventModel> eventdatalist;
    FirebaseFirestore db;
    myEventAadapter eventadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);

        sendUserToDesignatedmyEvents();

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myEvents.this,myEvents.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myEvents.this,createEvent.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myEvents.this,HomePageVolunteer.class );
                startActivity(intent);
                finish();
            }
        });


        Eventrecview=(RecyclerView)findViewById(R.id.recview_myEvent);
        Eventrecview.setLayoutManager(new LinearLayoutManager(this));
        eventdatalist=new ArrayList<>();
        eventadapter=new myEventAadapter(eventdatalist);
        Eventrecview.setAdapter(eventadapter);

        db=FirebaseFirestore.getInstance();

        //reference = FirebaseDatabase.getInstance().getReference().child("tasks");
        // Query firstQuery = reference.orderByChild("owner_one").equalsTo("x@gmail.com");
        db.collection("events").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> Eventlist=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:Eventlist)
                        {
                            eventModel obj=d.toObject(eventModel.class);
                            eventdatalist.add(obj);
                        }
                        eventadapter.notifyDataSetChanged();
                    }
                });

    }

    private void sendUserToDesignatedmyEvents() {

    }
}