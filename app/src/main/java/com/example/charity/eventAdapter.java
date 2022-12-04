package com.example.charity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.myViewHolder> implements Filterable{
 ArrayList<eventModel> eventdatalist;
 ArrayList<eventModel> backup;
 int done = 1;
 Context context;

    public eventAdapter(ArrayList<eventModel> eventlist) {

        this.eventdatalist = eventlist;
        backup = new ArrayList<>(eventlist);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_event,parent,false);
       return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String dateString = eventdatalist.get(position).getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date endDate = dateFormat.parse(dateString);

            SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MMMM-yyyy");
            Date currentDate = new Date();
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
                holder.parent_layout_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), EventDetails.class);
                        intent.putExtra("eventLocation1", eventdatalist.get(position).getLocation());
                        intent.putExtra("eventCategory1", eventdatalist.get(position).getCategory());
                        intent.putExtra("eventDescription1", eventdatalist.get(position).getDescription());
                        intent.putExtra("endDate1", eventdatalist.get(position).getEndDate());
                        intent.putExtra("endTime1", eventdatalist.get(position).getEndTime());
                        intent.putExtra("startDate1", eventdatalist.get(position).getStartDate());
                        intent.putExtra("startTime1", eventdatalist.get(position).getStartTime());
                        intent.putExtra("eventTitle1", eventdatalist.get(position).getTitle());
                        v.getContext().startActivity(intent);
                    }
                });
            }
            else {
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    final Filter filter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<eventModel> filteredData = new ArrayList<>();

            if(done == 1)
            {
            for (eventModel e : eventdatalist) {
                backup.add(e);
            }
           // backup = new ArrayList<eventModel>(Collections.unmodifiableSet(new LinkedHashSet<eventModel>(backup)));
            //backup = new ArrayList<>(new LinkedHashSet<>(backup));
            //cityList = cityList.stream().distinct().collect(Collectors.toList());
            //backup = (ArrayList<eventModel>) backup.stream().distinct().collect(Collectors.toList());
            done = 0;
            }
            if (constraint == null || constraint.length() == 0) {
                filteredData.addAll(backup);
                System.out.println("empty so add all");
            } else {
                for (eventModel item : backup) {
                    if (item.getCategory().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        filteredData.add(item);
                        System.out.println("add matched");
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventdatalist.clear();
            eventdatalist.addAll((ArrayList<eventModel>) results.values);
            notifyDataSetChanged();
        }
    };


    class myViewHolder extends RecyclerView.ViewHolder{
        CardView parent_layout_event;
        TextView eventCategory,eventDate,eventTime,eventTitle,startDate,startTime,endDate,endTime,eventLocation,eventDescription;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
           eventLocation=itemView.findViewById(R.id.eventLocation);
            eventCategory=itemView.findViewById(R.id.eventCategory);
            eventDate=itemView.findViewById(R.id.eventDate);
            eventDescription=itemView.findViewById(R.id.eventDescription);
            endDate=itemView.findViewById(R.id.endDate);
            endTime=itemView.findViewById(R.id.endTime);
            startDate=itemView.findViewById(R.id.startDate);
            startTime=itemView.findViewById(R.id.startTime);
            eventTime=itemView.findViewById(R.id.eventTime);
            eventTitle=itemView.findViewById(R.id.eventTitle);
            parent_layout_event=itemView.findViewById(R.id.parent_layout_event);


        }
    }
}
   // eventCategory eventDate eventTime eventTitle startDate startTime endDate endTime eventLocation
     //   eventDescription eventDetails