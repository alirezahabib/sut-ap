package ir.habibz;


import ir.habibz.model.Task;
import ir.habibz.model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<MenuItem> stack = new ArrayList<>();
    private static User currentUser;
    private static Task currentTask;
    private static boolean quit = false;

    public static void setScannerForTest(Scanner newScanner) {
        scanner = newScanner;
    }

    public static void run() {
        System.out.println("Welcome to AP-Trello. A command-line based project manager.");
        stack.add(MenuItem.MAIN_MENU);
        MenuItem.MAIN_MENU.run();
    }

    private static Integer safeParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static void menu(MenuItem... items) {

        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + " - " + items[i].title);
        }
        printStack();

        boolean isValidIndex = false;
        Integer itemIndex = 0;
        while (!isValidIndex) {
            String input = input("Please choose an item by it's index: ");

            itemIndex = safeParseInt(input);
            if (itemIndex != null) {
                if (itemIndex >= items.length || itemIndex < 0) {
                    System.out.println("Number must be between 0 and " + (items.length - 1));
                } else isValidIndex = true;
            } else {
                error("\"" + input + "\" is not a valid number");
            }

        }
        stack.add(items[itemIndex]);
        printStack();
        items[itemIndex].run();
        stack.remove(stack.size() - 1);
    }

    private static void printStack() {
        System.out.print("\n# You are here:");
        stack.forEach(menuItem -> System.out.print(" -> " + menuItem.title));
        System.out.println();
    }

    private static String input(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static void error(String message) {
        System.out.println("!! " + message);
    }

    private static boolean isValidPassword(String password) {
        if (Pattern.compile("^[0-9]{1,3}[A-Z][a-z]{5,10}$").matcher(password).matches()) {
            return true;
        }
        error("Password must be in format of " +
                "[one to three digit number][a capital letter][5-10 lowercase letters]");
        return false;
    }

    public static boolean isValidDateAndTime(String dateAndTime) {
        Matcher dateMatcher = Pattern.compile("^([0-9]+)/([0-9]{2})/([0-9]{2}) ([0-9]{2}):([0-9]{2}):([0-9]{2})$")
                .matcher(dateAndTime);
        if (!dateMatcher.matches()) return false;

        int month = Integer.parseInt(dateMatcher.group(2));
        int day = Integer.parseInt(dateMatcher.group(3));
        int hour = Integer.parseInt(dateMatcher.group(4));
        int minute = Integer.parseInt(dateMatcher.group(5));
        int second = Integer.parseInt(dateMatcher.group(6));

        if (month > 12 || month < 1 || day > 31 || day < 1 || hour > 23 || hour < 0 ||
                minute > 59 || minute < 0 || second > 59 || second < 0) return false;
        return month <= 6 || day != 31;
    }


    enum MenuItem {
        SEE_ALL_TASKS("See all tasks") {
            @Override
            void run() {
                Task.printAllTasks();
                menu(BACK, SEE_A_TASK);
            }
        },

        SEE_A_TASK("See a task") {
            @Override
            void run() {
                String input = input("Enter the task ID or title: ");
                Integer taskID = safeParseInt(input);
                if (taskID != null) {
                    currentTask = Task.getTask(taskID);
                } else {
                    currentTask = Task.getTask(input);
                }

                if (currentTask == null) {
                    error("Couldn't find a task with this ID or title");
                    return;
                }
                System.out.println(currentTask);
                menu(BACK, SET_TASK_DEADLINE, ASSIGN_TASK);
            }
        },

        SEE_ASSIGNED_TASKS("See tasks assigned to you") {
            @Override
            void run() {
                currentUser.printAssignedTasks();
                menu(BACK, SEE_A_TASK);
            }
        },


        MAIN_MENU("Main Menu") {
            @Override
            void run() {
                while (!quit) {
                    if (currentUser == null) menu(EXIT, LOGIN, SIGN_UP, FORGET_PASSWORD);
                    else {
                        System.out.println("Current user: " + currentUser.username);
                        menu(EXIT, LOGOUT, SEE_ALL_TASKS, SEE_ASSIGNED_TASKS, ADD_TASK);
                    }
                }
            }
        },

        SET_TASK_DEADLINE("Set task deadline") {
            @Override
            void run() {
                String taskDeadline =
                        input("Enter task deadline in format [year]/MM/DD HH:mm:ss\n");
                if (!isValidDateAndTime(taskDeadline)) {
                    error("Enter a valid date and time");
                    return;
                }
                currentTask.setDeadline(taskDeadline);
                System.out.println("Deadline set to task successfully");
            }
        },

        ASSIGN_TASK("Assign task to a user") {
            @Override
            void run() {
                String username = input("Enter a username to assign the task to (enter empty to cancel): ");
                if (username.equals("")) return;

                taskAssignUser = User.getUser(username);
                if (taskAssignUser == null) {
                    error("User with username \"" + username + "\" does not exist");
                    return;
                }

                System.out.println("Assign the task to user with full-name \"" + taskAssignUser.fullName + "\"?");
                menu(CANCEL_ASSIGN_TASK, CONFIRM_ASSIGN_TASK, BACK);
            }
        },

        CONFIRM_ASSIGN_TASK("Confirm") {
            @Override
            void run() {
                currentTask.assign(taskAssignUser);
                System.out.println("Task assigned successfully");
            }
        },

        CANCEL_ASSIGN_TASK("Assign to another user") {
            @Override
            void run() {
                ASSIGN_TASK.run();
            }
        },

        ADD_TASK("Add a task") {
            @Override
            void run() {
                String taskTitle = input("Enter task title: ");
                if (safeParseInt(taskTitle) != null) {
                    error("Task title can't be a number");
                    return;
                }
                new Task(taskTitle);
                System.out.println("Task created successfully");
            }
        },

        EXIT("Exit") {
            @Override
            void run() {
                quit = true;
            }
        },

        LOGOUT("Logout") {
            @Override
            void run() {
                currentUser = null;
            }
        },

        BACK("Back") {
            @Override
            void run() {
            }
        },

        SIGN_UP("Sign-up") {
            @Override
            void run() {
                String username = input("Enter your new username: ");
                String fullName = input("Enter your full name: ");
                String password = input("Enter your password: ");
                String passwordConfirm = input("Enter your password again: ");
                if (!password.equals(passwordConfirm)) {
                    error("Passwords do not match");
                    return;
                }
                if (!isValidPassword(password)) return;

                String birthday = input(
                        "Enter your birthday in format [year]/MM/DD HH\n");


                String birthdayDateAndTime = birthday + ":01:01";
                if (!isValidDateAndTime(birthdayDateAndTime)) {
                    error("Enter a valid birthday");
                    return;
                }

                try {
                    new User(username, fullName, password, birthdayDateAndTime);
                    System.out.println("User signed-up successfully");
                } catch (Exception e) {
                    error(e.getMessage());
                }
            }
        },

        FORGET_PASSWORD("Forget password") { // TODO

            @Override
            void run() {
                String username = input("Enter your username: ");
                User user = User.getUser(username);
                if (user == null) {
                    error("User with this username does not exist");
                    return;
                }

                String birthday = input(
                        "Enter your birthday in format [year]/MM/DD HH ");

                String birthdayDateAndTime = birthday + ":01:01";
                if (!isValidDateAndTime(birthdayDateAndTime)) {
                    error("Enter a valid birthday");
                    return;
                }

                if (!user.getBirthday().equals(birthdayDateAndTime)) {
                    error("Wrong birthday for this user");
                    return;
                }
                String password = input("Enter your new password: ");
                if (!isValidPassword(password)) return;
                user.changePassword(password);
                System.out.println("Password changed successfully");
            }
        },

        LOGIN("Login") {
            @Override
            void run() {
                String username = input("Enter your username: ");
                if (User.getUser(username) == null) {
                    error("User with this username does not exist");
                    return;
                }
                String password = input("Enter your password: ");

                try {
                    currentUser = User.login(username, password);
                    System.out.println("User logged-in successfully");
                } catch (Exception e) {
                    error(e.getMessage());
                }
            }
        };

        abstract void run();

        String title;
        static User taskAssignUser;

        MenuItem(String title) {
            this.title = title;
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearStatics() {
        stack = new ArrayList<>();
        currentUser = null;
        currentTask = null;
        quit = false;
        User.clearStatics();
        Task.clearStatics();
    }
}
