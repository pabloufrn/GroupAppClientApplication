package br.ufrn.imd.groupapp.views;

import br.ufrn.imd.groupapp.model.Message;
import br.ufrn.imd.groupapp.model.User;
import br.ufrn.imd.groupapp.service.GroupAppService;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatWindow extends JDialog {
    private JPanel chatPanel;
    private JTextField msgInput;
    private JButton sendButton;
    private JTextPane chatPane;
    private final User user;
    private final GroupAppService service;
    private Date lastUpdate;

    public ChatWindow(JFrame parentWindow, User user, GroupAppService service) {
//        super(title);
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setContentPane(chatPanel);

        super(parentWindow);
        this.setModal(true);
        this.setLocationRelativeTo(parentWindow);
        this.setContentPane(chatPanel);
        this.pack();


        this.service = service;
        this.user = user;
        this.lastUpdate = new Date(0L);


        // ----------------------------------------
        // ------------ SETUP ---------------------
        // ----------------------------------------

        //writeChatMsg("Olá, seja bem vindo ao chat global.\n");
        loadMessages();

        // ----------------------------------------
        // ------------ PROPS ---------------------
        // ----------------------------------------
        sendButton.setEnabled(false);

        // ----------------------------------------
        // ----------- EVENTOS --------------------
        // ----------------------------------------
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                leaveGroup();
            }
        });

        msgInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (msgInput.getText().trim().length() == 1) {
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (msgInput.getText().trim().isBlank()) {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        msgInput.addActionListener(e -> {
            if (!msgInput.getText().trim().isBlank()) {
                sendChatMsg();
            }
        });
        sendButton.addActionListener(e -> {
            // botão clicado
            sendChatMsg();
        });
    }

    public void writeChatMsg(String msg) {
        SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
        StyledDocument styledDocument = chatPane.getStyledDocument();
        try {
            styledDocument.insertString(styledDocument.getLength(), msg + "\n", simpleAttributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void sendChatMsg() {
        sendButton.setEnabled(false);
        try {
            Message message = new Message(null, msgInput.getText(), user, user.getGroup(), null);
            service.sendMessage(message).execute();
            loadMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendButton.setEnabled(true);
    }

/*    public void setClientGroup(ClientGroupRemote clientGroup) {
        this.clientGroup = clientGroup;
    }*/

    private void leaveGroup() {
        try {
            service.leaveGroup(user.getId()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMessages() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String dateString = dateFormat.format(lastUpdate);
            Response<List<Message>> response = service.getMessages(user.getGroup().getId(), dateString).execute();
            if (response.code() == 200) {
                List<Message> messages = response.body();
                if (messages != null) {
                    Date newLastUpdate = lastUpdate;
                    for (Message msg : messages) {
                        String msgString = msg.getUser().getName() + ": " + msg.getText();
                        writeChatMsg(msgString);
                        if (msg.getDate().after(newLastUpdate)) {
                            newLastUpdate = msg.getDate();
                        }
                    }
                    lastUpdate = newLastUpdate;
                }
            } else {
                System.err.println("Error:" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
