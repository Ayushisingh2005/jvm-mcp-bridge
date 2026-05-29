import java.util.ArrayList;
import java.util.List;

public class JVMTestLab {

    public static void main(String[] args) {
        System.out.println("JVM Test Lab Started!");
        System.out.println("Use 'jps -l' to find my PID.");

        // 1. Start a High CPU Thread
        startHighCPUThread();

        // 2. Start a Memory Leak Thread
        startMemoryLeakThread();

        // 3. Start a Deadlock
        startDeadlockThreads();

        // Keep main thread alive
        while (true) {
            try { Thread.sleep(10000); } catch (InterruptedException e) {}
        }
    }

    private static void startHighCPUThread() {
        new Thread(() -> {
            System.out.println("High CPU thread running...");
            while (true) {
                // Infinite loop with math to spike CPU
                double x = Math.sqrt(Math.random());
            }
        }, "HighCPU-Thread").start();
    }

    private static void startMemoryLeakThread() {
        new Thread(() -> {
            List<byte[]> leakList = new ArrayList<>();
            System.out.println("Memory Leak thread running...");
            while (true) {
                try {
                    // Add 1MB every second
                    leakList.add(new byte[1024 * 1024]);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }, "MemoryLeak-Thread").start();
    }

    private static void startDeadlockThreads() {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Locked lock1");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock2) {
                    System.out.println("Thread 1: Locked lock2");
                }
            }
        }, "Deadlock-Thread-1").start();

        new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Locked lock2");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock1) {
                    System.out.println("Thread 2: Locked lock1");
                }
            }
        }, "Deadlock-Thread-2").start();
    }
}