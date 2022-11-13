package com.hbnu.server.jdbc.util;

import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.vo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author luanhao
 * @date 2022年11月03日 13:31
 */
public class RandomUsername {
    public String getUsername() throws Exception {
//      获取数据库用户名
        List<User> users = DAOFactory.getUserDAOInstance().doQuery();
        Iterator<User> userIterator = users.iterator();
        List<String> usernames = new ArrayList<>();
        while (userIterator.hasNext()) {
            int id = userIterator.next().getId();
            usernames.add(String.valueOf(id));
        }
        Iterator<String> iterator = usernames.iterator();

//        随机用户名
        String username = "";
        Random random = new Random();
        int length = random.nextInt(9) + 1;
        do {
            for (int i = 0; i < length; i++) {
                username += String.valueOf(random.nextInt(10));
            }
        } while (iterator.hasNext() && username.equals(iterator.next()));

        return username;
    }
}
