package concurrent;

/**
 *
 */
public class Task implements Runnable {
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println(String.format("run task-%d, current thread : %s", taskId, Thread.currentThread().getName()));
    }
}
