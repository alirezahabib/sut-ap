import picocli.CommandLine;


@CommandLine.Command(
        name = "--logout", aliases = "-lo",
        mixinStandardHelpOptions = true,
        description = "logout user")
public class Logout implements Runnable {
    @Override
    public void run() {
        Controller.controller.logout();
    }
}
