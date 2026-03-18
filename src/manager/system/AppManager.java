package manager.system;

import apps.base.App;
import manager.base.*;


import java.util.*;

public class AppManager extends Manager {
    private Map<String, App> apps;

    public AppManager(){
        apps = new HashMap<>();
    }

    private String getKey(String name, String developer){
        return name + ":" + developer;
    }

    public void installApp(App app){
        String key = getKey(app.getName(), app.getDeveloper());
        if(apps.containsKey(key)) throw new IllegalArgumentException("[ERROR] App already installed.");
        apps.put(key, app);
    }

    public void uninstallApp(App app){
        String key = getKey(app.getName(), app.getDeveloper());
        if(!apps.containsKey(key)) throw new NoSuchElementException("[ERROR] App not found.");
        apps.remove(key);
    }

    public App getApp(String name, String developer){
        String key = getKey(name, developer);
        if(!apps.containsKey(key)) throw new NoSuchElementException("[ERROR] App not found.");
        return apps.get(key);
    }

    public List<App> getApps(){
        return new ArrayList<>(apps.values());
    }
}