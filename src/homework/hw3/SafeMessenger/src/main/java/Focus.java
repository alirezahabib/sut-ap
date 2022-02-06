import picocli.CommandLine;

@CommandLine.Command(
        name = "focus", aliases = "f",
        mixinStandardHelpOptions = true,
        description = "focus on contacts, hosts and ports", subcommands = {EndFocus.class})
public class Focus implements Runnable {
    @Override
    public void run() {
        boolean hasUsername = username != null && !username.equals("");
        boolean hasPort = port != 0;
        boolean hasHost = host != null && !host.equals("");
        Controller controller = Controller.controller;

        if (hasHost) controller.focus(host);
        if (hasPort) controller.focus(port);
        if (hasUsername) controller.focusOnContact(username);
    }

    @CommandLine.Option(names = {"--port", "-p"})
    int port;

    @CommandLine.Option(names = {"--username", "-u"})
    String username;

    @CommandLine.Option(names = {"--host", "-ht"})
    String host;

    @CommandLine.Option(names = {"--start", "-s"})
    boolean start;
}
