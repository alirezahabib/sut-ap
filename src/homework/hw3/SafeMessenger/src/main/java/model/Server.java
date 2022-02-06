package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Server {
    public static void send(String host, int port, String sender, String message) throws IOException {
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(sender + "\n" + message);
        dataOutputStream.flush();
        dataOutputStream.close();
        socket.close();
    }
}
