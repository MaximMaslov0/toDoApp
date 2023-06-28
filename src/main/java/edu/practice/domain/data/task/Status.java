package edu.practice.domain.data.task;

public enum Status {
    WORKING_ON_IT,
    DONE,
    STUCK;

    @Override
    public String toString() {
        switch (this) {
            case WORKING_ON_IT -> {
                return "Працюю над цим...";
            }
            case DONE -> {
                return "Виконано✔";
            }
            default -> {
                return "Застряг";
            }
        }
    }
}
