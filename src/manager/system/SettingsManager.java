package manager.system;

import manager.base.*;
import model.system.*;
import network.*;

import java.util.*;

public class SettingsManager extends Manager {
    private Map<WifiNetwork, String> savedNetworks;
    private SettingsModel settings;

    public SettingsManager(SettingsModel settings){
        this.settings = settings;
        savedNetworks = new HashMap<>();
    }

    public void connectWifi(WifiNetwork wifi, String password){
        if(wifi == null) throw new IllegalArgumentException("[ERROR] Wifi network cannot be null.");
        if(savedNetworks.containsKey(wifi)){
            reconnectWifi();
            return;
        }
        settings.setWifi(wifi, password);
        if(settings.getWifi()!=null && settings.getWifi().equals(wifi)){
            savedNetworks.put(wifi, password);
        }
    }
    public void reconnectWifi(){
        if(settings.getWifi() == null) throw new IllegalStateException("[ERROR] No wifi registered.");
        WifiNetwork currentWifi = settings.getWifi();
        currentWifi.connect(savedNetworks.get(currentWifi));
    }
    public void disconnectWifi(){
        if(settings.getWifi() == null) throw new IllegalStateException("[ERROR] No wifi connected.");
        settings.getWifi().disconnect();
    }
    public boolean isConnectedToWifi(){return settings.getWifi()!=null && settings.getWifi().isConnected();}
    public boolean hasRememberedWifiNetwork(){return settings.getWifi() != null;}
    public boolean hasSavedNetwork(WifiNetwork wifi){return savedNetworks.containsKey(wifi);}
    public String getWifiNetworkName(){
        if(settings.getWifi()==null) return "";
        return settings.getWifi().getSsid();
    } // can be null

    public void enableMobileData(){
        MobileData mobileData = settings.getMobileData();
        if(mobileData == null) throw new IllegalStateException("[ERROR] No mobile data configured.");
        mobileData.enable();
    }
    public void disableMobileData(){
        MobileData mobileData = settings.getMobileData();
        if(mobileData == null) throw new IllegalStateException("[ERROR] No mobile data configured.");
        mobileData.disable();
    }
    public void useMobileData(double gb){
        MobileData mobileData = settings.getMobileData();
        if(mobileData == null) throw new IllegalStateException("[ERROR] No mobile data configured.");
        if(!mobileData.isEnabled()) throw new IllegalStateException("[ERROR] Mobile data is not enabled.");
        if(gb <= 0) throw new IllegalArgumentException("[ERROR] Data usage must be positive.");
        mobileData.useData(gb);
        if(!mobileData.hasData()) mobileData.disable();
    }
    public boolean isConnectedToData(){return settings.getMobileData().isEnabled();}
    public boolean hasMobileDataRemaining(){return settings.getMobileData().hasData();}
    public double getDataUsedGB(){return settings.getMobileData().getDataUsedGB();}
    public double getDataLimitGB(){return settings.getMobileData().getDataLimitGB();}
    public double getDataRemainingGB(){
        MobileData mobileData = settings.getMobileData();
        return mobileData.getDataLimitGB() - mobileData.getDataUsedGB();
    }
    public String getCarrier(){return settings.getMobileData().getCarrier();}

    public int getVolume(){return settings.getVolume();}
    public void setVolume(int volume){settings.setVolume(volume);}
}