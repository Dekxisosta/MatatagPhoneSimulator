package ui.base;

import apps.base.*;
import util.*;

import java.io.*;
import java.util.*;

public class UI {
    protected AppReader reader;

    public interface Supplier<T>{
        T get() throws IOException;
    }

    public UI(){
        reader = AppReader.getInstance();
    }

    public void printBanner(String name){
        System.out.println(String.format("""
                ++==%s==++
                ||  %s  ||
                ++==%s==++""",
                "-".repeat(name.length()),
                name,
                "-".repeat(name.length())
        ));
    }
    public Command promptMenu(String name, List<Command> commands){
        if(commands.isEmpty()) throw new IllegalArgumentException("Menu must always be populated.");

        List<Command> available = new ArrayList<>();
        for(Command command : commands){
            if(command.isAvailable()) available.add(command);
        }

        if(available.isEmpty()) throw new IllegalStateException("No available commands.");

        printBanner(name);

        for(int i = 1; i < available.size(); i++){
            System.out.println(String.format("[%d] %s", i, available.get(i).getName()));
        }

        System.out.println(String.format("[%d] %s", 0, available.get(0).getName()));

        System.out.print("Choose: ");
        return available.get(getIntWithinRange(0, available.size()-1));
    }



    protected int getInt(){
        return supply(()->Integer.parseInt(reader.readLine()));
    }
    protected String getString(){return supply(()->reader.readLine().trim());}
    protected int getIntWithinRange(int min, int max){
        return supply(() -> {
            int value = Integer.parseInt(reader.readLine());
            if(value < min || value > max)
                throw new IllegalArgumentException("Enter a number between " + min + " and " + max + ".");
            return value;
        });
    }


    protected <T>T supply(Supplier<T> supplier){
        while(true) {
            try {
                return supplier.get();
            } catch (IOException | NumberFormatException e) {
                System.out.println("[ERROR] Input error: " + e.getMessage());
                System.out.print("Enter new input: ");
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
                System.out.print("Enter new input: ");
            }
        }
    }

}
