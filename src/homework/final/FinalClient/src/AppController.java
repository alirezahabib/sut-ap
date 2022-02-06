import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*

    IMPORTANT NOTE:
    Clean code practices are not important for "client" code in Question #2
    They are only important for "server" code in Question #2 and also inside "runQ3" in Question #3

 */

public class AppController {
    public enum ServerErrorType {
        NO_ERROR, NATIONAL_CODE_INVALID, STUDENT_NUMBER_INVALID, SERVER_CONNECTION_FAILED, UNKNOWN_ERROR
    }

    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void setupConnection() {
        try {
            socket = new Socket("localhost", 7755);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerErrorType isNationalCodeAndStudentNumberValid(String nationalCode, String studentNumber) {
        try {
            dataOutputStream.writeUTF("isValid-" + nationalCode + "-" + studentNumber);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            switch (result) {
                case "Success":
                    return ServerErrorType.NO_ERROR;
                case "Error - The provided national code is invalid":
                    return ServerErrorType.NATIONAL_CODE_INVALID;
                case "Error - The provided student number is invalid":
                    return ServerErrorType.STUDENT_NUMBER_INVALID;
                default:
                    return ServerErrorType.UNKNOWN_ERROR;
            }
        } catch (IOException ignored) {
            return ServerErrorType.SERVER_CONNECTION_FAILED;
        }
    }

    public static int getCountOfAuthenticatedPeople() throws Exception {
        dataOutputStream.writeUTF("count");
        dataOutputStream.flush();
        return Integer.parseInt(dataInputStream.readUTF());
    }
}
