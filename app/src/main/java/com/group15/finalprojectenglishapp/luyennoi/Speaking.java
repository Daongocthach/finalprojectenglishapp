package com.group15.finalprojectenglishapp.luyennoi;

import java.io.Serializable;

public class Speaking implements Serializable {
    private String topic;
    private int image;
    private String sentence;

    public Speaking() {

    }
    public Speaking(String topic, int image, String sentence) {
        this.topic = topic;
        this.image = image;
        this.sentence = sentence;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

}