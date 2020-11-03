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
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatWindow extends JDialog {
    private JPanel chatPanel;
    private JTextField msgInput;
    private JButton sendButton;
    private JTextPane chatPane;
    private final User user;
    private final GroupAppService service;
    private Date lastUpdate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ChatWindow(JFrame parentWindow, User user, GroupAppService service) {
        super(parentWindow);
        this.setModal(true);
        this.setLocationRelativeTo(parentWindow);
        this.setContentPane(chatPanel);
        this.pack();

        this.setTitle(user.getGroup().getTitle());
        this.service = service;
        this.user = user;
        this.lastUpdate = new Date(0L);

        LoadMessagesTask task = new LoadMessagesTask(this);
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

        // ----------------------------------------
        // ------------ SETUP ---------------------
        // ----------------------------------------


        // ----------------------------------------
        // ------------ PROPS ---------------------
        // ----------------------------------------
        sendButton.setEnabled(false);

        // ----------------------------------------
        // ----------- EVENTOS --------------------
        // ----------------------------------------
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                scheduler.shutdown();
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
            Message message = new Message(null, msgInput.getText(), null, null, null);
            service.sendMessage(message, user.getId()).execute();
            msgInput.setText("");
            loadNewMessages();
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

    public void loadNewMessages() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String dateString = dateFormat.format(lastUpdate);
            Response<List<Message>> response = service.getMessages(user.getGroup().getId(), dateString).execute();
            if (response.code() == 200) {
                List<Message> messages = response.body();
                if (messages != null) {
                    Date newLastUpdate = lastUpdate;
                    for (Message msg : messages) {
                        String msgString = msg.getAuthor() != null ? msg.getAuthor() + ": " : "";
                        msgString += msg.getText();
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
