import java.util.List;

public class ProgressMonitor implements Runnable{
    private final List<Sender> senders;
    private final double updateInterval;

    public ProgressMonitor(List<Sender> senders, double updateInterval) {
        this.senders = senders;
        this.updateInterval = updateInterval;
    }

    @Override
    public void run() {
        int totalMessagesSent = 0;
        int totalMessagesFailed = 0;
        double totalProcessingTime = 0;

        while (true) {
            try {
                Thread.sleep((long) (updateInterval * 1000)); // convert to milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            for (Sender sender : senders) {
                totalMessagesSent += sender.getMessagesSent();
                totalMessagesFailed += sender.getMessagesFailed();
                totalProcessingTime += sender.getTotalTime();
            }

            double averageTimePerMessage = totalMessagesSent > 0 ?
                    totalProcessingTime / totalMessagesSent : 0;

            System.out.println("Messages Sent: " + totalMessagesSent);
            System.out.println("Messages Failed: " + totalMessagesFailed);
            System.out.println("Average Time Per Message: " + averageTimePerMessage);

            if (totalMessagesSent + totalMessagesFailed >= 1000) {
                break;
            }

            totalMessagesSent = 0;
            totalMessagesFailed = 0;
            totalProcessingTime = 0;
        }
    }
}
