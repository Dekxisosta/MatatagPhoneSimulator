package ui.preinstalled;

import ui.base.*;
import model.preinstalled.Document;

import java.util.*;

public class DocsUI extends UI {
    public int promptDocument(List<Document> documents){
        for(int i=0;i<documents.size();i++){
            System.out.println(String.format("[%d] %s", (i+1), documents.get(i).getFileName()));
        }
        System.out.println("[0] Back");
        System.out.println("Enter: ");
        return getIntWithinRange(0, documents.size());
    }
    public String promptText(){
        System.out.println("Start typing in one line...");
        return getString();
    }
    public void showDocumentContent(Document document){System.out.println(document.getContent());}
    public void showUnexpectedError(){
        System.out.println("[ERROR] Unexpected error happened");
    }
}
