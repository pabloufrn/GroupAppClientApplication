package br.ufrn.imd.groupapp;

import br.ufrn.imd.groupapp.model.Group;
import br.ufrn.imd.groupapp.service.GroupAppService;
import br.ufrn.imd.groupapp.service.RetrofitInitializer;
//import br.ufrn.imd.groupapp.views.MainWindow;
import retrofit2.Callback;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
//
//            MainWindow mainWindow = new MainWindow("Multichat");
//            mainWindow.setVisible(true);

//            ChatWindow chatWindow = new ChatWindow("Multichat");
//            chatWindow.setVisible(true);
//            Client client = new Client(chatWindow, "Pablo");
//            Client client = new Client(chatWindow, "Pablo");
//            ClientGroupRemote clientGroup = stub
//            ♦.registerClient(client);
//            chatWindow.setClientGroup(clientGroup);

            GroupAppService groupAppService = new RetrofitInitializer().groupAppService();

            List<Group> groups = groupAppService.listGroups().execute().body();

            System.out.printf(groups.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
