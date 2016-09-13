package com.lei.bbs.bean;

public class Response {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                '}';
    }
}
/*  public Repository(String name, String language, Owner owner) {
        this.name = name;
        this.language = language;
        this.owner = owner;
    }

    public String fullName() {
        return owner.username + "/" + name;
    }*/