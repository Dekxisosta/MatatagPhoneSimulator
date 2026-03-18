package ui.system;

import apps.base.*;
import ui.base.*;

import java.util.*;

public class AppManagerUI extends UI {
    public int promptApps(List<App> apps){
        printBanner("App Manager");
        for(int i = 1; i <= apps.size(); i++)
            System.out.println(String.format("[%d] %s — %s", i, apps.get(i-1).getName(), apps.get(i-1).getDeveloper()));
        System.out.println("[0] Back");
        System.out.print("Choose: ");
        return getIntWithinRange(0, apps.size());
    }
    public int promptAppAction(App app){
        printBanner(app.getName());
        System.out.println("[1] View Details");
        if(!app.getAppType().equalsIgnoreCase("SYSTEM") && !app.getAppType().equalsIgnoreCase("PREINSTALLED"))
            System.out.println("[2] Uninstall");
        System.out.println("[0] Back");
        System.out.print("Choose: ");
        return getIntWithinRange(0,
                app.getAppType().equalsIgnoreCase("SYSTEM")
                        || app.getAppType().equalsIgnoreCase("PREINSTALLED") ? 1 : 2);
    }
    public void showMemoryOverview(int appCount, double totalMem){
        printBanner("Memory Overview");
        System.out.println(String.format("""
            Installed Apps : %d
            Total Memory   : %.2f MB""",
                appCount, totalMem
        ));
    }
    public void showAppDetails(App app){
        printBanner(app.getName());
        System.out.println(String.format("""
                Name               : %s
                Developer          : %s
                Version            : %s
                Type               : %s
                Memory             : %.2f MB
                Total BatteryUsage : %.2f mAh
                Total ScreenTime   : %d %s
                """,
                app.getName(),
                app.getDeveloper(),
                app.getVersion(),
                app.getAppType().toUpperCase(),
                app.getMemUsed(),
                app.getTotalBatteryUsed(),
                app.getScreenTime(),
                app.getScreenTime() == 1? "minute": "minutes"
        ));
    }
    public void showEmpty(String message){
        System.out.println(message);
    }
    public void showUnknownCommandFallback(){
        System.out.println("[ERROR] Unknown command.");
    }
}