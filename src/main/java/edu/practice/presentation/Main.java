package edu.practice.presentation;


import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final Task task1 = new Task(1, "Task", "description", LocalDateTime.now()),
                task2 = new Task(2, "Task 2", "description 2", LocalDateTime.MIN);

        System.out.println(task1 + " " + task2);

        final List<Task> tasks = new ArrayList<>(List.of(task1, task2));
        TaskList taskList = new TaskList("TaskList", tasks);

        System.out.println(taskList);
    }
}