package edu.practice.domain.data.task;

import java.time.LocalDateTime;

import static edu.practice.domain.ToDoAppHelper.getTaskInfo;

public class Task {
    private final int id;
    private String name, description;
    private LocalDateTime dueDateTime;
    private Status status;

    public Task(int id, String name, String description, LocalDateTime dueDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDateTime = dueDateTime;
        status = Status.WORKING_ON_IT;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getTaskInfo(this);
    }
}
