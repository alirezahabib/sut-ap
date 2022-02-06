package model;

import java.util.HashMap;

public class User {
    private static final HashMap<String, User> allUsers = new HashMap<>();
    public final String username;
    private String password;
    private final HashMap<String, Contact> contacts = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allUsers.put(username, this);
    }

    public static User login(String username, String password) throws LogicalError {
        User user = getUser(username);
        if (user.password.equals(password)) return user;
        throw new LogicalError("incorrect password");
    }

    public static User getUser(String username) throws LogicalError {
        if (allUsers.containsKey(username)) return allUsers.get(username);
        throw new LogicalError("user not found");
    }

    public static User signUp(String username, String password) throws LogicalError {
        if (allUsers.containsKey(username) ||
                username.replaceAll("[\\w|-]+","").length() != 0)
            throw new LogicalError("this username is unavailable");
        return new User(username, password);
    }

    public void addContact(Contact contact) {
        contacts.put(contact.username, contact);
    }

    public Contact getContact(String username) throws LogicalError {
        if (contacts.containsKey(username)) return contacts.get(username);
        throw new LogicalError("no contact with such username was found");
    }

    public HashMap<String, Contact> getContacts() {
        return contacts;
    }
}
