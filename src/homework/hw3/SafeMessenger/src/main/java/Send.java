import picocli.CommandLine;

@CommandLine.Command(
        name = "send", aliases = "sd",
        mixinStandardHelpOptions = true,
        description = "send messages")
public class Send implements Runnable {
    @Override
    public void run() {
        boolean hasUsername = username != null && !username.equals("");
        boolean hasPort = port != 0;
        boolean hasHost = host != null && !host.equals("");
        Controller controller = Controller.controller;

        if (hasHost && hasPort) controller.send(message, port, host);
        else if (hasPort) controller.send(message, port);
        else if (hasUsername) controller.send(message, username);
        else controller.send(message);
    }

    @CommandLine.Option(names = {"--message", "-m"}, required = true)
    String message;

    @CommandLine.Option(names = {"--username", "-u"})
    String username;

    @CommandLine.Option(names = {"--port", "-p"})
    int port;

    @CommandLine.Option(names = {"--host", "-ht"})
    String host;
}
