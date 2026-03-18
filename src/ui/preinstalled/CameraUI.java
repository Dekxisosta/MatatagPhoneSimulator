package ui.preinstalled;

import ui.base.*;
public class CameraUI extends UI {
    public int promptLocation(String[] locations) {
        System.out.println();
        super.printBanner("Location Prompt");
        System.out.println("Locations that you can visit: ");
        for(int i=0;i<locations.length;i++){
            System.out.println(String.format("[%d] %s", i+1, locations[i]));
        }
        System.out.println("[0] Back");
        System.out.print("Select location: ");
        return getIntWithinRange(0, locations.length);
    }

    public int promptSubject(String[] subjects) {
        if (subjects.length == 0) return -1;
        super.printBanner("Camera Subject Prompt");
        System.out.println("Take picture of:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.println("[" + (i + 1) + "] " + subjects[i]);
        }
        System.out.println("[0] Back");
        System.out.print("Select subject: ");
        return getIntWithinRange(0, subjects.length);
    }
}
