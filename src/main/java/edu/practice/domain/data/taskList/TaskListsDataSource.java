package edu.practice.domain.data.taskList;

import edu.practice.domain.data.JsonConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public record TaskListsDataSource(Path path, JsonConverter jsonConverter) {
    public List<TaskList> readTaskLists() throws IOException {
        final String taskLists = Files.readString(path);
        return jsonConverter.fromJson(taskLists);
    }

    public void writeTaskLists(List<TaskList> taskLists) throws IOException {
        final String jsonTaskLists = jsonConverter.toJson(taskLists);
        Files.writeString(path, jsonTaskLists);
    }
}
