package com.example.charity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Notifications extends AppCompatActivity {
    int i;
    TextView y;
    String userCategory;
    ImageButton backButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser user;
    //new work
    RecyclerView recview_notification;
    ArrayList<NotificationModel> datalist;
    FirebaseFirestore db;
    notificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        backButton = findViewById(R.id.backButton);
        y = findViewById(R.id.y);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                y.setText(documentSnapshot.getString("category"));
                userCategory = y.getText().toString();
                setUser();
            }
        });
        sendUserToDesignatedNotifications();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i==0)
                {
                    Intent intent = new Intent(Notifications.this,HomePage.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==1)
                {
                    Intent intent = new Intent(Notifications.this,HomePageVolunteer.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==2)
                {
                    Intent intent = new Intent(Notifications.this,HomePageOrganization.class );
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
    private void sendUserToDesignatedNotifications() {

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
        //new Work
        recview_notification=(RecyclerView)findViewById(R.id.recview_notification);
        recview_notification.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new notificationAdapter(datalist);
        recview_notification.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("Notification").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            NotificationModel obj=d.toObject(NotificationModel.class);
                            datalist.add(obj);


                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
