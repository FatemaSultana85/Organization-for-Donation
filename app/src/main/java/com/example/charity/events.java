package com.example.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class events extends AppCompatActivity {
    Button h1, h2, h3, h4;
    RecyclerView Eventrecview;
    ArrayList<eventModel> eventdatalist;
    FirebaseFirestore db;
    eventAdapter eventadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);

        sendUserToDesignatedevents();

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(events.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(events.this,events.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(events.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(events.this,HomePage.class );
                startActivity(intent);
                finish();
            }
        });

        Eventrecview=(RecyclerView)findViewById(R.id.recviewEvent);
        Eventrecview.setLayoutManager(new LinearLayoutManager(this));
        eventdatalist=new ArrayList<>();
        eventadapter=new eventAdapter(eventdatalist);
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

    private void sendUserToDesignatedevents() {
    }

    /*private void intSearchWidgets() {
        SearchView searchView = (SearchView)findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<eventModel> filteredEvents = new ArrayList<eventModel>();
                for(eventModel event:eventdatalist)
                {
                    if(event.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        filteredEvents.add(event);
                    }
                }
                eventadapter=new eventAdapter(filteredEvents);
                Eventrecview.setAdapter(eventadapter);
                eventadapter.notifyDataSetChanged();
                return false;
            }
        });
\
    } */

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem item = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("On create start");
                eventadapter.getFilter().filter(newText);
                System.out.println("On create end");
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }*/
}

