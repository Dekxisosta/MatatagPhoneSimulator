package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppReader {
    private static AppReader instance;
    private final BufferedReader reader;

    private AppReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static AppReader getInstance(){
        if(instance == null) instance = new AppReader();
        return instance;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}