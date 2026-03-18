package manager.preinstalled;

import manager.base.*;
import manager.system.*;
import model.preinstalled.*;

public class CameraManager extends Manager {
    private FileManager fileManager;
    private SettingsManager settingsManager;

    private static int photoCounter;
    private static int videoCounter;

    public CameraManager(FileManager fileManager){
        this.fileManager = fileManager;
    }

    public void capturePhoto(String cameraSubject){
        fileManager.addFile(new Photo(
                String.format(
                        "Image%04d",
                        ++photoCounter
                ),
                "png",
                cameraSubject,
                cameraSubject.length()
        ));
    }
    public void captureVideo(String cameraSubject){
        fileManager.addFile(new Video(
                String.format(
                        "Video%04d",
                        ++videoCounter
                ),
                "mp4",
                cameraSubject,
                cameraSubject.length()
        ));
    }
}
