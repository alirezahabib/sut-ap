import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Controller {
    public static Controller controller = new Controller();
    private User onlineUser;
    private int listeningPort;
    private Listener listener;

    public void success() {
        System.out.println("success");
    }

    private void assertLogin() throws LogicalError {
        if (onlineUser == null) throw new LogicalError("you must login to access this feature");
    }

    //region userconfig
    public void createUser(String username, String password) {
        try {
            User.signUp(username, password);
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void login(String username, String password) {
        try {
            onlineUser = User.login(username, password);
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void logout() {
        if (onlineUser == null) System.out.println("you must login before logging out");
        else {
            onlineUser = null;
            success();
        }
    }
    //endregion

    //region portconfig
    public void listen(int port, boolean rebind) {
        try {
            assertLogin();
            if (listeningPort != 0 && !rebind) {
                System.out.println("the port is already set");
                return;
            }
            listeningPort = port;
            if (listener != null) {
                listener.exit = true;
                if (!listener.getExit()) throw new LogicalError("an error occurred");
            }

            listener = new Listener(onlineUser, port);
            listener.start();
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void closePort(int port) {
        try {
            assertLogin();
            if (listeningPort != port) {
                System.out.println("the port you specified was not open");
                return;
            }
            listeningPort = 0;
            if (listener != null) listener.exit = true;
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }
    //endregion

    //region contactconfig
    public void link(String username, String host, int port) {
        try {
            assertLogin();
            onlineUser.addContact(new Contact(username, host, port));
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }
    //endregion

    private String focusedHost;
    private int focusedPort;
    private Contact focusedContact;

    //region send
    public void send(String message, int port, String host) {
        try {
            assertLogin();
            Server.send(host, port, onlineUser.username, message);
            success();
        } catch (LogicalError | IOException logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void send(String message, String username) {
        try {
            assertLogin();
            Contact contact = onlineUser.getContact(username);
            Server.send(contact.host, contact.port, onlineUser.username, message);
            success();
        } catch (LogicalError | IOException logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void send(String message, int port) {
        try {
            assertLogin();
            if (focusedHost == null) throw new
                    LogicalError("could not send message");
            Server.send(focusedHost, port, onlineUser.username, message);
            success();
        } catch (LogicalError | IOException logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void send(String message) {
        try {
            assertLogin();
            if (focusedContact != null) {
                Server.send(focusedContact.host, focusedContact.port, onlineUser.username, message);
            } else if (focusedHost != null && focusedPort != 0) {
                Server.send(focusedHost, focusedPort, onlineUser.username, message);
            } else {
                throw new
                        LogicalError("could not send message");
            }
            success();
        } catch (LogicalError | IOException logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void focus(String host) {
        try {
            assertLogin();
            focusedHost = host;
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void focus(int port) {
        try {
            assertLogin();
            if (focusedHost == null || focusedHost.equals("")) {
                throw new LogicalError("You must fist focus on a host");
            }
            focusedPort = port;
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void focusOnContact(String username) {
        try {
            assertLogin();
            focusedContact = onlineUser.getContact(username);
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void endFocus() {
        try {
            assertLogin();
            focusedHost = null;
            focusedPort = 0;
            focusedContact = null;
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }
    //endregion

    //region show
    public void showContacts() {
        try {
            assertLogin();
            HashMap<String, Contact> contacts = onlineUser.getContacts();
            for (Contact contact : contacts.values())
                System.out.println(contact.username + " -> " + contact.host + ":" + contact.port);
            success();
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showContact(String username) {
        try {
            assertLogin();
            System.out.println(onlineUser.getContact(username).toString());
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showSenders() {
        try {
            assertLogin();
            ArrayList<Message> allMessages = Message.getAllMessages();
            Set<String> senders = new HashSet<>();
            for (Message message : allMessages) senders.add(message.sender);
            for (String sender : senders) System.out.println(sender);
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showMessages() {
        try {
            assertLogin();
            Message.getAllMessages().forEach(message ->
                    System.out.println(message.toString()));
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showMessagesCount() {
        try {
            assertLogin();
            System.out.println(Message.getAllMessages().size());
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showSendersCount() {
        try {
            assertLogin();
            ArrayList<Message> allMessages = Message.getAllMessages();
            Set<String> senders = new HashSet<>();
            for (Message message : allMessages) senders.add(message.sender);
            System.out.println(senders.size());
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showMessages(String username) {
        try {
            assertLogin();
            Message.getAllMessages().stream().filter(message -> message.sender.equals(username))
                    .map(message -> message.text).forEach(System.out::println);
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }

    public void showMessagesCount(String username) {
        try {
            assertLogin();
            System.out.println(Message.getAllMessages().stream()
                    .filter(message -> message.sender.equals(username)).count());
        } catch (LogicalError logicalError) {
            System.out.println(logicalError.getMessage());
        }
    }
    //endregion
}
