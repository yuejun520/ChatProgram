package com.hbnu.server.jdbc.dao.impl;

import com.hbnu.server.jdbc.dao.IUserDao;
import com.hbnu.server.jdbc.dbc.ConnectionManager;
import com.hbnu.server.jdbc.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月02日 9:53
 */
public class UserDAOImpl implements IUserDao {
    @Override
    public boolean doInsert(User u) throws Exception {
        boolean flag = false;
        String sql = "insert into users(id,name,password,introduction," +
                "security_question,classified_answer,head_portrait)" +
                " values(?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u.getId());
            pstmt.setString(2, u.getName());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getIntroduction());
            pstmt.setString(5, u.getSecurityQuestion());
            pstmt.setString(6, u.getClassifiedAnswer());
            pstmt.setBytes(7, u.getHeadPortrait());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            flag = false;
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public boolean doUpdate(User u) throws Exception {
        boolean flag = false;
        String sql = "update users set name=?, password=?," +
                "introduction=?,security_question=?,classif" +
                "ied_answer=?,head_portrait=? where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getIntroduction());
            pstmt.setString(4, u.getSecurityQuestion());
            pstmt.setString(5, u.getClassifiedAnswer());
            pstmt.setBytes(6, u.getHeadPortrait());
            pstmt.setInt(7, u.getId());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            flag = false;
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public boolean doUpdateId(User u, int id) throws Exception {
        boolean flag = false;
        String sql = "update users set id=?, name=?, password=?," +
                "introduction=?,security_question=?,classif" +
                "ied_answer=?,head_portrait=? where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u.getId());
            pstmt.setString(2, u.getName());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getIntroduction());
            pstmt.setString(5, u.getSecurityQuestion());
            pstmt.setString(6, u.getClassifiedAnswer());
            pstmt.setBytes(7, u.getHeadPortrait());
            pstmt.setInt(8, id);
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            flag = false;
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }
    @Override
    public boolean doDelete(User u) throws Exception {
        boolean flag = true;
        String sql = "delete from users where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            flag = false;
        } finally {
            ConnectionManager.release(pstmt, conn);
        }
        return flag;
    }

    @Override
    public List<User> doQuery() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String introduction = rs.getString("introduction");
                String securityQuestion = rs.getString("security_question");
                String classifiedAnswer = rs.getString("classified_answer");
                byte[] headPortraits = rs.getBytes("head_portrait");
                User user = new User(id, name, password, introduction, securityQuestion, classifiedAnswer, headPortraits);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.release(rs, pstmt, conn);
        }
        return users;
    }

    @Override
    public User doQueryById(int id) throws Exception {
        User user = null;
        String sql = "select * from users where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        conn = ConnectionManager.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            String introduction = rs.getString("introduction");
            String securityQuestion = rs.getString("security_question");
            String classifiedAnswer = rs.getString("classified_answer");
            byte[] headPortraits = rs.getBytes("head_portrait");
            user = new User(id, name, password, introduction, securityQuestion, classifiedAnswer, headPortraits);
        }
        return user;
    }
}
