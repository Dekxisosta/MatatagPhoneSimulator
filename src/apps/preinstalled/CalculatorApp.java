package apps.preinstalled;

import apps.base.*;
import manager.base.*;
import ui.preinstalled.*;

final class CalculatorAppConfig{
    static final String NAME = "Calculator";
    static final String DEVELOPER = "MatatagCompany";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "PREINSTALLED";
    static final double MEM_USED = 10;
    static final String[] COMMAND_NAMES = {
            "Add two numbers",
            "Subtract two numbers",
            "Multiply two numbers",
            "Divide two numbers"
    };
    static final int[] COMMAND_DURATIONS = {
            3,
            3,
            3,
            3
    };
}
public class CalculatorApp extends App<Manager, CalculatorUI>{
    private Runnable[] runnables = {
            ()->{
                int num1 = ui.promptNumber("first");
                int num2 = ui.promptNumber("second");
                ui.showResult("Sum", num1+num2);
            },
            ()->{
                int num1 = ui.promptNumber("minuend");
                int num2 = ui.promptNumber("subtrahend");
                ui.showResult("Difference", num1-num2);
            },
            ()->{
                int num1 = ui.promptNumber("multiplicand");
                int num2 = ui.promptNumber("multiplier");
                ui.showResult("Product", num1*num2);
            },
            ()->{
                int num1 = ui.promptNumber("dividend");
                int num2 = ui.promptNumber("divisor");
                if(num2==0){
                    ui.showZeroFallback();
                    return;
                }
                ui.showResult("Quotient", num1/num2);
            }
    };
    private Command.BooleanSupplier[] checks = {
            null,
            null,
            null,
            null
    };
    public CalculatorApp(
            CalculatorUI ui,
            Manager manager
    ) {
        super(
                ui,
                manager,
                CalculatorAppConfig.NAME,
                CalculatorAppConfig.DEVELOPER,
                CalculatorAppConfig.VERSION,
                CalculatorAppConfig.APP_TYPE,
                CalculatorAppConfig.MEM_USED
        );
        configureCommands(
                CalculatorAppConfig.COMMAND_NAMES,
                CalculatorAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
    }
}
