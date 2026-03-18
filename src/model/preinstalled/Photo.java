package model.preinstalled;

import model.base.*;

public class Photo extends File {
    public Photo(String fileName, String fileType, String description, long fileSize){
        super(fileName, fileType, description, fileSize);
    }

    @Override
    public String getContent(){
        String desc = super.getContent();
        return String.format("""
                %s
                || %s ||
                || %s ||
                || %s ||
                %s
                """,
                "=".repeat(desc.length()+6),
                " ".repeat(desc.length()),
                desc,
                " ".repeat(desc.length()),
                "=".repeat(desc.length()+6)
        );
    }

    @Override
    public String toString(){
        return String.format("""
                ============================
                Photo File Details
                ============================
                This file shows a photo of %s
                %s
                """, super.getContent(), super.toString());
    }
}