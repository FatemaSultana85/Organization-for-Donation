package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ResourceBundle;

public class AboutUs extends AppCompatActivity {
    int i;
    TextView x;
    String userCategory;
    ImageButton backButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        backButton = findViewById(R.id.backButton);
        x = findViewById(R.id.x);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                x.setText(documentSnapshot.getString("category"));
                userCategory = x.getText().toString();
                setUser();
            }
        });
        sendUserToDesignatedAboutUs();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i==0)
                {
                    Intent intent = new Intent(AboutUs.this,HomePage.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==1)
                {
                    Intent intent = new Intent(AboutUs.this,HomePageVolunteer.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==2)
                {
                    Intent intent = new Intent(AboutUs.this,HomePageOrganization.class );
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
    private void sendUserToDesignatedAboutUs() {

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
