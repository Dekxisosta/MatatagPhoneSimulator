package ui.preinstalled;

import ui.base.*;

public class CalculatorUI extends UI {
    public int promptNumber(String valueType){
        System.out.print(String.format("Enter %s: ", valueType));
        return super.getInt();
    }
    public void showResult(String type, int result){
        System.out.println(String.format("%s: %d", type, result));
    }
    public void showZeroFallback(){
        System.out.println("[ERROR] Divisor cannot be zero.");
    }
}
