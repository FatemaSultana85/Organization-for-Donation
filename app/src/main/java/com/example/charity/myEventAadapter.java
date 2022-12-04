package com.example.charity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class myEventAadapter extends RecyclerView.Adapter<myEventAadapter.myViewHolder> {
    ArrayList<eventModel> eventdatalist;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String current_userID;


    public myEventAadapter(ArrayList<eventModel> eventdatalist) {
        this.eventdatalist = eventdatalist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_edit_event,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String userId =eventdatalist.get(position).getUserID();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        current_userID = fAuth.getCurrentUser().getUid();
        String eventId=eventdatalist.get(position).getEventpostId();



        String dateString = eventdatalist.get(position).getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date endDate = dateFormat.parse(dateString);

            SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MMMM-yyyy");
            Date currentDate = new Date();

        if (userId.equals(current_userID)){
            if (currentDate.before(endDate)) {

                holder.eventLocation.setText(eventdatalist.get(position).getLocation());
                holder.eventCategory.setText(eventdatalist.get(position).getCategory());
                holder.eventDate.setText(eventdatalist.get(position).getDate());
                holder.eventDescription.setText(eventdatalist.get(position).getDescription());
                holder.endDate.setText(eventdatalist.get(position).getEndDate());
                holder.endTime.setText(eventdatalist.get(position).getEndTime());
                holder.startDate.setText(eventdatalist.get(position).getStartDate());
                holder.startTime.setText(eventdatalist.get(position).getStartTime());
                holder.eventTime.setText(eventdatalist.get(position).getTime());
                holder.eventTitle.setText(eventdatalist.get(position).getTitle());

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.eventLocation.getContext())
                                .setContentHolder(new ViewHolder(R.layout.dialog_content_edit_event))
                                .setExpanded(true, 1700)
                                .create();
                        View myview = dialogPlus.getHolderView();

                        EditText loc = myview.findViewById(R.id.edit_event_loc);
                        EditText cat = myview.findViewById(R.id.edit_event_cat);
                        EditText des = myview.findViewById(R.id.edit_event_des);
                        EditText endd = myview.findViewById(R.id.edit_event_end_date);
                        EditText endt = myview.findViewById(R.id.edit_event_end_time);
                        EditText startd = myview.findViewById(R.id.edit_event_start_date);
                        EditText startt = myview.findViewById(R.id.edit_event_start_time);
                        EditText title = myview.findViewById(R.id.edit_event_title);


                        Button edit_button = myview.findViewById(R.id.edit_event_button);
                        loc.setText(eventdatalist.get(position).getLocation());
                        cat.setText(eventdatalist.get(position).getCategory());
                        des.setText(eventdatalist.get(position).getDescription());
                        endd.setText(eventdatalist.get(position).getEndDate());
                        endt.setText(eventdatalist.get(position).getEndTime());
                        startd.setText(eventdatalist.get(position).getStartDate());
                        startt.setText(eventdatalist.get(position).getStartTime());
                        title.setText(eventdatalist.get(position).getTitle());


                        dialogPlus.show();
                        edit_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                                DocumentReference documentReference = fStore.collection("events").document(eventId);
                                Map<String, Object> map = new HashMap<>();
                                map.put("Location", loc.getText().toString());
                                map.put("category", cat.getText().toString());
                                map.put("description", des.getText().toString());
                                map.put("endDate", endd.getText().toString());
                                map.put("endTime", endt.getText().toString());
                                map.put("startDate", startd.getText().toString());
                                map.put("startTime", startt.getText().toString());
                                map.put("title", title.getText().toString());


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
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.eventLocation.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Delete.....?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore docRef = FirebaseFirestore.getInstance();
                                DocumentReference selectedDoc = docRef.collection("events").document(eventId);
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
                holder.parent_layout_event.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.parent_layout_event.setLayoutParams(new CardView.LayoutParams(0,0));
                holder.parent_layout_event.setVisibility(View.GONE);

            }
        }
        else{
            holder.parent_layout_event.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.parent_layout_event.setLayoutParams(new CardView.LayoutParams(0,0));
            holder.parent_layout_event.setVisibility(View.GONE);

        }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    @Override
    public int getItemCount() {
        return eventdatalist.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CardView parent_layout_event;
        TextView eventCategory,eventDate,eventTime,eventTitle,startDate,startTime,endDate,endTime,eventLocation,eventDescription;
        Button edit,delete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            eventLocation=itemView.findViewById(R.id.event_Location);
            eventCategory=itemView.findViewById(R.id.event_Category);
            eventDate=itemView.findViewById(R.id.event_Date);
            eventDescription=itemView.findViewById(R.id.event_Description);
            endDate=itemView.findViewById(R.id.event_endDate);
            endTime=itemView.findViewById(R.id.event_endTime);
            startDate=itemView.findViewById(R.id.event_startDate);
            startTime=itemView.findViewById(R.id.event_startTime);
            eventTime=itemView.findViewById(R.id.event_Time);
            eventTitle=itemView.findViewById(R.id.event_Title);
            parent_layout_event=itemView.findViewById(R.id.parent_layout_edit_event);
            edit=itemView.findViewById(R.id.event_EditButton);
            delete=itemView.findViewById(R.id.event_deleteButton);


        }
    }
}
