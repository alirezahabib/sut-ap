import picocli.CommandLine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandLine.Command(
        name = "SafeMessenger",
        mixinStandardHelpOptions = true,
        version = "SafeMessenger 0.1",
        description = "Securely send and receive messages on local network",
        subcommands = {UserConfig.class, PortConfig.class, ContactConfig.class, Show.class, Send.class, Focus.class})
public class SafeMessenger implements Runnable {
    @Override
    public void run() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandLine commandLine = new CommandLine(new SafeMessenger());
        Pattern pattern = Pattern.compile("(^.*)\"(.*)\"(.*)$");

        while (true) {
            String request = scanner.nextLine();
            Matcher matcher = pattern.matcher(request);
            if (matcher.matches()) {
                String[] commands = matcher.group(1).split(" ");
                String[] commands2 = matcher.group(3).split(" ");
                ArrayList<String> allCommands = new ArrayList<>();
                for (String s : commands) {
                    if (s != null && !s.equals("")) allCommands.add(s);
                }
                allCommands.add(matcher.group(2));

                for (String s : commands2) {
                    if (s != null && !s.equals("")) allCommands.add(s);
                }

                String[] requestArray = allCommands.toArray(new String[0]);
                commandLine.execute(requestArray);
            } else {
                commandLine.execute(request.split(" "));
            }
        }
    }
}
