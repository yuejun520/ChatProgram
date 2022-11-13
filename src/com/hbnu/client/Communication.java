package com.hbnu.client;

import com.hbnu.client.view.component.GroupMessageManage;
import com.hbnu.client.view.component.OnlineManage;
import com.hbnu.client.view.component.PrivateMessageManage;
import com.hbnu.client.view.component.UpdateUserDialog;
import com.hbnu.client.view.ui.GetBackInterface;
import com.hbnu.client.view.ui.HomeInterface;
import com.hbnu.client.view.ui.MainInterface;
import com.hbnu.client.view.ui.RegistrationInterface;
import com.hbnu.common.Message;
import com.hbnu.common.MsgObjectList;
import com.hbnu.common.MsgType;
import com.hbnu.common.SocketCloseUtil;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月09日 21:55
 */
public class Communication implements Runnable {
    private InputStream is;
    private ObjectInputStream ois;
    private static OutputStream os;
    private static ObjectOutputStream oos;
    private static boolean flag = true;

    public Communication(Socket client) {
        try {
            is = client.getInputStream();
            os = client.getOutputStream();
        } catch (IOException e) {
            flag = false;
            SocketCloseUtil.closeAll(oos, os, is, client);
        }
    }

    public Map receive() {
        Map params = null;
        try {
            ois = new ObjectInputStream(is);
            System.out.println("接收数据");
            params = (Map) ois.readObject();
            System.out.println("存储数据");
        } catch (Exception e) {
            flag = false;
            SocketCloseUtil.closeAll(ois, is);
            System.out.println("错误");
        }
        return params;
    }

    public static void send(Map params) {
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(params);
            oos.flush();
        } catch (IOException e) {
            flag = false;
            SocketCloseUtil.closeAll(oos, os);
        }
    }

    @Override
    public void run() {
        while (flag) {
            Map receiveParams = receive();
            Map data;
            if (receiveParams == null)
                continue;
            MsgType type = (MsgType) receiveParams.get("type");
            if (type == MsgType.LOGIN_SUCCESSFUL) {
                Client.jf.dispose();
                Client.jf = new HomeInterface();
            } else if (type == MsgType.LOGIN_FAILED) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
            } else if (type == MsgType.SECURITY_QUESTIONS_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                String securityQuestion = (String) data.get("securityQuestion");
                GetBackInterface.setSecurityQuestion(securityQuestion);
            } else if (type == MsgType.SECURITY_QUESTIONS_FAILED) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
            } else if (type == MsgType.GET_BACK_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                String username = (String) data.get("username");
                String password = (String) data.get("password");
                JOptionPane.showMessageDialog(Client.jf, "您的密码为：" + password);
                Client.jf.dispose();
                Client.jf = new MainInterface();
                MainInterface.setUser(username, password);
            } else if (type == MsgType.GET_USERNAME_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                String username = (String) data.get("username");
                RegistrationInterface.setUsername(username);
            } else if (type == MsgType.GET_USERNAME_FAILED) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
                Client.jf.dispose();
                Client.jf = new MainInterface();
            } else if (type == MsgType.REGISTRATION_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                String username = (String) data.get("username");
                String password = (String) data.get("password");
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
                Client.jf.dispose();
                Client.jf = new MainInterface();
                MainInterface.setUser(username, password);
            } else if (type == MsgType.REGISTRATION_FAILED) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
            } else if (type == MsgType.SERVER_ERROR) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
            } else if (type == MsgType.GET_DATA_LIST_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                HomeInterface.setOwnData((MsgObjectList) data.get("ownData"));
                HomeInterface.setUser();
                JOptionPane.showMessageDialog(Client.jf, "获取用户信息成功");
            } else if (type == MsgType.GET_ONLINE_LIST_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                List<MsgObjectList> mols = (List<MsgObjectList>) data.get("list");
                mols.removeIf(mol -> mol.getUsername().equals(HomeInterface.username));
                HomeInterface.setOnlineDatas(mols);
                System.out.println(data.get("list"));
                OnlineManage.setOnlines();
            } else if (type == MsgType.GET_USER_INFO_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                String name = (String) data.get("name");
                String password = (String) data.get("password");
                String introduction = (String) data.get("introduction");
                String securityQuestion = (String) data.get("securityQuestion");
                String classifiedAnswer = (String) data.get("classifiedAnswer");
                byte[] headPortrait = (byte[]) data.get("headPortrait");
                new UpdateUserDialog(Client.jf, "修改信息", true, HomeInterface.username, name, password, introduction, securityQuestion, classifiedAnswer, headPortrait).setVisible(true);
            } else if (type == MsgType.UPDATE_USER_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                HomeInterface.setOwnData((MsgObjectList) data.get("ownData"));
                HomeInterface.setUser();
            } else if (type == MsgType.SELECT_USER_SUCCESSFUL) {
                data = (Map) receiveParams.get("data");
                MsgObjectList mol = (MsgObjectList) data.get("mol");
                JOptionPane.showMessageDialog(Client.jf, "用户名：" + mol.getUsername() + " 昵称" + mol.getName());
            } else if (type == MsgType.SELECT_USER_FALIED) {
                JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
            } else if (type == MsgType.LOGIN_OUT) {
                JOptionPane.showMessageDialog(Client.jf, "账号在其他客户端登录");
                Client.jf.dispose();
                Client.jf = new MainInterface();
            } else if (type == MsgType.SEND_MESSAGE_PRIVATE) {
                data = (Map) receiveParams.get("data");
                String otherId = (String) data.get("otherId");
                Message message = (Message) data.get("message");
                PrivateMessageManage.addMessage(otherId, message);
            } else if (type == MsgType.SEND_MESSAGE_GROUP) {
                data = (Map) receiveParams.get("data");
                Message message = (Message) data.get("message");
                GroupMessageManage.addMessage(message);
            } else if (type == MsgType.LOGOUT_SUCCESSFUL) {
                try {
                    JOptionPane.showMessageDialog(Client.jf, receiveParams.get("message"));
                    Client.jf.dispose();
                    Client.jf = new MainInterface();
                } catch (Exception e) {
                    System.out.println("客户端已关闭");
                }
            }
        }
        System.out.println("连接断开");
    }
}
