package com.hbnu.jdbc.dao.impl;

import com.hbnu.jdbc.dao.IPrivateMessageDao;
import com.hbnu.jdbc.dbc.ConnectionManager;
import com.hbnu.jdbc.vo.PrivateMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月02日 12:51
 */
public class PrivateMessageDAOImpl implements IPrivateMessageDao {
    @Override
    public boolean doInsert(PrivateMessage privateMessage) throws Exception {
        boolean flag = false;
        String sql = "insert into private_message(friend_id,spokesman_id,message,picture,speaking_time) values (?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, privateMessage.getFriendId());
            pstmt.setInt(2, privateMessage.getSpokesmanId());
            pstmt.setString(3, privateMessage.getMessage());
            pstmt.setBytes(4, privateMessage.getPicture());
            pstmt.setString(5, privateMessage.getSpeakingTime());
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
    public List<PrivateMessage> doQuery() throws Exception {
        List<PrivateMessage> privateMessages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from private_message";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int friendId = rs.getInt("friend_id");
                int spokesmanId = rs.getInt("spokesman_id");
                String message = rs.getString("message");
                byte[] pictures = rs.getBytes("picture");
                String speakingTime = rs.getString("speaking_time");

                PrivateMessage privateMessage = new PrivateMessage(id, friendId, spokesmanId, message, pictures, speakingTime);
                privateMessages.add(privateMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return privateMessages;
    }

    @Override
    public PrivateMessage doQueryById(int id) throws Exception {
        PrivateMessage privateMessage = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from private_message where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int friendId = rs.getInt("friend_id");
                int spokesmanId = rs.getInt("spokesman_id");
                String message = rs.getString("message");
                byte[] pictures = rs.getBytes("picture");
                String speakingTime = rs.getString("speaking_time");

                privateMessage = new PrivateMessage(id, friendId, spokesmanId, message, pictures, speakingTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return privateMessage;
    }
}
