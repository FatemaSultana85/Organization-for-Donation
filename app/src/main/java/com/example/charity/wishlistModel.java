package com.example.charity;

public class wishlistModel {
    String Location,category, description, donationDate, organizationName,   title, userID;

    public wishlistModel() {
    }

    public wishlistModel(String location, String category, String description, String donationDate, String organizationName, String title, String userID) {
        Location = location;
        this.category = category;
        this.description = description;
        this.donationDate = donationDate;
        this.organizationName = organizationName;
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
