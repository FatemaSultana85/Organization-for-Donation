package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class bookCategory extends AppCompatActivity {
    ImageView backButton;
    RecyclerView recview_book;
    ArrayList<foodModel> datalist;
    FirebaseFirestore db;
    bookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);
        backButton = findViewById(R.id.backButton);
        sendUserToDesignatedbookCategory();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bookCategory.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        recview_book=(RecyclerView)findViewById(R.id.recview_book);
        recview_book.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new bookAdapter(datalist);
        recview_book.setAdapter(adapter);
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
    private void sendUserToDesignatedbookCategory() {

    }
    @Override
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
                adapter.getFilter().filter(newText);
                System.out.println("On create end");
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}