package com.example.charity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private EditText name, email, contact, password, confirmPassword, location;
    private Button register;
    private TextView login;
    private CheckBox donor, volunteer, organization;
    private ProgressDialog progressDialog;
    String userCategory;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    boolean  verificationLinkSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        //location = findViewById(R.id.location);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        donor = findViewById(R.id.donor);
        volunteer = findViewById(R.id.volunteer);
        organization = findViewById(R.id.organization);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        //if already logged in
        /*if(fAuth.getCurrentUser()!=null){
            Intent intent = new Intent(Registration.this, Login.class );
            startActivity(intent);
            finish();
        }*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trimmimg to format data
                String name1 = name.getText().toString().trim();
                String email1 = email.getText().toString();
                String contact1 = contact.getText().toString();
                String location1 = "null";
                String password1 = password.getText().toString();
                String confirmPassword1 = confirmPassword.getText().toString();
                getUserCategory();

                if (TextUtils.isEmpty(name1)) {
                    name.setError("Enter your name");
                    return;
                } else if (TextUtils.isEmpty(email1)) {
                    email.setError("Enter your email");
                    return;
                } else if (TextUtils.isEmpty(contact1)) {
                    contact.setError("Enter your contact");
                    return;
                } else if (TextUtils.isEmpty(location1)) {
                    contact.setError("Enter your location");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    password.setError("Enter your password");
                    return;
                } else if (TextUtils.isEmpty(confirmPassword1)) {
                    confirmPassword.setError("Confirm your password");
                    return;
                } else if (!password1.equals(confirmPassword1)) {
                    confirmPassword.setError("Different Password");
                    return;
                } else if (password1.length() < 7) {
                    password.setError("At least 7 characters should be used as password!");
                    return;
                } else if (!isVallidEmail(email1)) {
                    email.setError("Invalid Email!");
                    return;
                } else if(!donor.isChecked() && !volunteer.isChecked() && !organization.isChecked()){
                    //donor.setError("Choose one checkbox!");
                    //volunteer.setError("Choose one checkbox!");
                    //organization.setError("Choose one checkbox!");
                    Toast.makeText(Registration.this, "Choose one option",Toast.LENGTH_LONG).show();
                    return;
                }else if((donor.isChecked() && volunteer.isChecked()) || (donor.isChecked() && volunteer.isChecked()) || (organization.isChecked() && volunteer.isChecked()) || (donor.isChecked() && volunteer.isChecked() && organization.isChecked())) {
                    //donor.setError("Choose one checkbox!");
                    //volunteer.setError("Choose one checkbox!");
                    //organization.setError("Choose one checkbox!");
                    Toast.makeText(Registration.this, "You can ony choose one option", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                //register user in firebase
                fAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //verify email code starts
                             fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        verificationLinkSent = true;
                                    }
                                    else
                                    {
                                        verificationLinkSent = false;

                                    }
                                }
                            });
                            //verify email ends

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",name1);
                            user.put("email",email1);
                            user.put("contactNo", contact1);
                            user.put("location", location1);
                            user.put("category", userCategory);
                             documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    if( verificationLinkSent == true)
                                   {
                                        Toast.makeText(Registration.this, "Registered successfully!\nA verification link has been sent to your email",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(Registration.this, "Successfully Registered but failed to send verification link!",Toast.LENGTH_LONG).show();
                               }
                            });
                            Intent intent = new Intent(Registration.this, Login.class );
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Registration.this, "SignUp Failed!",Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();

                    }
                });

            }
        });
    }

    void getUserCategory(){
        if(donor.isChecked())
        {
            userCategory =  "Donor";
        }
        else if(volunteer.isChecked())
        {
            userCategory =  "Volunteer";
        }
        else if(organization.isChecked())
        {
            userCategory =  "Organization";
        }
    }
    private boolean isVallidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}