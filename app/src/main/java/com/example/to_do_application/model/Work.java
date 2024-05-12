package com.example.to_do_application.model;

import java.io.Serializable;

public class Work implements Serializable {
    private int id;
    private String task;
    private String date;

    public Work() {
    }

    public Work(int id, String task, String date) {
        this.id = id;
        this.task = task;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
