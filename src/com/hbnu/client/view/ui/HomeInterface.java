package com.hbnu.client.view.ui;

import com.hbnu.client.Client;
import com.hbnu.client.Communication;
import com.hbnu.client.view.component.*;
import com.hbnu.common.MsgObjectList;
import com.hbnu.common.MsgType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月03日 19:00
 */
public class HomeInterface extends JFrame {
    private static MsgObjectList ownData;
    private static List<MsgObjectList> onlineDatas;
    private static JLabel hpIcon;
    private static JLabel userLabel;
    public static String username;
    public static String name;
    final int WIDTH = 1200;
    final int HEIGHT = 800;

    public HomeInterface() {
        setBounds(300, 150, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("聊天程序(用户：" + username + ")");

        Box vBox = Box.createVerticalBox();

//        组装头像及菜单按钮
        Box headBox = Box.createHorizontalBox();
        headBox.setMaximumSize(new Dimension(1200, 40));
        hpIcon = new JLabel();
        userLabel = new JLabel();
        JTextField selectField = new JTextField(15);
        JButton selectBtn = new JButton("查询用户");
        JButton deleteBtn = new JButton("账号注销");
        JButton updateBtn = new JButton("修改账号信息");
        JButton logoutBtn = new JButton("退出");

        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = selectField.getText().trim();
                Map sendParams = new HashMap<>();
                Map data = new HashMap<>();
                data.put("username", username);
                sendParams.put("type", MsgType.SELECT_USER_REQUEST);
                sendParams.put("data", data);
                Communication.send(sendParams);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"确认", "取消"};
                int i = JOptionPane.showOptionDialog(Client.jf, "账号注销后无法恢复，是否确认？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "确认");
                if (i == 1) {
                    return;
                }
                Map sendParams = new HashMap<>();
                sendParams.put("type", MsgType.DELETE_USER_REQUEST);
                Communication.send(sendParams);
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map sendParams = new HashMap<>();
                sendParams.put("type", MsgType.GET_USER_INFO_REQUEST);
                Communication.send(sendParams);
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map sendParams = new HashMap<>();
                sendParams.put("type", MsgType.LOGOUT_REQUEST);
                Communication.send(sendParams);
            }
        });

        headBox.add(Box.createHorizontalStrut(10));
        headBox.add(hpIcon);
        headBox.add(Box.createHorizontalStrut(5));
        headBox.add(userLabel);
        headBox.add(Box.createHorizontalStrut(300));
        headBox.add(selectField);
        headBox.add(Box.createHorizontalStrut(10));
        headBox.add(selectBtn);
        headBox.add(Box.createHorizontalStrut(10));
        headBox.add(deleteBtn);
        headBox.add(Box.createHorizontalStrut(10));
        headBox.add(updateBtn);
        headBox.add(Box.createHorizontalStrut(10));
        headBox.add(logoutBtn);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(headBox);

//        组装下部分
        Box f_g_mBox = Box.createHorizontalBox();
        f_g_mBox.setMaximumSize(new Dimension(1200, 600));
//        在线列表
        Box onlinesBox = new OnlineManage();
//        onlinesBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        JPanel onlineJp = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        onlinesBox.setMaximumSize(new Dimension(200, 600));
//        JLabel onlinesTitle = new JLabel("在线成员");
//        onlineJp.add(onlinesTitle);
//        Box onlineBox = Box.createVerticalBox();
//        oScrollPane = new JScrollPane(onlineBox);
//        onlinesBox.add(onlineJp);
//        onlinesBox.add(oScrollPane);
//        消息列表
        Box groupMsgBox = new GroupMessageManage();
        Box privateMsgBox = new PrivateMessageManage();
//        messageBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        messageBox.setMaximumSize(new Dimension(WIDTH, HEIGHT));
//
//        JPanel msgTitleJp = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JLabel msgTitle = new JLabel("请选择好友或群聊");
//        msgTitleJp.add(msgTitle);
//        Box reMsgBox = Box.createVerticalBox();
//        reMsgBox.setMaximumSize(new Dimension(400, 400));
//        rmScrollPane = new JScrollPane(reMsgBox);
//        groupsJpanel.add(rmScrollPane);
//
//        Box seMsgBox = Box.createVerticalBox();
//        seMsgBox.setMaximumSize(new Dimension(400, 140));
//        JTextArea sendText = new JTextArea(10, 5);
//        sendText.setLineWrap(true);
//        JPanel sendJp = new JPanel();
//        sendJp.setLayout(new FlowLayout(FlowLayout.RIGHT));
//        JButton sendBtn = new JButton("发送");
//
////        设置消息列表
//        messageBox.setMaximumSize(new Dimension(380, 600));
//
//        seMsgBox.add(sendText);
//        sendJp.add(sendBtn);
//        seMsgBox.add(sendJp);
//        messageBox.add(msgTitleJp);
//        messageBox.add(Box.createVerticalStrut(30));
//        messageBox.add(reMsgBox);
//        messageBox.add(Box.createVerticalStrut(30));
//        messageBox.add(seMsgBox);

        f_g_mBox.add(Box.createHorizontalStrut(20));
        f_g_mBox.add(onlinesBox);
        f_g_mBox.add(Box.createHorizontalStrut(20));
        f_g_mBox.add(groupMsgBox);
        f_g_mBox.add(Box.createHorizontalStrut(20));
        f_g_mBox.add(privateMsgBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(f_g_mBox);

        add(vBox);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        requestData();
        getOnlines();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Map sendParams = new HashMap<>();
                sendParams.put("type", MsgType.LOGOUT_REQUEST);
                Communication.send(sendParams);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                setTitle("聊天程序(用户：" + name + ")");

            }
        });
    }

    public void requestData() {
//        获取用户名头像
        Map sendParams = new HashMap<>();
        sendParams.put("type", MsgType.GET_DATA_LIST);
        Communication.send(sendParams);
    }

    public void getOnlines() {
        Map sendParams = new HashMap<>();
        sendParams.put("type", MsgType.GET_ONLINE_LIST);
        Communication.send(sendParams);
    }

    public static MsgObjectList getOwnData() {
        return ownData;
    }

    public static void setOwnData(MsgObjectList ownData) {
        HomeInterface.ownData = ownData;
    }

    public static void setUsername(String username) {
        HomeInterface.username = username;
    }

    public static void setOnlineDatas(List<MsgObjectList> onlineDatas) {
        HomeInterface.onlineDatas = onlineDatas;
    }

    public static List<MsgObjectList> getOnlineDatas() {
        return onlineDatas;
    }

    //    设置用户头像昵称
    public static void setUser() {
        name = ownData.getName();
        userLabel.setText(name);
        username = ownData.getUsername();
        ImageIcon icon = new ImageIcon(ownData.getHeadPortrait());
        icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        hpIcon.setIcon(icon);
        hpIcon.setSize(30, 30);
        hpIcon.updateUI();
    }

/*    public static void setFriends(List<MsgObjectList> mols) {
        for (MsgObjectList mol : mols) {
            Box molBox = Box.createHorizontalBox();
            JLabel molLamel = new JLabel();
            ImageIcon icon = new ImageIcon(mol.getHeadPortrait());
            icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            molLamel.setIcon(icon);
            molLamel.setSize(20, 20);
            JLabel molName = new JLabel(mol.getName());
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molLamel);
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molName);
            molBox.add(Box.createHorizontalStrut(20));
            fScrollPane.add(molBox);
        }
    }

    public static void setGroups(List<MsgObjectList> mols) {
        for (MsgObjectList mol : mols) {
            Box molBox = Box.createHorizontalBox();
            JLabel molLamel = new JLabel();
            ImageIcon icon = new ImageIcon(mol.getHeadPortrait());
            icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            molLamel.setIcon(icon);
            molLamel.setSize(20, 20);
            JLabel molName = new JLabel(mol.getName());
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molLamel);
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molName);
            molBox.add(Box.createHorizontalStrut(20));
            gScrollPane.add(molBox);
        }
    }

    public static void setOnlines(List<MsgObjectList> mols) {
        for (MsgObjectList mol : mols) {
            Box molBox = Box.createHorizontalBox();
            JLabel molLamel = new JLabel();
            ImageIcon icon = new ImageIcon(mol.getHeadPortrait());
            icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            molLamel.setIcon(icon);
            molLamel.setSize(20, 20);
            JLabel molName = new JLabel(mol.getName());
            JButton molBtn = new JButton("加好友");
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molLamel);
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molName);
            molBox.add(Box.createHorizontalStrut(20));
            molBox.add(molBtn);
            oScrollPane.add(molBox);
        }
    }

    public static void setMessage() {

    }*/

}
