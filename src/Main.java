import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        int numberOfMessages = 1000;
        int numberOfSenders = 5;
        double updateInterval = 1.0; // Update progress every 1 second

        BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(numberOfMessages);
        List<Sender> senders = new ArrayList<>();

        Producer producer = new Producer(messageQueue, numberOfMessages);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        //set up mean processing time and failure rate for each sender
        double[] meanProcessingTime = new double[]{5,6,7,8,9};
        double[] failureRate = new double[]{0.1,0.2,0.3,0.4,0.5};

        for (int i = 0; i < numberOfSenders; i++) {
            Sender sender = new Sender(messageQueue, meanProcessingTime[i], failureRate[i]);
            senders.add(sender);
            Thread senderThread = new Thread(sender);
            senderThread.start();
        }

        ProgressMonitor progressMonitor = new ProgressMonitor(senders, updateInterval);
        Thread progressMonitorThread = new Thread(progressMonitor);
        progressMonitorThread.start();
    }
}