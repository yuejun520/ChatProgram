package com.hbnu.server.jdbc.factory;

import com.hbnu.server.jdbc.dao.*;
import com.hbnu.server.jdbc.dao.impl.*;

/**
 * @author luanhao
 * @date 2022年11月02日 10:33
 */
public class DAOFactory {
    public static IUserDao getUserDAOInstance() {
        return new UserDAOImpl();
    }
}
