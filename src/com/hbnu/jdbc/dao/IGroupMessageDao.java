package com.hbnu.jdbc.dao;

import com.hbnu.jdbc.vo.GroupMessage;

import java.util.List;

public interface IGroupMessageDao {
    public boolean doInsert(GroupMessage groupMessage) throws Exception;

    public List<GroupMessage> doQuery(int groupId) throws Exception;

    public GroupMessage doQueryById(int id, int groupId) throws Exception;
}
