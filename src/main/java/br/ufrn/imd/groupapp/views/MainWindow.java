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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
        try {
            Response<User> response = service.joinGroup(
                    selectedGroup, Cache.getUsername()).execute();
            loadGroup(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGroup(String title) {
        try {
            Response<User> response = service.createGroup(Cache.getUsername(), new Group(title)).execute();
            loadGroup(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGroup(Response<User> response) {
        if (response.code() == 200) {
            user = response.body();
            ChatWindow chatWindow = createChatWindow();
            chatWindow.setVisible(true);
        } else {
            System.err.println("Error:" + response);
        }
    }

    private ChatWindow createChatWindow() {
        ChatWindow chatWindow = new ChatWindow(this, user, service);
        chatWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainWindow.this.refreshGroups();
            }
        });
        return chatWindow;
    }

}