package com.hbnu.jdbc.dao;

import com.hbnu.jdbc.vo.Group;

import java.util.List;

public interface IGroupDao {
    public boolean doCreateMsg(Group g) throws Exception;

    public boolean doInsert(Group g) throws Exception;

    public boolean doUpdate(Group g) throws Exception;

    public boolean doDelete(Group g) throws Exception;

    public List<Group> doQuery() throws Exception;

    public Group doQueryById(int id) throws Exception;
}
