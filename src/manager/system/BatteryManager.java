package manager.system;

import manager.base.*;
import model.system.*;

public class BatteryManager extends Manager {
    private Battery battery;

    public BatteryManager(Battery battery){
        this.battery = battery;
    }

    public void drain(double amount){ battery.drain(amount); }
    public void charge(double amount){ battery.charge(amount); }
    public double getLevel(){ return battery.getLevel(); }
    public double getCapacity(){ return battery.getCapacity(); }
    public double getPercentage(){ return battery.getPercentage(); }
    public boolean isDead(){ return battery.isDead(); }
    public boolean isLow(){ return battery.isLow(); }
}