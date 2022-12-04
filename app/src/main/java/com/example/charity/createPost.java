package com.example.charity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;





public class createPost extends AppCompatActivity {
    //adding calender for last date to donate
    TextView lastDate;
    ImageView backButton;
    FirebaseFirestore db;
    //for add post
    private ProgressDialog loadingBar;
    private Button share;
    private Spinner category;
    private EditText title, details, addDate,orgName;
    private TextView location;
    //private static final int gallery_pick=1;
    //private Uri ImageUri;
    private String userCategory;
    private String postTitle, description, userLocation, donationDate,orgName1;
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;
    //private StorageReference postImagesReference;
    private DatabaseReference UserRef, PostRef;
    private FirebaseAuth mAuth;

    FirebaseFirestore fStore;
    //String postId;
    String userID;

    int my_req_code=101;

    String save_lat_lon,show_address;


    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        //adding calender for last date to donate
        lastDate = findViewById(R.id.addLastDate);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createPost.this,posts.class );
                startActivity(intent);
                finish();
            }
        });

        lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        createPost.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        lastDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        category = (Spinner) findViewById(R.id.choseCategory);
        orgName = (EditText) findViewById(R.id.organizationName);
        title = (EditText) findViewById(R.id.addTitle);
        share = (Button) findViewById(R.id.button_apply);
        details = (EditText) findViewById(R.id.addDescription);
        location = (TextView) findViewById(R.id.addLocation);
        addDate = (EditText) findViewById(R.id.addLastDate);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(createPost.this, SearchLocation.class);
                startActivityForResult(i,1);
            }
        });

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        // postImagesReference = FirebaseStorage.getInstance().getReference();
        //UserRef = FirebaseDatabase.getInstance().getReference().child("users");
        //PostRef = FirebaseDatabase.getInstance().getReference().child("OrganizationCreatePost");

        //current_user_id = mAuth.getCurrentUser().getUid();

        //copy from muniya
        //fStore = FirebaseFirestore.getInstance();


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();


            }

        });


       /* lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                lastDate.setText(date);
            }
        };*/
   /* lastDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar calendar=Calendar.getInstance();
            mDate=calendar.get(Calendar.DATE);
            mMonth=calendar.get(Calendar.MONTH);
            mYear=calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                   lastDate.setText(dayOfMonth+"-"+month+"-"+year);
                }
            });
        }
    });*/
    }

    private void ValidatePostInfo() {
        userCategory = category.getSelectedItem().toString();
        orgName1 = orgName.getText().toString();
        description = details.getText().toString();
        userLocation = location.getText().toString();
        postTitle = title.getText().toString();
        donationDate = addDate.getText().toString();
        if (userCategory.equals("Choose Category")) {
            ((TextView) category.getSelectedView()).setError("Please enter a category");
            category.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userLocation)) {
            location.setError("Please enter the location...");
            location.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(orgName1)) {
            orgName.setError("Please enter the Organization Name...");
            orgName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(postTitle)) {
            title.setError("Please enter the Title...");
            title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(donationDate)) {
            addDate.setError("Please enter the Date...");
            addDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            details.setError("Please enter the details");
            details.requestFocus();
            return;
        }
        else {
            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());

            Calendar callForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            saveCurrentTime = currentTime.format(callForTime.getTime());

            postRandomName = saveCurrentDate + saveCurrentTime;
            //StoringImageToFirebaseStorage();
            userID = mAuth.getCurrentUser().getUid();
            //String userFullName=mAuth.getCurrentUser().getDisplayName();
            //String userFullName = dataSnapshot.child("name").getValue().toString();
            final int min = 1;
            final int max = 1000;
            final int random = new Random().nextInt((max - min) + 1) + min;
            String postId = userID+random;
            DocumentReference documentReference = fStore.collection("OrganizationCreatePost").document(postId);
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("userID", userID);
            postMap.put("date", saveCurrentDate);
            postMap.put("time", saveCurrentTime);
            postMap.put("category", userCategory);
            postMap.put("date", saveCurrentDate);
            postMap.put("organizationName", orgName1);
            postMap.put("description", description);
            postMap.put("Location", userLocation);
            postMap.put("donationDate", donationDate);
            postMap.put("title", postTitle);
            postMap.put("postId",postId);


            documentReference.set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(createPost.this, "Post successfully created", Toast.LENGTH_LONG).show();
                }
            });
            Intent intent = new Intent(createPost.this, createPost.class );
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            save_lat_lon = data.getStringExtra("KEY");
            show_address = data.getStringExtra("KEY2");
            location.setText(show_address);
        }
    }




}


/*

    private void ValidatePostInfo() {
        userCategory=category.getSelectedItem().toString();
        description = details.getText().toString();
        userLocation=location.getText().toString();
        postTitle=title.getText().toString();
        donationDate=addDate.getText().toString();

        if(userCategory.equals("Choose Category"))
        {
            ((TextView)category.getSelectedView()).setError("Please enter a category");
            category.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userLocation))
        {
            location.setError("Please enter the location...");
            location.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(postTitle))
        {
            title.setError("Please enter the Title...");
            title.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(donationDate))
        {
            addDate.setError("Please enter the Date...");
            addDate.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(description))
        {
            details.setError("Please enter the details");
            details.requestFocus();
            return;
        }

        else
        {
            loadingBar.setTitle("Add new post");
            loadingBar.setMessage("Updating new post");
            loadingBar.show();
            StoringImageToFirebaseStorage();
        }

    }*/

    /*private void StoringImageToFirebaseStorage () {
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = postImagesReference.child("Post images For Room").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(PostForRentingRoom.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(PostForRentingRoom.this,"Image uploaded", Toast.LENGTH_SHORT).show();
                    SavingPostInformationToDatabase();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(PostForRentingRoom.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    /*private void SavingPostInformationToDatabase()
    {
        UserRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String userFullName = dataSnapshot.child("name").getValue().toString();
//choseCategory addTitle addDescription addLocation addLastDate button_apply OrganizationCreatePost users
                    // private Spinner category; private String userCategory;
                    //private EditText title,details,location,addDate; private String postTitle,description,userLocation,donationDate;

                    HashMap postMap = new HashMap();
                    postMap.put("uid",current_user_id);
                    postMap.put("date",saveCurrentDate);
                    postMap.put("time",saveCurrentTime);
                    postMap.put("category",userCategory);
                    postMap.put("title",postTitle);
                    postMap.put("description",description);
                    postMap.put("Location",userLocation);
                    postMap.put("donationDate",donationDate);
                    postMap.put("name",userFullName);

                    PostRef.child(current_user_id + postRandomName).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        createPost();
                                        Toast.makeText(createPost.this,"Post updated successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(createPost.this,"Failed to upload post", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/


    /*private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,gallery_pick);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallery_pick && resultCode==RESULT_OK)
        {
            ImageUri=data.getData();
            addPhoto.setImageURI(ImageUri);
        }
    }*/


    /*public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        createPost();
        return super.onOptionsItemSelected(item);
    }

    private void createPost()
    {
        Intent intent=new Intent(createPost.this,createPost.class);
        startActivity(intent);
    }
}*/