import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileGenerator implements Runnable {
    private BlockingQueue<File> queue;

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] fileTypes = {"XML", "JSON", "XLS"};
        while (true) {
            try {
                Thread.sleep(random.nextInt(100, 1001));
                String randomFileType =
                        fileTypes[random.nextInt(fileTypes.length)];
                int randomFileSize = random.nextInt(10, 101);
                File file = new File(randomFileType, randomFileSize);
                queue.put(file);
                System.out.println("Generated file type: " + randomFileType + " and size: " + randomFileSize);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
