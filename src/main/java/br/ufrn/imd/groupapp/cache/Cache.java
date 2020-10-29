package br.ufrn.imd.groupapp.cache;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cache {
    private static String username = null;

    public static String getUsername() {
        if (username != null) {
            return username;
        }
        try {
            File file = new File(".username");
            if(file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNext()) {
                    username = scanner.next();
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
        try {
            File file = new File(".username");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(username + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
