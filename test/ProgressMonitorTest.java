import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProgressMonitorTest {
    private BlockingQueue<Message> messageQueue;
    private Sender sender1;
    private Sender sender2;
    private List<Sender> senders;
    private ProgressMonitor monitor;

    @BeforeEach
    void setUp() {
        messageQueue = new ArrayBlockingQueue<>(10);
        sender1 = new Sender(messageQueue, 0.5, 0.5);
        sender2 = new Sender(messageQueue, 0.5, 0.5);
        senders = Arrays.asList(sender1, sender2);
        monitor = new ProgressMonitor(senders, 1);
    }

    @Test
    void testProgressMonitorOutput() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Thread sender1Thread = new Thread(sender1);
        Thread sender2Thread = new Thread(sender2);
        Thread monitorThread = new Thread(monitor);

        messageQueue.put(new Message("Test1", "1234567890"));
        messageQueue.put(new Message("Test2", "2234567890"));

        sender1Thread.start();
        sender2Thread.start();
        monitorThread.start();

        TimeUnit.SECONDS.sleep(3); // Giving it 3 seconds for processing and monitoring

        monitorThread.interrupt();
        sender1Thread.interrupt();
        sender2Thread.interrupt();

        sender1Thread.join();
        sender2Thread.join();
        monitorThread.join();

        String monitorOutput = outContent.toString();
        assertTrue(monitorOutput.contains("Messages Sent:"));
        assertTrue(monitorOutput.contains("Messages Failed:"));
        assertTrue(monitorOutput.contains("Average Time Per Message:"));

        System.setOut(System.out); // Resetting the output stream
    }
}