public class Task {
    private String id;

    public Task(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id='" + id + '\'' +
               '}';
    }
}
