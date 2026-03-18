import core.*;

public class Main {
    public static void main(String[] args){
        try{
            new BootLoader().launch();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}