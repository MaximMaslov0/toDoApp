package edu.practice.presentation;

import edu.practice.domain.ToDoApp;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;
import edu.practice.domain.data.taskList.criteria.TaskToStringCriterion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static edu.practice.domain.ToDoAppHelper.STRAIGHT_LINE;
import static edu.practice.domain.data.LocalDateTimeAdapter.INPUT_FORMATTER;
import static edu.practice.domain.data.taskList.criteria.TaskSortingCriterion.toTaskSortingCriterion;
import static edu.practice.domain.data.taskList.criteria.TaskToStringCriterion.toTaskToStringCriterion;
import static edu.practice.presentation.Printer.printSomethingAlreadyExistsMessage;

public abstract class Getter {
    public static String getInputString(String description) {
        final Scanner input = new Scanner(System.in);

        System.out.print("\n" + STRAIGHT_LINE + description);

        final String string = input.nextLine();

        System.out.println("—".repeat(143) + "\n");
        return string;
    }

    public static int getInputInt(String description) {
        final Scanner input = new Scanner(System.in);

        System.out.print("\n" + STRAIGHT_LINE + description);
        if (!input.hasNextInt()) throw new InputMismatchException("\nВи ввели невірне значення номеру!");

        final int integer = input.nextInt();

        System.out.println("—".repeat(143) + "\n");
        return integer;
    }

    public static TaskList getInputTaskList(ToDoApp toDoApp) {
        while (true) {
            final String name = getInputString("Введіть назву списку: ");
            final boolean isNameCopy = toDoApp.hasNameCopy(name);

            if (isNameCopy) printSomethingAlreadyExistsMessage("Така назва");
            else return new TaskList(name, new ArrayList<>());
        }
    }

    public static Task getInputTask(TaskList taskList) {
        final List<Task> tasks = taskList.getTasks();
        final int id;

        if (tasks.isEmpty()) id = 1;
        else {
            final Task lastTask = tasks.get(tasks.size() - 1);
            id = lastTask.getId() + 1;
        }

        final String name = getInputString("Введіть назву завдання: "),
                description = getInputString("Введіть опис завдання: ");
        final String dueDateTimeString = getInputString("Введіть термін виконання завдання (в форматі dd-MM-yyyy / HH:mm, наприклад 01-12-2004 / 04:00): ");
        final LocalDateTime dueDateTime = LocalDateTime.parse(dueDateTimeString, INPUT_FORMATTER);

        return new Task(id, name, description, dueDateTime);
    }

    public static List<TaskList> getTaskListsUsingInputName(ToDoApp toDoApp) {
        final String name = getInputString("Введіть назву списку: ");

        return toDoApp.searchTaskList(name);
    }

    public static Task getTaskUsingInputId(TaskList taskList) {
        Task task;

        do {
            final int id = getInputInt("Введіть унікальний номер: ");
            task = taskList.searchTask(id);
        } while (task == null);
        return task;
    }

    public static TaskSortingCriterion getTaskSortingCriterionUsingInputString() {
        final String criterion = getInputString("Введіть критерій сортування: ");

        return toTaskSortingCriterion(criterion);
    }

    public static TaskToStringCriterion getTaskToStringCriterionUsingInputString() {
        final String criterion = getInputString("Введіть критерій показу: ");

        return toTaskToStringCriterion(criterion);
    }
}
