import picocli.CommandLine;

@CommandLine.Command(
        name = "--senders", aliases = "-s",
        mixinStandardHelpOptions = true,
        description = "show information about senders")
public class Senders implements Runnable {
    @Override
    public void run() {
        if (count) Controller.controller.showSendersCount();
        else Controller.controller.showSenders();
    }

    @CommandLine.Option(names = {"--count", "-c"})
    boolean count;
}
