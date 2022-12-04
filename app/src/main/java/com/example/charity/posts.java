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

public class posts extends AppCompatActivity {
    Button h1, h2, h3, h4;

    //showing post
    ImageView backButton;
    RecyclerView recview_ourPost;
    ArrayList<foodModel> datalist;
    FirebaseFirestore db;
    postsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);
        sendUserToDesignatedposts();

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(posts.this,posts.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(posts.this,org_event.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(posts.this,createPost.class );
                startActivity(intent);
                finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(posts.this,HomePageOrganization.class );
                startActivity(intent);
                finish();
            }
        });





        recview_ourPost=(RecyclerView)findViewById(R.id.recview_ourPost);
        recview_ourPost.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new postsAdapter(datalist);
        recview_ourPost.setAdapter(adapter);
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

    private void sendUserToDesignatedposts() {

    }
}