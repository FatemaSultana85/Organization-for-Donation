package com.example.charity;

import java.util.ArrayList;

public class eventModel {
    String EventpostId,Location,category,date,description,endDate,endTime,startDate,startTime,time,title,userID;

    public eventModel() {
    }

    public eventModel(String EventpostId,String location, String category, String date, String description, String endDate, String endTime, String startDate, String startTime, String time, String title, String userID) {
        this.EventpostId=EventpostId;
        Location = location;
        this.category = category;
        this.date = date;
        this.description = description;
        this.endDate = endDate;
        this.endTime = endTime;
        this.startDate = startDate;
        this.startTime = startTime;
        this.time = time;
        this.title = title;
        this.userID = userID;
    }

    public String getEventpostId() {
        return EventpostId;
    }

    public void setEventpostId(String eventpostId) {
        EventpostId = eventpostId;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
