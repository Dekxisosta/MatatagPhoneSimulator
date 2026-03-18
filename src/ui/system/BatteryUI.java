package ui.system;

import ui.base.*;

public class BatteryUI extends UI {
    public void showBatteryStatus(double percentage, double level, double capacity){
        printBanner("Battery");
        System.out.println(String.format("""
                Level    : %.2f / %.2f
                Charge   : %.1f%%
                Status   : %s""",
                level, capacity, percentage,
                percentage <= 20.0 ? "Low Battery" : "Normal"
        ));
    }
    public void showLowBatteryWarning(){
        System.out.println("[WARNING] Battery is low. Please charge your device.");
    }
    public void showDeadBattery(){
        System.out.println("[WARNING] Battery is dead. Please charge your device.");
    }
}