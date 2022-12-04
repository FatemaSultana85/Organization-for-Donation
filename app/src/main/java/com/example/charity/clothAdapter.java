package com.example.charity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import java.sql.SQLOutput;
        import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

//foodOrganizationName foodCategory foodDate foodTime
//foodTitle foodLocation foodLastDate foodDescription
// foodDetails

public class clothAdapter extends RecyclerView.Adapter<clothAdapter.myViewHolder> {
    String cat;
    ArrayList<foodModel> datalist;


    public clothAdapter(ArrayList<foodModel> datalist) {

        this.datalist = datalist;


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowfood,parent,false);
        return  new myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        cat=datalist.get(position).getCategory();

        String dateString = datalist.get(position).getDonationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date lastDateOfDonation = dateFormat.parse(dateString);


            SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MMMM-yyyy");
            Date currentDate = new Date();


        if(cat.equals("Cloth")) {
            if (currentDate.before(lastDateOfDonation)) {
                holder.location.setText(datalist.get(position).getLocation());
                holder.category.setText(datalist.get(position).getCategory());
                holder.date.setText(datalist.get(position).getDate());
                holder.des.setText(datalist.get(position).getDescription());
                holder.lastDate.setText(datalist.get(position).getDonationDate());
                holder.name.setText(datalist.get(position).getOrganizationName());
                holder.time.setText(datalist.get(position).getTime());
                holder.title.setText(datalist.get(position).getTitle());
                holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), donationDetails.class);
                        intent.putExtra("location1", datalist.get(position).getLocation());
                        intent.putExtra("cat1", datalist.get(position).getCategory());
                        intent.putExtra("des1", datalist.get(position).getDescription());
                        intent.putExtra("lastDate1", datalist.get(position).getDonationDate());
                        intent.putExtra("name1", datalist.get(position).getOrganizationName());
                        intent.putExtra("title1", datalist.get(position).getTitle());
                        v.getContext().startActivity(intent);
                    }
                });
            }else{
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0,0));
                holder.parent_layout.setVisibility(View.GONE);
            }

        }else{
            holder.parent_layout.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.parent_layout.setLayoutParams(new CardView.LayoutParams(0,0));
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
        TextView name,date,time,category,title,location,lastDate,des,userId,details;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            location=itemView.findViewById(R.id.foodLocation);
            category=itemView.findViewById(R.id.foodCategory);
            date=itemView.findViewById(R.id.foodDate);
            des=itemView.findViewById(R.id.foodDescription);
            lastDate=itemView.findViewById(R.id.foodLastDate);
            name=itemView.findViewById(R.id.foodOrganizationName);
            time=itemView.findViewById(R.id.foodTime);
            title=itemView.findViewById(R.id.foodTitle);

            //userId=itemView.findViewById(R.id.foodUserId);
            parent_layout=itemView.findViewById(R.id.parent_layout);
            itemView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            //}


        }

    }
}
