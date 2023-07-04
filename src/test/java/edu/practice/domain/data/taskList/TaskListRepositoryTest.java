package edu.practice.domain.data.taskList;

import edu.practice.domain.data.task.Status;
import edu.practice.domain.data.task.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static edu.practice.domain.data.task.Status.STUCK;
import static edu.practice.domain.data.taskList.criteria.TaskSortingCriterion.*;
import static edu.practice.domain.data.taskList.criteria.TaskToStringCriterion.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskListRepositoryTest {
    final Task test1Task = new Task(1, "name", "description",
            LocalDateTime.of(2022, 7, 5, 11, 0)),
            test2Task = new Task(2, "education", "practice",
                    LocalDateTime.of(2023, 7, 4, 9, 0)),
            test3Task = new Task(4, "test name", "test description",
                    LocalDateTime.of(2023, 7, 5, 11, 0)),
            test4Task = new Task(3, "дрова", "пилити, колоти, возити",
                    LocalDateTime.of(2023, 8, 1, 15, 0)),
            test5Task = new Task(6, "картошка", "копати",
                    LocalDateTime.of(2024, 1, 1, 1, 1));
    final TaskList testTaskList = new TaskList("test",
            new ArrayList<>(List.of(test1Task, test2Task, test3Task, test4Task)));
    final List<Task> tasks = testTaskList.getTasks();

    @Test
    void test1HasIdCopy() {
        final boolean isIdCopy = testTaskList.hasIdCopy(1);

        assertTrue(isIdCopy);
    }

    @Test
    void test2HasIdCopy() {
        final boolean isIdCopy = testTaskList.hasIdCopy(5);

        assertFalse(isIdCopy);
    }

    @Test
    void test1IsUndoneTask() {
        final boolean isUndoneTask = testTaskList.isUndoneTask(test1Task);

        assertTrue(isUndoneTask);
    }

    @Test
    void test2IsUndoneTask() {
        final boolean isUndoneTask = testTaskList.isUndoneTask(test4Task);

        assertFalse(isUndoneTask);
    }

    @Test
    void test1Sort() {
        testTaskList.sort(BY_NAME);

        final List<Task> expected = List.of(test2Task, test1Task, test3Task, test4Task);

        assertEquals(expected, tasks);
    }

    @Test
    void test2Sort() {
        testTaskList.sort(BY_ID);

        final List<Task> expected = List.of(test1Task, test2Task, test4Task, test3Task);

        assertEquals(expected, tasks);
    }

    @Test
    void test3Sort() {
        testTaskList.sort(BY_STATUS);

        final List<Task> expected = List.of(test1Task, test2Task, test3Task, test4Task);

        assertEquals(expected, tasks);
    }

    @Test
    void test4Sort() {
        testTaskList.sort(BY_DUE_DATE);

        final List<Task> expected = List.of(test1Task, test2Task, test3Task, test4Task);

        assertEquals(expected, tasks);
    }

    @Test
    void testAddTask() {
        testTaskList.addTask(test5Task);

        final List<Task> expected = List.of(test1Task, test2Task, test3Task, test4Task, test5Task);

        assertEquals(expected, tasks);
    }

    @Test
    void testRemoveTask() {
        testTaskList.removeTask(test5Task);

        final List<Task> expected = List.of(test1Task, test2Task, test3Task, test4Task);

        assertEquals(expected, tasks);
    }

    @Test
    void test1SearchTask() {
        final Task task = testTaskList.searchTask(1);

        assertEquals(test1Task, task);
    }

    @Test
    void test2SearchTask() {
        final List<Task> tasks1 = testTaskList.searchTask("name");

        final List<Task> expected = List.of(test1Task, test3Task);

        assertEquals(expected, tasks1);
    }

    @Test
    void test1TasksToString() {
        final String string = testTaskList.tasksToString(ALL),
                expected = test1Task + "\n" + test2Task + "\n" + test3Task + "\n" + test4Task;

        assertEquals(expected, string);
    }

    @Test
    void test2TasksToString() {
        final String string = testTaskList.tasksToString(DONE),
                expected = "";

        assertEquals(expected, string);
    }

    @Test
    void test3TasksToString() {
        final String string = testTaskList.tasksToString(UNDONE);

        assertEquals("", string);
    }

    @Test
    void testUpdateTask() {
        testTaskList.updateTask(test3Task, "my Task", "none",
                LocalDateTime.of(2024, 1, 2, 3, 4));
        assertEquals("my Task", test3Task.getName());
        assertEquals("none", test3Task.getDescription());
        assertEquals(LocalDateTime.of(2024, 1, 2, 3, 4), test3Task.getDueDateTime());
    }

    @Test
    void testDoTask() {
        testTaskList.doTask(test4Task);
        assertEquals(Status.DONE, test4Task.getStatus());
    }

    @Test
    void undoTask() {
        testTaskList.undoTask(test5Task);
        assertEquals(STUCK, test5Task.getStatus());
    }
}