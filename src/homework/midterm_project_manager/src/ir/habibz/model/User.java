package ir.habibz.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static HashMap<String, User> allUsersByUsername = new HashMap<>();

    public final String username;
    public final String fullName;
    private String password;
    private final String birthday;
    private final ArrayList<Task> assignedTasks;

    public User(String username, String fullName, String password, String birthday) throws Exception {
        if (allUsersByUsername.containsKey(username)) throw new Exception("User with username: \""
                + username + "\" already exists.");

        assignedTasks = new ArrayList<>();
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.birthday = birthday;
        allUsersByUsername.put(username, this);
    }

    public static User login(String username, String password) throws Exception {
        User user = allUsersByUsername.get(username);
        if (!user.password.equals(password)) throw new Exception("Wrong password");
        return user;
    }

    public void printAssignedTasks() {
        if (assignedTasks.isEmpty()) {
            System.out.println("Congrats! There is no task assigned to you.");
            return;
        }
        System.out.println("Tasks assigned to \""+ fullName + "\":");
        assignedTasks.forEach(Task::printSummary);
    }

    public static User getUser(String username) {
        return allUsersByUsername.get(username);
    }

    void assignTask(Task task) {
        assignedTasks.add(task);
    }

    void removeTask(Task task) {
        assignedTasks.remove(task);
    }

    public String getBirthday() {
        return birthday;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public static HashMap<String, User> getAllUsersByUsername() {
        return allUsersByUsername;
    }

    public boolean passwordMatches(String candidate) {
        return password.equals(candidate);
    }

    public static void clearStatics() {
        allUsersByUsername = new HashMap<>();
    }

    public ArrayList<Task> getAssignedTasks() {
        return assignedTasks;
    }
}
