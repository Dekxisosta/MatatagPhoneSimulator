package ui.preinstalled;

import apps.base.App;
import ui.base.UI;

import java.util.List;

public class AppStoreUI extends UI {

    public void showAvailableApps(List<App> apps) {
        System.out.println("--- Available Apps ---");
        for (int i = 0; i < apps.size(); i++)
            System.out.println(String.format("[%d] %s  by %s", i + 1, apps.get(i).getName(), apps.get(i).getDeveloper()));
    }

    public int promptAvailableApps(List<App> apps) {
        showAvailableApps(apps);
        System.out.println("[0] Back");
        System.out.print("Enter choice: ");
        return getIntWithinRange(0, apps.size());
    }

    public int promptInstalledApps(List<App> apps) {
        System.out.println("--- Installed Apps ---");
        for (int i = 0; i < apps.size(); i++)
            System.out.println(String.format("[%d] %s  by %s", i + 1, apps.get(i).getName(), apps.get(i).getDeveloper()));
        System.out.println("[0] Back");
        System.out.print("Enter choice: ");
        return getIntWithinRange(0, apps.size());
    }

    public void showInstallSuccess(String name) {System.out.println("[APPSTORE] "+name + " installed successfully!");}
    public void showEmpty(String message){System.out.println(message);}
    public void showUninstallSuccess(String name) {System.out.println("[APPSTORE] "+ name + " uninstalled successfully!");}
}