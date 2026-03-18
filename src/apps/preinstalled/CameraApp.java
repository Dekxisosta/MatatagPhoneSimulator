package apps.preinstalled;

import apps.base.*;
import manager.preinstalled.*;
import ui.preinstalled.*;

final class CameraAppConfig{
    static final String NAME = "Camera";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "PREINSTALLED";
    static final double MEM_USED = 10;
    static final String[] COMMAND_NAMES = {
            "Take a picture",
            "Take a video"
    };
    static final int[] COMMAND_DURATIONS = {
            1,
            1
    };
}
final class PredefinedCameraSubjects {
    static final String[] GARDEN_SUBJECTS = {
            "Butterflies", "Roses", "Sunflowers", "Reze watering plants"
    };
    static final String[] SCHOOL_SUBJECTS = {
            "Blackboard", "Makima at the whiteboard", "Library shelves", "Power doing homework"
    };
    static final String[] CITY_SUBJECTS = {
            "Neon signs", "Himeno at the crosswalk", "Street food stalls", "Rainy rooftop"
    };
    static final String[] ROAD_SUBJECTS = {
            "Sunset horizon", "Quanxi on a motorcycle", "Empty highway", "Street cats"
    };
    static final String[] BEACH_SUBJECTS = {
            "Waves crashing", "Asa doing cartwheels", "Seashells", "Sunset over the water"
    };
    static final String[] CAFE_SUBJECTS = {
            "Latte art", "Nobara reading a menu", "Rainy window", "Pastries on display"
    };
    static final String[] PARK_SUBJECTS = {
            "Pigeons", "Ichika on a bench", "Fallen leaves", "Kids flying kites"
    };
    static final String[] ROOFTOP_SUBJECTS = {
            "City skyline", "Yoru stargazing", "Antenna towers", "Clothes on a line"
    };
    static final String[] ENVIRONMENT_MENU = {
            "Garden",
            "School",
            "City",
            "Road",
            "Beach",
            "Cafe",
            "Park",
            "Rooftop"
    };
    static final String[][] ALL_SUBJECTS = {
            GARDEN_SUBJECTS,
            SCHOOL_SUBJECTS,
            CITY_SUBJECTS,
            ROAD_SUBJECTS,
            BEACH_SUBJECTS,
            CAFE_SUBJECTS,
            PARK_SUBJECTS,
            ROOFTOP_SUBJECTS
    };
}

public class CameraApp extends App<CameraManager, CameraUI> {
    private Runnable[] runnables = new Runnable[]{
            ()->{
                int location = ui.promptLocation(PredefinedCameraSubjects.ENVIRONMENT_MENU);
                if (location == 0) return;

                int subject = ui.promptSubject(PredefinedCameraSubjects.ALL_SUBJECTS[location - 1]);
                if (subject == 0) return;

                manager.capturePhoto(PredefinedCameraSubjects.ALL_SUBJECTS[location - 1][subject - 1]);

            },
            ()->{
                int location = ui.promptLocation(PredefinedCameraSubjects.ENVIRONMENT_MENU);
                if (location == 0) return;

                int subject = ui.promptSubject(PredefinedCameraSubjects.ALL_SUBJECTS[location - 1]);
                if (subject == 0) return;

                manager.captureVideo(PredefinedCameraSubjects.ALL_SUBJECTS[location - 1][subject - 1]);
            }
    };
    private Command.BooleanSupplier[] checks = new Command.BooleanSupplier[]{
            null,
            null
    };

    public CameraApp(CameraUI ui, CameraManager manager) {
        super(
                ui,
                manager,
                CameraAppConfig.NAME,
                CameraAppConfig.DEVELOPER,
                CameraAppConfig.VERSION,
                CameraAppConfig.APP_TYPE,
                CameraAppConfig.MEM_USED
        );
        configureCommands(
                CameraAppConfig.COMMAND_NAMES,
                CameraAppConfig.COMMAND_DURATIONS,
                this.runnables,
                this.checks
        );
        if(PredefinedCameraSubjects.ALL_SUBJECTS.length != PredefinedCameraSubjects.ENVIRONMENT_MENU.length)
            throw new IllegalStateException("Camera Subject Groups and Environment Menu must be of the same number");
    }
}
