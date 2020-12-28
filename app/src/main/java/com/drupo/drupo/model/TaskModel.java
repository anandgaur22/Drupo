package com.drupo.drupo.model;

public class TaskModel {

    private String taskName;
    private String taskTime;
    private String taskAddress;
    private int image;

    public TaskModel(String taskName, String taskTime, String taskAddress, int image) {
        this.taskName = taskName;
        this.taskTime = taskTime;
        this.taskAddress = taskAddress;
        this.image = image;
    }


    public String getTaskName() {
        return taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskAddress() {
        return taskAddress;
    }

    public int getImage() {
        return image;
    }

}
