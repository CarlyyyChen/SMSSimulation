import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message("Hello", "1234567890");
    }

    @Test
    void testConstructor() {
        assertNotNull(message);
    }

    @Test
    void testGetContent() {
        assertEquals("Hello", message.getContent());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("1234567890", message.getPhoneNumber());
    }

    @Test
    void testMessageWithNullContent() {
        assertThrows(NullPointerException.class, () -> new Message(null, "1234567890"));
    }

    @Test
    void testMessageWithNullPhoneNumber() {
        assertThrows(NullPointerException.class, () -> new Message("Hello", null));
    }

    @Test
    void testEmptyContent() {
        Message emptyContentMessage = new Message("", "1234567890");
        assertEquals("", emptyContentMessage.getContent());
    }

    @Test
    void testEmptyPhoneNumber() {
        Message emptyPhoneNumberMessage = new Message("Hello", "");
        assertEquals("", emptyPhoneNumberMessage.getPhoneNumber());
    }
}