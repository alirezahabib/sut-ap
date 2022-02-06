import picocli.CommandLine;


@CommandLine.Command(
        name = "--listen", aliases = "-l",
        mixinStandardHelpOptions = true,
        description = "listen to a port")
public class Listen implements Runnable {
    @Override
    public void run() {
        Controller.controller.listen(port, rebind);
    }

    @CommandLine.Option(names = {"--rebind", "-r"})
    boolean rebind;

    @CommandLine.Option(names = {"--port", "-p"}, required = true)
    int port;
}
