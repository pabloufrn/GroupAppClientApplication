package br.ufrn.imd.groupapp.views;

public class LoadGroupsTask implements Runnable{
    MainWindow mainWindow;

    LoadGroupsTask(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    @Override
    public void run() {
        this.mainWindow.refreshGroups();
    }
}
