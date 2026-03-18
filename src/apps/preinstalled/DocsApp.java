package apps.preinstalled;

import apps.base.*;
import manager.preinstalled.*;
import model.preinstalled.*;
import ui.preinstalled.*;

final class DocsAppConfig{
    static final String NAME = "Docs";
    static final String DEVELOPER = "Googuru";
    static final String VERSION = "v2.6.7";
    static final String APP_TYPE = "PREINSTALLED";
    static final double MEM_USED = 40;
    static final String[] COMMAND_NAMES = {
            "Create a note",
            "Read note",
            "Update a note",
            "Delete a note"
    };
    static final int[] COMMAND_DURATIONS = {
            5,
            5,
            5,
            5
    };
}
public class DocsApp extends App<DocsManager, DocsUI> {
    private Runnable[] runnables = {
            ()->{
                manager.addDocument(ui.promptText());
            },
            ()->{
                Document doc = manager.getDocument(ui.promptDocument(manager.getDocuments()) -1);
                if (doc == null){
                    ui.showUnexpectedError();
                    return;
                }
                ui.showDocumentContent(doc);
            },
            ()->{
                Document doc = manager.getDocument(ui.promptDocument(manager.getDocuments()) -1);
                if (doc == null){
                    ui.showUnexpectedError();
                    return;
                }
                manager.updateFileContent(doc, ui.promptText());
            },
            ()->{
                Document doc = manager.getDocument(ui.promptDocument(manager.getDocuments()) -1);
                if (doc == null){
                    ui.showUnexpectedError();
                    return;
                }
                manager.removeDocument(doc);
            }
    };
    private Command.BooleanSupplier[] checks = {
            null,
            ()->!manager.isDocumentsEmpty(),
            ()->!manager.isDocumentsEmpty(),
            ()->!manager.isDocumentsEmpty()
    };

    public DocsApp(
            DocsUI ui,
            DocsManager manager) {
        super(
                ui,
                manager,
                DocsAppConfig.NAME,
                DocsAppConfig.DEVELOPER,
                DocsAppConfig.VERSION,
                DocsAppConfig.APP_TYPE,
                DocsAppConfig.MEM_USED);
        configureCommands(
                DocsAppConfig.COMMAND_NAMES,
                DocsAppConfig.COMMAND_DURATIONS,
                runnables,
                checks

        );
    }
}
