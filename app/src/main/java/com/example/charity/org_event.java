package com.example.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class org_event extends AppCompatActivity {
    ImageView backButton;
    RecyclerView Eventrecview;
    ArrayList<eventModel> eventdatalist;
    FirebaseFirestore db;
    org_eventAdapter eventadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_event);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(org_event.this,posts.class );
                startActivity(intent);
                finish();
            }
        });
        Eventrecview=(RecyclerView)findViewById(R.id.recview_org_event);
        Eventrecview.setLayoutManager(new LinearLayoutManager(this));
        eventdatalist=new ArrayList<>();
        eventadapter=new org_eventAdapter(eventdatalist);
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
}