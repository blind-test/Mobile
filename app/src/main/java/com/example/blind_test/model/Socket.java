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
    @SerializedName("running")
    @Expose
    private String running;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("payload")
    @Expose
    private String payload;
    @SerializedName("statut")
    @Expose
    private String statut;

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
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
                ", running='" + running + '\'' +
                ", subject='" + subject + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
