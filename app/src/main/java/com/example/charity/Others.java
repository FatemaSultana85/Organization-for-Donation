package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Others extends AppCompatActivity {
    ImageView backButton;

    RecyclerView  recview_other;
    ArrayList<foodModel> datalist;
    FirebaseFirestore db;
    otherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        backButton = findViewById(R.id.backButton);
        sendUserToDesignatedOthers();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Others.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        recview_other=(RecyclerView)findViewById(R.id.recview_other);
        recview_other.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new otherAdapter(datalist);
        recview_other.setAdapter(adapter);
        //foodOrganizationName foodCategory foodDate foodTime
        //foodTitle foodLocation foodLastDate foodDescription
        // foodDetails
        db=FirebaseFirestore.getInstance();

        //reference = FirebaseDatabase.getInstance().getReference().child("tasks");
        // Query firstQuery = reference.orderByChild("owner_one").equalsTo("x@gmail.com");
        db.collection("OrganizationCreatePost").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            foodModel obj=d.toObject(foodModel.class);
                            datalist.add(obj);


                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
    private void sendUserToDesignatedOthers() {

    }
}