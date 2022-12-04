package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ResourceBundle;

public class HomePage extends AppCompatActivity {
    Button editProfile, notification, aboutUs, logout, h1, h2, h3, h4;
    TextView text,text1, text2, text3;
    String userName, userEmail, userContact, userLocation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        editProfile = findViewById(R.id.editProfile);
        notification = findViewById(R.id.notification);
        aboutUs = findViewById(R.id.aboutUs);
        logout = findViewById(R.id.logout);
        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);
        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text.setText(documentSnapshot.getString("name"));
                text1.setText(documentSnapshot.getString("email"));
                text2.setText(documentSnapshot.getString("location"));
                text3.setText(documentSnapshot.getString("contactNo"));
                userName = text.getText().toString();
                userEmail = text1.getText().toString();
                userLocation = text2.getText().toString();
                userContact = text3.getText().toString();
            }
        });

        sendUserToDesignatedHomepage();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,editAccount.class );
                startActivity(intent);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,Notifications.class );
                startActivity(intent);
                finish();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,AboutUs.class );
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,Login.class );
                startActivity(intent);
                finish();
            }
        });
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,events.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,HomePage.class );
                startActivity(intent);
                finish();
            }
        });

    }

    private void sendUserToDesignatedHomepage() {
      /*  if(userCategory.equals("Donor"))
        {
            Intent intent = new Intent(HomePage.this,DonationCategory.class );
            startActivity(intent);
            finish();

        }
        else if(userCategory.equals("Volunteer"))
        {
            Intent intent = new Intent(HomePage.this,volunteer_bottom_navigation.class );
            startActivity(intent);
            finish();

        }
        else if(userCategory.equals("Organization"))
        {
            Intent intent = new Intent(HomePage.this,organizationBottomNavigation.class );
            startActivity(intent);
            finish();

        } */

    }
}