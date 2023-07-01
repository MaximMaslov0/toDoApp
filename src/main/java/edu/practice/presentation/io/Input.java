package edu.practice.presentation.io;

import java.util.Scanner;

import static edu.practice.domain.ToDoAppHelper.STRAIGHT_LINE;

public abstract class Input {
    public static String getInputString(String description) {
        final Scanner inputCommand = new Scanner(System.in);

        System.out.print("\n" + STRAIGHT_LINE + description);

        final String command = inputCommand.nextLine();

        System.out.println("—".repeat(143) + "\n");
        return command;
    }
}
