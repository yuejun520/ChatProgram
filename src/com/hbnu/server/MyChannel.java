package com.hbnu.server;

import com.hbnu.common.MsgObjectList;
import com.hbnu.common.MsgType;
import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.util.RandomUsername;
import com.hbnu.common.SocketCloseUtil;
import com.hbnu.server.jdbc.vo.*;
import com.hbnu.server.view.MainInterface;
import com.hbnu.server.view.UserManage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月03日 17:44
 */
public class MyChannel implements Runnable {
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;
    private String username;
    private User user = null;
    private boolean flag = true;

    public MyChannel(Socket client) {
        try {
            is = client.getInputStream();
            os = client.getOutputStream();
        } catch (IOException e) {
            flag = false;
            SocketCloseUtil.closeAll(os, is, client);
        }
    }

    public Map receive() {
        Map params = new HashMap<>();
        try {
            ois = new ObjectInputStream(is);
            params = (Map) ois.readObject();
        } catch (Exception e) {
            flag = false;
            SocketCloseUtil.closeAll(ois, is);
            Server.loginChannels.remove(this);
        }
        return params;
    }

    public void send(Map params) {
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(params);
            oos.flush();
        } catch (IOException e) {
            flag = false;
            SocketCloseUtil.closeAll(oos, os);
            Server.loginChannels.remove(this);
        }
    }

    public void sendOther(Map params) {
        List<MyChannel> channels = Server.loginChannels;
        for (MyChannel other : channels) {
            if (other == this) {
                continue;
            }
            other.send(params);
        }
    }

    public void sendOther(Map params, String otherUsername) {
        List<MyChannel> channels = Server.loginChannels;
        for (MyChannel other : channels) {
            if (other.username.equals(otherUsername)) {
                other.send(params);
                break;
            }
        }
    }

    @Override
    public void run() {
        while (flag) {
            Map receiveParams = receive();
            Map data;
            Map sendParams = new HashMap<>();
            Map<String, String> params = new HashMap<>();
            MsgType type = (MsgType) receiveParams.get("type");
            System.out.println(type);
            if (type == MsgType.LOGIN_REQUEST) {
                data = (Map) receiveParams.get("data");
                String username = (String) data.get("username");
                String password = (String) data.get("password");
                User user = null;
                try {
                    user = DAOFactory.getUserDAOInstance().doQueryById(Integer.parseInt(username));
                } catch (Exception e) {
                    sendParams.put("type", MsgType.LOGIN_FAILED);
                    sendParams.put("message", "数据库连接出错，请稍后重试");
                    send(sendParams);
                }
                if (user == null) {
                    sendParams.put("type", MsgType.LOGIN_FAILED);
                    sendParams.put("message", "该用户名不存在");
                    send(sendParams);
                }
                if (String.valueOf(user.getId()).equals(username) && user.getPassword().equals(password)) {
                    List<MyChannel> logins = Server.loginChannels;
                    for (MyChannel other : logins) {
                        if (other.username.equals(username)) {
                            sendParams.put("type", MsgType.LOGIN_OUT);
                            sendParams.put("message", "您的账号在其他客户端登录");
                            sendOther(sendParams);
                            Server.loginChannels.remove(other);
                        }
                    }
                    this.username = username;
                    this.user = user;
                    Server.loginChannels.add(this);
                    params.put("username", username);
                    params.put("message", "登录成功");
                    MainInterface.sendLog(params);
                    sendParams.put("type", MsgType.LOGIN_SUCCESSFUL);
                    sendParams.put("message", "登陆成功");
                    send(sendParams);
                } else {
                    sendParams.put("type", MsgType.LOGIN_FAILED);
                    sendParams.put("message", "密码错误");
                    send(sendParams);
                }
            } else if (type == MsgType.SECURITY_QUESTIONS_REQUEST) {
                data = (Map) receiveParams.get("data");
                String username = (String) data.get("username");
                try {
                    user = DAOFactory.getUserDAOInstance().doQueryById(Integer.parseInt(username));
                } catch (Exception e) {
                    sendParams.put("type", MsgType.SECURITY_QUESTIONS_FAILED);
                    sendParams.put("message", "服务器出错，请稍后重试");
                    send(sendParams);
                }
                String sq = user.getSecurityQuestion();
                data = new HashMap<>();
                data.put("securityQuestion", sq);
                sendParams.put("type", MsgType.SECURITY_QUESTIONS_SUCCESSFUL);
                sendParams.put("data", data);
                send(sendParams);
            } else if (type == MsgType.GET_BACK_REQUEST) {
                data = (Map) receiveParams.get("data");
                String ca = (String) data.get("classifiedAnswer");
                if (ca.equals(user.getClassifiedAnswer())) {
                    data = new HashMap<>();
                    data.put("username", String.valueOf(user.getId()));
                    data.put("password", user.getPassword());
                    sendParams.put("type", MsgType.GET_BACK_SUCCESSFUL);
                    sendParams.put("data", data);
                    send(sendParams);
                } else {
                    sendParams.put("type", MsgType.GET_BACK_FAILED);
                    sendParams.put("message", "密保答案错误，请重试");
                }
            } else if (type == MsgType.GET_USERNAME_REQUEST) {
                String username;
                try {
                    username = new RandomUsername().getUsername();
                    data = new HashMap<>();
                    data.put("username", username);
                    sendParams.put("type", MsgType.GET_USERNAME_SUCCESSFUL);
                    sendParams.put("data", data);
                    send(sendParams);
                } catch (Exception e) {
                    sendParams.put("type", MsgType.GET_USERNAME_FAILED);
                    sendParams.put("message", "请求用户名失败，请重试");
                    send(sendParams);
                }
            } else if (type == MsgType.REGISTRATION_REQUEST) {
                data = (Map) receiveParams.get("data");
                User userRegist = new User();
                userRegist.setId(Integer.parseInt((String) data.get("username")));
                userRegist.setName((String) data.get("name"));
                userRegist.setPassword((String) data.get("password"));
                userRegist.setIntroduction((String) data.get("introduction"));
                userRegist.setSecurityQuestion((String) data.get("securityQuestion"));
                userRegist.setClassifiedAnswer((String) data.get("classifiedAnswer"));
                userRegist.setHeadPortrait((byte[]) data.get("headPortrait"));
                try {
                    if (DAOFactory.getUserDAOInstance().doInsert(userRegist)) {
                        data = new HashMap<>();
                        data.put("username", String.valueOf(userRegist.getId()));
                        data.put("password", userRegist.getPassword());
                        sendParams.put("type", MsgType.REGISTRATION_SUCCESSFUL);
                        sendParams.put("data", data);
                        sendParams.put("message", "注册成功");
                        send(sendParams);
                        params.put("username", String.valueOf(userRegist.getId()));
                        params.put("message", "注册成功");
                        MainInterface.sendLog(params);
                        UserManage.requestData();
                    } else {
                        sendParams.put("type", MsgType.REGISTRATION_FAILED);
                        sendParams.put("message", "用户创建失败，请重试");
                        send(sendParams);
                    }
                } catch (Exception e) {
                    sendParams.put("type", MsgType.SERVER_ERROR);
                    sendParams.put("message", "未知错误，请稍后重试");
                    send(sendParams);
                }
            } else if (type == MsgType.GET_DATA_LIST) {
                MsgObjectList own = new MsgObjectList();
                own.setUsername(username);
                own.setName(user.getName());
                own.setHeadPortrait(user.getHeadPortrait());
                data = new HashMap<>();
                data.put("ownData", own);
                sendParams.put("type", MsgType.GET_DATA_LIST_SUCCESSFUL);
                sendParams.put("data", data);
                send(sendParams);
            } else if (type == MsgType.GET_ONLINE_LIST) {
                data = new HashMap<>();
                List<MsgObjectList> mols = new ArrayList<>();
                for (MyChannel loginChannel : Server.loginChannels) {
                    MsgObjectList mol = new MsgObjectList(loginChannel.username, loginChannel.user.getName(), loginChannel.user.getHeadPortrait(), false);
                    mols.add(mol);
                }
                data.put("list", mols);
                sendParams.put("type", MsgType.GET_ONLINE_LIST_SUCCESSFUL);
                sendParams.put("data", data);
                send(sendParams);
                sendOther(sendParams);
            } else if (type == MsgType.SEND_MESSAGE_PRIVATE) {
                data = (Map) receiveParams.get("data");
                String otherId = (String) data.get("otherId");
                data.put("otherId", username);
                sendParams.put("type", MsgType.SEND_MESSAGE_PRIVATE);
                sendParams.put("data", data);
                sendOther(sendParams, otherId);
            } else if (type == MsgType.SEND_MESSAGE_GROUP) {
                sendOther(receiveParams);
            } else if (type == MsgType.SELECT_USER_REQUEST) {
                data = (Map) receiveParams.get("data");
                User userSelected = null;
                String userId = (String) data.get("username");
                try {
                    userSelected = DAOFactory.getUserDAOInstance().doQueryById(Integer.parseInt(userId));
                } catch (Exception e) {
                    sendParams.put("type", MsgType.SERVER_ERROR);
                    sendParams.put("message", "未知错误，请稍后重试");
                    send(sendParams);
                }
                if (userSelected == null) {
                    sendParams.put("type", MsgType.SELECT_USER_FALIED);
                    sendParams.put("message", "您查找的用户不存在");
                    send(sendParams);
                } else {
                    MsgObjectList mol = new MsgObjectList(String.valueOf(userSelected.getId()), userSelected.getName(), userSelected.getHeadPortrait(), false);
                    data = new HashMap<>();
                    data.put("mol", mol);
                    sendParams.put("type", MsgType.SELECT_USER_SUCCESSFUL);
                    sendParams.put("data", data);
                    send(sendParams);
                }
            } else if (type == MsgType.DELETE_USER_REQUEST) {
                User deletaUser = new User();
                deletaUser.setId(Integer.parseInt(username));
                try {
                    DAOFactory.getUserDAOInstance().doDelete(deletaUser);
                } catch (Exception e) {
                    sendParams.put("type", MsgType.SERVER_ERROR);
                    sendParams.put("message", "未知错误，请稍后重试");
                    send(sendParams);
                }
                Server.loginChannels.remove(this);
                params.put("username", username);
                params.put("message", "删除账号");
                MainInterface.sendLog(params);
                sendParams.put("type", MsgType.LOGOUT_SUCCESSFUL);
                sendParams.put("message", "删除成功，即将退出登录");
                send(sendParams);
                username = null;
            } else if (type == MsgType.LOGOUT_REQUEST) {
                Server.loginChannels.remove(this);
                params.put("username", username);
                params.put("message", "退出登录");
                MainInterface.sendLog(params);
                sendParams.put("type", MsgType.LOGOUT_SUCCESSFUL);
                sendParams.put("message", "请重新登录");
                send(sendParams);
                username = null;
            } else if (type == MsgType.GET_USER_INFO_REQUEST) {
                data = new HashMap<>();
                data.put("name", user.getName());
                data.put("password", user.getPassword());
                data.put("introduction", user.getIntroduction());
                data.put("securityQuestion", user.getSecurityQuestion());
                data.put("classifiedAnswer", user.getClassifiedAnswer());
                data.put("headPortrait", user.getHeadPortrait());
                sendParams.put("type", MsgType.GET_USER_INFO_SUCCESSFUL);
                sendParams.put("data", data);
                send(sendParams);
            } else if (type == MsgType.UPDATE_USER_REQUEST) {
                data = (Map) receiveParams.get("data");
                String name = (String) data.get("name");
                String password = (String) data.get("password");
                String introduction = (String) data.get("introduction");
                String securityQuestion = (String) data.get("securityQuestion");
                String classifiedAnswer = (String) data.get("classifiedAnswer");
                byte[] headPortrait = (byte[]) data.get("headPortrait");
                User updateUser = new User(Integer.parseInt(username), name, password, introduction, securityQuestion, classifiedAnswer, headPortrait);
                try {
                    DAOFactory.getUserDAOInstance().doUpdate(updateUser);
                } catch (Exception e) {
                    sendParams.put("type", MsgType.SERVER_ERROR);
                    sendParams.put("message", "未知错误，请稍后重试");
                    send(sendParams);
                }
                MsgObjectList own = new MsgObjectList();
                own.setUsername(username);
                own.setName(user.getName());
                own.setHeadPortrait(user.getHeadPortrait());
                data = new HashMap<>();
                data.put("ownData", own);
                sendParams.put("type", MsgType.UPDATE_USER_SUCCESSFUL);
                sendParams.put("data", data);
                send(sendParams);
            }
        }
        Server.loginChannels.remove(this);
        Map params = new HashMap<>();
        if (username == null) {
            params.put("username", "未登录用户");
        } else {
            params.put("username", username);
        }
        params.put("message", "断开连接");
        MainInterface.sendLog(params);
    }
}
