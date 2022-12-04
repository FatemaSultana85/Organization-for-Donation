package com.example.charity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Login extends AppCompatActivity {
    EditText email, password;
    //private CheckBox donor, volunteer, organization;
    Button cirLoginButton;
    TextView cirRegisterButton;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        progressDialog = new ProgressDialog(this);
        cirRegisterButton = findViewById(R.id.register);
        forgotPass = findViewById(R.id.forgotPassword);
        //donor = findViewById(R.id.donor);
        //volunteer = findViewById(R.id.volunteer);
        //organization = findViewById(R.id.organization);


        cirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                if (TextUtils.isEmpty(email1)) {
                    email.setError("Enter your email");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    password.setError("Enter your password");
                    return;
                }
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                fAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                               /* if(donor.isChecked()){
                                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, HomePage.class );
                                    startActivity(intent);
                                    finish();
                                }
                                else if(volunteer.isChecked()){
                                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, HomePageVolunteer.class );
                                    startActivity(intent);
                                    finish();
                                }
                                else if(organization.isChecked()){
                                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, HomePageOrganization.class );
                                    startActivity(intent);
                                    finish();
                                }*/
                                Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, IntermediateHomepage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Please verify your email first", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "SignIn Failed!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetPass = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset password?");
                passwordResetDialog.setMessage("Enter your email to receive reset link");
                passwordResetDialog.setView(resetPass);

                passwordResetDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract email and send reset link
                        String mail = resetPass.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "A link was sent to your email", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Failed to send link!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close ddialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

}
