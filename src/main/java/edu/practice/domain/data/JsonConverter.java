package edu.practice.domain.data;

import edu.practice.domain.data.taskList.TaskList;

import java.util.List;

public interface JsonConverter {
    String toJson(List<TaskList> taskLists);

    List<TaskList> fromJson(String taskLists);
}
