package core;

import apps.base.App;
import apps.preinstalled.*;
import apps.store.*;
import apps.system.*;
import manager.base.*;
import manager.preinstalled.*;
import manager.system.*;
import model.preinstalled.*;
import model.system.*;
import network.*;
import ui.base.*;
import ui.preinstalled.*;
import ui.system.*;

import java.util.*;

public class SmartphoneSystem {
    private static final boolean IS_IN_DEBUG_MODE = true;
    private AppLoader appLoader;
    private AppManager appManager;
    private FileManager fileManager;
    private PhoneState phoneState;
    private SettingsManager settingsManager;
    private BatteryManager batteryManager;

    public void init(){
        System.out.println("[CORE] Smartphone initializing...");
        initCore();
        initSystem();
        initApps();
        System.out.println("[CORE] All systems nominal. Matatag OS ready.");
    }

    public void start(){
        loop();
    }

    public void loop(){
        App app;
        try{
            app = appManager.getApp("MatatagProMax - Phone Sim", "MatatagCompany");
            if(app==null|| !(app instanceof HomeApp)) throw new IllegalArgumentException();
        }catch(NoSuchElementException e){
            throw new NoSuchElementException("""
                [FATAL] Double string keys for home app is incorrect.
                Look at HomeAppConfig and match app and developer name
                for successful HomeApp dependency injection.
                """);
        }catch(IllegalArgumentException e){
            throw new NoSuchElementException("""
                [FATAL] Attempted to inject an app of different type as
                home app. Use the designated HomeApp class to successfully
                load the system
                """);
        }

        try{
            HomeApp home = (HomeApp) app;

            home.launch();
            while(home.isRunning()) {
                if(batteryManager.isDead()){
                    System.out.println("[SYSTEM] Battery dead. Shutting system down...");
                    break;
                }

                if(batteryManager.isLow())
                    System.out.println("[SYSTEM] Battery low. Please charge");
                home.launch();
            }
        }catch(Exception e){
            if(IS_IN_DEBUG_MODE){
                StackTraceElement origin = e.getStackTrace()[0];
                System.out.println(String.format("""
                    [FATAL] %s
                    %s
                    at %s.%s (line %d)
                    """,
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        origin.getClassName(),
                        origin.getMethodName(),
                        origin.getLineNumber()
                ));
            }else{
                System.out.println("Performed action resulted in an error :(");
            }
            this.loop();
        }
    }

    private void initCore(){
        System.out.println("[CORE] Initializing critical core subsystems...");
        appManager = new AppManager();
        System.out.println("[CORE] AppManager loaded.");
        appLoader = new AppLoader(appManager);
        System.out.println("[CORE] AppLoader loaded.");
        fileManager = new FileManager();
        System.out.println("[CORE] FileManager loaded.");
        settingsManager = new SettingsManager(new SettingsModel(new MobileData("Smart", 20)));
        System.out.println("[CORE] SettingsManager loaded. [model: SettingsModel, network: MobileData(Smart, 20GB)]");
        batteryManager = new BatteryManager(new Battery(9000));
        System.out.println("[CORE] BatteryManager loaded. [model: Battery(9000)]");
        phoneState = new PhoneState(settingsManager, batteryManager);
        System.out.println("[CORE] PhoneState loaded.");
    }

    private void initSystem(){
        System.out.println("[APPS] Loading system apps...");
        HomeApp homeApp = new HomeApp(new HomeUI(), appManager, phoneState);
        System.out.println("[APPS] HomeApp registered. [ui: HomeUI, manager: AppManager]");
        SettingsApp settingsApp = new SettingsApp(settingsManager, new SettingsUI(), new WifiScanner());
        System.out.println("[APPS] SettingsApp registered. [ui: SettingsUI, manager: SettingsManager, network: WifiScanner]");
        BatteryApp batteryApp = new BatteryApp(new BatteryUI(), batteryManager);
        System.out.println("[APPS] BatteryApp registered. [ui: BatteryUI, manager: BatteryManager, model: Battery(9000)]");
        AppManagerApp appManagerApp = new AppManagerApp(appManager);
        System.out.println("[APPS] AppManagerApp registered. [ui: AppManagerUI, manager: AppManager]");
        appLoader.loadSystemApps(homeApp, settingsApp, batteryApp, appManagerApp, batteryManager);
    }

    private void initApps(){
        System.out.println("[APPS] Loading default apps...");
        List<App> defaultApps = new ArrayList<>();
        defaultApps.add(new FileManagerApp(new FileManagerUI(), fileManager));
        System.out.println("[APPS] FileManagerApp registered. [ui: FileManagerUI, manager: FileManager]");
        defaultApps.add(new DocsApp(new DocsUI(), new DocsManager(fileManager)));
        System.out.println("[APPS] DocsApp registered. [ui: DocsUI, manager: DocsManager]");
        defaultApps.add(new CameraApp(new CameraUI(), new CameraManager(fileManager)));
        System.out.println("[APPS] CameraApp registered. [ui: CameraUI, manager: CameraManager]");
        defaultApps.add(new CalculatorApp(new CalculatorUI(), new Manager()));
        System.out.println("[APPS] CalculatorApp registered. [ui: CalculatorUI, manager: Manager]");
        defaultApps.add(new AppStoreApp(new AppStoreUI(), appManager, new ArrayList<>(Arrays.asList(
                new BuggyApp(new UI(), new Manager()),
                new MusicPlayerApp(fileManager),
                new PhoneApp(phoneState)
        ))));
        System.out.println("[APPS] AppStoreApp registered. [ui: AppStoreUI, manager: AppManager, Available Apps For Install: 3]");
        appLoader.loadDefaultApps(defaultApps, batteryManager);
    }
}