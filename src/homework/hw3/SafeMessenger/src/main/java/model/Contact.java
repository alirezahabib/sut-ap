package model;

public class Contact {
    public final String username;
    public final String host;
    public final int port;

    public Contact(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
