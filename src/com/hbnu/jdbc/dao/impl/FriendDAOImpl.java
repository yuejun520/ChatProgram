package com.hbnu.jdbc.dao.impl;

import com.hbnu.jdbc.dao.IFriendDao;
import com.hbnu.jdbc.dbc.ConnectionManager;
import com.hbnu.jdbc.vo.Friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月02日 8:05
 */
public class FriendDAOImpl implements IFriendDao {
    @Override
    public boolean doInsert(Friend f) throws Exception {
        boolean flag = false;
        String sql = "insert into friend(mId,remark,fId,addDate) values (?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, f.getmId());
            pstmt.setString(2, f.getRemark());
            pstmt.setInt(3, f.getfId());
            pstmt.setString(4, f.getAddDate());
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
    public boolean doUpdate(Friend f) throws Exception {
        boolean flag = false;
        String sql = "update friend set remark=?,fid=? where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, f.getRemark());
            pstmt.setInt(2, f.getfId());
            pstmt.setInt(3, f.getId());
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
    public boolean doDelete(Friend f) throws Exception {
        boolean flag = false;
        String sql = "delete from friend where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, f.getId());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public List<Friend> doQuery() throws Exception {
        List<Friend> friends = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from friend";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int mId = rs.getInt("m_id");
                String remark = rs.getString("remark");
                int fId = rs.getInt("f_id");
                String addDate = rs.getString("add_date");

                Friend friend = new Friend(id, mId, remark, fId, addDate);
                friends.add(friend);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return friends;
    }

    @Override
    public Friend doQueryById(int id) throws Exception {
        Friend friend = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from friend where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int mId = rs.getInt("m_id");
                String remark = rs.getString("remark");
                int fId = rs.getInt("f_id");
                String addDate = rs.getString("add_date");

                friend = new Friend(id, mId, remark, fId, addDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return friend;
    }
}
