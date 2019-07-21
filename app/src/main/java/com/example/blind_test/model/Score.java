package com.example.blind_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("id")
    @Expose
    private String id;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Score{" +
                "nickname='" + nickname + '\'' +
                ", score='" + score + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
