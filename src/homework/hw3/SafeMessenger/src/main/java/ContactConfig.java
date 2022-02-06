import picocli.CommandLine;

@CommandLine.Command(
        name = "contactconfig", aliases = "cc",
        mixinStandardHelpOptions = true,
        description = "configures contacts manually", subcommands = {Link.class})
public class ContactConfig implements Runnable {
    @Override
    public void run() {
    }
}
