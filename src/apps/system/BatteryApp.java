package apps.system;

import apps.base.*;
import manager.system.*;
import ui.system.*;

final class BatteryAppConfig {
    static final String NAME = "Battery";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "SYSTEM";
    static final double MEM_USED = 5;
    static final String[] COMMAND_NAMES = {
            "View Battery Status",
            "Charge Battery"
    };
    static final int[] COMMAND_DURATIONS = { 1, 1 };
}

public class BatteryApp extends App<BatteryManager, BatteryUI> {
    private Runnable[] runnables = {
            () -> {
                ui.showBatteryStatus(
                        manager.getPercentage(),
                        manager.getLevel(),
                        manager.getCapacity()
                );
            },
            () -> {
                manager.charge(20.0);
                ui.showBatteryStatus(
                        manager.getPercentage(),
                        manager.getLevel(),
                        manager.getCapacity()
                );
            }
    };

    private Command.BooleanSupplier[] checks = {
            null,
            () -> manager.getPercentage() < 100.0
    };

    public BatteryApp(BatteryUI ui, BatteryManager manager) {
        super(
                ui,
                manager,
                BatteryAppConfig.NAME,
                BatteryAppConfig.DEVELOPER,
                BatteryAppConfig.VERSION,
                BatteryAppConfig.APP_TYPE,
                BatteryAppConfig.MEM_USED
        );
        configureCommands(
                BatteryAppConfig.COMMAND_NAMES,
                BatteryAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}