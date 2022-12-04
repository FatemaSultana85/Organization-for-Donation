package com.example.charity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class editAccount extends AppCompatActivity {
    int i;
    Button backButton, update;
    TextView check;
    EditText text,text1, text2, text3;
    String userName, userEmail, userLocation, userContact, userCategory;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        backButton = findViewById(R.id.backButton);
        update = findViewById(R.id.update);
        check = findViewById(R.id.check);
        text = findViewById(R.id.editFullName);
        text1 = findViewById(R.id.addEmail);
        text2 = findViewById(R.id.editPhone);
        text3 = findViewById(R.id.addLocation);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                check.setText(documentSnapshot.getString("category"));
                text.setText(documentSnapshot.getString("name"));
                text1.setText(documentSnapshot.getString("email"));
                text2.setText(documentSnapshot.getString("contactNo"));
                text3.setText(documentSnapshot.getString("location"));
                userCategory = check.getText().toString();
                userName = text.getText().toString();
                userEmail = text1.getText().toString();
                userContact = text2.getText().toString();
                userLocation = text3.getText().toString();

                setUser();
            }
        });

        sendUserToDesignatededitAccount();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty() || text1.getText().toString().isEmpty() || text2.getText().toString().isEmpty() || text3.getText().toString().isEmpty()) {
                    Toast.makeText(editAccount.this, "No field should remain empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = text1.getText().toString();
                String name = text.getText().toString();
                String contact = text2.getText().toString();
                String location = text3.getText().toString();
                String check1 = check.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("location",location);
                        edited.put("contactNo",contact);
                        edited.put("email",email);
                        edited.put("name",name);
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(editAccount.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                                if(i==0)
                                {
                                    Intent intent = new Intent(editAccount.this,HomePage.class );
                                    startActivity(intent);
                                    finish();

                                }
                                else if(i==1)
                                {
                                    Intent intent = new Intent(editAccount.this,HomePageVolunteer.class );
                                    startActivity(intent);
                                    finish();

                                }
                                else if(i==2)
                                {
                                    Intent intent = new Intent(editAccount.this,HomePageOrganization.class );
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                       // Toast.makeText(editAccount.this, "Email is changed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i==0)
                {
                    Intent intent = new Intent(editAccount.this,HomePage.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==1)
                {
                    Intent intent = new Intent(editAccount.this,HomePageVolunteer.class );
                    startActivity(intent);
                    finish();

                }
                else if(i==2)
                {
                    Intent intent = new Intent(editAccount.this,HomePageOrganization.class );
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void sendUserToDesignatededitAccount() {
    }


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
