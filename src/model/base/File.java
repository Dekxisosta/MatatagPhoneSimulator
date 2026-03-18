package model.base;

import java.time.*;
import java.time.format.*;

public class File {
    private String fileName;
    private String fileType;
    private String content;
    private long fileSize;
    private LocalDateTime createdAt;

    public File(String fileName, String fileType, String content, long fileSize) {
        this.createdAt = LocalDateTime.now();
        this.fileName = fileName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        this.fileType = fileType;
        this.content = content;
        this.fileSize = fileSize;
    }
    public File(String fileName, String fileType, String content, long fileSize, boolean hasDateTime) {
        this.createdAt = LocalDateTime.now();
        if (hasDateTime) {
            this.fileName = fileName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        }else{
            this.fileName = fileName;
        }
        this.fileType = fileType;
        this.content = content;
        this.fileSize = fileSize;
    }

    public String getFileName() {return fileName;}
    public String getFileType() {return fileType;}
    public String getContent() {return content;}
    public long getFileSize() {return fileSize;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setContent(String content){this.content = content;}

    @Override
    public String toString() {
        return String.format("""
                File Name: %s
                File Type: %s
                File Size: %d
                Created At: %s
                """,
                fileName, fileType, fileSize, createdAt);
    }
}
