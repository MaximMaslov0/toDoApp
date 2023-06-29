package edu.practice.presentation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.practice.domain.ToDoApp;
import edu.practice.domain.data.GsonConverter;
import edu.practice.domain.data.JsonConverter;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.TaskListsDataSource;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;
import edu.practice.domain.data.taskList.criteria.TaskToStringCriterion;

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

        System.out.println(taskList1.hasIdCopy(2));
        taskList1.addTask(task3);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));
        taskList1.undoTask(task1);
        taskList1.doTask(task3);
        taskList1.updateTask(task2, "test name", "test description", LocalDateTime.now());
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));
        taskList1.sort(TaskSortingCriterion.BY_NAME);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));
        taskList1.sort(TaskSortingCriterion.BY_STATUS);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));
        taskList1.sort(TaskSortingCriterion.BY_ID);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));
        taskList1.sort(TaskSortingCriterion.BY_DUE_DATE);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));

        final String tasksUsingSearch = taskList1.searchTask("Task").stream()
                .map(String::valueOf).collect(Collectors.joining());

        System.out.println(taskList1.searchTask(1) + "\n" + tasksUsingSearch +
                taskList1.tasksToString(TaskToStringCriterion.DONE) + taskList1.tasksToString(TaskToStringCriterion.UNDONE));
        taskList1.removeTask(task1);
        System.out.println(taskList1.tasksToString(TaskToStringCriterion.ALL));

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