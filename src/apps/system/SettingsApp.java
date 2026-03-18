package apps.system;

import apps.base.*;
import manager.system.*;
import network.*;
import ui.system.SettingsUI;

import java.util.*;

final class SettingsAppConfig{
    static final String NAME = "Settings";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "SYSTEM";
    static final double MEM_USED = 10;
    static final String[] COMMAND_NAMES = {
            "Connect to Wifi Network",
            "Reconnect to Last Wifi Network",
            "Disconnect Current Wifi Network",
            "Enable Mobile Data",
            "Disable Mobile Data",
            "View Data Usage"
    };
    static final int[] COMMAND_DURATIONS = {
            1,
            1,
            1,
            1,
            1,
            1
    };
}

public class SettingsApp extends App<SettingsManager, SettingsUI> {
    private WifiScanner wifiScanner;

    private Runnable[] runnables = new Runnable[]{
            ()->{
                List<WifiNetwork> networks = wifiScanner.scan();
                int choice = ui.promptWifiSelection(networks);
                if (choice == 0) return;
                WifiNetwork selected = networks.get(choice - 1);
                if(selected==null) return;

                if (!selected.hasPassword())
                    manager.connectWifi(selected, null);
                else if (manager.hasSavedNetwork(selected))
                    manager.connectWifi(selected, null);
                else
                    manager.connectWifi(selected, ui.promptWifiPassword());
            },
            ()->{
                manager.reconnectWifi();
            },
            ()->{
                manager.disconnectWifi();
            },
            ()->{
                manager.enableMobileData();
                ui.showMobileDataStatus(true);
            },
            ()->{
                manager.disableMobileData();
                ui.showMobileDataStatus(false);
            },
            ()->{
                ui.showDataUsage(
                        manager.getCarrier(),
                        manager.getDataUsedGB(),
                        manager.getDataLimitGB(),
                        manager.getDataRemainingGB()
                );
            }
    };

    private Command.BooleanSupplier[] checks = new Command.BooleanSupplier[]{
            ()-> !manager.isConnectedToWifi(),
            ()-> !manager.isConnectedToWifi() && manager.hasRememberedWifiNetwork(),
            ()-> manager.isConnectedToWifi(),
            ()-> !manager.isConnectedToData() && manager.hasMobileDataRemaining(),
            ()-> manager.isConnectedToData(),
            null
    };

    public SettingsApp(
            SettingsManager manager,
            SettingsUI ui,
            WifiScanner wifiScanner
    ) {
        super(
                ui,
                manager,
                SettingsAppConfig.NAME,
                SettingsAppConfig.DEVELOPER,
                SettingsAppConfig.VERSION,
                SettingsAppConfig.APP_TYPE,
                SettingsAppConfig.MEM_USED
        );
        this.manager = manager;
        this.ui = ui;
        this.wifiScanner = wifiScanner;
        configureCommands(
                SettingsAppConfig.COMMAND_NAMES,
                SettingsAppConfig.COMMAND_DURATIONS,
                this.runnables,
                this.checks
        );
    }
}