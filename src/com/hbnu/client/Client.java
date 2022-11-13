package com.hbnu.client;

import com.hbnu.client.view.ui.MainInterface;

import javax.swing.*;
import java.net.Socket;

/**
 * @author luanhao
 * @date 2022年11月03日 17:06
 */
public class Client {
    public static JFrame jf;
    public static void main(String[] args) throws Exception {
        jf = new MainInterface();
        Socket client = new Socket("localhost", 9999);

        Communication communication = new Communication(client);
        new Thread(communication).start();


    }
}
