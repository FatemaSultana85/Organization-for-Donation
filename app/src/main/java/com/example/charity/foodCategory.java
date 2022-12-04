package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
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

public class foodCategory extends AppCompatActivity {
    ImageView backButton;
    RecyclerView recview;
    ArrayList<foodModel> datalist;
    FirebaseFirestore db;
    foodAdapter adapter;
    private TextView category;
    Button details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        backButton = findViewById(R.id.backButton);
        sendUserToDesignatedfoodCategory();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(foodCategory.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        //showing value in another page;
        category=findViewById(R.id.foodCategory);
        



        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new foodAdapter(datalist);
        recview.setAdapter(adapter);
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


        /*details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(foodCategory.this,donationDetails.class);
                startActivity(intent);
            }
        });*/



    }
    private void sendUserToDesignatedfoodCategory() {

    }
}
