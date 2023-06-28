package edu.practice.domain.data.taskList;

import edu.practice.domain.data.task.Task;

import java.util.List;

import static edu.practice.domain.ToDoAppHelper.getTaskListInfo;

public class TaskList implements TaskListRepository {
    private String name;
    private List<Task> tasks;

    public TaskList(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return getTaskListInfo(this);
    }
}
