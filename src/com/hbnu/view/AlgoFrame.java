package com.hbnu.view;

import javax.swing.*;

/**
 * @author luanhao
 * @date 2022年11月01日 20:41
 */
public class AlgoFrame extends JFrame {
    private int canvaxWidth;
    private int canvaxHeight;
    private int leftMargin;
    private int topMargin;

    public AlgoFrame(String title, int canvaxWidth, int canvaxHeight) {
        super(title);

        this.setBounds(300, 150, 450, 800);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }
}
