package com.example.blind_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lobbies {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("restricted")
    @Expose
    private String restricted;

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("questions")
    @Expose
    private String questions;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("theme_id")
    @Expose
    private String theme_id;

    @SerializedName("theme_title")
    @Expose
    private String theme_title;

    @SerializedName("theme_description")
    @Expose
    private String theme_description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public String getTheme_title() {
        return theme_title;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public String getTheme_description() {
        return theme_description;
    }

    public void setTheme_description(String theme_description) {
        this.theme_description = theme_description;
    }

    @Override
    public String toString() {
        return "Lobbies{" +
                "id='" + id + '\'' +
                ", restricted='" + restricted + '\'' +
                ", active='" + active + '\'' +
                ", questions='" + questions + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_by='" + created_by + '\'' +
                ", theme_id='" + theme_id + '\'' +
                ", theme_title='" + theme_title + '\'' +
                ", theme_description='" + theme_description + '\'' +
                '}';
    }
}
