import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Use_Future {
    private static int getSquare(int n) throws InterruptedException {
        Thread.sleep(new Random().nextInt(1000, 5001));
        return n * n;
    }

    private static void printFutureResult(Future<Integer> future) {
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        ExecutorService pool = Executors.newCachedThreadPool();
        Scanner scan = new Scanner(System.in);
        System.out.println("Print some numbers and exit");
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            if ("exit".equalsIgnoreCase(input) || input.isEmpty()) {
                break;
            }

            int n = Integer.parseInt(input);
            Future<Integer> future = pool.submit(() -> getSquare(n));
            pool.execute(() -> printFutureResult(future));
        }
        pool.shutdown();
    }
}