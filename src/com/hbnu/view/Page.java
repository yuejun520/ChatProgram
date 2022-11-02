package com.hbnu.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author luanhao
 * @date 2022年11月01日 20:10
 */
public class Page {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame jf = new JFrame();

            jf.setTitle("OO");
            jf.setBounds(300, 150, 450, 800);
            jf.setResizable(false);
            jf.setVisible(true);

            jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });
    }
}
