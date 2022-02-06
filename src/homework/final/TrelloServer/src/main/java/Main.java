import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*

IMPORTANT NOTE:
This example answer might not be free of bugs. Also, clean code
practices are not checked completely. This answer only acts as a hint
to help you get started with server/client coding easier and be ready for
the final exam.

 */


public class Main {
    public static void main(String[] args) {
        runApp();
    }

    private static void runApp() {
        ServerController.loadData();
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                startNewThread(serverSocket, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startNewThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                getInputAndProcess(dataInputStream, dataOutputStream);
                dataInputStream.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while (true) {
            String input = dataInputStream.readUTF();
            String result = process(input);
            if (result.equals("")) break;
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        }
    }

    static String process(String command) {
        String[] parts = command.split(" ");
        if (command.startsWith("register")) {
            return String.valueOf(ServerController.register(parts[1], parts[2], parts[3]));
        } else if (command.startsWith("login")) {
            return String.valueOf(ServerController.login(parts[1], parts[2]));
        } else if (command.startsWith("getTasks")) {
            return ServerController.getTasks(parts[1]);
        } else if (command.startsWith("addTask")) {
            return ServerController.addTask(parts[1], parts[2]);
        }
        return "";
    }
}
