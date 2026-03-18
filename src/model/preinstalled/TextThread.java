package model.preinstalled;

import java.util.*;

public class TextThread {
    private Contact contact;
    private List<String> messages = new ArrayList<>();
    public TextThread(Contact contact) { this.contact = contact; }
    public Contact getContact() { return contact; }
    public void addMessage(String message) { messages.add(message); }
    public List<String> getMessages() { return messages; }
}
