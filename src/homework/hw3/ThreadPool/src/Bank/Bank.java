package Bank;

import Tasks.Task;

import java.util.ArrayList;

public class Bank {
    private final ArrayList<ATM> ATMs = new ArrayList<>();
    private final ThreadPool threadPool;
    private int ATMIndex = 0;

    public Bank(int AtmCount) {
        for (int i = 0; i < AtmCount; i++) ATMs.add(new ATM());
        threadPool = new ThreadPool(AtmCount, 50);
    }

    public ArrayList<Object> runATM(ArrayList<Task> tasks, Handler handler) {
        ArrayList<Object> objects = new ArrayList<>();

        threadPool.execute(() -> {
            int ATMIndexLocal = ATMIndex;
            increaseATMIndex(1);
            for (Task task : tasks) {
                task.setATM(ATMs.get(ATMIndexLocal));
                try {
                    objects.add(task.run());
                } catch (Exception e) {
                    objects.add(e);
                }
            }
            handler.done();
            increaseATMIndex(-1);
        });
        return objects;
    }

    private synchronized void increaseATMIndex(int amount) {
        ATMIndex += amount;
    }
}
