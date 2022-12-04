package com.example.charity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class wishlistDetails extends AppCompatActivity {
    ImageView backButton;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_details);
        getIncomingIntent();

        backButton = findViewById(R.id.backButton);
        done = findViewById(R.id.done);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishlistDetails.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wishlistDetails.this,wishList.class );
                startActivity(intent);
                finish();
            }
        });
    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("location1") && getIntent().hasExtra("cat1")&& getIntent().hasExtra("des1")&& getIntent().hasExtra("lastDate1")&& getIntent().hasExtra("name1")&& getIntent().hasExtra("title1"))
        {
            String location=getIntent().getStringExtra("location1");
            String cat=getIntent().getStringExtra("cat1");
            String des=getIntent().getStringExtra("des1");
            String lastDate=getIntent().getStringExtra("lastDate1");
            String name=getIntent().getStringExtra("name1");
            String title=getIntent().getStringExtra("title1");
            setValue(location,cat,des,lastDate,name,title);
        }
    }

    private void setValue(String location,String cat,String des,String lastDate,String name,String title){
        TextView addDonationTo=findViewById(R.id.wishlist_DonationTo);
        addDonationTo.setText(name);
        TextView addDonationTitle =findViewById(R.id.wishlist_DonationTitle);
        addDonationTitle.setText(title);
        TextView addDonationDescription=findViewById(R.id.wishlist_DonationDescription);
        addDonationDescription.setText(des);
        TextView addLastDateDetails=findViewById(R.id.wishlist_LastDateDetails);
        addLastDateDetails.setText(lastDate);
        TextView addContactPageLocation=findViewById(R.id.wishlist_ContactPageLocation);
        addContactPageLocation.setText(location);
        TextView category=findViewById(R.id.wishlist_Category);
        category.setText(cat);

    }
}