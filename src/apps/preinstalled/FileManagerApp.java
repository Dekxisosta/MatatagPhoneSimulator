package apps.preinstalled;

import apps.base.*;
import manager.system.*;
import model.base.*;
import ui.preinstalled.*;

final class FileManagerAppConfig{
    static final String NAME = "File Manager";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "PREINSTALLED";
    static final double MEM_USED = 30;
    static final String[] COMMAND_NAMES = {
            "Show all",
            "Show photos",
            "Show videos",
            "Show audios",
            "Show documents",
            "Memory used by file genre"
    };
    static final int[] COMMAND_DURATIONS = {
            1,
            1,
            1,
            1,
            1,
            1
    };
}

public class FileManagerApp extends App<FileManager, FileManagerUI> {
    private Runnable[] runnables = {
            ()->{
                while(true){
                    int fileKey = ui.promptFiles("File", manager.getAllFiles());
                    if(fileKey == 0) break;

                    File file = manager.getFile(fileKey-1);

                    boolean shouldExit = false;
                    while(!shouldExit){
                        int action = ui.promptActionToFile("File", file);
                        if(action == 0) {
                            shouldExit = true;
                            continue;
                        }

                        switch(action){
                            case 1:
                                ui.showFile(file);
                                break;
                            case 2:
                                ui.showFileDetails(file);
                                break;
                            case 3:
                                manager.removeFile(fileKey-1);
                                shouldExit = true;
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                shouldExit=true;
                                break;
                        }
                    }
                    if(manager.isFilesEmpty())
                        break;
                }
            },
            ()->{
                while(true){
                    int fileKey = ui.promptFiles("Photo", manager.getPhotos());
                    if(fileKey == 0) break;

                    File file = manager.getPhotos().get(fileKey-1);

                    boolean shouldExit = false;
                    while(!shouldExit){
                        int action = ui.promptActionToFile("Photo", file);
                        if(action == 0) {
                            shouldExit = true;
                            continue;
                        }

                        switch(action){
                            case 1: ui.showFile(file); break;
                            case 2: ui.showFileDetails(file); break;
                            case 3:
                                manager.removeFile(file);
                                shouldExit = true;
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                shouldExit = true;
                                break;
                        }
                    }
                    if(manager.getPhotos().isEmpty()) break;
                }
            },
            ()->{
                while(true){
                    int fileKey = ui.promptFiles("Video", manager.getVideos());
                    if(fileKey == 0) break;

                    File file = manager.getVideos().get(fileKey-1);

                    boolean shouldExit = false;
                    while(!shouldExit){
                        int action = ui.promptActionToFile("Video", file);
                        if(action == 0) {
                            shouldExit = true;
                            continue;
                        }

                        switch(action){
                            case 1: ui.showFile(file); break;
                            case 2: ui.showFileDetails(file); break;
                            case 3:
                                manager.removeFile(file);
                                shouldExit = true;
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                shouldExit = true;
                                break;
                        }
                    }
                    if(manager.getVideos().isEmpty()) break;
                }
            },
            ()->{
                while(true){
                    int fileKey = ui.promptFiles("Audio", manager.getAudios());
                    if(fileKey == 0) break;

                    File file = manager.getAudios().get(fileKey-1);

                    boolean shouldExit = false;
                    while(!shouldExit){
                        int action = ui.promptActionToFile("Audio", file);
                        if(action == 0) {
                            shouldExit = true;
                            continue;
                        }

                        switch(action){
                            case 1: ui.showFile(file); break;
                            case 2: ui.showFileDetails(file); break;
                            case 3:
                                manager.removeFile(file);
                                shouldExit = true;
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                shouldExit = true;
                                break;
                        }
                    }
                    if(manager.getAudios().isEmpty()) break;
                }
            },
            ()->{
                while(true){
                    int fileKey = ui.promptFiles("Document", manager.getDocuments());
                    if(fileKey == 0) break;

                    File file = manager.getDocuments().get(fileKey-1);

                    boolean shouldExit = false;
                    while(!shouldExit){
                        int action = ui.promptActionToFile("Document", file);
                        if(action == 0) {
                            shouldExit = true;
                            continue;
                        }

                        switch(action){
                            case 1: ui.showFile(file); break;
                            case 2: ui.showFileDetails(file); break;
                            case 3:
                                manager.removeFile(file);
                                shouldExit = true;
                                break;
                            default:
                                ui.showUnknownCommandFallback();
                                shouldExit = true;
                                break;
                        }
                    }
                    if(manager.getDocuments().isEmpty()) break;
                }
            },
            ()->{
                double photos = manager.getMemoryUsedByPhotos();
                double videos = manager.getMemoryUsedByVideos();
                double audios = manager.getMemoryUsedByAudios();
                double documents = manager.getMemoryUsedByDocuments();
                ui.showTotalMemoryUsed(
                    photos+videos+audios+documents,
                    photos,
                    videos,
                    audios,
                    documents
                );
            }
    };
    private Command.BooleanSupplier[] checks = {
            null,
            ()->!manager.getPhotos().isEmpty(),
            ()->!manager.getVideos().isEmpty(),
            ()->!manager.getAudios().isEmpty(),
            ()->!manager.getDocuments().isEmpty(),
            null
    };

    public FileManagerApp(FileManagerUI ui, FileManager manager) {
        super(
                ui,
                manager,
                FileManagerAppConfig.NAME,
                FileManagerAppConfig.DEVELOPER,
                FileManagerAppConfig.VERSION,
                FileManagerAppConfig.APP_TYPE,
                FileManagerAppConfig.MEM_USED
        );

        configureCommands(
                FileManagerAppConfig.COMMAND_NAMES,
                FileManagerAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}
