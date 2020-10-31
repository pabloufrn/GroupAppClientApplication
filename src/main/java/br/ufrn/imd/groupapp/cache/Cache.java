package br.ufrn.imd.groupapp.cache;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Cache {
    private static String username = null;
    private static Integer file_number = 0;
    private static FileLock lock = null;
    private static RandomAccessFile file = null;

    public static String getUsername() {
        if (username != null) {
            return username;
        }
        try {
            file = new RandomAccessFile(
                    ".username_" + file_number.toString(), "rw");
            FileChannel channel = file.getChannel();
            lock = channel.tryLock();
            if(lock == null) {
                file_number += 1;
                return getUsername();
            }
            Scanner scanner = new Scanner(channel);
            if (scanner.hasNext()) {
                username = scanner.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
        try {
            if(file == null) {
                file = new RandomAccessFile(
                        ".username_" + file_number.toString(), "rw");
            }
            FileChannel channel = file.getChannel();
            if(lock == null)
                lock = channel.tryLock();
            else
                System.out.println(lock);
            channel.write(
                    ByteBuffer.wrap((username + "\n").getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void close() throws IOException {
        lock.close();
        file.close();
    }
}
