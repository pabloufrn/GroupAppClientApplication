package br.ufrn.imd.groupapp.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.*;
import java.rmi.RemoteException;
/*
public class ChatWindow extends JDialog {
    private JPanel chatPanel;
    private JTextField msgInput;
    private JButton sendButton;
    private JTextPane chatPane;

    private ClientGroupRemote clientGroup;

    public ChatWindow(JFrame parentWindow) {
//        super(title);
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setContentPane(chatPanel);
        super(parentWindow);
        this.setModal(true);
        this.setLocationRelativeTo(parentWindow);
        this.setContentPane(chatPanel);
        this.pack();

        this.clientGroup = clientGroup;

        // ----------------------------------------
        // ------------ SETUP ---------------------
        // ----------------------------------------

        writeChatMsg("Olá, seja bem vindo ao chat global.\n");

        // ----------------------------------------
        // ------------ PROPS ---------------------
        // ----------------------------------------
        sendButton.setEnabled(false);

        // ----------------------------------------
        // ----------- EVENTOS --------------------
        // ----------------------------------------
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) { }
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ChatWindow.this.clientGroup.leaveGroup();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
            @Override public void windowClosed(WindowEvent e) { }
            @Override public void windowIconified(WindowEvent e) { }
            @Override public void windowDeiconified(WindowEvent e) { }
            @Override public void windowActivated(WindowEvent e) { }
            @Override public void windowDeactivated(WindowEvent e) { }
        });

        msgInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               if(msgInput.getText().trim().length() == 1) {
                    sendButton.setEnabled(true);
               }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(msgInput.getText().trim().isBlank()){
                    sendButton.setEnabled(false);
                }
            }
            @Override public void changedUpdate(DocumentEvent e){}
        });

        msgInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dwfew");
                if(!msgInput.getText().trim().isBlank())
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
        try {
            this.clientGroup.sendMessage(msgInput.getText());
            sendButton.setEnabled(false);
            msgInput.setText("");
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
    }
    public void setClientGroup(ClientGroupRemote clientGroup) {
        this.clientGroup = clientGroup;
    }
}
*/