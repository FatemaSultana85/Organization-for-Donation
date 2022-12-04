package com.example.charity;

import com.google.firebase.database.Exclude;

import javax.annotation.meta.Exclusive;

public class foodModel {
    String Location,category,date,description,donationDate,organizationName,postId,time,title,userID;



    public foodModel() {
    }

    public foodModel(String location, String category, String date, String description, String donationDate, String organizationName,String postId, String time, String title, String userID) {
        Location = location;
        this.category = category;
        this.date = date;
        this.description = description;
        this.donationDate = donationDate;
        this.organizationName = organizationName;
        this.postId=postId;
        this.time = time;
        this.title = title;
        this.userID = userID;

    }



    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
