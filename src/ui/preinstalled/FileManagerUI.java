package ui.preinstalled;

import model.base.*;
import ui.base.*;

import java.util.*;

final class FileManagerUIConfig{
    static final String[] ACTIONS = {
            "Show",
            "More details about",
            "Delete"
    };
}

public class FileManagerUI extends UI {
    public void showTotalMemoryUsed(
            double totalUsedByAllFiles,
            double totalUsedByPhotos,
            double totalUsedByVideos,
            double totalUsedByAudios,
            double totalUsedByDocuments
    ){
        printBanner("File Memory Usage");
        System.out.println(String.format("""
                
                All Files: %.2fMiB
                Photos: %.2fMiB
                Videos: %.2fMiB
                Audios: %.2fMiB
                Documents: %.2fMiB
      
                """,
                totalUsedByAllFiles,
                totalUsedByPhotos,
                totalUsedByVideos,
                totalUsedByAudios,
                totalUsedByDocuments
                ));
    }
    public int promptFiles(String fileType, List<? extends File> files) {
        if(files.isEmpty()){
            System.out.println(String.format("[INFO] No %ss to show", fileType.toLowerCase()));
            return 0;
        }
        printBanner(fileType);
        String [] fileNames = new String[files.size()];
        for(int i=0;i<files.size();i++){
            fileNames[i] = files.get(i).getFileName();
        }
        for(int i=0;i<fileNames.length;i++){
            System.out.println(String.format("[%d] %s", i+1, fileNames[i]));
        }
        System.out.println("[0] Back");
        System.out.print("Select file: ");

        return getIntWithinRange(0, fileNames.length);
    }
    public int promptActionToFile(String fileType, File file){
        printBanner(String.format("Information about %s", file.getFileName()));
        for(int i=0;i<FileManagerUIConfig.ACTIONS.length;i++){
            System.out.println(String.format("[%d] %s", i+1, FileManagerUIConfig.ACTIONS[i]));
        }
        System.out.println("[0] Back");
        System.out.print("Select action: ");

        return getIntWithinRange(0, FileManagerUIConfig.ACTIONS.length);
    }
    public void showFile(File file){
        System.out.println(file.getContent());
    }
    public void showFileDetails(File file){
        System.out.println(file);
    }
    public void showUnknownCommandFallback(){
        System.out.println("[ERROR] Unknown command");
    }
}
