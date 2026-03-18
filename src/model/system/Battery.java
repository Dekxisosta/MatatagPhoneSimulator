package model.system;

public class Battery {
    private double capacity;
    private double level;

    public Battery(double capacity){
        this.capacity = capacity;
        this.level = capacity;
    }

    public double getLevel(){ return level; }
    public double getCapacity(){ return capacity; }
    public double getPercentage(){ return (level / capacity) * 100.0; }
    public boolean isDead(){ return level <= 0; }
    public boolean isLow(){ return getPercentage() <= 20.0; }

    public void drain(double amount){
        level = Math.max(0, level - amount);
    }
    public void charge(double amount){
        level = Math.min(capacity, level + amount);
    }
}