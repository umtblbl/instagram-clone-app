package com.example.myinstagram.Model;

public class Story {

    private String imageUrl, storyId, userId;
    private long timeStart, timeEnd;

    public Story(String imageUrl, String storyId, long timeStart, long timeEnd, String userId) {
        this.imageUrl = imageUrl;
        this.storyId = storyId;
        this.userId = userId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public Story() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }
}
