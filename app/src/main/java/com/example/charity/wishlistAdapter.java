package com.example.charity;



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
import android.util.Log;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.myViewHolder> {


    String cat;
    ArrayList<wishlistModel> datalist;


    public wishlistAdapter(ArrayList<wishlistModel> datalist) {

        this.datalist = datalist;



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_wishlist,parent,false);
        return  new myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.location.setText(datalist.get(position).getLocation());
        holder.category.setText(datalist.get(position).getCategory());
        //holder.date.setText(datalist.get(position).getDate());
        holder.des.setText(datalist.get(position).getDescription());
        holder.lastDate.setText(datalist.get(position).getDonationDate());
        holder.name.setText(datalist.get(position).getOrganizationName());
        //holder.time.setText(datalist.get(position).getTime());
        holder.title.setText(datalist.get(position).getTitle());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), wishlistDetails.class);
                intent.putExtra("location1", datalist.get(position).getLocation());
                intent.putExtra("cat1", datalist.get(position).getCategory());
                intent.putExtra("des1", datalist.get(position).getDescription());
                intent.putExtra("lastDate1", datalist.get(position).getDonationDate());
                intent.putExtra("name1", datalist.get(position).getOrganizationName());
                intent.putExtra("title1", datalist.get(position).getTitle());
                v.getContext().startActivity(intent);
            }
        });



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


            location=itemView.findViewById(R.id.wishlist_Location);
             category=itemView.findViewById(R.id.wishlist_Category);
            des=itemView.findViewById(R.id.wishlist_Description);
            lastDate=itemView.findViewById(R.id.wishlist_LastDate);
            name=itemView.findViewById(R.id.wishlist_orgName);
            title=itemView.findViewById(R.id.wishlist_Title);
            parent_layout=itemView.findViewById(R.id.parent_layout_wishlist);


        }

    }
}
