package core;

import manager.system.*;
import network.*;

public class PhoneState {
    private SettingsManager settingsManager;
    private BatteryManager batteryManager;
    public PhoneState(
            SettingsManager settingsManager,
            BatteryManager batteryManager
    ){
        this.settingsManager = settingsManager;
        this.batteryManager = batteryManager;
    }
    public boolean isConnectedToWifi(){return settingsManager.isConnectedToWifi();}
    public boolean hasRememberedWifiNetwork(){return settingsManager.hasRememberedWifiNetwork();}
    public boolean hasSavedNetwork(WifiNetwork wifi){return settingsManager.hasSavedNetwork(wifi);}
    public boolean isConnectedToData(){return settingsManager.isConnectedToData();}
    public boolean hasMobileDataRemaining(){return settingsManager.hasMobileDataRemaining();}
    public double getDataUsedGB(){return settingsManager.getDataUsedGB();}
    public double getDataLimitGB(){return settingsManager.getDataLimitGB();}
    public double getDataRemainingGB(){return settingsManager.getDataRemainingGB();}
    public String getCarrier(){return settingsManager.getCarrier();}
    public void useMobileData(double gb){settingsManager.useMobileData(gb);}
    public int getCurrentVolume(){return settingsManager.getVolume();}
    public double getBatteryPercentage(){return batteryManager.getPercentage();}
    public String getWifiNetworkName(){return settingsManager.getWifiNetworkName();}
}