package ir.habibz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static int count;

    private static synchronized void countIncrement() {
        count++;
    }

    private static synchronized int getCount() {
        return count;
    }

    public static String process(String command) {
        if (command.equals("count")) {
            return String.valueOf(getCount());
        }

        Matcher matcher = Pattern.compile("^isValid-(.*)-(.*)$").matcher(command);
        if (!matcher.find()) return "Invalid command";
        if (!isNationalIDValid(matcher.group(1))) return "Error - The provided national code is invalid";
        if (!isStudentNumberValid(matcher.group(2))) return "Error - The provided student number is invalid";
        countIncrement();
        return "Success";
    }

    private static boolean isNationalIDValid(String nationalID) {
        try {
            Long.parseLong(nationalID);
            return nationalID.length() == 10;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    private static boolean isStudentNumberValid(String studentNumber) {
        try {
            Integer.parseInt(studentNumber);
            if (studentNumber.length() != 8) return false;

            String facultyCode = studentNumber.substring(0, 4);
            return facultyCode.equals("9510") || facultyCode.equals("9610") ||
                    facultyCode.equals("9710") || facultyCode.equals("9810") || facultyCode.equals("9910");

        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
