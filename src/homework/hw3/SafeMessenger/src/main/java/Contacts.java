import picocli.CommandLine;

@CommandLine.Command(
        name = "--contacts", aliases = "-cs",
        mixinStandardHelpOptions = true,
        description = "show contacts you saved before")
public class Contacts implements Runnable {
    @Override
    public void run() {
        Controller.controller.showContacts();
    }
}
