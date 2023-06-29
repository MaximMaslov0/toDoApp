package edu.practice.domain.data.taskList.criteria;

public enum TaskSortingCriterion {
    BY_ID,
    BY_NAME,
    BY_DUE_DATE,
    BY_STATUS;

    public static TaskSortingCriterion getTaskSortingCriterion(String criterion) {
        switch (criterion) {
            case "byId" -> {
                return BY_ID;
            }
            case "byName" -> {
                return BY_NAME;
            }
            case "byDueDate" -> {
                return BY_DUE_DATE;
            }
            case "byStatus" -> {
                return BY_STATUS;
            }
            default -> {
                return null;
            }
        }
    }
}
