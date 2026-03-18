package ui.system;

import apps.base.Command;
import network.WifiNetwork;
import ui.base.*;
import java.util.List;

public class SettingsUI extends UI {
    public int promptWifiSelection(List<WifiNetwork> networks) {
        System.out.println("\nAvailable Networks:");
        for (int i = 0; i < networks.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + networks.get(i).getSsid());
        }
        System.out.println("[0] Back");
        System.out.print("Select network: ");

        return getIntWithinRange(0, networks.size());
    }

    public String promptWifiPassword(){
        System.out.print("Enter password: ");
        return supply(() -> reader.readLine());
    }
    public void showMobileDataStatus(boolean enabled) {
        System.out.println("Mobile data " + (enabled ? "enabled." : "disabled."));
    }
    public void showDataUsage(String carrier, double used, double limit, double remaining) {
        printBanner("Data Usage");
        System.out.println(String.format("""
            Carrier   : %s
            Used      : %.2f GB
            Limit     : %.2f GB
            Remaining : %.2f GB""",
                carrier, used, limit, remaining
        ));
    }
}