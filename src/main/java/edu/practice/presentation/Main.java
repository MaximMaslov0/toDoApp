package edu.practice.presentation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.practice.domain.ToDoApp;
import edu.practice.domain.data.GsonConverter;
import edu.practice.domain.data.JsonConverter;
import edu.practice.domain.data.LocalDateTimeAdapter;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.TaskListsDataSource;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static edu.practice.presentation.io.Input.getInputString;
import static edu.practice.presentation.io.Output.*;

public class Main {
    public static void main(String[] args) {
        final Path path = Path.of("C:\\Maxim Maslov\\projects\\2023\\java\\my projects\\toDoApp\\src\\main\\java\\edu\\practice\\domain\\data\\taskList\\taskListsData.json");
        final Gson gson = new GsonBuilder().setPrettyPrinting().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        final JsonConverter jsonConverter = new GsonConverter(gson);
        final TaskListsDataSource taskListsDataSource = new TaskListsDataSource(path, jsonConverter);
        final List<TaskList> taskLists;
        String command;

        try {
            if (taskListsDataSource.readTaskLists() == null) {
                taskLists = new ArrayList<>();

                System.out.println(WELCOME_TEXT);
                do {
                    command = getInputString("Введіть команду: ");

                    if (command.equals("/addTaskList")) {
                        final String name = getInputString("Введіть назву списку: ");
                        final TaskList taskList = new TaskList(name, new ArrayList<>());

                        taskLists.add(taskList);
                        System.out.println("\nВи успішно створили свій перший список з назвою \"" + name + "\"!!!");
                    } else System.out.println(INCORRECT_COMMAND_MESSAGE);
                } while (!command.equals("/addTaskList"));
            } else taskLists = taskListsDataSource.readTaskLists();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final ToDoApp toDoApp = new ToDoApp(taskListsDataSource, taskLists);

        do {
            command = getInputString("Введіть команду: ");

            switch (command) {
                case "/sortTaskLists" -> {
                    toDoApp.sortByName();
                    System.out.println("\nСписки посортовано за назвою");
                }
                case "/addTaskList" -> {
                    String name = "";

                    while (!name.equals("/backToMain")) {
                        name = getInputString("Введіть назву списку (/backToMain - повернутись до головного меню): ");
                        final boolean isNameCopy = toDoApp.hasNameCopy(name);

                        if (isNameCopy) System.out.println("\nТака назва вже існує!!!");
                        else {
                            final TaskList taskList = new TaskList(name, new ArrayList<>());

                            toDoApp.addTaskList(taskList);
                            System.out.println("\nВи додали список з назвою \"" + name + "\"!!!");
                            break;
                        }
                    }
                }
                case "/removeTaskList" -> {
                    String fullName = "";

                    while (!fullName.equals("/backToMain")) {
                        fullName = getInputString("Введіть повну назву списку (/backToMain - повернутись до головного меню): ");
                        final List<TaskList> taskListsUsingSearch = toDoApp.searchTaskList(fullName);

                        if (taskListsUsingSearch.size() > 1) {
                            final String taskListsUsingSearchToString = taskListsUsingSearch.stream()
                                    .map(String::valueOf).collect(Collectors.joining());

                            System.out.println("\nЗнайдено кілька списків (" +
                                    taskListsUsingSearch.size() + "), які містять таку назву:\n"
                                    + taskListsUsingSearchToString +
                                    "\nБудь ласка, введіть повну назву, щоб видалити конкретний список!!!");
                        } else if (taskListsUsingSearch.size() == 1) {
                            final TaskList taskList = taskListsUsingSearch.get(0);

                            toDoApp.removeTaskList(taskList);
                            System.out.println("\nВи видалили список з назвою \"" + taskList.getName() + "\"!!!");
                            break;
                        } else
                            System.out.println("\nНе знайдено список з такою назвою...\nБудь ласка, введіть повну назву, щоб видалити конкретний список!!!");
                    }
                }
                case "/searchTaskList" -> {
                    final String name = getInputString("Введіть назву списку: ");
                    final List<TaskList> taskListsUsingSearch = toDoApp.searchTaskList(name);

                    if (taskListsUsingSearch.size() > 1) {
                        final String taskListsUsingSearchToString = taskListsUsingSearch.stream()
                                .map(String::valueOf).collect(Collectors.joining());

                        System.out.println("\nЗнайдено кілька списків (" +
                                taskListsUsingSearch.size() + "), які містять таку назву: "
                                + taskListsUsingSearchToString);
                    } else {
                        final TaskList taskList = taskLists.get(0);

                        System.out.println("\nРезультат пошуку: " + taskList);
                    }
                }
                case "/showAllTaskLists" -> System.out.println("\n" + toDoApp.allTaskListsToString());
                case "/processTaskList" -> {
                    String fullName = "";

                    while (!fullName.equals("/backToMain")) {
                        fullName = getInputString("Введіть повну назву списку (/backToMain - повернутись до головного меню): ");
                        final List<TaskList> taskListsUsingSearch = toDoApp.searchTaskList(fullName);

                        if (taskListsUsingSearch.size() > 1) {
                            final String taskListsUsingSearchToString = taskListsUsingSearch.stream()
                                    .map(String::valueOf).collect(Collectors.joining());

                            System.out.println("\nЗнайдено кілька списків (" +
                                    taskListsUsingSearch.size() + "), які містять таку назву:\n"
                                    + taskListsUsingSearchToString +
                                    "\nБудь ласка, введіть повну назву, щоб видалити конкретний список!!!");
                        } else {
                            final TaskList taskList = taskListsUsingSearch.get(0);
                            String taskCommand;

                            System.out.println("\nВи вибрали список з назвою \"" + taskList.getName() + "\" для опрацювання завдань!");

                            do {
                                taskCommand = getInputString("Введіть команду для опрацювання завдань (/backToMain - повернутись в головне меню): ");
                                switch (taskCommand) {
                                    case "/sortTasks" -> {

                                    }
                                    case "/addTasks" -> {

                                    }
                                    case "/removeTask" -> {

                                    }
                                    case "/searchTask" -> {

                                    }
                                    case "/showTasks" -> {

                                    }
                                    case "/updateTask" -> {

                                    }
                                    case "/doTask" -> {

                                    }
                                }
                            } while (!taskCommand.equals("/backToMain"));
                            break;
                        }
                    }
                }
                case "/renameTaskList" -> {
                    String fullName = "";

                    while (!fullName.equals("/backToMain")) {
                        fullName = getInputString("Введіть повну назву списку (/backToMain - повернутись до головного меню): ");
                        final List<TaskList> taskListsUsingSearch = toDoApp.searchTaskList(fullName);

                        if (taskListsUsingSearch.size() > 1) {
                            final String taskListsUsingSearchToString = taskListsUsingSearch.stream()
                                    .map(String::valueOf).collect(Collectors.joining());

                            System.out.println("\nЗнайдено кілька списків (" +
                                    taskListsUsingSearch.size() + "), які містять таку назву:\n"
                                    + taskListsUsingSearchToString +
                                    "\nБудь ласка, введіть повну назву, щоб редагувати назву конкретного списку!!!");
                        } else if (taskListsUsingSearch.size() == 1) {
                            final TaskList taskList = taskListsUsingSearch.get(0);
                            String newName = "";

                            while (!newName.equals("/backToMain")) {
                                newName = getInputString("Введіть нову назву списку (/backToMain - повернутись до головного меню): ");
                                final boolean isNameCopy = toDoApp.hasNameCopy(newName);

                                if (isNameCopy) System.out.println("\nТака назва вже існує!!!");
                                else {
                                    toDoApp.renameTaskList(taskList, newName);
                                    System.out.println("\nВи редагували назву списку: \"" + taskList.getName() + "\" -> \"" + newName + "\"!!!");
                                    break;
                                }
                            }
                            break;
                        } else
                            System.out.println("\nНе знайдено список з такою назвою...\nБудь ласка, введіть повну назву, щоб видалити конкретний список!!!");
                    }
                }
                case "/exit" -> System.out.println(EXIT_MESSAGE);
                default -> System.out.println(INCORRECT_COMMAND_MESSAGE);
            }
        } while (!command.equals("/exit"));
        try {
            toDoApp.saveChanges();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}