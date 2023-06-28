package edu.practice.domain.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.practice.domain.data.taskList.TaskList;

import java.util.List;

public record GsonConverter(Gson gson) implements JsonConverter {
    @Override
    public String toJson(List<TaskList> taskLists) {
        return gson.toJson(taskLists);
    }

    @Override
    public List<TaskList> fromJson(String taskLists) {
        TypeToken<List<TaskList>> taskListsType = new TypeToken<>() {
        };

        return gson.fromJson(taskLists, taskListsType);
    }
}
