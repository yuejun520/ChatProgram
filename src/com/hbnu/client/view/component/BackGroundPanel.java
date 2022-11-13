package com.hbnu.client.view.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author luanhao
 * @date 2022年11月03日 12:44
 */
public class BackGroundPanel extends JPanel {

    private Image backIcon;

    public BackGroundPanel() {
    }

    public BackGroundPanel(Image backIcon) {
        this.backIcon = backIcon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backIcon, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
