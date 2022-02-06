import picocli.CommandLine;

@CommandLine.Command(
        name = "userconfig", aliases = "uc",
        mixinStandardHelpOptions = true,
        version = "userconfig 0.1",
        description = "login, logout, or register a new user",
        subcommands = {Logout.class})
public class UserConfig implements Runnable {

    @Override
    public void run() {
        if (login) Controller.controller.login(username, password);
        else if (create) Controller.controller.createUser(username, password);
    }

    @CommandLine.Option(names = {"--username", "-u"})
    String username;

    @CommandLine.Option(names = {"--password", "-p"})
    String password;

    @CommandLine.Option(names = {"--create", "-c"})
    boolean create;

    @CommandLine.Option(names = {"--login", "-l"})
    boolean login;
}
