import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {
    private BlockingQueue<Message> messageQueue;
    private Producer producer;
    private final int numberOfMessages = 1000;

    @BeforeEach
    void setUp() {
        messageQueue = new ArrayBlockingQueue<>(numberOfMessages);
        producer = new Producer(messageQueue, numberOfMessages);
    }

    @Test
    void testProducerCreatesExpectedNumberOfMessages() throws InterruptedException {
        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();

        assertEquals(numberOfMessages, messageQueue.size());
    }

    @Test
    void testGenerateRandomMessageThrowsExceptionForInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> producer.generateRandomMessage(-1));
    }

    @Test
    void testGeneratedMessageLength() {
        String generatedMessage = producer.generateRandomMessage(50);
        assertEquals(50, generatedMessage.length());
    }

    @Test
    void testGeneratedPhoneNumberFormat() {
        String phoneNumber = producer.generateRandomPhoneNumber();
        System.out.println(phoneNumber);
        assertTrue(Pattern.matches("\\+1\\d{10}", phoneNumber));
    }
}