package com.example.blind_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class listUsers {

    @SerializedName("q")
    @Expose
    private String nickname;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nickname")
    @Expose
    private String nicknameFinal;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("rank")
    @Expose
    private String rank;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("friendship_id")
    @Expose
    private int friendship_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(int friendship_id) {
        this.friendship_id = friendship_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNicknameFinal() {
        return nicknameFinal;
    }

    public void setNicknameFinal(String nicknameFinal) {
        this.nicknameFinal = nicknameFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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

    public String getName() {
        return nickname;
    }

    public void setName(String name) {
        this.nickname = name;
    }

    public listUsers(int id, int friendship_id,String password, String email, String nicknameFinal, String status, String rank, String created_at, String updated_at) {
        this.id = id;
        this.id = friendship_id;
        this.password = password;
        this.email = email;
        this.nicknameFinal = nicknameFinal;
        this.status = status;
        this.rank = rank;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "listUsers{" +
                "nickname='" + nickname + '\'' +
                ", id=" + id +
                ", id=" + friendship_id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nicknameFinal='" + nicknameFinal + '\'' +
                ", status='" + status + '\'' +
                ", rank='" + rank + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
