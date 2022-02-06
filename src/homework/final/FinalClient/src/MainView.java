import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/*

    IMPORTANT NOTE:
    Clean code practices are not important for "client" code in Question #2
    They are only important for "server" code in Question #2 and also inside "runQ3" in Question #3

 */

public class MainView {
    private static Scanner scanner;

    public static void main(String[] args) {
        AppController.setupConnection();
        scanner = new Scanner(System.in);
        runApp();
    }

    private static void runApp() {
        runLoop:
        while (true) {
            System.out.println("Hello! Welcome to the Computer Department!");
            System.out.println("Enter your national code for the door to be opened, 'count' to see how many people have been authenticated so far, or 'end' to exit the app:");
            String nationalCode = scanner.nextLine();
            switch (nationalCode) {
                case "end":
                    break runLoop;
                case "count":
                    countPeopleAuthenticated();
                    break;
                case "help":
                    runQ3();
                    break;
                default:
                    inputStudentNumber(nationalCode);
                    break;
            }
        }
    }

    private static void countPeopleAuthenticated() {
        try {
            System.out.println("Count of people authenticated: " + AppController.getCountOfAuthenticatedPeople());
        } catch (Exception e) {
            System.out.println("Server error occurred");
        }
    }

    private static void inputStudentNumber(String nationalCode) {
        System.out.println("Now, please enter your student number...");
        String studentNumber = scanner.nextLine();
        switch (AppController.isNationalCodeAndStudentNumberValid(nationalCode, studentNumber)) {
            case NO_ERROR:
                System.out.println("Thanks! You can now enter... DOOR IS OPENING");
                break;
            case NATIONAL_CODE_INVALID:
                System.out.println("I'm so sorry... your national code seems to be invalid. Please try again!");
                break;
            case SERVER_CONNECTION_FAILED:
                System.out.println("Hmm... There seems to be an error in the connection status. Maybe you should contact SSC to get more information.");
                break;
            case STUDENT_NUMBER_INVALID:
                System.out.println("Your student number doesn't belong to the CE department; Maybe you've had a typo... Please try again!");
                break;
            case UNKNOWN_ERROR:
                System.out.println("Oops! I really don't know what happened to my lovely server. Please send an email or a private message to SSC.");
                break;
        }
    }

    private static void runQ3() {
        System.out.println("Enter class name:");
        String className = scanner.nextLine();

        Class<?> selectedClass;
        try {
            selectedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("No Class Found!");
            return;
        }
        Method[] methods = selectedClass.getDeclaredMethods();
        printMethods(methods);
        if (selectedClass.equals(MainView.class)) askToRunMethod(methods);
    }

    private static void printMethods(Method[] methods) {
        for (int i = 0; i < methods.length; i++) {
            System.out.println("#" + i + " - Name: " + methods[i].getName()
                    + " - Return Type: " + methods[i].getReturnType().getName());
        }
    }

    private static void askToRunMethod(Method[] methods) {
        while (true) {
            System.out.println("Enter a method ID to execute (or -1 to return):");
            try {
                int index = Integer.parseInt(scanner.nextLine());
                if (index == -1) return;
                methods[index].invoke(null);

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid Number!");
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
