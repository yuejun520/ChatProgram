package com.hbnu.server.jdbc.dao;

import com.hbnu.server.jdbc.vo.User;

import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月01日 21:52
 */
public interface IUserDao {
    public boolean doInsert(User u) throws Exception;

    public boolean doUpdate(User u) throws Exception;

    public boolean doUpdateId(User u, int id) throws Exception;

    public boolean doDelete(User u) throws Exception;

    public List<User> doQuery() throws Exception;

    public User doQueryById(int id) throws Exception;
}
