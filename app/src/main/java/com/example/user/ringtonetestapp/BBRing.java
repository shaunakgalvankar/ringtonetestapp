package com.example.user.ringtonetestapp;

public class BBRing {
    private String id;
    private String post_title;
    private String post_audio;

    public BBRing(String id, String post_title, String post_audio) {
        this.id = id;
        this.post_title = post_title;
        this.post_audio = post_audio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_audio() {
        return post_audio;
    }

    public void setPost_audio(String post_audio) {
        this.post_audio = post_audio;
    }
}
