package com.lei.bbs.bean;

public class Response {

    private String status;
    private String result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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