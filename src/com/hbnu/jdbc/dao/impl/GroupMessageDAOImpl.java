package com.hbnu.jdbc.dao.impl;

import com.hbnu.jdbc.dao.IGroupMessageDao;
import com.hbnu.jdbc.dbc.ConnectionManager;
import com.hbnu.jdbc.vo.GroupMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月02日 13:13
 */
public class GroupMessageDAOImpl implements IGroupMessageDao {
    @Override
    public boolean doInsert(GroupMessage groupMessage) throws Exception {
        boolean flag = false;
        String sql = "insert into group_message_?(groupId,spokesman_id,message,picture,speaking_time) values (?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupMessage.getGroupId());
            pstmt.setInt(2, groupMessage.getGroupId());
            pstmt.setInt(3, groupMessage.getSpokesmanId());
            pstmt.setString(4, groupMessage.getMessage());
            pstmt.setBytes(5, groupMessage.getPicture());
            pstmt.setString(6, groupMessage.getSpeakingTime());
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
    public List<GroupMessage> doQuery(int groupId) throws Exception {
        List<GroupMessage> groupMessages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from group_message_?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int spokesmanId = rs.getInt("spokesman_id");
                String message = rs.getString("message");
                byte[] pictures = rs.getBytes("picture");
                String speakingTime = rs.getString("speaking_time");

                GroupMessage groupMessage = new GroupMessage(id, groupId, spokesmanId, message, pictures, speakingTime);
                groupMessages.add(groupMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return groupMessages;
    }

    @Override
    public GroupMessage doQueryById(int id, int groupId) throws Exception {
        GroupMessage groupMessage = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from group_message_? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int spokesmanId = rs.getInt("spokesman_id");
                String message = rs.getString("message");
                byte[] pictures = rs.getBytes("picture");
                String speakingTime = rs.getString("speaking_time");

                groupMessage = new GroupMessage(id, groupId, spokesmanId, message, pictures, speakingTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return groupMessage;
    }
}
