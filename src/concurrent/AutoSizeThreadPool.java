package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class AutoSizeThreadPool {
    private ThreadPoolExecutor threadPoolExecutor;

    public AutoSizeThreadPool(int coreSize, int maxSize, long keepAlive, TimeUnit timeUnit, BlockingQueue queue) {
        this.threadPoolExecutor = new ThreadPoolExecutor(coreSize, maxSize, keepAlive, timeUnit, queue);
    }

    public void execute(Runnable task) {
        if (needPutQueue()) {
            threadPoolExecutor.getQueue().offer(task);
            return;
        }
        threadPoolExecutor.execute(task);
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    public String monitor() {
        return String.format("active num: %d, current size: %d, core size: %d, max size: %d, queue size: %d",
                threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize(),
                threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getMaximumPoolSize(),
                threadPoolExecutor.getQueue().size());
    }

    private boolean needPutQueue() {
        if (threadPoolExecutor.getActiveCount() < threadPoolExecutor.getCorePoolSize()) {
            return threadPoolExecutor.getQueue().size() == 0;
        }
        return false;
    }
}
