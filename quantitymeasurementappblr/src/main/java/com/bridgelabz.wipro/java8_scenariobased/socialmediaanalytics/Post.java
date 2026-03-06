package com.bridgelabz.wipro.java8_scenariobased.socialmediaanalytics;

import java.util.List;

public class Post {

    String user;
    List<String> hashtags;
    int likes;

    public Post(String user, List<String> hashtags, int likes) {
        this.user = user;
        this.hashtags = hashtags;
        this.likes = likes;
    }

    public String getUser() {
        return user;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "user='" + user + '\'' +
                ", hashtags=" + hashtags +
                ", likes=" + likes +
                '}';
    }
}
