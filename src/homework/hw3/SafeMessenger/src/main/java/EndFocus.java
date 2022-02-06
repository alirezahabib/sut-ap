import picocli.CommandLine;

@CommandLine.Command(
        name = "--stop", aliases = "-sp",
        mixinStandardHelpOptions = true,
        description = "stop the focus")
public class EndFocus implements Runnable {
    @Override
    public void run() {
        Controller.controller.endFocus();
    }
}
