package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Timer;
import java.util.TimerTask;

public class IntermediateHomepage extends AppCompatActivity {
    TextView text1;
    int i;
    String userCategory;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate_homepage);
        text1 = findViewById(R.id.text1);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text1.setText(documentSnapshot.getString("category"));
                userCategory = text1.getText().toString();
                //userCategory = documentSnapshot.getString("category");
                setUser();
                sendUserToDesignatedHomepage();
            }
        });
    }

    private void sendUserToDesignatedHomepage() {
        if(i==0)
        {
            Intent intent = new Intent(IntermediateHomepage.this,DonationCategory.class );
            startActivity(intent);
            finish();

        }
        else if(i==1)
        {
            Intent intent = new Intent(IntermediateHomepage.this,myEvents.class );
            startActivity(intent);
            finish();

        }
        else if(i==2)
        {
            Intent intent = new Intent(IntermediateHomepage.this,posts.class );
            startActivity(intent);
            finish();

        }

    }

    private void setUser() {
        if(userCategory.equals("Donor"))
        {
            i=0;

        }
        if(userCategory.equals("Volunteer"))
        {
            i=1;

        }
        if(userCategory.equals("Organization"))
        {
            i=2;

        }

    }
}