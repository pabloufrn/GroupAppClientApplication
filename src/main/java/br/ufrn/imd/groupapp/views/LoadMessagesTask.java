package br.ufrn.imd.groupapp.views;

public class LoadMessagesTask implements Runnable{
    ChatWindow chatWindow;

    LoadMessagesTask(ChatWindow chatWindow){
        this.chatWindow = chatWindow;
    }

    @Override
    public void run() {
        this.chatWindow.loadNewMessages();
    }
}
