package br.ufrn.imd.groupapp;

import br.ufrn.imd.groupapp.cache.Cache;
import br.ufrn.imd.groupapp.model.Group;
import br.ufrn.imd.groupapp.service.GroupAppService;
import br.ufrn.imd.groupapp.service.RetrofitInitializer;
import br.ufrn.imd.groupapp.views.MainWindow;

import java.util.List;
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
//
            MainWindow mainWindow = new MainWindow("Multichat");
            mainWindow.setVisible(true);

//            ChatWindow chatWindow = new ChatWindow("Multichat");
//            chatWindow.setVisible(true);
//            Client client = new Client(chatWindow, "Pablo");
//            Client client = new Client(chatWindow, "Pablo");
//            ClientGroupRemote clientGroup = stub
//            ♦.registerClient(client);
//            chatWindow.setClientGroup(clientGroup);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
