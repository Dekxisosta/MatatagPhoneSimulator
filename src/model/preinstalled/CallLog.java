package model.preinstalled;

public class CallLog {
    private Contact contact;
    private String timestamp; // or use LocalDateTime if your project uses it
    public CallLog(Contact contact) {
        this.contact = contact;
        this.timestamp = new java.util.Date().toString();
    }
    public Contact getContact() { return contact; }
    public String getTimestamp() { return timestamp; }
}
