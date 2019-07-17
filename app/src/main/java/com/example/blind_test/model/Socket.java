package com.example.blind_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Socket {
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("topic")
    @Expose
    private String topic;

    public Socket(String event, String topic) {
        this.event = event;
        this.topic = topic;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Socket{" +
                "event='" + event + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
