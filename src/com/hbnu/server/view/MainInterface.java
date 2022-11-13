package com.hbnu.server.view;

import com.hbnu.server.Server;
import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.vo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月09日 8:14
 */
public class MainInterface extends JFrame {
    final int WIDTH = 1200;
    final int HEIGHT = 800;
    private static JTextArea log = new JTextArea(15, 50);
    private ServerSocket server;
    public static JFrame jf;

    public MainInterface() throws HeadlessException {
        setTitle("服务端");
        setBounds(300, 150, WIDTH, HEIGHT);
        setResizable(false);


        JPanel jPanel = new JPanel();

        Box vBox = Box.createVerticalBox();

        Box modeBox = Box.createHorizontalBox();
        JLabel connLabel = new JLabel();
        connLabel.setText("未启动服务");
        connLabel.setForeground(Color.red);
        connLabel.setSize(15, 15);
        modeBox.add(connLabel);

        Box connectBox = Box.createHorizontalBox();
        JButton connectBtn = new JButton("启动服务");
        connectBox.add(connectBtn);

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = connLabel.getText().trim();
                if (text.equals("未启动服务")) {
                    try {
                        server = new ServerSocket(9999);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    new Thread(new Server(server)).start();
                    connLabel.setText("服务已启动");
                    connLabel.setForeground(Color.green);
                    connectBtn.setText("关闭服务");
                    sendLog(true);
                } else {
                    try {
                        server.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Server.flag = false;
                    connLabel.setText("未启动服务");
                    connLabel.setForeground(Color.red);
                    connectBtn.setText("启动服务");
                    sendLog(false);
                }
            }
        });

//        组装下半部分
        Box mBox = Box.createHorizontalBox();

//        组装用户列表
//        Box operateBox = Box.createVerticalBox();
        Box operateBox = new UserManage();
//        组装操作按钮
/*        Box btnBox = Box.createHorizontalBox();
        JButton addBtn = new JButton("添加");
        JButton deleteBtn = new JButton("删除");
        JButton updateBtn = new JButton("修改");
        JButton inquiryBTN = new JButton("查询");
        btnBox.add(addBtn);
        btnBox.add(Box.createHorizontalStrut(20));
        btnBox.add(deleteBtn);
        btnBox.add(Box.createHorizontalStrut(20));
        btnBox.add(updateBtn);
        btnBox.add(Box.createHorizontalStrut(20));
        btnBox.add(inquiryBTN);
        operateBox.add(btnBox);

//        组装用户列表表头
        Box thBox = Box.createHorizontalBox();
        JLabel hp = new JLabel("头像");
        hp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel ulabel = new JLabel("用户名");
        JLabel nlabel = new JLabel("昵称");
        JLabel plabel = new JLabel("密码");
        JLabel ilabel = new JLabel("个人介绍");
        JLabel sqlabel = new JLabel("密保问题");
        JLabel calabel = new JLabel("密保答案");
        thBox.add(hp);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(ulabel);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(nlabel);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(plabel);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(ilabel);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(sqlabel);
        thBox.add(Box.createHorizontalStrut(10));
        thBox.add(calabel);
        operateBox.add(thBox);

//        获取用户列表
        try {
            getUser(operateBox);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

//        组装日志列表
        Box logBox = Box.createVerticalBox();
        JLabel logLabel = new JLabel("信息提示");

        JScrollPane jScrollPane = new JScrollPane(log, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logBox.add(logLabel);
        logBox.add(Box.createVerticalStrut(20));
        logBox.add(jScrollPane);


        mBox.add(operateBox);
        mBox.add(Box.createHorizontalStrut(40));
        mBox.add(logBox);

        vBox.add(modeBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(connectBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(mBox);

        jPanel.add(vBox);

        add(jPanel);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void getUser(Box box) throws Exception {
        List<User> users = DAOFactory.getUserDAOInstance().doQuery();
        for (int i = 0; i < users.size(); i++) {
            Box userBox = Box.createHorizontalBox();
            /*JLabel headPortrait = new JLabel();
            ImageIcon icon = new ImageIcon(users.get(i).getHeadPortrait());
            icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            headPortrait.setIcon(icon);
            headPortrait.setSize(50, 50);*/
            JLabel name = new JLabel(users.get(i).getName());
//            userBox.add(headPortrait);
            userBox.add(Box.createHorizontalStrut(20));
            userBox.add(name);
            box.add(userBox);
        }
    }

    public static void sendLog(boolean flag) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
        if (flag) {
            log.append("----------服务端已启动连接----------" + date);
        } else {
            log.append("----------服务端已关闭连接----------" + date);
        }
    }

    public static void sendLog(Map<String, String> params) {
        String username = params.get("username");
        String message = params.get("message");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
        log.append("----------" + username + " - " + message + "----------" + date);
    }

    public static void main(String[] args) {
        jf = new MainInterface();
    }
}
