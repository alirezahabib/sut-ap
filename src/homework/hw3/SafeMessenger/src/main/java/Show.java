import picocli.CommandLine;

@CommandLine.Command(
        name = "show", aliases = "s",
        mixinStandardHelpOptions = true,
        description = "show information about things",
        subcommands = {Senders.class, Messages.class, Contacts.class, Count.class})
public class Show implements Runnable {
    @Override
    public void run() {
        Controller.controller.showContact(username);
    }

    @CommandLine.Option(names = {"--contact", "-c"})
    String username;
}