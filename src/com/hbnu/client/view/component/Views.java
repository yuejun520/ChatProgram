package com.hbnu.client.view.component;

import com.hbnu.common.MsgObjectList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author luanhao
 * @date 2022年11月10日 4:08
 */
public class Views extends Box {
    private String username;
    private String name;

    public Views(MsgObjectList mol, boolean isGroup, boolean isOnline) {
        super(BoxLayout.X_AXIS);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(180, 30));

        this.username = mol.getUsername();
        this.name = mol.getName();

        JLabel molLamel = new JLabel();
        ImageIcon icon = new ImageIcon(mol.getHeadPortrait());
        icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        molLamel.setIcon(icon);
        molLamel.setSize(20, 20);
        JLabel molName = new JLabel(mol.getName());
        JButton molBtn = new JButton("私聊");

        molBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrivateMessageManage.update(username, name);
            }
        });

        add(Box.createHorizontalStrut(20));
        add(molLamel);
        add(Box.createHorizontalStrut(20));
        add(molName);
        add(Box.createHorizontalStrut(20));
        add(molBtn);
        add(Box.createHorizontalStrut(20));

    }
}
