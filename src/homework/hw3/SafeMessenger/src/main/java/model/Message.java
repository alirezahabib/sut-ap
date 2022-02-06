package model;


import java.util.ArrayList;

public class Message {
    private static final ArrayList<Message> allMessages = new ArrayList<>();

    public final String sender;
    public final String text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
        allMessages.add(this);
    }

    public static ArrayList<Message> getAllMessages() {
        return allMessages;
    }

    @Override
    public String toString() {
        return sender + " -> " + text;
    }
}
