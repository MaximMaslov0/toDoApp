package edu.practice.presentation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.practice.domain.ToDoApp;
import edu.practice.domain.data.GsonConverter;
import edu.practice.domain.data.JsonConverter;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.TaskListsDataSource;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        final Task task1 = new Task(1, "Task", "description", LocalDateTime.now()),
                task2 = new Task(2, "Task 2", "description 2", LocalDateTime.MIN),
                task3 = new Task(3, "Task 3", "description 3", LocalDateTime.MAX),
                task4 = new Task(4, "Task 4", "description 4", LocalDateTime.MIN);
        final List<Task> tasks1 = new ArrayList<>(List.of(task1, task2)),
                tasks2 = new ArrayList<>(List.of(task3, task4));
        final TaskList taskList1 = new TaskList("TaskList1", tasks1),
                taskList2 = new TaskList("TaskList21", tasks2);
        final List<TaskList> taskLists = new ArrayList<>(List.of(taskList2));
        final Path path = Path.of("test");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonConverter jsonConverter = new GsonConverter(gson);
        final TaskListsDataSource taskListsDataSource = new TaskListsDataSource(path, jsonConverter);
        final ToDoApp toDoApp = new ToDoApp(taskListsDataSource, taskLists);

        System.out.println(toDoApp.hasNameCopy("TaskList1"));
        toDoApp.addTaskList(taskList1);
        System.out.println(toDoApp.allTaskListsToString());
        toDoApp.sortByName();
        System.out.println(toDoApp.allTaskListsToString());
        toDoApp.removeTaskList(taskList2);
        System.out.println(toDoApp.allTaskListsToString());
        toDoApp.addTaskList(taskList2);
        System.out.println(toDoApp.searchTaskList("Task").stream().map(String::valueOf)
                .collect(Collectors.joining()));
        toDoApp.renameTaskList(taskList2, "TaskList2");
        System.out.println(toDoApp.allTaskListsToString());

        final Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Введіть команду: ");
            command = scanner.nextLine();
            switch (command) {
                case "" -> System.out.println();
                case "exit" -> System.out.println("\nВи вийшли із програми!\n");
                default -> System.out.println("\nВи ввели невірну команду!\n");
            }
        } while (command.equals("exit"));
    }
}