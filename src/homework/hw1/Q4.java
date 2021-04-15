package homework.hw1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q4 {
    public static void main(String[] args) throws ParseException {

        Pattern pattern = Pattern.compile("Message\\{\\s*messageId=%[1-9 ]*-[B-GI-LNP-RU-Z][b-gi-lnp-ru-z]{4}\\$\\d{2}"
                + "(\\d{2})?%,\\s*from=User\\{\\s*firstName='([^']+)',\\s*isBot=(true|false),\\s*lastName='([^']*)'"
                + ",\\s*userName='([a-zA-Z]\\w{3,30}[a-zA-Z0-9])?'\\s*},\\s*date=(\\d{14}),"
                + "\\s*text='([^']*)',\\s*location=(-?\\d+(\\.\\d+)?)\\s*}");


        Scanner input = new Scanner(System.in);
        Matcher matcher = pattern.matcher(input.nextLine());

        while (matcher.find()) {
            new Message(matcher); // "allMessages" arrayList stores the references of these objects on initialization
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(sdf.parse(input.nextLine()));
        endDate.setTime(sdf.parse(input.nextLine()));

        double ThomasLocation = input.nextDouble();

        for (int i = 0; i < Message.allMessages.size(); i++) {
            Message message = Message.allMessages.get(i);

            if (message.isBot) {
                if (i != Message.allMessages.size() - 1) {
                    Message.allMessages.get(i + 1).isAfterBot = true;
                }
            } else if (!message.isAfterBot && message.isNear(ThomasLocation) && message.isBetween(startDate, endDate)) {
                System.out.println(message);
            }
        }
    }


    private static class Message {

        public static ArrayList<Message> allMessages = new ArrayList<>();

        private final String firstName;
        private final String lastName;
        private final String text;
        private final Calendar date = Calendar.getInstance();
        public boolean isBot;
        public boolean isAfterBot;
        private Double location;


        private Message(Matcher matcher) throws ParseException {
            this.firstName = matcher.group(2);
            this.lastName = matcher.group(4);
            this.text = matcher.group(7);
            this.setDate(matcher.group(6));
            this.setIsBot(matcher.group(3));
            this.setLocation(matcher.group(8));
            allMessages.add(this);
        }

        private void setLocation(String location) {
            this.location = Double.parseDouble(location);
        }

        private void setIsBot(String isBot) {
            this.isBot = isBot.equals("true"); // (true|false) validation is already checked by regex
        }

        private void setDate(String date) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            this.date.setTime(sdf.parse(date));
        }

        public boolean isNear(double ThomasLocation) {
            return !(this.location - ThomasLocation > 1) && !(this.location - ThomasLocation < -1);
        }

        public boolean isBetween(Calendar startDate, Calendar endDate) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar dateOnly = Calendar.getInstance();
            dateOnly.setTime(sdf.parse(sdf.format(this.date.getTime()))); // removing the time part by reformatting

            return dateOnly.compareTo(startDate) >= 0 && dateOnly.compareTo(endDate) <= 0;
        }

        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("_HH:mm_");
            return "--------------------\n*"
                    + firstName + " " + lastName + "*\n"
                    + text + "\n"
                    + formatter.format(date.getTime()) + "\n"
                    + "--------------------";
        }
    }
}
