import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class F_functions {
    private static AtomicInteger sum = new AtomicInteger(0);

    private static void summing(int n) {
        sum.addAndGet(n);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.err.println("Interruption!");
        }
    }

    private static void F1(int[] array) {
        for (int n : array) {
            summing(n);
        }
    }

    private static void F2(int[] array) {
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int n : array) {
            pool.execute(() -> summing(n));
        }
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (Exception e) {
            System.err.println("Interruption!");
        }
    }

    private static void F3(int[] array) {
        ExecutorService pool = Executors.newWorkStealingPool();

        for (int n : array) {
            pool.execute(() -> summing(n));
        }
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (Exception e) {
            System.err.println("Interuption!");
        }
    }

    public static void start() {
        int[] array = IntStream.range(1, 10000).toArray();
        long startTime, endTime;

        sum = new AtomicInteger(0);
        startTime = System.currentTimeMillis();

        F1(array);
        System.out.println(sum);

        endTime = System.currentTimeMillis();
        printTimeAndMemoryUsage(startTime, endTime);

        sum = new AtomicInteger(0);
        startTime = System.currentTimeMillis();

        F2(array);
        System.out.println(sum);

        endTime = System.currentTimeMillis();
        printTimeAndMemoryUsage(startTime, endTime);

        sum = new AtomicInteger(0);
        startTime = System.currentTimeMillis();

        F3(array);
        System.out.println(sum);

        endTime = System.currentTimeMillis();
        printTimeAndMemoryUsage(startTime, endTime);
    }

    private static void printTimeAndMemoryUsage(long startTime, long endTime)
    {
        long elapsedTime = endTime - startTime;
        System.out.println("Runtime: " + elapsedTime + " мс");
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage: " + memoryUsed / (1024 * 1024)
                + " MB");
        System.out.println();
    }
}