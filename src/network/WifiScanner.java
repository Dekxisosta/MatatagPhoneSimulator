package network;

import java.util.*;

final class PredefinedWifiNetworks {
    static final WifiNetwork HOME         = new WifiNetwork("PLDTFIBER-Home",          "home1234");
    static final WifiNetwork OFFICE       = new WifiNetwork("CorpNet-Office",           "office456");
    static final WifiNetwork CAFE         = new WifiNetwork("BrewAndBrowse-Cafe",       "cafe789");
    static final WifiNetwork SCHOOL       = new WifiNetwork("MatatagU-Faculty",         "school321");
    static final WifiNetwork GYM          = new WifiNetwork("IronHub-Gym",              "liftbro99");
    static final WifiNetwork RESTAURANT   = new WifiNetwork("SizzlersNet-Restaurant",   "pasta2024");
    static final WifiNetwork HOTEL        = new WifiNetwork("GrandStay-Hotel",          "welcome888");
    static final WifiNetwork PUBLIC_MALL  = new WifiNetwork("PublicWifi-SM",            null);
    static final WifiNetwork PUBLIC_PARK  = new WifiNetwork("PublicWifi-RizalPark",     null);
    static final WifiNetwork PUBLIC_LIB   = new WifiNetwork("PublicWifi-Library",       null);
    private PredefinedWifiNetworks(){}
}

public class WifiScanner {
    private List<WifiNetwork> availableNetworks;

    public WifiScanner(){
        availableNetworks = new ArrayList<>();
        availableNetworks.add(PredefinedWifiNetworks.HOME);
        availableNetworks.add(PredefinedWifiNetworks.OFFICE);
        availableNetworks.add(PredefinedWifiNetworks.CAFE);
        availableNetworks.add(PredefinedWifiNetworks.SCHOOL);
        availableNetworks.add(PredefinedWifiNetworks.GYM);
        availableNetworks.add(PredefinedWifiNetworks.RESTAURANT);
        availableNetworks.add(PredefinedWifiNetworks.HOTEL);
        availableNetworks.add(PredefinedWifiNetworks.PUBLIC_MALL);
        availableNetworks.add(PredefinedWifiNetworks.PUBLIC_PARK);
        availableNetworks.add(PredefinedWifiNetworks.PUBLIC_LIB);
    }
    public List<WifiNetwork> scan(){
        System.out.println("Scanning for networks...");
        return Collections.unmodifiableList(availableNetworks);
    }
}