package core;

import apps.base.*;
import apps.system.*;
import manager.system.*;

import java.util.*;

public class AppLoader {
    private AppManager appManager;

    public AppLoader(AppManager appManager){
        this.appManager = appManager;
    }
    public void loadSystemApps(HomeApp homeApp, SettingsApp settingsApp, BatteryApp batteryApp, AppManagerApp appManagerApp, BatteryManager batteryManager){
        if(homeApp == null || settingsApp == null || batteryApp == null){
            throw new IllegalArgumentException("HomeApp, SettingsApp or BatteryApp can't be null.");
        }
        appManager.installApp(homeApp);
        System.out.println("[APPLOADER] Successfully loaded " + homeApp.getName());
        appManager.installApp(settingsApp);
        System.out.println("[APPLOADER] Successfully loaded " + settingsApp.getName());
        appManager.installApp(appManagerApp);
        System.out.println("[APPLOADER] Successfully loaded " + appManagerApp.getName());
        appManager.installApp(batteryApp);
        System.out.println("[APPLOADER] Successfully loaded " + batteryApp.getName());

        homeApp.setBatteryDrainCallback(()->batteryManager.drain(homeApp.consumeBattery()));
        settingsApp.setBatteryDrainCallback(()->batteryManager.drain(settingsApp.consumeBattery()));
        batteryApp.setBatteryDrainCallback(()->batteryManager.drain(batteryApp.consumeBattery()));
    }
    public void loadDefaultApps(List<App> defaultApps, BatteryManager batteryManager){
        defaultApps.forEach(appManager::installApp);
        for (App app : defaultApps)
            System.out.println(String.format("[APPLOADER] Successfully loaded %s", app.getName()));
        for(App app : defaultApps)
            app.setBatteryDrainCallback(()->batteryManager.drain(app.consumeBattery()));
    }
}