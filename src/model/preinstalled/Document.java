package model.preinstalled;

import model.base.File;

public class Document extends File {

    public Document(String fileName, String fileType, String description, long fileSize){
        super(fileName, fileType, description, fileSize);
    }
    @Override
    public String toString(){
        return String.format("""
                ============================
                Document File Details
                ============================
                This file shows a document of %s
                %s
                """, super.getContent(), super.toString());
    }
}
