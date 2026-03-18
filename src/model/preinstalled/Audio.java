package model.preinstalled;

import model.base.*;

import java.time.format.*;

public class Audio extends File {
    public Audio(String fileName, String fileType, String description, long fileSize){
        super(fileName, fileType, description, fileSize, false);
    }
    public String getAudioDetails(){
        return String.format("""
            ++==--------------------==++
            ||   Song Information     ||
            ++==--------------------==++
              Title   : %s
              Artist  : %s
              Type    : %s
              Size    : %d bytes
              Added   : %s
            ============================""",
                getFileName(),
                getContent(),
                getFileType(),
                getFileSize(),
                getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))
        );
    }
    @Override
    public String toString(){
        return String.format("""
                ============================
                Audio File Details
                ============================
                This file is an audio about %s
                %s
                """, super.getContent(), super.toString());
    }
}
