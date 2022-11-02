package com.hbnu.jdbc.factory;

import com.hbnu.jdbc.dao.*;
import com.hbnu.jdbc.dao.impl.*;

/**
 * @author luanhao
 * @date 2022年11月02日 10:33
 */
public class DAOFactory {
    public static IUserDao getUserDAOInstance() {
        return new UserDAOImpl();
    }

    public static IFriendDao getFriendDAOInstance() {
        return new FriendDAOImpl();
    }

    public static IGroupDao getGroupDAOInstance() {
        return new GroupDAOImpl();
    }

    public static IGroupMessageDao getGroupMessageDAOInstance() {
        return new GroupMessageDAOImpl();
    }

    public static IPrivateMessageDao getPrivateMessageDAOInstance() {
        return new PrivateMessageDAOImpl();
    }
}
