package br.ufrn.imd.groupapp.views;

import br.ufrn.imd.groupapp.controllers.Client;
import br.imd.ufrn.TwoClients.server.interfaces.ClientGroupRemote;
import br.imd.ufrn.TwoClients.server.interfaces.GroupRemote;
import br.imd.ufrn.TwoClients.server.interfaces.ServerRemote;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame implements ListSelectionListener {
    private JList<String> gList;
    private JPanel mainPane;
    private JButton joinButton;
    private JButton createGroupButton;
    private final DefaultListModel<String> listModel;

    private List<GroupRemote> groups;
    private Integer selectedGroup;
    Client client;

    ServerRemote stub;

    public MainWindow(String title, ServerRemote stub) {
        super(title);
        this.stub = stub;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPane);
        this.pack();
        selectedGroup = -1;

        listModel = new DefaultListModel<>();
        gList.setModel(listModel);

        gList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gList.setSelectedIndex(0);
        gList.addListSelectionListener(this);
        gList.setVisibleRowCount(5);

        this.client = new Client("Pablo");

//        try {
//            stub.createGroup("dwe", client);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

        // -------------------------------------------
        // --------------- CARREGAR ------------------
        // -------------------------------------------
        this.refreshGroups();
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
        try {
            groups = stub.listGroups();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.listModel.clear();
        this.listModel.addAll(
                groups.stream().map(group -> {
                    try {
                        return group.getName();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return "";
                    }
                }).collect(Collectors.toList())
        );
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false && gList.getSelectedIndex() != -1) {
            Integer newSelectedGroup = null;
            try {
                newSelectedGroup = groups.get(gList.getSelectedIndex()).getId();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            if (newSelectedGroup == selectedGroup) {
                joinSelectedGroup();
            } else {
                selectedGroup = newSelectedGroup;
            }
        }
    }

    public void joinSelectedGroup() {
        ChatWindow chatWindow = createChatWindow();
        try {
            client.setWindow(chatWindow);
            ClientGroupRemote clientGroup = stub.enterGroup(selectedGroup, client);
            if(clientGroup == null){
                System.out.println("Grupo não existe");
                this.refreshGroups();
                return;
            }
            chatWindow.setClientGroup(clientGroup);
            chatWindow.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ChatWindow createChatWindow() {
        ChatWindow chatWindow = new ChatWindow(this);
        client.setWindow(chatWindow);
        chatWindow.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) { }
            @Override public void windowClosing(WindowEvent e) {
                MainWindow.this.refreshGroups();
            }
            @Override public void windowClosed(WindowEvent e) { }
            @Override public void windowIconified(WindowEvent e) { }
            @Override public void windowDeiconified(WindowEvent e) { }
            @Override public void windowActivated(WindowEvent e) { }
            @Override public void windowDeactivated(WindowEvent e) { }
        });
        return chatWindow;
    }
    private void createGroup(String name) {

        ChatWindow chatWindow = createChatWindow();
        try {
            ClientGroupRemote clientGroup = stub.createGroup(name, client);
            chatWindow.setClientGroup(clientGroup);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        chatWindow.setVisible(true);
    }
}