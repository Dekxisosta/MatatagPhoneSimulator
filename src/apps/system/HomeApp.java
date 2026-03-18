package apps.system;

import apps.base.*;
import core.*;
import manager.system.*;
import ui.system.*;

import java.util.*;

final class HomeAppConfig{
    static final String NAME = "MatatagProMax - Phone Sim";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "SYSTEM";
    static final double MEM_USED = 10;
    static final String[] GREETINGS = {
            "Rise and Shine, Denji! What are we doing today?",
            "Good morning, Denji! Let's get productive today!",
            "Yet another morning venture awaits, Denji!",
            "Let's face the day with a smile, Denji!",
            "Oi Denji, stop sleeping and get moving!",
            "Another day, another grind, Denji!",
            "Hey Denji, the world won't wait for you!",
            "Up and at 'em, Denji!",
            "What's the plan today, Denji?",
            "Time to make it count, Denji!"
    };
}

public class HomeApp extends App<AppManager, HomeUI> {
    private PhoneState phoneState;
    public HomeApp(
            HomeUI ui,
            AppManager manager,
            PhoneState phoneState

    ) {
        super(
                ui,
                manager,
                HomeAppConfig.NAME,
                HomeAppConfig.DEVELOPER,
                HomeAppConfig.VERSION,
                HomeAppConfig.APP_TYPE,
                HomeAppConfig.MEM_USED
        );
        this.phoneState = phoneState;
    }
    // The loop for home app is handled by the SmartphoneSystem,
    // Even though, it can already tell if battery dropped to 0 with phoneState
    // It's a cleaner design to let the system handle such event
    @Override
    public void launch(){
        isRunning = true;
        ui.showRandomGreeting(HomeAppConfig.GREETINGS);
        ui.showNotificationBar(
                phoneState.getBatteryPercentage(),
                phoneState.getCurrentVolume(),
                phoneState.isConnectedToWifi(),
                phoneState.getWifiNetworkName(),
                phoneState.isConnectedToData(),
                phoneState.getCarrier(),
                phoneState.getDataRemainingGB()
        );
        configureHomeCommands();
        Command command = ui.promptMenu(HomeAppConfig.NAME, commands);
        if(command == null) return;
        updateUsage(command.getDuration());
        command.execute();
    }
    private void configureHomeCommands(){
        List<App> apps = manager.getApps();

        // Inefficient but intended: rebuilds entire command list on every app change
        // to maintain alphabetical sorting without a separate sorting mechanism
        Command closeCommand = commands.get(0);
        commands.clear();
        commands.add(closeCommand);

        // bubble sort for lexicographical sortation of apps
        for(int i = 0; i < apps.size(); i++){
            for(int j = 0; j < apps.size()-i-1; j++){
                if(apps.get(j).getName().compareTo(apps.get(j+1).getName()) > 0){
                    App temp = apps.get(j);
                    apps.set(j, apps.get(j+1));
                    apps.set(j+1, temp);
                }
            }
        }

        for(int i = 0; i < apps.size(); i++){
            App app = apps.get(i);
            if(app.getName().equals(HomeAppConfig.NAME) && app.getDeveloper().equals(HomeAppConfig.DEVELOPER)){
                continue;
            }
            commands.add(new Command(
                    i+1,
                    app.getName(),
                    (int)(app.getMemUsed()*0.1),
                    app::launch,
                    null
            ));
        }
    }
}
