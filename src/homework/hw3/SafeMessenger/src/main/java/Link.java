import picocli.CommandLine;


@CommandLine.Command(
        name = "--link", aliases = "-l",
        mixinStandardHelpOptions = true,
        description = "link a contact")
public class Link implements Runnable {
    @Override
    public void run() {
        Controller.controller.link(username, host, port);
    }

    @CommandLine.Option(names = {"--username", "-u"}, required = true)
    String username;

    @CommandLine.Option(names = {"--host", "-ht"}, required = true)
    String host;

    @CommandLine.Option(names = {"--port", "-p"}, required = true)
    int port;
}
