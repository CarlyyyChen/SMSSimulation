import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable{
    private final BlockingQueue<Message> messageQueue;
    private final double meanProcessingTime;
    private final double failureRate;
    private int messagesSent = 0;
    private int messagesFailed = 0;
    private double totalTime = 0;

    public Sender(BlockingQueue<Message> messageQueue, double meanProcessingTime,
                  double failureRate) {
        if (meanProcessingTime < 0 || failureRate < 0) {
            throw new IllegalArgumentException("Mean processing time " +
                    "and failure rate must be non-negative.");
        }

        this.messageQueue = messageQueue;
        this.meanProcessingTime = meanProcessingTime;
        this.failureRate = failureRate;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (true) {
            try {
                Message message = messageQueue.take();
                double processingTime = generateProcessingTime();
                Thread.sleep((long) processingTime);

                if (random.nextDouble() >= failureRate) {
                    messagesSent++;
                    totalTime += processingTime;
                } else {
                    messagesFailed++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public double generateProcessingTime() {
        Random random = new Random();
        double processingTime = random.nextGaussian() + meanProcessingTime;
        return processingTime;
    }

    public int getMessagesSent() {
        return this.messagesSent;
    }

    public int getMessagesFailed() {
        return this.messagesFailed;
    }

    public double getTotalTime() {
        return this.totalTime;
    }
}
