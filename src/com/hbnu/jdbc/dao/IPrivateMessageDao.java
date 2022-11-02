package com.hbnu.jdbc.dao;

import com.hbnu.jdbc.vo.PrivateMessage;

import java.util.List;

public interface IPrivateMessageDao {
    public boolean doInsert(PrivateMessage privateMessage) throws Exception;

    public List<PrivateMessage> doQuery() throws Exception;

    public PrivateMessage doQueryById(int id) throws Exception;
}
