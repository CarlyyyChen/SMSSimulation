import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    static final int MAX_LENGTH_OF_MESSAGE = 100;
    private final BlockingQueue<Message> messageQueue;
    private final int numberOfMessages;

    public Producer(BlockingQueue<Message> messageQueue, int numberOfMessages) {
        this.messageQueue = messageQueue;
        this.numberOfMessages = numberOfMessages;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfMessages; i++) {
            String content = generateRandomMessage(MAX_LENGTH_OF_MESSAGE);
            String phoneNumber = generateRandomPhoneNumber();
            Message message = new Message(content, phoneNumber);

            try {
                messageQueue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public String generateRandomMessage(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder content = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            content.append(randomChar);
        }
        return content.toString();
    }

    public String generateRandomPhoneNumber() {
        Random random = new Random();
        String phoneNumber = "+1" + String.format("%10d", random.nextLong(1000000000,10000000000L));
        return phoneNumber;
    }

}
