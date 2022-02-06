import picocli.CommandLine;

@CommandLine.Command(
        name = "portconfig", aliases = "pc",
        mixinStandardHelpOptions = true,
        description = "configures ports")
public class PortConfig implements Runnable {
    @Override
    public void run() {
        if (listen) Controller.controller.listen(port, rebind);
        if (close) Controller.controller.closePort(port);
    }

    @CommandLine.Option(names = {"--rebind", "-r"})
    boolean rebind;

    @CommandLine.Option(names = {"--port", "-p"})
    int port;

    @CommandLine.Option(names = {"--listen", "-l"})
    boolean listen;

    @CommandLine.Option(names = {"--close", "-c"})
    boolean close;

}
