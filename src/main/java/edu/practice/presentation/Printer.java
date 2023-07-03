package edu.practice.presentation;

import edu.practice.domain.data.task.Task;
import edu.practice.domain.data.taskList.TaskList;
import edu.practice.domain.data.taskList.criteria.TaskSortingCriterion;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Printer {
    private static final String WELCOME_TEXT = """




            Вітаємо Вас вперше в програмі ToDoApp!!!
            Оскільки Ви запустили програму, це значить, що Ви прочитали файл README.md
            Якщо ж Ви цього не зробили, то обов'язково детально ознайомтесь з цим файлом
            ...
            Для початку роботи, давайте створимо перший список завдань!
            Щоб зробити це, введіть команду /addTaskList
            Інші команди можна дізнатися також за допомогою файла README.md
                            
            """, EXIT_TEXT = "\n\nВи вийшли із програми!\n\n",
            INCORRECT_COMMAND_MESSAGE = "\n\nВи ввели невірну команду!";

    public static void printWelcomeText() {
        System.out.println(WELCOME_TEXT);
    }

    public static void printFirstTaskListMessage(TaskList firstTaskList) {
        System.out.println("\nВи успішно створили свій перший список з назвою \"" + firstTaskList.getName() + "\"!!!");
    }

    public static void printExitText() {
        System.out.println(EXIT_TEXT);
    }

    public static void printIncorrectCommandMessage() {
        System.out.println(INCORRECT_COMMAND_MESSAGE);
    }

    public static void printIncorrectCriterionMessage() {
        System.out.println("\nВи ввели невірний критерій!");
    }

    public static void printIncorrectIntMessage() {
        System.out.println("\nВи ввели невірний номер!");
    }

    public static void printSortingTaskListsByNameMessage() {
        System.out.print("\nСписки посортовано за назвою!");
    }

    public static void printSortingTasksMessage(TaskSortingCriterion taskSortingCriterion) {
        switch (taskSortingCriterion) {
            case BY_ID -> System.out.print("\nЗавдання відсортовано за унікальним номером");
            case BY_NAME -> System.out.print("\nЗавдання відсортовано за назвою");
            case BY_STATUS -> System.out.print("\nЗавдання відсортовано за статусом");
            case BY_DUE_DATE -> System.out.print("\nЗавдання відсортовано за терміном виконання");
        }
    }

    public static void printAddingOrRemovingTaskListMessage(String addingOrRemovingString, TaskList taskList) {
        System.out.println("\n\nВи " + addingOrRemovingString + " список з назвою \"" + taskList.getName() + "\"!");
    }

    public static void printAddingOrRemovingTaskMessage(String addingOrRemovingString, Task task) {
        System.out.println("\n\nВи " + addingOrRemovingString + " завдання #" + task.getId() + "!");
    }

    public static void printSearchingSeveralTaskListMessage(List<TaskList> taskLists, String searchCause) {
        final String taskListsToString = taskLists.stream()
                .map(String::valueOf).collect(Collectors.joining());

        System.out.println("\nЗнайдено кілька списків (" +
                taskLists.size() + "), які містять таку назву:\n"
                + taskListsToString + searchCause);
    }

    public static void printSearchingSeveralTaskMessage(List<Task> tasks, String searchCause) {
        final String tasksToString = tasks.stream()
                .map(String::valueOf).collect(Collectors.joining());

        System.out.println("\nЗнайдено кілька завдань (" +
                tasks.size() + "), які містять таку назву:\n"
                + tasksToString + searchCause);
    }


    public static void printSearchingSeveralOrNoneTaskListMessage(List<TaskList> taskLists, String searchCause) {
        if (taskLists.size() > 1) printSearchingSeveralTaskListMessage(taskLists, searchCause);
        else if (taskLists.isEmpty()) System.out.print("\nНе знайдено список з такою назвою..." + searchCause);
    }

    public static void printSearchingSeveralOrNoneTaskMessage(List<Task> tasks, String searchCause) {
        if (tasks.size() > 1) printSearchingSeveralTaskMessage(tasks, searchCause);
        else if (tasks.isEmpty()) System.out.println("\nНе знайдено завдання з такою назвою..." + searchCause);
    }

    public static void printProcessingTaskListMessage(TaskList taskList) {
        System.out.println("\n\nВи вибрали список з назвою \"" + taskList.getName() + "\" для опрацювання завдань!");
    }

    public static void printRenamingTaskListMessage(TaskList taskList, String oldName) {
        System.out.print("\nВи відредагували назву списку: \"" + oldName + "\" -> \"" + taskList.getName() + "\"!");
    }

    public static void printSomethingAlreadyExistsMessage(String something) {
        System.out.println("\n" + something + " вже існує!!!");
    }

}
