import picocli.CommandLine;

@CommandLine.Command(
        name = "--count", aliases = "-c",
        mixinStandardHelpOptions = true,
        description = "counts info")
public class Count implements Runnable {
    @Override
    public void run() {
        Controller controller = Controller.controller;
        if (username != null && !username.equals("") && messages) {
            controller.showMessagesCount(username);
        } else if (senders) controller.showSendersCount();
        else if (messages) controller.showMessagesCount();
    }

    @CommandLine.Option(names = {"--senders", "-s"})
    boolean senders;

    @CommandLine.Option(names = {"--messages", "-m"})
    boolean messages;

    @CommandLine.Option(names = {"--from", "-f"})
    String username;
}
