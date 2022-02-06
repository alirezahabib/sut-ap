import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class ServerController {

    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<Task> allTasks = new ArrayList<>();
    private static HashMap<String, User> loggedInUsers;

    public static void loadData() {
        try {
            Gson gson = new Gson();
            allUsers = gson.fromJson(new String(Files.readAllBytes(Paths.get("allUsers.json"))),
                    new TypeToken<List<User>>() {
                    }.getType());
            allTasks = gson.fromJson(new String(Files.readAllBytes(Paths.get("allTasks.json"))),
                    new TypeToken<List<Task>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {
            Gson gson = new Gson();
            FileWriter allUsersWriter = new FileWriter("allUsers.json");
            FileWriter allTasksWriter = new FileWriter("allTasks.json");
            allUsersWriter.write(gson.toJson(allUsers));
            allTasksWriter.write(gson.toJson(allTasks));
            allUsersWriter.close();
            allTasksWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean register(String username, String password, String fullName) {
        for (User user: allUsers) {
            if (user.getUsername().equals(username)) return false;
        }
        allUsers.add(new User(username, password, fullName));
        saveData();
        return true;
    }

    public synchronized static String login(String username, String password) {
        if (loggedInUsers == null) loggedInUsers = new HashMap<>();
        for (User user: allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                loggedInUsers.put(token, user);
                return token;
            }
        }
        return "error";
    }

    public static String getTasks(String token) {
        if (loggedInUsers != null && loggedInUsers.containsKey(token)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Task task : allTasks) stringBuilder.append(task.getID()).append(" ");
            if (stringBuilder.toString().isEmpty()) return " ";
            else return stringBuilder.toString();
        } else return " ";
    }

    private static boolean allTasksContainsID(String id) {
        for (Task task : allTasks) if (task.getID().equals(id)) return true;
        return false;
    }

    public synchronized static String addTask(String id, String token) {
        if (loggedInUsers != null && loggedInUsers.containsKey(token)) {
            if (!allTasksContainsID(id)) {
                allTasks.add(new Task(id));
                saveData();
                return "success";
            } else return "error";
        } else return "error";
    }

}
