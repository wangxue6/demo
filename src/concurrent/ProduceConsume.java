package concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProduceConsume {
    
    public static final int ProducerNum = 3;
    public static final int ConsumerNum = 3;
    public static final int QueueSize = 10;
    public static Queue<String> queue = new LinkedBlockingDeque<>();
    public static Lock lock = new ReentrantLock();
    public static Condition produce = lock.newCondition();
    public static Condition consume = lock.newCondition();
    public static AtomicInteger size = new AtomicInteger();
    public static AtomicInteger produceStop = new AtomicInteger();
    public static AtomicInteger consumeStop = new AtomicInteger();
    public static volatile int consumeStart = 0;
    
    public static void main(String[] args){
        for(int i=0;i<3;i++) {
        	new Producer().start();
        }
        for(int i=0;i<3;i++) {
        	new Consumer().start();
        }
    }
    
    
    public static class Producer extends Thread {
        

        @Override
        public void run() {
            while(true){
            	int cur = size.get();
                if(consumeStart >= 3 && cur < 10 && size.compareAndSet(cur, cur+1)) {
                	queue.offer("producer");
                	System.out.println("produce");
                }else {
                	if(queue.size() < 10)continue;
                	lock.lock();
                	if(produceStop.get() == 2) {
                		consume.signalAll();
                		try {
							produceStop.compareAndSet(2, 0);
                			produce.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}else {
                		int cnt = produceStop.get();
                		if(produceStop.compareAndSet(cnt, cnt+1))
							try {
								produce.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                	}
                	lock.unlock();
                }
            }
        }
    }
    
    
    
    public static class Consumer extends Thread {

        
        @Override
        public void run() {
        	lock.lock();
        	try {
        		consumeStart++;
        		System.out.println("consume get lock.");
				consume.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				lock.unlock();
				System.out.println("consume release lock.");
			}
        	while(true){
            	int cur = size.get();
                if(cur > 0 && size.compareAndSet(cur, cur-1)) {
                	String msg = queue.poll();
                	System.out.println("consume");
                }else {
                	if(queue.size() > 0)continue;
                	lock.lock();
                	if(consumeStop.get() == 2) {
                		produce.signalAll();
                		try {
                			consumeStop.compareAndSet(2, 0);
                			consume.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}else {
                		int cnt = consumeStop.get();
                		if(consumeStop.compareAndSet(cnt, cnt+1))
							try {
								consume.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                	}
                	lock.unlock();
                }
            }
        }

    }
    
}
