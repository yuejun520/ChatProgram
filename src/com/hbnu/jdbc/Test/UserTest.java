package com.hbnu.jdbc.Test;

import com.hbnu.jdbc.vo.User;
import com.hbnu.jdbc.factory.DAOFactory;

/**
 * @author luanhao
 * @date 2022年11月02日 10:35
 */
public class UserTest {
    public static void main(String[] args) throws Exception {
        User user = DAOFactory.getUserDAOInstance().doQueryById(0);
        System.out.println(user);
    }
}
