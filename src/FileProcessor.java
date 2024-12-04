import java.util.concurrent.BlockingQueue;

class FileProcessor implements Runnable {
    private BlockingQueue<File> queue;
    private String allowedFileType;
    public FileProcessor(BlockingQueue<File> queue, String allowedFileType) {
        this.queue = queue;
        this.allowedFileType = allowedFileType;
    }
    @Override
    public void run() {
        while (true) {
            try {
                File file = queue.peek();
                if (file != null && file.getFileType().equals(allowedFileType)) {
                    queue.remove();
                    long processingTime = file.getFileSize() * 7L;
                    Thread.sleep(processingTime);
                    System.out.println("Processed file type: " + file.getFileType() +
                            " and size: " + file.getFileSize() + ". Processing time: " + processingTime + " Mills");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}