package edu.practice.domain.data.taskList;

import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;
import edu.practice.domain.data.taskList.criteria.TaskToStringCriterion;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskListRepository {
    boolean hasIdCopy(int id);

    void sort(TaskSortingCriterion taskSortingCriterion);

    void addTask(Task task);

    void removeTask(Task task);

    Task searchTask(int id);

    List<Task> searchTask(String name);

    String tasksToString(TaskToStringCriterion taskToStringCriterion);

    void updateTask(Task task, String name, String description, LocalDateTime dueDate);

    void doTask(Task task);

    void undoTask(Task task);
}
