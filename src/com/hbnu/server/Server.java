package com.hbnu.server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月03日 17:35
 */
public class Server implements Runnable {
    public static List<MyChannel> loginChannels  =new ArrayList<>();
    public static boolean flag = true;
    public ServerSocket server;

    public Server(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        System.out.println("----------服务器开启---------");

        while (flag) {
            Socket socket = null;
            try {
                socket = server.accept();
                System.out.println("获取连接");
            } catch (IOException e) {
                System.out.println("未接收到客户端连接");
                continue;
            }
            MyChannel channel = new MyChannel(socket);
            new Thread(channel).start();
        }
    }
}
