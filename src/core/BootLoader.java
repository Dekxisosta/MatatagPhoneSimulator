package core;

public class BootLoader {
    private SmartphoneSystem system;
    public void launch(){
        system = new SmartphoneSystem();
        System.out.println("[BOOTLOADER] Initializing smartphone system...");
        system.init();
        System.out.println("[BOOTLOADER] Successfully initialized smartphone system...");
        System.out.println("[BOOTLOADER] Launching main loop thread...");
        system.start();
        System.out.println("[BOOTLOADER] See you again next time...");
    }
}
