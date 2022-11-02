package com.hbnu.jdbc.dao.impl;

import com.hbnu.jdbc.dao.IGroupDao;
import com.hbnu.jdbc.dbc.ConnectionManager;
import com.hbnu.jdbc.vo.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月02日 11:25
 */
public class GroupDAOImpl implements IGroupDao {
    @Override
    public boolean doCreateMsg(Group g) throws Exception {
        boolean flag = false;
        String sql = "create table group_message_?(" +
                "  `id` int NOT NULL," +
                "  `spokesman_id` int NULL," +
                "  `message` varchar(255) NULL," +
                "  `pciture` mediumblob NULL," +
                "  `spakeingTime` datetime NULL," +
                "  PRIMARY KEY (`id`)," +
                "  FOREIGN KEY (`spokesman_id`) REFERENCES `chat_program`.`users` (`id`) ON DELETE NO ACTION)" +
                "DEFAULT CHARSET=utf8;";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, g.getId());
            pstmt.executeUpdate();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public boolean doInsert(Group g) throws Exception {
        boolean flag = false;
        String sql = "insert into group(name,head_portrait,introduction,create_date) values (?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, g.getName());
            pstmt.setBytes(2, g.getHeadPortrait());
            pstmt.setString(3, g.getIntroduction());
            pstmt.setString(4, g.getCreateDate());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(pstmt, conn);
        }

        return flag;
    }

    @Override
    public boolean doUpdate(Group g) throws Exception {
        boolean flag = false;
        String sql = "update group set name=?,head_portrait=?,introduction=?,create_date=? where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, g.getName());
            pstmt.setBytes(2, g.getHeadPortrait());
            pstmt.setString(3, g.getIntroduction());
            pstmt.setString(4, g.getCreateDate());
            pstmt.setInt(5, g.getId());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public boolean doDelete(Group g) throws Exception {
        boolean flag = false;
        String sql = "delete from group where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, g.getId());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public List<Group> doQuery() throws Exception {
        List<Group> groups = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from group";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte[] headPortraits = rs.getBytes("head_portrait");
                String introduction = rs.getString("introduction");
                String createDate = rs.getString("create_date");

                Group group = new Group(id, name, headPortraits, introduction, createDate);
                groups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return groups;
    }

    @Override
    public Group doQueryById(int id) throws Exception {
        Group group = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from `group` where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                byte[] headPortraits = rs.getBytes("head_portrait");
                String introduction = rs.getString("introduction");
                String createDate = rs.getString("create_date");

                group = new Group(id, name, headPortraits, introduction, createDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return group;
    }
}
