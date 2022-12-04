package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ResourceBundle;

public class DonationCategory extends AppCompatActivity {
    Button h1, h2, h3, h4;
    ImageView food, cloth, medicine, book, money, other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_category);

        food = findViewById(R.id.food);
        cloth = findViewById(R.id.cloth);
        medicine = findViewById(R.id.medicine);
        book = findViewById(R.id.book);
        //money = findViewById(R.id.money);
        other = findViewById(R.id.other);
        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);


        sendUserToDesignatedDonationCategory();
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,foodCategory.class );
                startActivity(intent);
                finish();
            }
        });
        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,clothCategory.class );
                startActivity(intent);
                finish();
            }
        });
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,medicineCategory.class );
                startActivity(intent);
                finish();
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,bookCategory.class );
                startActivity(intent);
                finish();
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,Others.class );
                startActivity(intent);
                finish();
            }
        });

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,events.class );
                startActivity(intent);
                finish();
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationCategory.this,HomePage.class );
                startActivity(intent);
                finish();
            }
        });

    }

    private void sendUserToDesignatedDonationCategory() {

    }
}
