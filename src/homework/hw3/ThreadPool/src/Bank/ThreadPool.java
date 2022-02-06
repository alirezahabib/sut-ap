// http://tutorials.jenkov.com/java-concurrency/thread-pools.html

package Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<PoolThreadRunnable> allRunnable = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) allRunnable.add(new PoolThreadRunnable(taskQueue));
        for (PoolThreadRunnable runnable : allRunnable) new Thread(runnable).start();
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (PoolThreadRunnable runnable : allRunnable) {
            runnable.doStop();
        }
    }
}