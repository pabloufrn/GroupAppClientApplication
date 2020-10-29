package br.ufrn.imd.groupapp;

import br.ufrn.imd.groupapp.views.MainWindow;
import br.imd.ufrn.TwoClients.server.interfaces.ServerRemote;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        System.setProperty("java.rmi.server.hostname","25.6.51.213");

        try{
            Registry registry = LocateRegistry.getRegistry("25.97.85.1", 1010);
            ServerRemote stub = (ServerRemote) registry.lookup("MyServer");

            MainWindow mainWindow = new MainWindow("Multichat", stub);
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
