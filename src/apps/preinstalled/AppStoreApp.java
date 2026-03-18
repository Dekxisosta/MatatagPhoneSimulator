package apps.preinstalled;

import apps.base.*;
import manager.system.*;
import ui.preinstalled.*;

import java.util.*;

final class AppStoreAppConfig {
    static final String NAME = "App Store";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "PREINSTALLED";
    static final double MEM_USED = 3.5;
    static final String[] COMMAND_NAMES = {
            "Browse Apps",
            "Install App",
            "Uninstall App"
    };
    static final int[] COMMAND_DURATIONS = {
            1,
            2,
            2
    };
}

public class AppStoreApp extends App<AppManager, AppStoreUI> {
    private final List<App> availableApps;

    private Runnable[] runnables;
    private Command.BooleanSupplier[] checks;

    public AppStoreApp(AppStoreUI ui, AppManager appManager, List<App> availableApps) {
        super(
                ui,
                appManager,
                AppStoreAppConfig.NAME,
                AppStoreAppConfig.DEVELOPER,
                AppStoreAppConfig.VERSION,
                AppStoreAppConfig.APP_TYPE,
                AppStoreAppConfig.MEM_USED
        );
        this.availableApps = availableApps;

        runnables = new Runnable[]{
                () -> ui.showAvailableApps(availableApps),
                () -> {
                    int key = ui.promptAvailableApps(availableApps);
                    if (key == 0) return;

                    App selected = availableApps.get(key - 1);
                    manager.installApp(selected);
                    availableApps.remove(selected);
                    ui.showInstallSuccess(selected.getName());
                },
                () -> {
                    List<App> installed = new ArrayList<>();
                    for(App a : manager.getApps())
                        if(!a.getAppType().equalsIgnoreCase("SYSTEM")
                                && !a.getAppType().equalsIgnoreCase("PREINSTALLED"))
                            installed.add(a);

                    if(installed.isEmpty()){
                        ui.showEmpty("No uninstallable apps.");
                        return;
                    }

                    int key = ui.promptInstalledApps(installed);
                    if(key == 0) return;

                    App selected = installed.get(key - 1);
                    manager.uninstallApp(selected);
                    availableApps.add(selected);
                    ui.showUninstallSuccess(selected.getName());
                }
        };

        checks = new Command.BooleanSupplier[]{
                () -> !availableApps.isEmpty(),
                () -> !availableApps.isEmpty(),
                () -> {
                    for(App a : manager.getApps())
                        if(!a.getAppType().equalsIgnoreCase("SYSTEM")
                                && !a.getAppType().equalsIgnoreCase("PREINSTALLED"))
                            return true;
                    return false;
                }
        };

        configureCommands(
                AppStoreAppConfig.COMMAND_NAMES,
                AppStoreAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}