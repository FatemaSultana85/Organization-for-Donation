package com.example.charity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class notificationAdapter extends RecyclerView.Adapter<com.example.charity.notificationAdapter.myViewHolder> {


    String cat;
    ArrayList<NotificationModel> datalist;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String current_userID;

    public notificationAdapter(ArrayList<NotificationModel> datalist) {

        this.datalist = datalist;



    }

    @NonNull
    @Override
    public com.example.charity.notificationAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_notification,parent,false);
        return  new com.example.charity.notificationAdapter.myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull com.example.charity.notificationAdapter.myViewHolder holder, int position) {
        String userId =datalist.get(position).getUserID();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        current_userID = fAuth.getCurrentUser().getUid();


        String dateString = datalist.get(position).getStartDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date endDate = dateFormat.parse(dateString);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            Date today = new Date();

            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            if (userId.equals(current_userID)) {
                if ((todayWithZeroTime.compareTo(endDate) == 0)) {

                    holder.location.setText(datalist.get(position).getLocation());
                    holder.title.setText(datalist.get(position).getTitle());
                } else {
                    holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0, 0));
                    holder.parent_layout.setVisibility(View.GONE);
                }
            }else{
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0, 0));
                holder.parent_layout.setVisibility(View.GONE);
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
        TextView date,title,location;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            title=itemView.findViewById(R.id.notification_title);
            location=itemView.findViewById(R.id.notification_location);

            parent_layout=itemView.findViewById(R.id.parent_layout_notification);


        }

    }
}
