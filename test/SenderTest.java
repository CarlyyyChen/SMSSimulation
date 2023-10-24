import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SenderTest {
    private BlockingQueue<Message> messageQueue;
    private Sender sender;
    private final int numberOfMessages = 1000;

    @BeforeEach
    void setUp() {
        messageQueue = new ArrayBlockingQueue<>(numberOfMessages);
        sender = new Sender(messageQueue, 1.0, 0.5);
    }

    @Test
    void testInvalidConstructorArgs() {
        assertThrows(IllegalArgumentException.class, () -> new Sender(messageQueue, -1.0, 0.5));
        assertThrows(IllegalArgumentException.class, () -> new Sender(messageQueue, 1.0, -0.5));
    }

    @Test
    void testGenerateProcessingTime() {
        double totalTime = 0;
        int iterations = 1000;
        for (int i = 0; i < iterations; i++) {
            totalTime += sender.generateProcessingTime();
        }

        double averageTime = totalTime / iterations;
        assertTrue(Math.abs(averageTime - 1.0) < 0.5); // Assuming the average is close to the mean, with some variance
    }

    @Test
    void testMessageProcessing() throws InterruptedException {
        messageQueue.put(new Message("Test1", "123"));
        messageQueue.put(new Message("Test2", "456"));

        Thread senderThread = new Thread(sender);
        senderThread.start();

        TimeUnit.SECONDS.sleep(5); // Giving it 5 seconds for processing
        senderThread.interrupt();

        assertTrue(sender.getMessagesSent() + sender.getMessagesFailed() >= 2);
    }

    @Test
    void testTotalTimeAccumulation() throws InterruptedException {
        messageQueue.put(new Message("Test1", "123"));
        messageQueue.put(new Message("Test2", "456"));

        Thread senderThread = new Thread(sender);
        senderThread.start();

        TimeUnit.SECONDS.sleep(5); // Giving it 5 seconds for processing
        senderThread.interrupt();

        assertTrue(sender.getTotalTime() > 0);
    }
}