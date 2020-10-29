package br.ufrn.imd.groupapp.views;
import br.ufrn.imd.groupapp.cache.Cache;
import br.ufrn.imd.groupapp.model.Group;
import br.ufrn.imd.groupapp.model.User;
import br.ufrn.imd.groupapp.service.GroupAppService;
import br.ufrn.imd.groupapp.service.RetrofitInitializer;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame implements ListSelectionListener {
    private JList<String> gList;
    private JPanel mainPane;
    private JButton joinButton;
    private JButton createGroupButton;
    private DefaultListModel<String> listModel;

    private List<Group> groups;
    private Long selectedGroup;

    private GroupAppService service;
    User user;

    public MainWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPane);
        this.pack();
        selectedGroup = -1L;

        listModel = new DefaultListModel<String>();
        gList.setModel(listModel);

        gList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gList.setSelectedIndex(0);
        gList.addListSelectionListener(this);
        gList.setVisibleRowCount(5);

        this.service = new RetrofitInitializer().groupAppService();
        this.refreshGroups();
        // -------------------------------------------
        // ---------------- EVENTOS ------------------
        // -------------------------------------------
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinSelectedGroup();
            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Digite o nome do grupo:");
                createGroup(name);
            }
        });
    }

    private void refreshGroups() {
        this.listModel.clear();
        try {
            groups = service.listGroups().execute().body();
            this.listModel.addAll(
                    groups.stream().map(group -> group.getTitle())
                        .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false && gList.getSelectedIndex() != -1) {
            Long newSelectedGroup = groups.get(gList.getSelectedIndex()).getId();
            if (newSelectedGroup == selectedGroup) {
                joinSelectedGroup();
            } else {
                selectedGroup = newSelectedGroup;
            }
        }
    }

    public void joinSelectedGroup() {
//        ChatWindow chatWindow = createChatWindow();
//            client.setWindow(chatWindow);
        try {
            Response<User> response = service.joinGroup(selectedGroup, Cache.getUsername()).execute();
            if(response.code() == 200) {
                user = response.body();
                System.out.println(user);
                // abrir janela
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        User user = service.joinGroup(selectedGroup, "")
//        if(clientGroup == null){
//            System.out.println("Grupo não existe");
//            this.refreshGroups();
//            return;
//        }
//        chatWindow.setClientGroup(clientGroup);
//        chatWindow.setVisible(true);

    }

//    private ChatWindow createChatWindow() {
//        ChatWindow chatWindow = new ChatWindow(this);
//        client.setWindow(chatWindow);
//        chatWindow.addWindowListener(new WindowListener() {
//            @Override public void windowOpened(WindowEvent e) { }
//            @Override public void windowClosing(WindowEvent e) {
//                MainWindow.this.refreshGroups();
//            }
//            @Override public void windowClosed(WindowEvent e) { }
//            @Override public void windowIconified(WindowEvent e) { }
//            @Override public void windowDeiconified(WindowEvent e) { }
//            @Override public void windowActivated(WindowEvent e) { }
//            @Override public void windowDeactivated(WindowEvent e) { }
//        });
//        return chatWindow;
//    }
    private void createGroup(String title) {

//        ChatWindow chatWindow = createChatWindow();

        try {
            Response<User> response = service.createGroup(Cache.getUsername(), new Group(title)).execute();
            if (response.code() == 200){
                user = response.body();
                // abrir janela
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//            chatWindow.setClientGroup(clientGroup);

//        chatWindow.setVisible(true);
    }
}