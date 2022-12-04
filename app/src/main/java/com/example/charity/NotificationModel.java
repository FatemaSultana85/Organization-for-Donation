package com.example.charity;

public class NotificationModel {
    String Location,startDate,title,userID;

    public NotificationModel() {
    }

    public NotificationModel(String location, String startDate, String title, String userID) {
        Location = location;
        this.startDate = startDate;
        this.title = title;
        this.userID = userID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
