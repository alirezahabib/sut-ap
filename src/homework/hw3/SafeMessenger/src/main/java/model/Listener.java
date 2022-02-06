package model;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
    private int port;
    private User user;
    public boolean exit;

    public Listener(User user, int port) {
        this.user = user;
        this.port = port;
    }

    public synchronized boolean getExit() {
        return exit;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (!getExit()) {
                Socket socket;
                socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                String text = inputStream.readUTF();
                String[] texts = text.split("\n");
                String username = texts[0];
                new Message(username, texts[1]);
                user.addContact(new Contact(username,
                        socket.getInetAddress().getHostAddress(), socket.getPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
