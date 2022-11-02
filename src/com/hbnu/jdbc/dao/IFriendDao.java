package com.hbnu.jdbc.dao;

import com.hbnu.jdbc.vo.Friend;

import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月01日 21:53
 */
public interface IFriendDao {
    public boolean doInsert(Friend f) throws Exception;

    public boolean doUpdate(Friend f) throws Exception;

    public boolean doDelete(Friend f) throws Exception;

    public List<Friend> doQuery() throws Exception;

    public Friend doQueryById(int id) throws Exception;
}
