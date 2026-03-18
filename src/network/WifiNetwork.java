package network;

public class WifiNetwork {
    private String ssid;
    private String password;
    private boolean isConnected;

    public WifiNetwork(String ssid, String password){
        this.ssid = ssid;
        this.password = password;
        this.isConnected = false;
    }

    public String getSsid(){ return ssid; }
    public boolean isConnected(){ return isConnected; }
    public boolean hasPassword(){return password!=null;}

    public void connect(String password){
        if(hasPassword() && !this.password.equals(password)) throw new IllegalArgumentException("[ERROR] Incorrect password.");
        this.isConnected = true;
    }
    public void disconnect(){ this.isConnected = false; }
}