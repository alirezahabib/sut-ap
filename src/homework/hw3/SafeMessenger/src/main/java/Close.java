import picocli.CommandLine;


@CommandLine.Command(
        name = "--close", aliases = "-c",
        mixinStandardHelpOptions = true,
        description = "close a port that was being listened")
public class Close implements Runnable {
    @Override
    public void run() {
        Controller.controller.closePort(port);
    }

    @CommandLine.Option(names = {"--port", "-p"}, required = true)
    int port;
}
