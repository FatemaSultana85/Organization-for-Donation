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

public class wishList extends AppCompatActivity {
    Button h1, h2, h3, h4;
    RecyclerView recview_wishlist;
    ArrayList<wishlistModel> datalist;
    FirebaseFirestore db;
    wishlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_list);

        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);

        sendUserToDesignatedwishList();

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishList.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishList.this,events.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishList.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishList.this,HomePage.class );
                startActivity(intent);
                finish();
            }
        });
        recview_wishlist=(RecyclerView)findViewById(R.id.recview_wishlist);
        recview_wishlist.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new wishlistAdapter(datalist);
        recview_wishlist.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("Wishlist").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            wishlistModel obj=d.toObject(wishlistModel.class);
                            datalist.add(obj);


                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    private void sendUserToDesignatedwishList() {

    }
}
