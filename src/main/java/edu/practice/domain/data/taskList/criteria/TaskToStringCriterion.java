package edu.practice.domain.data.taskList.criteria;

public enum TaskToStringCriterion {
    ALL,
    DONE,
    UNDONE;

    public static TaskToStringCriterion toTaskToStringCriterion(String taskToStringCriterion) {
        switch (taskToStringCriterion) {
            case "all" -> {
                return ALL;
            }
            case "done" -> {
                return DONE;
            }
            case "undone" -> {
                return UNDONE;
            }
            default -> {
                return null;
            }
        }
    }
}
