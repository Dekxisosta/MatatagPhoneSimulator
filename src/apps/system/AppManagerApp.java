package apps.system;

import apps.base.*;
import manager.system.*;
import ui.system.*;

import java.util.*;

final class AppManagerAppConfig {
    static final String NAME = "App Manager";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "SYSTEM";
    static final double MEM_USED = 10;
    static final String[] COMMAND_NAMES = {
            "View All Apps",
            "Memory Overview"
    };
    static final int[] COMMAND_DURATIONS = { 1, 1 };
}

public class AppManagerApp extends App<AppManager, AppManagerUI> {
    private Runnable[] runnables = {
            () -> {
                while(true){
                    List<App> apps = manager.getApps();
                    int appKey = ui.promptApps(apps);
                    if(appKey == 0) break;

                    App selected = apps.get(appKey - 1);
                    while(true){
                        int action = ui.promptAppAction(selected);
                        if(action == 0) break;

                        switch(action){
                            case 1:
                                ui.showAppDetails(selected);
                                break;
                            case 2:
                                if(selected.getAppType().equals("SYSTEM")) {
                                    ui.showEmpty("Cannot uninstall a system app.");
                                }else if(selected.getAppType().equals("PREINSTALLED")){
                                    ui.showEmpty("Cannot uninstall a preinstalled app.");
                                } else {
                                    manager.uninstallApp(selected);
                                    ui.showEmpty(selected.getName() + " uninstalled.");
                                    break;
                                }
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                break;
                        }
                    }
                }
            },
            () -> {
                List<App> apps = manager.getApps();
                double totalMem = 0;
                for(App app : apps) totalMem += app.getMemUsed();
                ui.showMemoryOverview(apps.size(), totalMem);
            }
    };

    private Command.BooleanSupplier[] checks = {
            () -> !manager.getApps().isEmpty(),
            () -> !manager.getApps().isEmpty()
    };

    public AppManagerApp(AppManager manager) {
        super(
                new AppManagerUI(),
                manager,
                AppManagerAppConfig.NAME,
                AppManagerAppConfig.DEVELOPER,
                AppManagerAppConfig.VERSION,
                AppManagerAppConfig.APP_TYPE,
                AppManagerAppConfig.MEM_USED
        );
        configureCommands(
                AppManagerAppConfig.COMMAND_NAMES,
                AppManagerAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}