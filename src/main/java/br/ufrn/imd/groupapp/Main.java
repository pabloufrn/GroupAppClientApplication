package br.ufrn.imd.groupapp;

import br.ufrn.imd.groupapp.cache.Cache;
import br.ufrn.imd.groupapp.views.MainWindow;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String username = Cache.getUsername();
        if(username == null) {
            System.out.println("Digite seu nome: ");
            Scanner scanner = new Scanner(System.in);
            username = scanner.next();
            Cache.setUsername(username);
        }
        System.out.println("user: " + username);
        try{
            MainWindow mainWindow = new MainWindow("Multichat");
            mainWindow.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
