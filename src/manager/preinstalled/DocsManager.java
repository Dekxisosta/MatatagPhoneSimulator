package manager.preinstalled;

import manager.base.*;
import manager.system.*;
import model.preinstalled.*;

import java.util.*;

public class DocsManager extends Manager {
    private FileManager fileManager;

    private static int documentCounter;

    public DocsManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    public void addDocument(String content){
        fileManager.addFile(new Document(
                String.format("Document%04d", ++documentCounter),
                "docx",
                content,
                content.length()
        ));
    }
    public boolean isDocumentsEmpty(){return fileManager.getDocuments().isEmpty();}
    public void updateFileContent(Document document, String content){fileManager.updateFileContent(document, content);}
    public Document getDocument(int index){return getDocuments().get(index);}
    public List<Document> getDocuments(){return fileManager.getDocuments();}
    public void removeDocument(Document document){fileManager.removeFile(document);}
}
