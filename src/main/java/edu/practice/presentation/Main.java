package edu.practice.presentation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.practice.domain.ToDoApp;
import edu.practice.domain.data.GsonConverter;
import edu.practice.domain.data.JsonConverter;
import edu.practice.domain.data.LocalDateTimeAdapter;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.TaskListsDataSource;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;
import edu.practice.domain.data.taskList.criteria.TaskToStringCriterion;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static edu.practice.domain.data.LocalDateTimeAdapter.INPUT_FORMATTER;
import static edu.practice.presentation.Getter.*;
import static edu.practice.presentation.Printer.*;

public class Main {
    public static void main(String[] args) {
        final String absolutePathString = "C:\\Maxim Maslov\\projects\\2023\\java\\my projects\\toDoApp\\src\\main\\java\\edu\\practice\\domain\\data\\taskList\\taskListsData.json";
        final Gson gson = new GsonBuilder().setPrettyPrinting().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        final Path path = Path.of(absolutePathString);
        final JsonConverter jsonConverter = new GsonConverter(gson);
        final TaskListsDataSource taskListsDataSource = new TaskListsDataSource(path, jsonConverter);
        final List<TaskList> taskLists;
        String command;

        try {
            if (taskListsDataSource.readTaskLists() == null) {
                taskLists = new ArrayList<>();

                printWelcomeText();
                do {
                    command = getInputString("Введіть команду: ");

                    if (command.equals("/addTaskList")) {
                        final String name = getInputString("Введіть назву списку: ");
                        final TaskList taskList = new TaskList(name, new ArrayList<>());

                        taskLists.add(taskList);
                        printFirstTaskListMessage(taskList);
                    } else printIncorrectCommandMessage();
                } while (!command.equals("/addTaskList"));
            } else taskLists = taskListsDataSource.readTaskLists();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final ToDoApp toDoApp = new ToDoApp(taskListsDataSource, taskLists);

        do {
            System.out.println("\n" + toDoApp.getNotificationsMessageAndUndoTasks() + "\n");

            command = getInputString("Введіть команду: ");

            switch (command) {
                //tested
                case "/sortTaskLists" -> {
                    toDoApp.sortByName();
                    printSortingTaskListsByNameMessage();
                }
                //tested
                case "/addTaskList" -> {
                    final TaskList taskList = getInputTaskList(toDoApp);

                    toDoApp.addTaskList(taskList);
                    printAddingOrRemovingTaskListMessage("додали", taskList);
                }
                //tested
                case "/removeTaskList" -> {
                    while (true) {
                        final List<TaskList> taskListsUsingSearch = getTaskListsUsingInputName(toDoApp);

                        if (taskListsUsingSearch.size() != 1)
                            printSearchingSeveralOrNoneTaskListMessage(taskListsUsingSearch,
                                    "\nБудь ласка, введіть повну назву, щоб видалити конкретний список!");
                        else {
                            final TaskList taskList = taskListsUsingSearch.get(0);

                            toDoApp.removeTaskList(taskList);
                            printAddingOrRemovingTaskListMessage("видалили", taskList);
                            break;
                        }
                    }
                }
                //tested
                case "/searchTaskList" -> {
                    final List<TaskList> taskListsUsingSearch = getTaskListsUsingInputName(toDoApp);

                    printSearchingSeveralOrNoneTaskListMessage(taskListsUsingSearch, "");
                }
                //tested
                case "/showAllTaskLists" -> System.out.print("\n" + toDoApp.allTaskListsToString());
                //tested
                case "/processTaskList" -> {
                    while (true) {
                        final List<TaskList> taskListsUsingSearch = getTaskListsUsingInputName(toDoApp);

                        if (taskListsUsingSearch.size() != 1)
                            printSearchingSeveralOrNoneTaskListMessage(taskListsUsingSearch,
                                    "\nБудь ласка, введіть повну назву, щоб вибрати конкретний список!");
                        else {
                            final TaskList taskList = taskListsUsingSearch.get(0);
                            String taskCommand;

                            printProcessingTaskListMessage(taskList);
                            do {
                                System.out.println(toDoApp.getNotificationsMessageAndUndoTasks());

                                taskCommand = getInputString("Введіть команду для опрацювання завдань (/backToMain - повернутись в головне меню): ");

                                switch (taskCommand) {
                                    //tested
                                    case "/sortTasks" -> {
                                        while (true) {
                                            final TaskSortingCriterion taskSortingCriterion = getTaskSortingCriterionUsingInputString();

                                            if (taskSortingCriterion != null) {
                                                taskList.sort(taskSortingCriterion);
                                                printSortingTasksMessage(taskSortingCriterion);
                                                break;
                                            } else printIncorrectCriterionMessage();
                                        }
                                    }
                                    //tested
                                    case "/addTask" -> {
                                        final Task task = getInputTask(taskList);

                                        taskList.addTask(task);
                                        printAddingOrRemovingTaskMessage("додали", task);
                                    }
                                    //tested
                                    case "/removeTask" -> {
                                        final Task task = getTaskUsingInputId(taskList);

                                        taskList.removeTask(task);
                                        printAddingOrRemovingTaskMessage("видалили", task);
                                    }
                                    //tested
                                    case "/searchTask" -> {
                                        while (true) {
                                            final String criterion = getInputString("Введіть критерій пошуку: ");

                                            if (criterion.equals("byId")) {
                                                final int id = getInputInt("Введіть унікальний номер: ");
                                                final Task task = taskList.searchTask(id);

                                                System.out.print("\n\nРезультат пошуку:" + task);
                                                break;
                                            } else if (criterion.equals("byName")) {
                                                final String name = getInputString("Введіть назву: ");
                                                final List<Task> tasks = taskList.searchTask(name);

                                                printSearchingSeveralOrNoneTaskMessage(tasks, "");
                                                break;
                                            } else printIncorrectCriterionMessage();
                                        }
                                    }
                                    //tested
                                    case "/showTasks" -> {
                                        while (true) {
                                            final TaskToStringCriterion taskToStringCriterion =
                                                    getTaskToStringCriterionUsingInputString();

                                            if (taskToStringCriterion != null) {
                                                System.out.println("\n" + taskList.tasksToString(taskToStringCriterion));
                                                break;
                                            } else printIncorrectCriterionMessage();
                                        }
                                    }
                                    //tested
                                    case "/updateTask" -> {
                                        final Task task = getTaskUsingInputId(taskList);
                                        final String name = getInputString("Введіть нове ім'я: "),
                                                description = getInputString("Введіть нове ім'я: "),
                                                dueDateTimeString = getInputString("Введіть новий термін виконання завдання (в форматі dd-MM-yyyy / HH:mm, наприклад 01-12-2004 / 04:00): ");
                                        final LocalDateTime dueDateTime = LocalDateTime.parse(dueDateTimeString, INPUT_FORMATTER);

                                        taskList.updateTask(task, name, description, dueDateTime);
                                        System.out.print("\nВи оновили завдання #" + task.getId() + '!');
                                    }
                                    //tested
                                    case "/doTask" -> {
                                        final Task task = getTaskUsingInputId(taskList);

                                        taskList.doTask(task);
                                        System.out.println("\nВи виконали завдання #" + task.getId() + "!");
                                    }
                                    //tested
                                    case "/backToMain" -> System.out.print("\nВи повернулися до головного меню");
                                    default -> printIncorrectCommandMessage();
                                }
                            } while (!taskCommand.equals("/backToMain"));
                            break;
                        }
                    }
                }
                //tested
                case "/renameTaskList" -> {
                    while (true) {
                        final List<TaskList> taskListsUsingSearch = getTaskListsUsingInputName(toDoApp);

                        if (taskListsUsingSearch.size() != 1)
                            printSearchingSeveralOrNoneTaskListMessage(taskListsUsingSearch,
                                    "\nБудь ласка, введіть повну назву, щоб редагувати назву конкретного списку!");
                        else {
                            final TaskList taskList = taskListsUsingSearch.get(0);
                            final String oldName = taskList.getName();

                            while (true) {
                                final String newName = getInputString("Введіть нову назву списку: ");
                                final boolean isNameCopy = toDoApp.hasNameCopy(newName);

                                if (isNameCopy) printSomethingAlreadyExistsMessage("Така назва");
                                else {
                                    toDoApp.renameTaskList(taskList, newName);
                                    printRenamingTaskListMessage(taskList, oldName);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                //tested
                case "/exit" -> printExitText();
                //tested
                default -> printIncorrectCommandMessage();
            }
        }
        while (!command.equals("/exit"));
        try {
            toDoApp.saveChanges();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}