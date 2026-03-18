package ui.system;

import ui.base.*;

public class HomeUI extends UI {
    private String lastGreeting;
    private char borderStyle = '~';

    public void showRandomGreeting(String[] greetings){
        String greeting = greetings[(int) (Math.random() * (greetings.length-1))];
        lastGreeting = greeting;
        int len = greeting.length();
        String border = String.valueOf(borderStyle).repeat(len + 4);
        System.out.println(String.format("""
            %s
            * %s *
            %s""", border, greeting, border));
    }

    public void showNotificationBar(
            double batteryPercentage,
            int currentVolume,
            boolean connectedToWifi,
            String wifiNetworkName,
            boolean connectedToData,
            String dataCarrier,
            double remainingGB
    ) {
        if (lastGreeting == null)
            throw new IllegalStateException("[ERROR] Random greeting should run first before the notification bar");

        String batteryCol = String.format("Battery: %.1f%%", batteryPercentage);
        String volumeCol  = String.format("Volume: %d%%", currentVolume);
        String wifiCol    = connectedToWifi ? String.format("Wifi: %s", wifiNetworkName) : "Wifi: Off";
        String dataCol    = connectedToData ? String.format("Data: %s (%.2fGB)", dataCarrier, remainingGB) : "Data: Off";

        int colWidth = (int) Math.floor((lastGreeting.length() + 4) / 2.0);
        System.out.println(String.format("%-" + colWidth + "s %s", batteryCol, wifiCol));
        System.out.println(String.format("%-" + colWidth + "s %s", volumeCol, dataCol));
        System.out.println(String.valueOf(borderStyle).repeat(lastGreeting.length() + 4));
    }
}
