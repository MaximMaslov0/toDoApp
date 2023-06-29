package edu.practice.domain;

import edu.practice.domain.data.taskList.TaskList;

import java.io.IOException;
import java.util.List;

public interface ToDoAppRepository {
    boolean hasNameCopy(String name);

    void sortByName();

    void addTaskList(TaskList taskList);

    void removeTaskList(TaskList taskList);

    List<TaskList> searchTaskList(String name);

    String allTaskListsToString();

    void renameTaskList(TaskList taskList, String name);

    void saveChanges() throws IOException;
}
