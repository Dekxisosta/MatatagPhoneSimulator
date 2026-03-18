package apps.store;

import apps.base.*;
import core.*;
import manager.base.*;
import manager.preinstalled.*;
import model.preinstalled.*;
import ui.base.*;
import java.util.*;

final class PredefinedContacts {
    static final List<Contact> ALL = new ArrayList<>(Arrays.asList(
            new Contact("Power",        "+63 917 384 9021"),
            new Contact("Aki Hayakawa", "+63 905 762 3148"),
            new Contact("Makima",       "+63 932 019 4857"),
            new Contact("Pochita",      "+63 956 230 7614"),
            new Contact("Ichika Nakano",  "+63 921 304 8816"),
            new Contact("Nino Nakano",    "+63 908 173 5492"),
            new Contact("Miku Nakano",    "+63 935 620 1847"),
            new Contact("Yotsuba Nakano", "+63 947 081 3625"),
            new Contact("Itsuki Nakano",  "+63 919 762 4031")
    ));
}
final class PredefinedReplies {
    private static final Map<String, String> REPLIES = new HashMap<>();

    static {
        REPLIES.put("Power",          "Hmph! You should feel honored I even replied, fool.");
        REPLIES.put("Aki Hayakawa",   "Got it. Stay out of trouble.");
        REPLIES.put("Makima",         "Good. I'll be in touch.");
        REPLIES.put("Pochita",        "...");
        REPLIES.put("Ichika Nakano",  "Haha, sure~ Let's hang out sometime.");
        REPLIES.put("Nino Nakano",    "Fine. But don't make it a habit.");
        REPLIES.put("Miku Nakano",    "Oh! Did you know Date Masamune also sent letters like this?");
        REPLIES.put("Yotsuba Nakano", "YES!! I've been waiting for you to text me!!");
        REPLIES.put("Itsuki Nakano",  "Acknowledged. Please eat properly too.");
    }

    static String getReply(String name){
        return REPLIES.getOrDefault(name, "...");
    }
}
class PhoneManager extends Manager {
    private List<Contact> contacts;
    private List<CallLog> callLogs;
    private List<TextThread> textThreads;

    public PhoneManager() {
        this.contacts = PredefinedContacts.ALL;
        this.callLogs = new ArrayList<>();
        this.textThreads = new ArrayList<>();
    }

    public List<Contact> getContacts() { return contacts; }
    public Contact getContact(int index) { return contacts.get(index); }

    public void logCall(Contact contact) {
        callLogs.add(new CallLog(contact));
    }
    public List<CallLog> getCallLogs() { return callLogs; }
    public void sendText(Contact contact, String message) {
        TextThread thread = findOrCreateThread(contact);
        thread.addMessage("Denji: " + message);
        thread.addMessage(contact.getName() + ": " + PredefinedReplies.getReply(contact.getName()));
    }
    public List<TextThread> getTextThreads() { return textThreads; }

    private TextThread findOrCreateThread(Contact contact) {
        for (TextThread thread : textThreads)
            if (thread.getContact().equals(contact)) return thread;
        TextThread newThread = new TextThread(contact);
        textThreads.add(newThread);
        return newThread;
    }
}

final class PhoneAppConfig {
    static final String NAME = "Messenger Lite";
    static final String DEVELOPER = "Zuck Markerberg";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "STORE";
    static final double MEM_USED = 20;
    static final String[] COMMAND_NAMES = {
            "Make a call",
            "Send a text",
            "View call logs",
            "View messages",
            "View contacts"
    };
    static final int[] COMMAND_DURATIONS = {
            3,
            1,
            1,
            1,
            1
    };
}

class PhoneUI extends UI {
    public int promptContacts(String context, List<Contact> contacts) {
        printBanner(context);
        for (int i = 1; i <= contacts.size(); i++)
            System.out.println(String.format("[%d] %s — %s", i, contacts.get(i-1).getName(), contacts.get(i-1).getNumber()));
        System.out.println("[0] Back");
        System.out.print("Choose: ");
        return getIntWithinRange(0, contacts.size());
    }
    public void showContactDetails(Contact contact) {
        printBanner("MESSAGE LOG " + contact.getName());
        System.out.println("Name   : " + contact.getName());
        System.out.println("Number : " + contact.getNumber());
    }
    public void showCalling(Contact contact) {
        System.out.println("Calling " + contact.getName() + " (" + contact.getNumber() + ")...");
    }
    public int promptCallLogs(List<CallLog> logs) {
        printBanner("Call Logs");
        for (int i = 1; i <= logs.size(); i++)
            System.out.println(String.format("[%d] %s — %s", i, logs.get(i-1).getContact().getName(), logs.get(i-1).getTimestamp()));
        System.out.println("[0] Back");
        System.out.print("Choose: ");
        return getIntWithinRange(0, logs.size());
    }
    public void showCallLogDetails(CallLog log) {
        printBanner("Call Log");
        System.out.println("Contact : " + log.getContact().getName());
        System.out.println("Number  : " + log.getContact().getNumber());
        System.out.println("Time    : " + log.getTimestamp());
    }
    public String promptMessage() {
        System.out.print("Enter message: ");
        return getString();
    }
    public void showMessageSent(Contact contact) {
        System.out.println("Message sent to " + contact.getName() + ".");
    }
    public int promptTextThreads(List<TextThread> threads) {
        printBanner("Messages");
        for (int i = 1; i <= threads.size(); i++)
            System.out.println(String.format("[%d] %s (%d messages)", i, threads.get(i-1).getContact().getName(), threads.get(i-1).getMessages().size()));
        System.out.println("[0] Back");
        System.out.print("Choose: ");
        return getIntWithinRange(0, threads.size());
    }
    public void showThread(TextThread thread) {
        printBanner(thread.getContact().getName());
        List<String> messages = thread.getMessages();
        for (int i = 0; i < messages.size(); i++)
            System.out.println(String.format("[%d] %s", i+1, messages.get(i)));
    }
    public void showEmpty(String message) { System.out.println(message); }
}

public class PhoneApp extends App<PhoneManager, PhoneUI> {
    private PhoneState phoneState;

    private Runnable[] runnables = {
            () -> {
                if (!phoneState.isConnectedToWifi() && !phoneState.isConnectedToData()) {
                    ui.showEmpty("No network connection.");
                    return;
                }
                int contactKey = ui.promptContacts("Call", manager.getContacts());
                if (contactKey == 0) return;

                Contact contact = manager.getContact(contactKey - 1);
                ui.showCalling(contact);
                manager.logCall(contact);
                if (!phoneState.isConnectedToWifi()) phoneState.useMobileData(0.01);
            },
            () -> {
                if (!phoneState.isConnectedToWifi() && !phoneState.isConnectedToData()) {
                    ui.showEmpty("No network connection.");
                    return;
                }
                int contactKey = ui.promptContacts("Text", manager.getContacts());
                if (contactKey == 0) return;

                Contact contact = manager.getContact(contactKey - 1);
                String message = ui.promptMessage();
                if (message == null || message.isBlank()) return;

                manager.sendText(contact, message);
                ui.showMessageSent(contact);
                if (!phoneState.isConnectedToWifi()) phoneState.useMobileData(0.001);
            },
            () -> {
                List<CallLog> logs = manager.getCallLogs();
                if (logs.isEmpty()) {
                    ui.showEmpty("No call logs.");
                    return;
                }
                while (true) {
                    int logKey = ui.promptCallLogs(logs);
                    if (logKey == 0) break;
                    ui.showCallLogDetails(logs.get(logKey - 1));
                }
            },
            () -> {
                List<TextThread> threads = manager.getTextThreads();
                if (threads.isEmpty()) {
                    ui.showEmpty("No messages.");
                    return;
                }
                while (true) {
                    int threadKey = ui.promptTextThreads(threads);
                    if (threadKey == 0) break;
                    ui.showThread(threads.get(threadKey - 1));
                }
            },
            () -> {
                while (true) {
                    int contactKey = ui.promptContacts("Contacts", manager.getContacts());
                    if (contactKey == 0) break;
                    ui.showContactDetails(manager.getContact(contactKey - 1));
                }
            }
    };

    private Command.BooleanSupplier[] checks = {
            () -> !manager.getContacts().isEmpty(),
            () -> !manager.getContacts().isEmpty(),
            () -> !manager.getCallLogs().isEmpty(),
            () -> !manager.getTextThreads().isEmpty(),
            () -> !manager.getContacts().isEmpty()
    };

    public PhoneApp(PhoneState phoneState) {
        super(
                new PhoneUI(),
                new PhoneManager(),
                PhoneAppConfig.NAME,
                PhoneAppConfig.DEVELOPER,
                PhoneAppConfig.VERSION,
                PhoneAppConfig.APP_TYPE,
                PhoneAppConfig.MEM_USED
        );
        this.phoneState = phoneState;

        configureCommands(
                PhoneAppConfig.COMMAND_NAMES,
                PhoneAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}