package com.hbnu.client.view.component;

import com.hbnu.client.view.ui.HomeInterface;
import com.hbnu.common.MsgObjectList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月10日 2:51
 */
public class OnlineManage extends Box {
    private static Box onlineBox;
    private static JScrollPane oScrollPane;
    final int WIDTH = 200;
    final int HEIGHT = 600;

    public OnlineManage() {
        super(BoxLayout.Y_AXIS);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        JPanel onlineJp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        onlineJp.setMaximumSize(new Dimension(180, 100));
        JLabel onlinesTitle = new JLabel("在线成员");
        onlineJp.add(onlinesTitle);
        onlineBox = Box.createVerticalBox();
        onlineBox.setMaximumSize(new Dimension(180, 500));
        oScrollPane = new JScrollPane(onlineBox);
        add(onlineJp);
        add(oScrollPane);
    }

    public static void setOnlines() {
        onlineBox = Box.createVerticalBox();
        onlineBox.setMaximumSize(new Dimension(180, 500));
        List<MsgObjectList> mols = HomeInterface.getOnlineDatas();
        for (MsgObjectList mol : mols) {
            Box molBox = new Views(mol, false, true);
            onlineBox.add(molBox);
            System.out.println(mol);
        }
        oScrollPane.setViewportView(onlineBox);
    }
}
