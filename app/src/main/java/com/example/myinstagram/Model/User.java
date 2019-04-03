package com.example.myinstagram.Model;

public class User {

    private String Id, userName, fullName, imageUrl, bio;

    public User(String id, String userName, String fullName, String imageUrl, String bio) {
        Id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public User() {

    }

    public void setId(String id) {
        Id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return Id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBio() {
        return bio;
    }
}
