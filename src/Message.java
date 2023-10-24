public class Message {
    private final String content;
    private final String phoneNumber;

    public Message(String content, String phoneNumber) {
        if (content == null || phoneNumber == null) {
            throw new NullPointerException("Message content and phone number cannot be null!");
        }
        this.content = content;
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
