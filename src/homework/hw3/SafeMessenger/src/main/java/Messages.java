import picocli.CommandLine;

@CommandLine.Command(
        name = "--messages", aliases = "-m",
        mixinStandardHelpOptions = true,
        description = "show messages")
public class Messages implements Runnable {
    @Override
    public void run() {
        boolean focused = username != null && !username.equals("");
        Controller controller = Controller.controller;
        if (count && focused) controller.showMessagesCount(username);
        else if (count) controller.showMessagesCount();
        else if (focused) controller.showMessages(username);
        else controller.showMessages();
    }

    @CommandLine.Option(names = {"--count", "-c"})
    boolean count;

    @CommandLine.Option(names = {"--from", "-f"})
    String username;
}
