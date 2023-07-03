package edu.practice.domain.data.taskList;

import edu.practice.domain.data.task.Status;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;
import edu.practice.domain.data.taskList.criteria.TaskToStringCriterion;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean hasIdCopy(int id) {
        return tasks.stream().anyMatch(task -> task.getId() == id);
    }

    @Override
    public boolean isUndoneTask(Task task) {
        return LocalDateTime.now().isAfter(task.getDueDateTime());
    }

    @Override
    public void sort(TaskSortingCriterion taskSortingCriterion) {
        final Comparator<Task> byId = Comparator.comparingInt(Task::getId),
                byName = Comparator.comparing(Task::getName),
                byDueDate = Comparator.comparing(Task::getDueDateTime),
                byStatus = Comparator.comparing(Task::getStatus);

        switch (taskSortingCriterion) {
            case BY_ID -> tasks.sort(byId);
            case BY_NAME -> tasks.sort(byName);
            case BY_DUE_DATE -> tasks.sort(byDueDate);
            case BY_STATUS -> tasks.sort(byStatus);
        }
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public Task searchTask(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Task> searchTask(String name) {
        return tasks.stream().filter(task -> task.getName().contains(name)).
                collect(Collectors.toList());
    }

    @Override
    public String tasksToString(TaskToStringCriterion taskToStringCriterion) {
        final String allTasks = tasks.stream().map(String::valueOf).collect(Collectors.joining("\n")),
                doneTasks = tasks.stream().filter(task -> task.getStatus() == Status.DONE).map(String::valueOf)
                        .collect(Collectors.joining("\n")),
                undoneTasks = tasks.stream().filter(task -> task.getStatus() == Status.STUCK).map(String::valueOf)
                        .collect(Collectors.joining("\n"));

        switch (taskToStringCriterion) {
            case ALL -> {
                return allTasks;
            }
            case DONE -> {
                return doneTasks;
            }
            default -> {
                return undoneTasks;
            }
        }
    }

    @Override
    public void updateTask(Task task, String name, String description, LocalDateTime dueDateTime) {
        task.setName(name);
        task.setDescription(description);
        task.setDueDateTime(dueDateTime);
    }

    @Override
    public void doTask(Task task) {
        task.setStatus(Status.DONE);
    }

    @Override
    public void undoTask(Task task) {
        task.setStatus(Status.STUCK);
    }

    @Override
    public String toString() {
        return getTaskListInfo(this);
    }
}
