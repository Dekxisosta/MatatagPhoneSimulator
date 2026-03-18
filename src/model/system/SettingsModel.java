package model.system;

import network.*;

public class SettingsModel {
    private WifiNetwork wifi;
    private MobileData mobileData;
    private int volume;

    public SettingsModel(MobileData mobileData){
        this.wifi = null;
        this.mobileData = mobileData;
        this.volume = 100;
    }

    public WifiNetwork getWifi(){ return wifi; }
    public int getVolume(){ return volume; }
    public MobileData getMobileData(){ return mobileData; }

    public void setWifi(WifiNetwork wifi, String password){
        try{
            wifi.connect(password);
            if(this.wifi!=null)
                this.wifi.disconnect();
            this.wifi = wifi;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public void setVolume(int volume){
        if(volume>100) volume = 100;
        if(volume<0) volume = 0;
        this.volume = volume;
    }
}