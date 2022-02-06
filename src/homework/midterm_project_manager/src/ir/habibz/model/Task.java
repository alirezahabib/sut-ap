package ir.habibz.model;

import java.util.HashMap;

public class Task {
    private static int taskCounter = 1;
    private static HashMap<Integer, Task> allTasksByID = new HashMap<>();

    private final int ID;
    private final String title;
    private User assignedTo;
    private String deadline;

    public Task(String title) {
        this.ID = taskCounter++;
        this.title = title;
        allTasksByID.put(ID, this);
    }

    public static void printAllTasks() {
        if (allTasksByID.isEmpty()) {
            System.out.println("No task available");
            return;
        }
        System.out.println("All tasks: ");
        allTasksByID.values().forEach(Task::printSummary);
    }

    public static Task getTask(int ID) {
        return allTasksByID.get(ID);
    }

    public static Task getTask(String title) {
        for (Task task : allTasksByID.values()) {
            if (task.title.equals(title)) return task;
        }
        return null;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task ID: " + ID +
                "\n  Title      : " + title +
                "\n  Assigned to: " + (assignedTo == null ? "not assigned" : assignedTo.username) +
                "\n  Deadline   : " + (deadline == null ? "no deadline" : deadline) +
                "\n-----";
    }

    public void printSummary() {
        System.out.println("ID " + ID + ": " + title);
    }

    public void assign(User user) {
        if (assignedTo != null) assignedTo.removeTask(this);
        assignedTo = user;
        user.assignTask(this);
    }

    public static HashMap<Integer, Task> getAllTasksByID() {
        return allTasksByID;
    }

    public static void clearStatics() {
        taskCounter = 1;
        allTasksByID = new HashMap<>();
    }
}
