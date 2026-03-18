package manager.system;

import model.base.File;
import manager.base.*;
import model.preinstalled.*;

import java.util.*;

final class FileManagerConfig {
    static final String[] PHOTO_TYPES = {
            "jpeg", "jpg", "png", "gif", "webp"
    };
    static final String[] VIDEO_TYPES = {
            "mp4", "mpeg", "mov", "avi"
    };
    static final String[] AUDIO_TYPES = {
            "mp3", "wav", "aac", "flac"
    };
    static final String[] DOCUMENT_TYPES = {
            "pdf", "txt", "docx"
    };
}

public class FileManager extends Manager {
    private List<File> files;

    public FileManager(){files = new ArrayList<>();}
    public void addFile(File file){files.add(file);}
    public File getFile(int index){
        if(index<0 || index>=files.size())
            throw new IndexOutOfBoundsException();
        return files.get(index);
    }
    public void updateFileContent(File file, String content){files.get(files.indexOf(file)).setContent(content);}
    public boolean isFilesEmpty(){return files.isEmpty();}
    public void removeFile(File file){files.remove(file);}
    public void removeFile(int index) throws IndexOutOfBoundsException{
        if(index<0||index>=files.size()) throw new IndexOutOfBoundsException();
        files.remove(index);
    }


    // !! ALL GETTERS RETURN A SHALLOW COPY OF THE LIST OF FILES TO AVOID UNINTENDED DATA MANIPULATION
    public List<File> getAllFiles(){return new ArrayList<>(files);}
    public List<Photo> getPhotos(){return getFilesOfType(FileManagerConfig.PHOTO_TYPES);}
    public List<Video> getVideos(){return getFilesOfType(FileManagerConfig.VIDEO_TYPES);}
    public List<Audio> getAudios(){return getFilesOfType(FileManagerConfig.AUDIO_TYPES);}
    public List<Document> getDocuments(){return getFilesOfType(FileManagerConfig.DOCUMENT_TYPES);}
    public double getMemoryUsedByPhotos(){return getMemoryUsedByType(FileManagerConfig.PHOTO_TYPES);}
    public double getMemoryUsedByVideos(){return getMemoryUsedByType(FileManagerConfig.VIDEO_TYPES);}
    public double getMemoryUsedByAudios(){return getMemoryUsedByType(FileManagerConfig.AUDIO_TYPES);}
    public double getMemoryUsedByDocuments(){return getMemoryUsedByType(FileManagerConfig.DOCUMENT_TYPES);}

    private <T extends File> List<T> getFilesOfType(String[] validTypes){
        List<T> result = new ArrayList<>();
        for(File file: files)
            for(String type: validTypes)
                if(file.getFileType().toLowerCase().equals(type))
                    result.add((T) file);
        return result;
    }
    private double getMemoryUsedByType(String[] validTypes){
        double totalMemUsed = 0;
        for(File file: files)
            for(String type: validTypes){
                if(file.getFileType().toLowerCase().equals(type)){
                    totalMemUsed+=file.getFileSize();
                }
            }
        return totalMemUsed;
    }
}
