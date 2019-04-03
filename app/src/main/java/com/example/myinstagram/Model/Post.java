package com.example.myinstagram.Model;

public class Post {

    private String description;
    private String publisher;
    private String postsId;
    private String postsImage;

    public Post(String description, String publisher, String postsId, String postsImage) {
        this.description = description;
        this.publisher = publisher;
        this.postsId = postsId;
        this.postsImage = postsImage;
    }
    public Post() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public String getPostsImage() {
        return postsImage;
    }

    public void setPostsImage(String postsImage) {
        this.postsImage = postsImage;
    }
}
