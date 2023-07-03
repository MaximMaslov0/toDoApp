package edu.practice.domain;

import edu.practice.domain.data.task.Status;
import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.TaskListsDataSource;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record ToDoApp(TaskListsDataSource taskListsDataSource, List<TaskList> taskLists) implements ToDoAppRepository {
    @Override
    public boolean hasNameCopy(String name) {
        return taskLists.stream().anyMatch(taskList -> taskList.getName().equalsIgnoreCase(name));
    }

    @Override
    public void sortByName() {
        final Comparator<TaskList> byName = Comparator.comparing(TaskList::getName);

        taskLists.sort(byName);
    }

    @Override
    public void addTaskList(TaskList taskList) {
        taskLists.add(taskList);
    }

    @Override
    public void removeTaskList(TaskList taskList) {
        taskLists.remove(taskList);
    }

    @Override
    public List<TaskList> searchTaskList(String name) {
        return taskLists.stream().filter(taskList -> taskList.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public String allTaskListsToString() {
        return taskLists.stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }

    @Override
    public void renameTaskList(TaskList taskList, String name) {
        taskList.setName(name);
    }

    @Override
    public String getNotificationsMessageAndUndoTasks() {
        StringBuilder notificationsMessage = new StringBuilder();
        taskLists.forEach(taskList -> {
            final List<Task> tasks = taskList.getTasks();

            tasks.stream().filter(task -> task.getStatus() == Status.WORKING_ON_IT && taskList.isUndoneTask(task))
                    .forEach(task -> {
                        taskList.undoTask(task);
                        notificationsMessage.append("\n\nУвага!!! Ви застрягли на завданні #").append(task.getId())
                                .append(" в списку із назвою \"").append(taskList.getName()).append("\"!");
                    });
        });
        return notificationsMessage.toString();
    }

    @Override
    public void saveChanges() throws IOException {
        taskListsDataSource.writeTaskLists(taskLists);
    }
}
