package ru.skuznetsov;

import ru.skuznetsov.input.ConsoleInput;
import ru.skuznetsov.intefraces.ITaskManager;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Sergey on 05.12.2016.
 */
public class StartUI {
    /**
     * Task, here will be saved result of operations for tests.
     * */
    private static Task testTask = null;
    /**
     * Collection of tasks for tests.
     * */
    private static Task[] tasks = null;
    /**
     * Number of operation.
     * */
    private static String operationNumber = "";
    /**
     * Tracker instance with storage.
     * */
    private static ITaskManager taskManager = null;
    /**
     * Scanner.
     * */
    private static Scanner scanner = null;
    /**
     * Default constructor with operation number  as string.
     * @param operationNumber - operation from 1 - 7
     * */
    public StartUI(String operationNumber) {
        System.setIn(new ByteArrayInputStream(operationNumber.getBytes()));
        scanner = new Scanner(System.in);
        this.operationNumber = operationNumber;
    }
    /**
     * Main method without args.
     * @param args - arguments
     * */
    public static void main(String[] args) {
//        ITaskManager taskManager = new TrackerPolimorth(
//                new StubInput(
//                        new Task[]{new Task("task1", "desc1"),
//                                new Task("task2", "desc2"),
//                                new Task("task3", "desc3")}));
        taskManager = new TrackerPolimorth(new ConsoleInput(args[0]));

        final int exitOperation = 7, add = 1, edit = 2, remove = 3, showAll = 4, showSpec = 5, addComment = 6;
        int userInput = 0;
        while ((userInput = mainMenu()) != exitOperation) {
            switch (userInput) {
                case add:
                    testTask = taskManager.addTask();
                    break;
                case edit:
                    testTask = taskManager.editTaskByName();
                    break;
                case remove:
                    try {
                        taskManager.removeTask();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Unknown name of task");
                    } finally {
                        break;
                    }
                case showAll:
                    tasks = taskManager.getAllTasks();
                    System.out.println(Arrays.toString(tasks));
                    break;
                case showSpec:
                    testTask = taskManager.getTaskByName();
                    System.out.println(testTask);
                    break;
                case addComment:
                    testTask = taskManager.addCommentToTask();
                    break;
                default:
                    System.out.println("Unknown operation");
            }
        }

    }
    /**
     * Main menu.
     * @return user input - choosen operation
     * */
    public static int mainMenu() {
        String userInput = "";
        final int firstNumberOfOperation = 1, numberOfExitOperation = 7;
        int userOperationNumber = 0;
        System.out.println("\n Choose action: \n");
        System.out.println("1. Add task");
        System.out.println("2. Edit task");
        System.out.println("3. Remove task");
        System.out.println("4. Show all tasks");
        System.out.println("5. Show task by name");
        System.out.println("6. Add comment to task");
        System.out.println("7. Exit");
        System.out.print("\nYour input: ");
        userInput = scanner.next();

        try {

            userOperationNumber = Integer.valueOf(userInput);
            if (userOperationNumber < firstNumberOfOperation || userOperationNumber > numberOfExitOperation) {
                mainMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter number of operation from 1 to 7");
            mainMenu();
        }
        return userOperationNumber;
    }
    /**
     * Getter of task manager.
     * @return instance of task manager
     * */
    public static ITaskManager getTaskManager() {
        return taskManager;
    }
    /**
     * Getter of created or modified task.
     * @return task
     * */
    public static Task getTestTask() {
        return testTask;
    }

    /**
     * Getter of tasks returned after operations.
     * @return array with tasks
     * */
    public static Task[] getTasks() {
        return tasks;
    }

}
