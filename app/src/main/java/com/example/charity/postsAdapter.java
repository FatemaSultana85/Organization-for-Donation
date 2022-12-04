package com.example.charity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import java.sql.SQLOutput;
        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Map;

        import android.util.Log;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.orhanobut.dialogplus.DialogPlus;
        import com.orhanobut.dialogplus.ViewHolder;




public class postsAdapter extends RecyclerView.Adapter<postsAdapter.myViewHolder>{
    ArrayList<foodModel> datalist;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String current_userID;
    DatabaseReference getRef;
    DocumentSnapshot documentSnapshot;
    private String saveCurrentDate;
    String orgId;

    String TAG="asdfa";


    public postsAdapter(ArrayList<foodModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public postsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_edit_post,parent,false);
        return  new postsAdapter.myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull postsAdapter.myViewHolder holder, final int position) {
        //cat=datalist.get(position).getCategory();
        String userId =datalist.get(position).getUserID();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        current_userID = fAuth.getCurrentUser().getUid();
        String posId=datalist.get(position).getPostId();



        String dateString = datalist.get(position).getDonationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date lastDateOfDonation = dateFormat.parse(dateString);


        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        Date currentDate = new Date();

        Log.d(TAG, "come to me: "+lastDateOfDonation);


        if (userId.equals(current_userID)){
            if (currentDate.before(lastDateOfDonation)){
                holder.location.setText(datalist.get(position).getLocation());
                holder.category.setText(datalist.get(position).getCategory());
                holder.date.setText(datalist.get(position).getDate());
                holder.des.setText(datalist.get(position).getDescription());
                holder.lastDate.setText(datalist.get(position).getDonationDate());
                holder.name.setText(datalist.get(position).getOrganizationName());
                holder.time.setText(datalist.get(position).getTime());
                holder.title.setText(datalist.get(position).getTitle());


                holder.editPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                                .setContentHolder(new ViewHolder(R.layout.dialog_content_edit_post))
                                .setExpanded(true,1700)
                                .create();
                        View myview=dialogPlus.getHolderView();
                        EditText addDonationTo=myview.findViewById(R.id.edit_org_Name);
                        EditText addDonationTitle =myview.findViewById(R.id.edit_title);
                        EditText addDonationDescription=myview.findViewById(R.id.edit_des);
                        EditText addLastDateDetails=myview.findViewById(R.id.edit_last_date);
                        EditText addContactPageLocation=myview.findViewById(R.id.edit_loc);
                        EditText category=myview.findViewById(R.id.edit_cat);
                        Button edit_button=myview.findViewById(R.id.edit_button);
                        addDonationTo.setText(datalist.get(position).getOrganizationName());
                        addDonationTitle.setText(datalist.get(position).getTitle());
                        addDonationDescription.setText(datalist.get(position).getDescription());
                        addLastDateDetails.setText(datalist.get(position).getDonationDate());
                        addContactPageLocation.setText(datalist.get(position).getLocation());
                        category.setText(datalist.get(position).getCategory());
                        dialogPlus.show();
                        edit_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



                                DocumentReference documentReference = fStore.collection("OrganizationCreatePost").document(posId);
                                Map<String,Object> map=new HashMap<>();
                                map.put("Location",addContactPageLocation.getText().toString());
                                map.put("category",category.getText().toString());
                                map.put("description",addDonationDescription.getText().toString());
                                map.put("donationDate",addLastDateDetails.getText().toString());
                                map.put("organizationName",addDonationTo.getText().toString());
                                map.put("title", addDonationTitle.getText().toString());


                                documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        dialogPlus.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                        //Log.e(TAG, "onFailure: ",e );
                                    }
                                });

                            }
                        });


                    }
                });
                holder.postDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder =new AlertDialog.Builder(holder.name.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Delete.....?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore docRef = FirebaseFirestore.getInstance();
                                DocumentReference selectedDoc = docRef.collection("OrganizationCreatePost").document(posId);
                                selectedDoc.delete();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    }
                });

            }
            else {
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0,0));
                holder.parent_layout.setVisibility(View.GONE);
            }


        }
        else {


            holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0,0));
            holder.parent_layout.setVisibility(View.GONE);

            //holder.parent_layout.setMinimumHeight(1);

            //holder.setIsRecyclable(false);



        }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CardView parent_layout;
        TextView name,date,time,category,title,location,lastDate,des,Design_userId,details;
        Button editPostButton,postDeleteButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

                location=itemView.findViewById(R.id.edit_post_Location);
                category=itemView.findViewById(R.id.edit_post_Category);
                date=itemView.findViewById(R.id.edit_post_Date);
                des=itemView.findViewById(R.id.edit_post_Description);
                lastDate=itemView.findViewById(R.id.edit_post_LastDate);
                name=itemView.findViewById(R.id.edit_post_OrganizationName);
                time=itemView.findViewById(R.id.edit_post_Time);
                title=itemView.findViewById(R.id.edit_post_Title);
                editPostButton=itemView.findViewById(R.id.editPostButton);
                postDeleteButton=itemView.findViewById(R.id.deletePostButton);
                //Design_userId=itemView.findViewById(R.id.userId);
                parent_layout=itemView.findViewById(R.id.parent_layout_edit_post);



        }

    }

}
