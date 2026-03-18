package apps.base;

import manager.base.*;
import ui.base.*;

import java.util.*;

final class DefaultAppConfig {
    static final String NAME = "Untitled";
    static final String DEVELOPER = "Unnamed";
    static final String VERSION = "v0.0.0";
}

public class App <M extends Manager, U extends UI> {
    protected U ui;
    protected M manager;
    protected List<Command> commands;
    protected boolean isRunning;
    private String name;
    private String developer;
    private String version;
    private String appType;
    private double memUsed;
    private int screenTime;
    private double totalBatteryUsed;
    private double batteryUsed;
    private int keyCounter;
    private Runnable onBatteryDrain;

    public App(
            U ui,
            M manager,
            String appType,
            double memUsed
    ){
        this.ui = ui;
        this.manager = manager;
        this.commands = new ArrayList<>();
        this.name = DefaultAppConfig.NAME;
        this.developer = DefaultAppConfig.DEVELOPER;
        this.version = DefaultAppConfig.VERSION;
        this.memUsed = memUsed;
        this.appType = appType.toUpperCase();
        isRunning = false;
        this.screenTime = 0;
        this.batteryUsed = 0;
        addCloseCommand(commands);
    }
    public App(
            U ui,
            M manager,
            String name,
            String appType,
            double memUsed
    ){
        this.ui = ui;
        this.manager = manager;
        this.commands = new ArrayList<>();
        this.name = name;
        this.developer = DefaultAppConfig.DEVELOPER;
        this.version = DefaultAppConfig.VERSION;
        this.memUsed = memUsed;
        this.appType = appType.toUpperCase();
        isRunning = false;
        this.screenTime = 0;
        this.batteryUsed = 0;
        addCloseCommand(commands);
    }
    public App(
            U ui,
            M manager,
            String name,
            String developer,
            String appType,
            double memUsed
    ){
        this.ui = ui;
        this.manager = manager;
        this.commands = new ArrayList<>();
        this.name = name;
        this.developer = developer;
        this.version = DefaultAppConfig.VERSION;
        this.memUsed = memUsed;
        this.appType = appType.toUpperCase();
        isRunning = false;
        this.screenTime = 0;
        this.batteryUsed = 0;
        addCloseCommand(commands);
    }
    public App(
            U ui,
            M manager,
            String name,
            String developer,
            String version,
            String appType,
            double memUsed
    ){
        this.ui = ui;
        this.manager = manager;
        this.commands = new ArrayList<>();
        this.name = name;
        this.developer = developer;
        this.version = version;
        this.memUsed = memUsed;
        this.appType = appType.toUpperCase();
        isRunning = false;
        this.screenTime = 0;
        this.batteryUsed = 0;
        addCloseCommand(commands);
    }
    public List<Command> getCommands(){return commands;}
    public String getName(){return name;}
    public String getDeveloper(){return developer;}
    public String getVersion(){return version;}
    public String getAppType(){return appType;}
    public boolean isRunning(){return isRunning;}
    public double getMemUsed(){return memUsed;}
    public int getScreenTime(){return screenTime;}
    public double getTotalBatteryUsed(){return totalBatteryUsed;}
    public double consumeBattery(){
        double temp = batteryUsed;
        totalBatteryUsed += batteryUsed;
        batteryUsed = 0;
        return temp;
    }


    public void launch(){
        isRunning = true;
        while(isRunning){
            Command command = ui.promptMenu(name, commands);
            if(command == null) return;
            updateUsage(command.getDuration());
            command.execute();
        }
    }
    public void close(){isRunning = false;}

    public void update(String version, List<Command> commands){
        this.version = version;
        this.commands = commands;
    }
    public void setBatteryDrainCallback(Runnable onBatteryDrain){
        this.onBatteryDrain = onBatteryDrain;
    }
    protected void updateUsage(int minutes) {
        screenTime += minutes;
        batteryUsed += (minutes * 0.5) + (memUsed * 0.1);
        if(onBatteryDrain != null) onBatteryDrain.run();
    }
    protected void configureCommands(
            String[] names,
            int[] durations,
            Runnable[] runnables,
            Command.BooleanSupplier[] checks
    ){
        int paramNum = names.length;
        if (
                paramNum != durations.length
                        || paramNum != runnables.length
                        || paramNum != checks.length
        ){
            throw new IllegalStateException(String.format("""
                    [APP CRASH] Dynamic configurator cannot run. Param count is unequal
                    Names: %d | Durations: %d | Runnables: %d | Checks: %d
                    """, names.length, durations.length, runnables.length, checks.length
            ));
        }
        for (int i=0; i < paramNum; i++){
            commands.add(new Command(
                    keyCounter++,
                    names[i],
                    durations[i],
                    runnables[i],
                    checks[i]
            ));
        }
    }
    private void addCloseCommand(List<Command> commands){
        this.commands = commands;
        commands.add(new Command(
                keyCounter++,
                String.format("Close %s", name),
                0,
                this::close,
                null
        ));
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        App app = (App) o;
        return name.equals(app.getName()) && developer.equals(app.getDeveloper());
    }
}
