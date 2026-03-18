package apps.store;

import apps.base.*;
import manager.base.*;
import ui.base.*;

public class BuggyApp extends App {
    public BuggyApp(UI ui, Manager manager) {
        super(
                ui,
                manager,
                "BayolaHub",
                "Wally",
                "STORE",
                67);
    }
    @Override
    public void launch(){
        throw new IllegalStateException("%*(^)$(%$*(^$)()(@*#_&(%");
    }
}