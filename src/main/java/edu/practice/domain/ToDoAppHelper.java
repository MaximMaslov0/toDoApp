package edu.practice.domain;

import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;

import java.util.stream.Collectors;

import static edu.practice.domain.data.LocalDateTimeAdapter.OUTPUT_FORMATTER;

public abstract class ToDoAppHelper {
    public static final String STRAIGHT_LINE = "\n" + "—".repeat(143) + "\n",
            STAR_LINE = "\n" + "★".repeat(107) + "\n",
            MIXED_LINE = "\n" + "——————------".repeat(12) + "\n";

    public static String getTaskInfo(Task task) {
        return STRAIGHT_LINE +
                "Завдання #" + task.getId() + MIXED_LINE +
                "Назва: " + task.getName() + MIXED_LINE +
                "Опис: " + task.getDescription() + MIXED_LINE +
                "Термін виконання: " + OUTPUT_FORMATTER.format(task.getDueDateTime()) + MIXED_LINE +
                "Статус: " + task.getStatus() +
                STRAIGHT_LINE;
    }

    public static String getTaskListInfo(TaskList taskList) {
        return taskList.getTasks().stream().map(String::valueOf)
                .collect(Collectors.joining("", STAR_LINE + "\n" + taskList.getName() + "\n", STAR_LINE));
    }
}
