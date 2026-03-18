package network;

public class MobileData {
    private String carrier;
    private double dataLimitGB;
    private double dataUsedGB;
    private boolean isEnabled;

    public MobileData(String carrier, double dataLimitGB){
        this.carrier = carrier;
        this.dataLimitGB = dataLimitGB;
        this.dataUsedGB = 0;
        this.isEnabled = false;
    }

    public String getCarrier(){ return carrier; }
    public double getDataUsedGB(){ return dataUsedGB; }
    public double getDataLimitGB(){ return dataLimitGB; }
    public boolean isEnabled(){ return isEnabled; }
    public boolean hasData(){ return dataUsedGB < dataLimitGB; }

    public void enable() throws IllegalStateException{
        if(!hasData()) throw new IllegalStateException("[ERROR] No data remaining.");
        this.isEnabled = true;
    }
    public void disable(){ this.isEnabled = false; }
    public void useData(double gb){ dataUsedGB += gb; }
}